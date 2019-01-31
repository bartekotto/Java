import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

class BallEngine extends Thread {
    ball a;
    Boolean isPaused = false;

    BallEngine(ball a) {
        this.a = a;
        start();

    }

    public void run() {
        try {
            while (!a.hasEnded) {
                if (isPaused) {
                    sleep(15);
                } else {
                    a.nextKrok();
                    sleep(15);
                }
            }
        } catch (InterruptedException e) {
        }
    }


}

class ball extends Ellipse2D.Float {
    board p;
    double dx, dy;
    log b;
    ArrayList<brick> brickArrayList;
    Boolean hasEnded = false;
    Integer frames = 0;


    ball(board p, int x, int y, int dx, int dy, log b, ArrayList<brick> brickArrayList) {
        this.x = x;
        this.y = y;
        this.width = 10;
        this.height = 10;

        this.p = p;
        this.dx = dx;
        this.dy = dy;

        this.b = b;
        this.brickArrayList = brickArrayList;
    }

    void nextKrok() {
        x += dx;
        y += dy;

        if (getMaxY() > p.getHeight() && frames > 40) this.hasEnded = true;
        if (getMinX() < 0 || getMaxX() > p.getWidth()) dx = -dx;
        if (getMinY() < 0) dy = -dy;

        if (getMaxY() >= this.b.getMinY() && (this.getX() == this.b.getMinX() || this.getX() == this.b.getMaxX())) {
            dx = -dx;
            dy = -dy;
        } else if (getMaxY() >= this.b.getMinY() && this.getX() > this.b.getMinX() && this.getX() < this.b.getMaxX()) {
            double scale = getCenterX() - this.b.getCenterX();
            double alpha = (getCenterX() - b.getCenterX()) / (width / 2);
            double w_x = Math.sqrt(dx*dx + dy*dy);
            dx = Math.sin(alpha)*w_x;
            dy = -Math.cos(alpha)*w_x;
            dx = (scale * this.b.width) / 1000;
            dy = -dy;
        }


        for (int i = 0; i < this.brickArrayList.size(); i++) {
            brick c = this.brickArrayList.get(i);


            if ((y < c.getMaxY() && y > c.getMinY()) && x > c.getMinX() && x < c.getMaxX()) {
                c.x = -100;
                c.y = -100;
                dy = -dy;
            } else if (y < c.getMaxY() && y > c.getMinY() && (x == c.getMinX() || x == c.getMaxX())) {
                c.x = -100;
                c.y = -100;
                dx = -dx;
            } else if (c.intersects(this.x, this.y, this.width, this.height)) {
                c.x = -100;
                c.y = -100;
                dx = -dx;
                dy = -dy;
            }
        }

        if (frames < 50) {
            frames++;
        }
        p.repaint();
    }
}

class log extends Rectangle2D.Float {
    log(int x) {
        this.x = x;
        this.y = 300;
        this.width = 60;
        this.height = 10;
    }

    void setX(int x) {
        this.x = x;
    }
}

class brick extends Rectangle2D.Float {
    brick(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = 60;
        this.height = 10;
    }
}

class board extends JPanel implements MouseMotionListener, KeyListener {
    log b;
    ball a;
    ArrayList<brick> bricks = new ArrayList<brick>();
    BallEngine s;
    BufferedImage image;

    board() {
        super();
        addMouseMotionListener(this);
        addKeyListener(this);
        setFocusable(true);
        b = new log(100);

        int x = 30;
        int y = 10;
        for (int i = 0; i < 16; i++) {
            if (i % 4 == 0) {
                y += 30;
                x = 30;
            }
//            System.out.println(x);
            brick c = new brick(x, y);
            bricks.add(c);
            x += 85;

        }
        a = new ball(this, 30, 200, 1, 1, b, bricks);
        s = new BallEngine(a);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        if (!a.hasEnded || bricks.isEmpty()) {
            g2d.fill(b);
            g2d.fill(a);
            for (int i = 0; i < 16; i++) {

                g2d.fill(this.bricks.get(i));

            }
        } else {
            g2d.setColor(new Color(1, 0, 0));
            g2d.setFont(new Font("TimesNewRoman", Font.PLAIN, 40));
            g2d.drawString("The End", 120, 70);
            g2d.drawString("Your score:", 40, 150);
            g2d.drawString(policzPunkty().toString() + " / 16", 120, 230);

        }

    }

    private Integer policzPunkty() {
        int punkty = 0;
        for (int i = 0; i < bricks.size(); i++) {
            if (bricks.get(i).x == -100) {
                punkty += 1;
            }
        }
        return punkty;
    }

    public void mouseMoved(MouseEvent e) {
        b.setX(e.getX() - 30);
        repaint();
    }

    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            s.isPaused = !s.isPaused;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}


public class arkanoid {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            board p;
            p = new board();

            JFrame jf = new JFrame();
            jf.add(p);

            jf.setTitle("Test grafiki");
            jf.setSize(400, 370);
            jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            jf.setVisible(true);
        });
    }
}