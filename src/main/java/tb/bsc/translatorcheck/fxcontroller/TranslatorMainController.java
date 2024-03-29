package tb.bsc.translatorcheck.fxcontroller;

import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import tb.bsc.translatorcheck.Exception.TranslatorException;
import tb.bsc.translatorcheck.TranslatorApplication;
import tb.bsc.translatorcheck.logic.CheckSession;
import tb.bsc.translatorcheck.logic.SessionState;
import tb.bsc.translatorcheck.logic.dto.Vocab;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * GUI Logik für den Translater_Main.fxml
 */
public class TranslatorMainController {

    Timer tm = new java.util.Timer();
    private CheckSession session = null;
    private int currentSuggestionIndex = 0;
    private SimpleIntegerProperty failCount = new SimpleIntegerProperty(0);
    private SimpleIntegerProperty okCount = new SimpleIntegerProperty(0);
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

    @FXML
    private Label lblOkCount;
    @FXML
    private Label lblFailCount;

    /**
     * Einstiegspunkt der Logik
     * Setzt Standartwerte und bindet controls an ihre Werteträger
     */
    public void initialize() {
        lblFailCount.textProperty().bind(failCount.asString());
        lblOkCount.textProperty().bind(okCount.asString());
        btnSession.setText("Start");
        lblTimer.setText("Minutes: " + 0 + " : " + 0);
        txtDE.setDisable(true);
        txtEN.setDisable(true);
        btnChange.setDisable(true);
        btnSession.setDisable(false);
    }

    /**
     * Logik für das Öffnen des Administration GUI's
     * Läd neue JAVAFX Komponente Translater_Admin.fxml und öffnet diesen als modalen Dialog
     *
     * @param event
     */
    @FXML
    void btnAdminOnClick(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(TranslatorApplication.class.getResource("Translator_Admin.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1100, 500);
            TranslatorAdminController controller = fxmlLoader.getController();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.DECORATED);
            stage.setTitle("Administration");
            stage.setScene(scene);
            stage.setOnHidden( e -> controller.shutdown());
            stage.show();
        } catch (IOException e) {
            ControllerHelper.createErrorAlert(e.getMessage());
        }
    }

    @FXML
    void btnSessionOnClick(ActionEvent event) throws TranslatorException {
        try {
            if (session == null || session.getCurrentState() == SessionState.STOPPED) {
                session = new CheckSession(Path.of("data.json"));
                session.resetSession();
                session.startSession();
                btnSession.setText("Stopp");
                txtDE.setDisable(true);
                txtDE.setText(session.getCurrentVocab().getValueDe());
                txtEN.setDisable(false);
                txtEN.setText("");
                btnChange.setDisable(false);
                btnAdmin.setDisable(true);
                failCount.setValue(0);
                okCount.setValue(0);
                tm.schedule(new UiTimer(), 1000, 1000);
                txtEN.requestFocus();
            } else if (session.getCurrentState() == SessionState.RUNNING) {
                btnChange.setDisable(!btnChange.isDisable());
                txtDE.setDisable(true);
                txtEN.setDisable(true);
                btnAdmin.setDisable(false);
                session.stopSession();
                uiTimeGetter();
                btnChange.setDisable(true);
                btnSession.setText("Start");
            } else {
                throw new TranslatorException("Sessionstate is not recognizable");
            }
        } catch (Exception e) {
            ControllerHelper.createErrorAlert(e.getMessage()).show();
        }
    }

    private void uiTimeGetter() {
        long currentSessionTime = session.getTimeElapsed();
        long minutes = TimeUnit.MILLISECONDS.toMinutes(currentSessionTime);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(currentSessionTime);
        lblTimer.setText("Minutes: " + minutes + " : " + seconds);
    }

    @FXML
    void btnChangeOnKlick(ActionEvent event) {
        session.setNextVocab();
        if (txtEN.isDisable()) {
            txtEN.setText("");
            txtEN.setDisable(false);
            txtDE.setText(session.getCurrentVocab().getValueDe());
            txtDE.setDisable(true);
        } else {
            txtEN.setText(session.getCurrentVocab().getValueEn());
            txtEN.setDisable(true);
            txtDE.setText("");
            txtDE.setDisable(false);
        }
    }

    @FXML
    void txtDETabKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.TAB || event.getCode() == KeyCode.ENTER) {
            if (!validateCurrentCheckInput(txtDE.getText(), "de")) {
                txtDE.setStyle("-fx-text-box-border: #B22222; -fx-text-box-background: #B22222; -fx-focus-color: #B22222;");
            } else {
                txtDE.setStyle("-fx-text-box-border: #54b222; -fx-text-box-background: #54b222; -fx-focus-color: #54b222;");
            }
            session.setNextVocab();
            txtEN.setText(session.getCurrentVocab().getValueEn());
            txtDE.setText("");
            txtDE.requestFocus();
        }
    }

    @FXML
    void txtENTabKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.TAB || event.getCode() == KeyCode.ENTER) {
            if (!validateCurrentCheckInput(txtEN.getText(), "en")) {
                txtEN.setStyle("-fx-text-box-border: #B22222; -fx-text-box-background: #B22222; -fx-focus-color: #B22222;");
            } else {
                txtEN.setStyle("-fx-text-box-border: #54b222; -fx-text-box-background: #54b222; -fx-focus-color: #54b222;");
            }
            session.setNextVocab();
            txtDE.setText(session.getCurrentVocab().getValueDe());
            txtEN.setText("");
            txtEN.requestFocus();
        }
    }

    private boolean validateCurrentCheckInput(String value, String lang) {
        Vocab currentVocab = session.getCurrentVocab();
        currentVocab.setCheckcounter(currentVocab.getCheckcounter() + 1);
        if (lang.equalsIgnoreCase("de")) {
            if (value.equalsIgnoreCase(session.getCurrentVocab().getValueDe())) {
                okCount.set(okCount.getValue() + 1);
                currentVocab.setCorrectnesCounter(currentVocab.getCorrectnesCounter() + 1);
                return true;
            } else {
                failCount.set(failCount.getValue() + 1);
                Alert solutionInfo = ControllerHelper.getSolutionInfo(session.getCurrentVocab().getValueDe());
                solutionInfo.show();
                return false;
            }
        } else {
            if (value.equalsIgnoreCase(session.getCurrentVocab().getValueEn())) {
                okCount.set(okCount.getValue() + 1);
                currentVocab.setCorrectnesCounter(currentVocab.getCorrectnesCounter() + 1);
                return true;
            } else {
                failCount.set(failCount.getValue() + 1);
                Alert solutionInfo = ControllerHelper.getSolutionInfo(session.getCurrentVocab().getValueEn());
                solutionInfo.show();
                return false;
            }
        }
    }

    private class UiTimer extends TimerTask {
        // run method
        @Override
        public void run() {
            // method
            Platform.runLater(() -> {
                uiTimeGetter();
            });
        }
    }

}
