package sample;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class HTTPClient {
    public static void main(String args[]) {
    }

    public Response SendRequest(Request request) {
        StringBuilder sb = new StringBuilder(8096);
        try {

//            String charset = getCharset(request);
//            System.out.println(charset);
//            if (charset.equals("windows-1251")){
//                charset = "Cp1251";
//                System.out.println(charset);
//            }
            Socket s = new Socket(request.getHost(), request.getPort());

            s.getOutputStream().write(request.getRequestAsString().getBytes());

            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream(),"UTF-8"));
            boolean loop = true;
            while (loop) {
                if (in.ready()) {
                    int i = 0;
                    while (i != -1) {
                        i = in.read();
                        sb.append((char) i);
                    }
                    loop = false;
                }
            }
            s.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
//        String str = sb.toString();
//        if (str.contains("charset=")){
//            int start = str.indexOf("charset=");
//            int end = str.indexOf("\n", start);
//            String charset = str.substring(start+8, end);

//        }
        Response response = new Response(sb.toString());
        if (response.getStatusCode()%300 < 100 && response.getHeaders().contains("Location:")){
            Request newRequest = request;
            int start = response.getHeaders().indexOf("Location:");
            start += 10;
            int end = response.getHeaders().indexOf("\n", start);
            String newURI = response.getHeaders().substring(start, end);
            newRequest.uri = newURI;
            response = SendRequest(newRequest);
            response.setComment("Request was redirected");
        }

        return response;
    }

    private String getCharset(Request request){
        StringBuilder sb = new StringBuilder(8096);
        try {

            Socket s = new Socket(request.getHost(), request.getPort());

            Request headReq = new Request(request.getHost(), request.getPort(), "HEAD", request.uri, request.getProtocol(), request.getHeaders(), "");


            s.getOutputStream().write(headReq.getRequestAsString().getBytes());

            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream(),"UTF-8"));
            boolean loop = true;
            while (loop) {
                if (in.ready()) {
                    int i = 0;
                    while (i != -1) {
                        i = in.read();
                        sb.append((char) i);
                    }
                    loop = false;
                }
            }
            s.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        String str = sb.toString();
        if (str.contains("charset=")){
            int start = str.indexOf("charset=");
            int end = str.indexOf("\n", start);
            String charset = str.substring(start+8, end);
            return charset;
//            System.out.println(charset);
        }

        return null;
    }
}
