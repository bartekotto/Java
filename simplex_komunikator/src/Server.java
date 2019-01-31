import java.io.*;
import java.net.*;

public class Server
{
    public static final int PORT=50007;

    public static void main(String args[]) throws IOException
    {
        //tworzenie gniazda serwerowego
        ServerSocket serv;
        serv=new ServerSocket(PORT);

        //oczekiwanie na polaczenie i tworzenie gniazda sieciowego
        System.out.println("Nasluchuje: "+serv);
        Socket sock;
        sock=serv.accept();
        System.out.println("Jest polaczenie: "+sock);

        //tworzenie strumienia danych pobieranych z gniazda sieciowego
        BufferedReader inp;
        inp=new BufferedReader(new InputStreamReader(sock.getInputStream()));
        BufferedReader klaw;
        klaw=new BufferedReader(new InputStreamReader(System.in));
        PrintWriter outp;
        outp=new PrintWriter(sock.getOutputStream());

        //komunikacja - czytanie danych ze strumienia
        String str;
        str=inp.readLine();
        while (!str.equals("exit")){

            System.out.println("<Nadeszlo:> " + str);
            System.out.print("<Wysylamy:> ");
            str=klaw.readLine();
            outp.println(str);
            outp.flush();
            if (str.equals("exit")){
                break;
            }
            str = inp.readLine();
        }


        //zamykanie polaczenia
        inp.close();
        sock.close();
        serv.close();
    }
}