package sample;

public class Response {
    private String protocol;
    private String status;
    private int statusCode;
    private String headers;
    private String body;
    private String response;
//    private String charset;

    public Response(String response){
        //убрать лишние переносы строки
        this.response = response;
        int start = response.indexOf(" ");
        this.protocol = response.substring(0, start);
        int end = response.indexOf("\n");
        this.status = response.substring(start+1, end);
        this.statusCode = Integer.parseInt(this.status.substring(0, 3));
        start = end + 1;
        end = response.indexOf("\r\n\r\n");
        this.headers = response.substring(start, end);
        this.body = response.substring(end + 4, response.length());
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

    public String getBody() {
        return this.body;
    }

    public String getProtocol() {
        return protocol;
    }
}
