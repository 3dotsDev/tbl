package tb.bsc.translatorcheck;

import tb.bsc.translatorcheck.Exception.TranslatorException;
import tb.bsc.translatorcheck.logic.ValueLoader;
import tb.bsc.translatorcheck.logic.dto.Vocab;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

public class CheckSession {

    private final Instant start;
    private List<Vocab> vocabulary;
    private Instant end = null;

    public CheckSession(Path dataFilePath) throws TranslatorException {
        File dataFile = new File(String.valueOf(dataFilePath));
        System.out.println(dataFile.getAbsolutePath());
        System.out.println(dataFile.toPath().getFileName());
        if (!dataFile.exists()) {
            try {
                if (!dataFile.createNewFile()) {
                    throw new TranslatorException("Beim erstellen des Datenfiles ist etwas schief gelaufen");
                }
                try (FileWriter writer = new FileWriter(String.valueOf(dataFilePath))) {
                    writer.write("{}"); // wird benoetigt damit das file als json file erkannt wird
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
                throw new TranslatorException("Beim erstellen des Datenfiles ist etwas schief gelaufen");
            }
        }

        try {
            vocabulary = new ValueLoader().loadData(dataFilePath);
        } catch (Exception e) {
            throw new TranslatorException("Das Datenfile kann nicht gelesen werden");
        }
        start = java.time.Instant.now(); // beim initialisieren der session wird automatisch der timer gestartet
    }


    public void stopSession() {
        end = Instant.now();
    }

    /**
     * Zeigt die vergangene Zeit an seit dem Start
     * https://www.baeldung.com/java-measure-elapsed-time
     *
     * @return millisekunden durchlauf
     */
    public long getTimeElapsed() {
        if (end == null) {// abfrage zwischendurch
            return Duration.between(start, Instant.now()).toMillis();
        }
        //wird ausgegeben wenn gestoppt wurde
        return Duration.between(start, end).toMillis();
    }

    public List<Vocab> getVocabulary() {
        return vocabulary;
    }
}
