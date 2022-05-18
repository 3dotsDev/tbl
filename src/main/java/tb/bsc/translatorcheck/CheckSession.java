package tb.bsc.translatorcheck;

import tb.bsc.translatorcheck.logic.ValueLoader;
import tb.bsc.translatorcheck.logic.dto.Vocab;

import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

public class CheckSession {

    private final Instant start;
    private List<Vocab> vocabulary;
    private Instant end = null;

    public CheckSession(Path dataFilePath) {
        vocabulary = new ValueLoader().loadData(dataFilePath);
        start = java.time.Instant.now(); // beim initialisieren der session wird automatisch der timer gestartet
    }


    public void stopSession() {
        end = Instant.now();
    }

    /**
     * Zeigt die vergangene Zeit an seit dem Start
     * https://www.baeldung.com/java-measure-elapsed-time
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
