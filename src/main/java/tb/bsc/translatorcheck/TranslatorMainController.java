package tb.bsc.translatorcheck;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import tb.bsc.translatorcheck.Exception.TranslatorException;

import java.io.IOException;
import java.nio.file.Path;

public class TranslatorMainController {

    private CheckSession session;

    @FXML
    private Button btnSession;
    @FXML
    private Button btnAdmin;

    @FXML
    void btnAdminOnClick(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(TranslatorApplication.class.getResource("Translator_Admin.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 900, 500);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("Administration");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            ControllerHelper.createErrorAlert(e.getMessage());
        }
    }

    @FXML
    void btnSessionOnClick(ActionEvent event) {
        btnSession.setText("Geil");
        try{
        session = new CheckSession(Path.of("data.json"));}
        catch (TranslatorException e){
            ControllerHelper.createErrorAlert(e.getMessage()).show();
        }
    }


}
