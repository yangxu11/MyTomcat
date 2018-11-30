package ex01;

import java.io.*;
import java.net.ServerSocket;

public class Response {
    private OutputStream out;
    private Request request;
    public Response(OutputStream out,Request request) {
        this.out = out;
        this.request = request;
    }

    public void sendStaticResource() throws IOException {

        BufferedReader br = null;
        PrintWriter writer = null;


        try {
            //System.out.println(HttpServer.WEB_ROOT + request.getUri());
            File file = new File(HttpServer.WEB_ROOT,request.getUri());
            if (file.exists()) {

                br = new BufferedReader(new FileReader(file));
                writer = new PrintWriter(out);
                String s;
                StringBuffer sb = new StringBuffer();
                sb.append("http/1.1 200 ok\n" +"\n\n");//一定一定要加上HTTP的头，否则收不到response
                while((s = br.readLine()) != null) {
                    sb.append(s+"\n");
                    //writer.println(s);
                }

                writer.println(sb.toString());
                System.out.println(sb.toString());
            } else {
                String errorMessage = "HTTP/1.1 404 File Not Found\r\n" +
                        "Content-Type: text/html\r\n" +
                        "Content-Length: 23\r\n" +
                        "\r\n" +
                        "<h1>File Not Found</h1>";
                out.write(errorMessage.getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            writer.close();
            br.close();
        }
    }
}
