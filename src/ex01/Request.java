package ex01;

import java.io.IOException;
import java.io.InputStream;

public class Request {
    private InputStream in;
    private String Uri;
    public Request(InputStream in) {
        this.in = in;
    }

    public void parse() {
        StringBuffer request = new StringBuffer(2048);
        int i=-1;
        byte[] buffer = new byte[2048];
        try {
            i=in.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
            i=-1;
        }
        for(int j=0 ; j<i ; j++) {
            request.append((char)buffer[j]);
        }
        System.out.println(request.toString());
        Uri = parseUri(request.toString());

    }

    private String parseUri(String request) {
        int index1,index2;
        index1 = request.indexOf(' ');
        if(index1!=-1) {
            index2 = request.indexOf(' ',index1+1);
            if(index2 > index1) {
                return request.substring(index1+1,index2);
            }
        }
        return null;
    }

    public String getUri() {
        return Uri;
    }
}
