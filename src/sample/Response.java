package sample;

import java.nio.charset.Charset;

public class Response {
    private String protocol;
    private String status;
    private int statusCode;
    private String headers;
    private String body;
    private String response;
    private String comment;
    private String charset;

    public Response(String response){
        this.response = response;
        int start = response.indexOf(" ");
        this.protocol = response.substring(0, start);
        int end = response.indexOf("\n");
        this.status = response.substring(start+1, end);
        this.statusCode = Integer.parseInt(this.status.substring(0, 3));
        start = end + 1;
        end = response.indexOf("\r\n\r\n");
        this.headers = response.substring(start, end)+ "\n";
        this.body = response.substring(end + 4, response.length());
        comment = "";
        if (this.headers.contains("charset=")){
            start = this.headers.indexOf("charset=");
            end = this.headers.indexOf("\n", start);
            this.charset = this.headers.substring(start+8, end);
//            if (Charset.forName(this.charset).equals(Charset.forName("CP1251"))){
//                return;
//            }
//            try {
//                String newBody = new String(this.body.getBytes(Charset.forName("CP1251")), Charset.forName(this.charset));
//                this.body = newBody;
//            }
//            catch (Exception e) {
//                e.printStackTrace();
//            }
        }
    }

    public String getResponseAsString(){
        return this.response;
    }
    public String getStatus(){
        return this.status;
    }

    public int getStatusCode(){
        return this.statusCode;
    }

    public String getHeaders(){
        return this.headers;
    }

    public String getComment(){
        return this.comment;
    }

    public void setComment(String com){
        this.comment = com;
    }

    public String getBody() {
        return this.body;
    }

    public String getProtocol() {
        return protocol;
    }
}
