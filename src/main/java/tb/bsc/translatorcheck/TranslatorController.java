package tb.bsc.translatorcheck;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TranslatorController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
    
    private void juhu(){
        String s = "auf der welt";
    }
}
