package sample;

import java.io.*;
import java.net.*;

public class HTTPClient {
    public static void main(String args[]) {
    }

    public Response SendRequest(Request request) {
        StringBuilder sb = new StringBuilder(8096);
        try {

            Socket s = new Socket(request.getHost(), request.getPort());

            s.getOutputStream().write(request.getRequestAsString().getBytes());

            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream(),"CP1251"));
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
        Response response = new Response(sb.toString());
        if (response.getStatusCode()%300 < 100 && response.getHeaders().contains("Location:")){
            Request newRequest = request;
            int start = response.getHeaders().indexOf("Location:");
            start += 10;
            int end = response.getHeaders().indexOf("\n", start);
            String newURI = response.getHeaders().substring(start, end);
            newRequest.uri = newURI;
            response = SendRequest(newRequest);
        }

        return response;
    }
}
