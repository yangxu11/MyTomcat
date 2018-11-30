package ex01;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.TimeUnit;


//一定一定要加上HTTP的头，否则收不到response
//"http/1.1 200 ok\n" +"\n\n"
//"\n\n"也很重要，不加显示不出信息

public class HttpServer {
//system.getProperty("user.dir")获取当前路径
    public static final String WEB_ROOT = System.getProperty("user.dir") + File.separator + "webroot";
    public static final String SHUTDOWN_COMMAND = "/showdown";
    private boolean shutdown = false;

    public static void main(String[] args) throws Exception {
        HttpServer server = new HttpServer();
        server.await();
    }

    private void await() {
        ServerSocket serverSocket=null;
        try {
            serverSocket = new ServerSocket(8080);
        } catch (IOException e) {
            System.out.println("fail to start serverSocket");
            e.printStackTrace();
            System.exit(1);
        }
        while(!shutdown) {
            Socket socket = null;
            InputStream in = null;
            OutputStream out = null;

            try {
                socket = serverSocket.accept();
                in = socket.getInputStream();
                out = socket.getOutputStream();

//                String html = "http/1.1 200 ok\n"
//                        +"\n\n"
//                        +"1234服务端。。。。";
//                PrintWriter pw = new PrintWriter(socket.getOutputStream());
//                pw.println(html);
//                pw.close();


            Request request = new Request(in);
            request.parse();

            Response response = new Response(out,request);
            response.sendStaticResource();

            socket.close();

            shutdown = request.getUri().equals(SHUTDOWN_COMMAND);
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }
    }


}
