import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Serwer {
    public static List<Socket> clientList = new ArrayList<Socket>();
    Socket sock = null;

    public static void main(String args[]) throws IOException {
        ServerSocket serv = new ServerSocket(80);

        while (true) {
            System.out.println("Waiting for connection...");
            Socket sock = serv.accept();
            new TaskHandler(sock).start();
            clientList.add(sock);
        }
    }
}

class Sender extends Thread {
    String message = null;
    String exitOrder = null;
    Socket sock = null;

    PrintWriter output = null;

    Sender(String message, String exitOrder) {
        this.message = message;
        this.exitOrder = exitOrder;
    }

    public void run() {
        try {
            for (int i = 0; i < Serwer.clientList.size(); i++) {
                output = new PrintWriter(Serwer.clientList.get(i).getOutputStream());
                output.println(this.message);
                output.flush();
            }
        } catch (IOException e) {
        }
    }
}


class TaskHandler extends Thread {
    Socket sock = null;
    BufferedReader reader = null;
    String message = null;
    Serwer server = null;


    TaskHandler(Socket clientSocket) {
        try {
            this.sock = clientSocket;
            this.reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {

        try {

            while (true) {
                message = this.reader.readLine();
                System.out.println("<Sender: " + this.sock.getPort() + "> says: " + message);
                String responce = "<Client: " + this.sock.getPort() + "> says: " + message;
                new Sender(responce, message).start();
                if (message.equalsIgnoreCase("exit")) {
                    System.out.println("Connection with " + this.sock.getPort() + " terminated");
                    this.reader.close();
                    break;
                }
            }


        } catch (IOException e) {
            System.out.println("Client " + this.sock.getPort() + " disconnected improperly (was supposed to send 'exit')");
        }

    }

}
