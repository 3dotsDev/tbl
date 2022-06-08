package tb.bsc.translatorcheck;

import tb.bsc.translatorcheck.Exception.TranslatorException;
import tb.bsc.translatorcheck.logic.ValueHelper;
import tb.bsc.translatorcheck.logic.ValueLoader;
import tb.bsc.translatorcheck.logic.ValueWriter;
import tb.bsc.translatorcheck.logic.dto.Suggestions;
import tb.bsc.translatorcheck.logic.dto.Vocab;
import tb.bsc.translatorcheck.logic.dto.Vocabulary;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Random;

public class CheckSession implements AutoCloseable {

    private SessionState currentState = SessionState.STOPPED;
    private Path dataFilePath;
    private Instant start;
    private List<Vocab> vocabulary;
    private Instant end = null;

    private Vocab currentVocab;
    private int currentVocabIndex;


    public CheckSession(Path dataFilePath) throws TranslatorException {
        this.dataFilePath = dataFilePath;
        File dataFile = new File(String.valueOf(this.dataFilePath));
        System.out.println(dataFile.getAbsolutePath());
        System.out.println(dataFile.toPath().getFileName());
        if (!dataFile.exists()) {
            try {
                if (!dataFile.createNewFile()) {
                    throw new TranslatorException("Beim erstellen des Datenfiles ist etwas schief gelaufen");
                }
                try (FileWriter writer = new FileWriter(String.valueOf(dataFilePath))) { // File initialisierung wenn noch keines vorhanden ist
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
    }

    public void resetSession() {
        start = null;
        end = null;
        this.currentState = SessionState.STOPPED;
    }

    public void startSession() {
        getRandomVocab();
        start = java.time.Instant.now(); // timer gestartet
        this.currentState = SessionState.RUNNING;
    }

    private void getRandomVocab() {
        currentVocabIndex = ValueHelper.getLottery(vocabulary.size(), currentVocabIndex);
        currentVocab = vocabulary.get(currentVocabIndex);
    }

    public void stopSession() {
        end = Instant.now();
        this.currentState = SessionState.STOPPED;
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

    public SessionState getCurrentState() {
        return currentState;
    }

    public Vocab getCurrentVocab() {
        return currentVocab;
    }

    public void setNextVocab() {
        getRandomVocab();
    }

    @Override
    public void close() throws Exception {
        ValueWriter valueWriter = new ValueWriter();
        valueWriter.writeData(vocabulary,this.dataFilePath);
    }
}
