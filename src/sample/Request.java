package sample;

public class Request {
    public String uri;
    private String host;
    private int port;
    private String method;
    private String protocol;
    private String headers;
    private String body;

    public Request(String host, int port, String method, String uri, String protocol, String headers, String body) {
        if (port < 0)
            this.port = 80;
        else this.port = port;
        this.host = host;
        this.method = method;
        this.uri = uri;
        this.protocol = protocol;
        this.headers = headers;
        if (this.method.equals("POST")) {
            this.body = body;
        } else this.body = "";
    }

    public String getRequestAsString() {
        String request = this.method + " " + this.uri + " " + this.protocol + "\n" + this.headers + "\n\n" + this.body + "\n\n";
        return request;
    }

    public String getHost() {
        return this.host;
    }

    public String getMethod() {
        return this.method;
    }

//    public String getURI() {
//        return this.uri;
//    }

    public String getProtocol() {
        return this.protocol;
    }

    public String getHeaders() {
        return this.headers;
    }

    public String getBody() {
        return this.body;
    }

    public int getPort() {
        return this.port;
    }
}
