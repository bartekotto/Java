import java.io.*;
import java.net.*;

public class Client
{
    public static final int PORT=50007;
    public static final String HOST = "127.0.0.1";

    public static void main(String[] args) throws IOException
    {
        try{
            Socket sock;
            try{sock=new Socket(HOST,PORT);}
            catch (ConnectException e) {
                System.out.println("Server is not reachable");
                return;
            }

            BufferedReader kbr;
            kbr=new BufferedReader(new InputStreamReader(System.in));
            PrintWriter OPUT;
            OPUT=new PrintWriter(sock.getOutputStream());
            BufferedReader input;
            input=new BufferedReader(new InputStreamReader(sock.getInputStream()));

            while (true){
                System.out.println("connection established: "+sock);
                System.out.print("<Sending:> ");
                String str=kbr.readLine();
                OPUT.println(str);
                OPUT.flush();
                if (str.equals("exit")){
                    break;
                }
                str=input.readLine();
                System.out.println("<Incoming:> " + str);
                if (input.equals("exit")){
                    break;
                }
            }
            //zamykanie polaczenia
            kbr.close();
            OPUT.close();
            sock.close();
        }catch(SocketException e){
            System.out.println("Connection is Off");
        }
    }
}