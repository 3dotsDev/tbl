package tb.bsc.translatorcheck;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Border;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import tb.bsc.translatorcheck.Exception.TranslatorException;
import tb.bsc.translatorcheck.logic.dto.Suggestions;
import tb.bsc.translatorcheck.logic.dto.Vocab;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class TranslatorMainController {

    private CheckSession session = null;

    Timer tm = new java.util.Timer();

    @FXML
    private Button btnSession;
    @FXML
    private Button btnAdmin;

    @FXML
    private Button btnChange;
    @FXML
    private TextField txtDE;

    @FXML
    private TextField txtEN;
    @FXML
    private Label lblTimer;

    public void initialize() {
        btnSession.setText("Start");
        lblTimer.setText(getTimme(0));
        txtDE.setDisable(true);
        txtEN.setDisable(true);
        btnChange.setDisable(true);
        try {
            session = new CheckSession(Path.of("data.json"));
            btnSession.setDisable(false);
        } catch (TranslatorException e) {
            btnSession.setDisable(true);
            ControllerHelper.createErrorAlert(e.getMessage()).show();
        }
        session.resetSession();
    }

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
    void btnSessionOnClick(ActionEvent event) throws TranslatorException {
        try {
            if (session.getCurrentState() == SessionState.STOPPED) {
                session.resetSession();
                session.startSession();
                btnSession.setText("Stopp");
                txtDE.setDisable(false);
                txtDE.setText(getSuggestionRand());
                btnChange.setDisable(!btnChange.isDisable());
                tm.schedule(new subtimer(), 1000, 1000);
            } else if (session.getCurrentState() == SessionState.RUNNING) {
                btnChange.setDisable(!btnChange.isDisable());
                txtDE.setDisable(true);
                txtEN.setDisable(true);
                session.stopSession();
                lblTimer.setText(getTimme(session.getTimeElapsed()));
                btnSession.setText("Start");
            } else {
                throw new TranslatorException("Sessionstate is not recognizable");
            }
        } catch (TranslatorException e) {
            ControllerHelper.createErrorAlert(e.getMessage()).show();
        }
    }

    private String getSuggestionRand() {
        Vocab currentVocab = session.getCurrentVocab();
        List<Suggestions> currentSuggestions = currentVocab.getSuggestions().stream().filter(c -> c.getLang().equalsIgnoreCase("de")).toList();
        Random rand = new Random();
        return currentSuggestions.get(rand.nextInt(currentSuggestions.size())).getText();
    }

    private class subtimer extends TimerTask {
        // run method
        @Override
        public void run() {
            // method
            Platform.runLater(() -> {
                lblTimer.setText(getTimme(session.getTimeElapsed()));
            });
        }
    }

    @FXML
    void btnChangeOnKlick(ActionEvent event) {
        txtEN.setDisable(!txtEN.isDisable());
        txtDE.setDisable(!txtDE.isDisable());
    }

    @FXML
    void txtDEKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.TAB) {
            if( !validateInput(txtDE.getText())){
                txtDE.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
            }else{
                txtDE.setStyle("-fx-text-box-border: #54b222; -fx-focus-color: #54b222;");
            }
            txtDE.requestFocus();
        }
    }

    @FXML
    void txtENKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.TAB) {
            validateInput(txtEN.getText());
            txtEN.requestFocus();
        }
    }

    private boolean validateInput(String value) {
        Vocab currentVocab = session.getCurrentVocab();
        currentVocab.setCheckcounter(currentVocab.getCheckcounter() + 1);
        List<Suggestions> currentSuggestions = currentVocab.getSuggestions().stream().filter(c -> c.getLang().equalsIgnoreCase("en")).toList();
        if (currentSuggestions.stream().anyMatch(c -> c.getText().equalsIgnoreCase(value))) {
            currentVocab.setCorrectnesCounter(currentVocab.getCorrectnesCounter() + 1);
            return true;
        }
        return false;
    }

    private String getTimme(long milliseconds) {
        long minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds);
        return minutes + ":" + seconds;
    }

}
