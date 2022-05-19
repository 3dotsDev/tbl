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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import tb.bsc.translatorcheck.Exception.TranslatorException;

import java.io.IOException;
import java.nio.file.Path;
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
    private Label lblTimer;

    public void initialize() {
        lblTimer.setText(getTimme(0));
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
    void btnSessionOnClick(ActionEvent event) {
        if (session == null) {
            try {
                session = new CheckSession(Path.of("data.json"));
                btnSession.setText("Stopp");
                tm.schedule(new subtimer(), 1000, 1000);
            } catch (TranslatorException e) {
                ControllerHelper.createErrorAlert(e.getMessage()).show();
            }
        } else {
            if (session.getCurrentState() == SessionState.RUNNING) {
                session.stopSession();
                lblTimer.setText(getTimme(session.getTimeElapsed()));
                btnSession.setText("Restart");
            } else if (session.getCurrentState() == SessionState.STOPPED) {
                session = null;
                try {
                    session = new CheckSession(Path.of("data.json"));
                    btnSession.setText("Stopp");
                } catch (TranslatorException e) {
                    ControllerHelper.createErrorAlert(e.getMessage()).show();
                }
            } else {
                ControllerHelper.createErrorAlert("Status nicht erkannt, bitte schliessen").show();
            }
        }
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

    private String getTimme(long milliseconds) {
        long minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds);
        return minutes + ":" + seconds;
    }

}
