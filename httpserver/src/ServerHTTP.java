import java.io.*;
import java.net.*;

public class ServerHTTP {
    public static void main(String[] args) throws IOException {
        ServerSocket serv = new ServerSocket(80);

        while (true) {
            //przyjecie polaczenia
            System.out.println("Oczekiwanie na polaczenie...");
            Socket sock = serv.accept();

            //strumienie danych
            InputStream is = sock.getInputStream();
            OutputStream os = sock.getOutputStream();
            BufferedReader inp = new BufferedReader(new InputStreamReader(is));
            DataOutputStream outp = new DataOutputStream(os);

            //przyjecie zadania (request)
            String request = inp.readLine();

            //wyslanie odpowiedzi (response)
            if (request != null) {
                if (request.startsWith("GET")) {
                    String output_req = request;
                    String print_req = "";
                    String req_file = output_req.substring(output_req.indexOf("/") + 1, output_req.lastIndexOf(" "));
                    while (output_req.length() != 0) {
                        print_req += output_req + "\n";
                        output_req = inp.readLine();


                    }
                    System.out.println(print_req);
                    if (!req_file.isEmpty()) {
                        System.out.println("this file is requested: " + req_file);
                    } else {
                        System.out.println("no file is requested");
                    }


                    //response header
                    outp.writeBytes("HTTP/1.0 200 OK\r\n");
                    outp.writeBytes("Content-Type: text/html\r\n");
                    outp.writeBytes("Content-Length: \r\n");
                    outp.writeBytes("\r\n");

                    //response body
                    try {
                        FileInputStream R_file = new FileInputStream(req_file);

                        int dlugoscNazwy = req_file.length() - 1;
                        String rozszerzenie = "";
                        for(int i=0 ; i<4; i++){
                            rozszerzenie = req_file.charAt(dlugoscNazwy) + rozszerzenie;
                            dlugoscNazwy = dlugoscNazwy - 1;
                        }
                        byte[] bufor;
                        bufor=new byte[1024];
                        int n=0;

                        while ((n = R_file.read(bufor)) != -1 )
                        {
                            outp.write(bufor, 0, n);
//                            outp.writeBytes("</br>");
                        }
                        outp.writeBytes("</html>\r\n");
                    } catch (FileNotFoundException f) {
                        outp.writeBytes("HTTP/1.0 404 Not Found\r\n");
                    }

                } else {
                    outp.writeBytes("HTTP/1.1 501 Not supported.\r\n");
                }
            }


            //zamykanie strumieni
            inp.close();
            outp.close();
            sock.close();
        }
    }
}