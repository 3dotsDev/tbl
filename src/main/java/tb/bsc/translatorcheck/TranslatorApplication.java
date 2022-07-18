package tb.bsc.translatorcheck;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TranslatorApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Laden vom GUI Translater_Main (Siehe "ressources Ordner")

        FXMLLoader fxmlLoader = new FXMLLoader(TranslatorApplication.class.getResource("Translator_Main.fxml"));

        // Ladet das Fenster innherlab vom GUI mit der Auflösung von 600x400 pixel

        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}