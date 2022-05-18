package tb.bsc.translatorcheck;

import javafx.scene.control.Alert;

public class ControllerHelper {

    public static Alert createErrorAlert(String content){
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle("Fehler");
        errorAlert.setHeaderText("Es ist ein Fehler aufgetreten");
        errorAlert.setContentText(content);
        return errorAlert;
    }
}
