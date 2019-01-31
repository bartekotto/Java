import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;

public class Kalk implements ActionListener {
    JTextField t1;
    JButton b1, b2, b3, b4, b5, b6, b7, b8, b9, b0, bpoint;
    JButton bplus, brow, bminus, bpower;
    JButton bmul, bdiv, broot, bclear, bundo;
    String operation_buf;

    double x, buf;


    public void bw2(String str)
            throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("memory.txt", true));
//        writer.append('\n');
        writer.append(str);

        writer.close();
    }

    public void actionPerformed(ActionEvent e) {
        Object target = e.getSource();
        if (target == b1 || target == b2 || target == b3 || target == b4 || target == b5 || target == b6 || target == b7 || target == b8 || target == b9) {
            t1.setText(t1.getText() + ((JButton) target).getText());
            t1.requestFocus();
        } else if (target == b0) {
            if (t1.getText().equals("")) {
                t1.setText("0.");
                t1.requestFocus();
            } else {
                t1.setText(t1.getText() + ((JButton) target).getText());
                t1.requestFocus();
            }

        } else if (target == bplus) {
            buf = Double.parseDouble(t1.getText());
            t1.setText("");
            t1.requestFocus();
            operation_buf = "+";
        } else if (target == bundo) {
            t1.setText(Double.toString(buf));
            buf = 0;
        } else if (target == bminus) {
            buf = Double.parseDouble(t1.getText());
            t1.setText("");
            t1.requestFocus();
            operation_buf = "-";
        } else if (target == bpower) {
            buf = Double.parseDouble(t1.getText());
            t1.setText("");
            t1.requestFocus();
            operation_buf = "^";
        } else if (target == bpoint) {
            if (t1.getText().equals("")) {
                t1.setText("0.");
                t1.requestFocus();
            } else {
                if (t1.getText().indexOf('.') != -1) {
                    t1.requestFocus();
                } else {
                    t1.setText(t1.getText() + ((JButton) target).getText());
                    t1.requestFocus();
                }
            }
        } else if (target == bclear) {
            x = 0.0;
            buf = 0.0;
            t1.setText("");
            t1.requestFocus();
        } else if (target == bdiv) {
            buf = Double.parseDouble(t1.getText());
            t1.setText("");
            t1.requestFocus();
            operation_buf = "/";
        } else if (target == bmul) {
            buf = Double.parseDouble(t1.getText());
            t1.setText("");
            t1.requestFocus();
            operation_buf = "*";
        } else if (target == broot) {
            x = Double.parseDouble(t1.getText());
            x = Math.sqrt(x);
            t1.setText(Double.toString(x));
            t1.requestFocus();
        } else if (target == brow || target == t1) {
            x = Double.parseDouble(t1.getText());
            double mem_x = x;
            if (operation_buf.equals("-")) {
                x = buf - x;
            } else if (operation_buf.equals("+")) {
                x = buf + x;
            } else if (operation_buf.equals("*")) {
                x = buf * x;
            } else if (operation_buf.equals("^")) {
                x = Math.pow(buf, x);
            } else if (operation_buf.equals("/")) {
                if (x == 0) {
                    throw new IllegalArgumentException("Argument 'divisor' is 0");
                } else {
                    x = buf / x;
                }

            }
            String str = (buf + operation_buf + mem_x + "=" + x + "\n");
            try {
                bw2(str);
            } catch (java.io.IOException i) {

            }
            t1.setText(Double.toString(x));
            t1.requestFocus();
        }
    }

    void init() {

        JFrame f = new JFrame();
        Container c = f.getContentPane();

        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        c.setLayout(gbl);


        t1 = new JTextField(15);
        t1.setEditable(false);
        t1.addActionListener(this);
        t1.setHorizontalAlignment(JTextField.RIGHT);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 5;
        gbc.ipadx = 0;
        gbc.ipady = 5;
        gbc.insets = new Insets(5, 5, 0, 5);
        gbl.setConstraints(t1, gbc);
        c.add(t1);



        b1 = new JButton("1");
        b1.addActionListener(this);
        b1.setFocusable(false);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.insets = new Insets(5, 5, 0, 0);
        gbl.setConstraints(b1, gbc);
        c.add(b1);

        b2 = new JButton("2");
        b2.addActionListener(this);
        b2.setFocusable(false);
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.insets = new Insets(5, 5, 0, 0);
        gbl.setConstraints(b2, gbc);
        c.add(b2);

        b3 = new JButton("3");
        b3.addActionListener(this);
        b3.setFocusable(false);
        gbc.gridx = 3;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.insets = new Insets(5, 5, 0, 0);
        gbl.setConstraints(b3, gbc);
        c.add(b3);

        b4 = new JButton("4");
        b4.addActionListener(this);
        b4.setFocusable(false);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.insets = new Insets(5, 5, 0, 0);
        gbl.setConstraints(b4, gbc);
        c.add(b4);

        b5 = new JButton("5");
        b5.addActionListener(this);
        b5.setFocusable(false);
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.insets = new Insets(5, 5, 0, 0);
        gbl.setConstraints(b5, gbc);
        c.add(b5);

        b6 = new JButton("6");
        b6.addActionListener(this);
        b6.setFocusable(false);
        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.insets = new Insets(5, 5, 0, 0);
        gbl.setConstraints(b6, gbc);
        c.add(b6);

        b7 = new JButton("7");
        b7.addActionListener(this);
        b7.setFocusable(false);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.insets = new Insets(5, 5, 0, 0);
        gbl.setConstraints(b7, gbc);
        c.add(b7);

        b8 = new JButton("8");
        b8.addActionListener(this);
        b8.setFocusable(false);
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.insets = new Insets(5, 5, 0, 0);
        gbl.setConstraints(b8, gbc);
        c.add(b8);

        b9 = new JButton("9");
        b9.addActionListener(this);
        b9.setFocusable(false);
        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.insets = new Insets(5, 5, 0, 0);
        gbl.setConstraints(b9, gbc);
        c.add(b9);

        b0 = new JButton("0");
        b0.addActionListener(this);
        b0.setFocusable(false);
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.insets = new Insets(5, 5, 0, 0);
        gbl.setConstraints(b0, gbc);
        c.add(b0);


        bplus = new JButton("+");
        bplus.addActionListener(this);
        bplus.setFocusable(false);
        bplus.setToolTipText("dodawanie");
        gbc.gridx = 4;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.ipadx = 30;
        gbc.ipady = 0;
        gbc.insets = new Insets(5, 5, 0, 0);
        gbl.setConstraints(bplus, gbc);
        c.add(bplus);

        bminus = new JButton("-");
        bminus.addActionListener(this);
        bminus.setFocusable(false);
        bminus.setToolTipText("odejmowanie");
        gbc.gridx = 4;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.ipadx = 30;
        gbc.ipady = 0;
        gbc.insets = new Insets(5, 5, 0, 0);
        gbl.setConstraints(bminus, gbc);
        c.add(bminus);

        bclear = new JButton("C");
        bclear.addActionListener(this);
        bclear.setFocusable(false);
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.insets = new Insets(5, 5, 5, 0);
        gbl.setConstraints(bclear, gbc);
        c.add(bclear);

        bpower = new JButton("^");
        bpower.addActionListener(this);
        bpower.setFocusable(false);
        bpower.setToolTipText("potegowanie");
        gbc.gridx = 3;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        gbc.ipadx = 30;
        gbc.ipady = 0;
        gbc.insets = new Insets(5, 5, 0, 0);
        gbl.setConstraints(bpower, gbc);
        c.add(bpower);

        bmul = new JButton("*");
        bmul.addActionListener(this);
        bmul.setFocusable(false);
        bmul.setToolTipText("potegowanie");
        gbc.gridx = 4;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.ipadx = 30;
        gbc.ipady = 0;
        gbc.insets = new Insets(5, 5, 0, 0);
        gbl.setConstraints(bmul, gbc);
        c.add(bmul);

        bdiv = new JButton("/");
        bdiv.addActionListener(this);
        bdiv.setFocusable(false);
        bdiv.setToolTipText("potegowanie");
        gbc.gridx = 4;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.ipadx = 30;
        gbc.ipady = 0;
        gbc.insets = new Insets(5, 5, 0, 0);
        gbl.setConstraints(bdiv, gbc);
        c.add(bdiv);

        brow = new JButton("=");
        brow.addActionListener(this);
        brow.setFocusable(false);
        brow.setToolTipText("wykonaj działanie");
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 4;
        gbc.ipadx = 30;
        gbc.ipady = 0;
        gbc.insets = new Insets(5, 5, 5, 0);
        gbl.setConstraints(brow, gbc);
        c.add(brow);

        bpoint = new JButton(".");
        bpoint.addActionListener(this);
        bpoint.setFocusable(false);
        gbc.gridx = 3;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.insets = new Insets(5, 5, 5, 0);
        gbl.setConstraints(bpoint, gbc);
        c.add(bpoint);

        broot = new JButton("√");
        broot.addActionListener(this);
        broot.setFocusable(false);
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.insets = new Insets(5, 5, 5, 0);
        gbl.setConstraints(broot, gbc);
        c.add(broot);

        bundo = new JButton("Undo");
        bundo.addActionListener(this);
        bundo.setFocusable(false);
        gbc.gridx = 2;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        gbc.ipadx = 0;
        gbc.ipady = 0;
        gbc.insets = new Insets(5, 5, 5, 0);
        gbl.setConstraints(bundo, gbc);
        c.add(bundo);


        f.pack();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setTitle("Kalk");
        f.setVisible(true);
    }

    public static void main(String[] args) {
        //do wersji 1.4
        //new Kalk().init();

        //od wersji 1.5
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Kalk().init();
            }
        });
    }
}