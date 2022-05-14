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
        //was geht ab
        int leckMichNochmal = 1;
    }

    private void juhu2(){
        //was geht ab
        String s = "auf der welt";
    }
}
