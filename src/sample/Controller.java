package sample;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class Controller {
    public TextField uri;
    public TextField host;
    public TextField port;
    public RadioButton rbtnGET;
    public RadioButton rbtnPOST;
    public RadioButton rbtnHEAD;
    public VBox boxBody;
    public VBox boxHeader;
    public TextArea areaBody;
    public TextArea areaHeader;
    public TextArea areaHistory;
    public TextArea areaBodyResp;
    public TextArea areaHeaderResp;
    public Label lblStatus;
    public Label lblProtocol;

    public String method;
    private HTTPClient client;

    @FXML
    public void initialize() {
        rbtnGET.setSelected(true);
        method = "GET";
        boxBody.setDisable(true);
        uri.setText("http://localhost:80/denwer/index.html");
        areaHeader.setText("User-Agent:HTTPClient\n\n");
        boxHeader.setDisable(false);
        client = new HTTPClient();

    }

    public void connectServer(ActionEvent actionEvent) {
    }

    public void selectMethod(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(rbtnGET)) {
            boxBody.setDisable(true);
            boxHeader.setDisable(false);
            method = "GET";
            uri.setText("http://localhost:80/denwer/index.html");
            areaBody.setText("");
            areaHeader.setText("User-Agent:HTTPClient\n\n");
        }
        if (actionEvent.getSource().equals(rbtnPOST)) {
            boxBody.setDisable(false);
            boxHeader.setDisable(false);
            method = "POST";
            uri.setText("http://localhost:80/denwer/example.php");
            areaBody.setText("user_name=Nastya&age=20");
            areaHeader.setText("User-Agent:HTTPClient\n" +
                    "Content-Type: application/x-www-form-urlencoded\n" +
                    "Content-Length: 23");
        }
        if (actionEvent.getSource().equals(rbtnHEAD)) {
            boxBody.setDisable(true);
            method = "HEAD";
            uri.setText("http://localhost:80/denwer/index.html");
            areaBody.setText("");
            areaHeader.setText("User-Agent:HTTPClient");
        }
    }

    public void SendRequest(ActionEvent actionEvent) {
        if (CheckInputs()) {
            lblStatus.setText("Error");
            lblProtocol.setText("");
            areaBodyResp.setText("");
            areaHeaderResp.setText("");
            Request request = new Request(host.getText(), Integer.parseInt(port.getText()), method, uri.getText(), "HTTP/1.0", areaHeader.getText(), areaBody.getText());
            Response response = client.SendRequest(request);
            areaHistory.setText(areaHistory.getText() + "\nClient: \n" + request.getRequestAsString());
            areaHistory.setText(areaHistory.getText() + "Server: \n" + response.getResponseAsString());
            FillResponse(response);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Incorrect parameters");
            alert.setContentText("Fill all necessary fields!");
            alert.showAndWait();
        }
    }

    private void FillResponse(Response response) {

        lblStatus.setText("Status: " + response.getStatus());
        lblProtocol.setText("Protocol: " + response.getProtocol());
        areaHeaderResp.setText(response.getHeaders());
        areaBodyResp.setText(response.getBody());

        if (!response.getComment().equals("")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Info");
            alert.setContentText(response.getComment());
            alert.showAndWait();
        }
    }

    private boolean CheckInputs() {
        if (!uri.getText().isEmpty() && !areaHeader.getText().isEmpty() && !host.getText().isEmpty() && !port.getText().isEmpty()) {
            return true;
        } else return false;
    }

    public void ClearRequest(ActionEvent actionEvent) {
        ClearRequestInputs();
    }

    private void ClearRequestInputs() {
        areaHeader.setText("");
        areaBody.setText("");
    }

    public void ClearHistory(ActionEvent actionEvent) {
        areaHistory.setText("");
    }
}
