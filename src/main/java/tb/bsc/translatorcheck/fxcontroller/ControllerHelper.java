package tb.bsc.translatorcheck.fxcontroller;

import javafx.scene.control.Alert;

public class ControllerHelper {

    public static Alert createErrorAlert(String content){
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle("Fehler");
        errorAlert.setHeaderText("Es ist ein Fehler aufgetreten");
        errorAlert.setContentText(content);
        return errorAlert;
    }

    public  static Alert getSolutionInfo(String content){
        Alert errorAlert = new Alert(Alert.AlertType.INFORMATION);
        errorAlert.setTitle("Lösung");
        errorAlert.setHeaderText("Korrekte Antwort:");
        errorAlert.setContentText(content);
        return errorAlert;
    }

    public  static Alert getCustomerInfo(String content){
        Alert errorAlert = new Alert(Alert.AlertType.WARNING);
        errorAlert.setTitle("Achtung");
        errorAlert.setHeaderText("Bitte korrigieren");
        errorAlert.setContentText(content);
        return errorAlert;
    }
}
