package tb.bsc.translatorcheck;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import tb.bsc.translatorcheck.Exception.TranslatorException;

import java.nio.file.Path;

public class TranslatorMainController {

    private CheckSession session;

    @FXML
    private Button btnSession;

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
