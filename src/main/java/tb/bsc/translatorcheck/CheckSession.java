package tb.bsc.translatorcheck;

import tb.bsc.translatorcheck.Exception.TranslatorException;
import tb.bsc.translatorcheck.logic.ValueHelper;
import tb.bsc.translatorcheck.logic.ValueLoader;
import tb.bsc.translatorcheck.logic.ValueWriter;
import tb.bsc.translatorcheck.logic.dto.Vocab;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class CheckSession {

    private SessionState currentState = SessionState.STOPPED;
    private Path dataFilePath;
    private Instant start;
    private List<Vocab> vocabulary;
    private Instant end = null;

    private int hardeningCount = 0;

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
        hardeningCount = 0;
        this.currentState = SessionState.STOPPED;
    }

    public void startSession() {
        getRandomVocab();
        start = java.time.Instant.now(); // timer gestartet
        this.currentState = SessionState.RUNNING;
    }

    private void getRandomVocab() {
        if (hardeningCount % 3 != 0) { // modulo -> nur beim 3 durchlauf wird nicht auf die lottery zugegriffen sondern auf die werte welche am schlechtesten bewertet sind
            doHardening();
        } else {
            System.out.println("Hardening % 5");
            currentVocabIndex = ValueHelper.getLottery(vocabulary.size(), currentVocabIndex);
            hardeningCount = hardeningCount + 1;
        }
        System.out.println("Current Vocab Index" + currentVocabIndex );
        currentVocab = vocabulary.get(currentVocabIndex);
        System.out.println("Current Vocab ID Value" + currentVocab.getId() );
    }

    /**
     * Dieser Teil wird genutzt und fehlerhafte und wenig genutzte vokabeln nach vorne zu schieben und die Lottery zu umgehen
     */
    private void doHardening() {
        if (hardeningCount % 2 == 0) {
            System.out.println("hardening % 2");
            Optional<Vocab> vocab = vocabulary.stream().max(Comparator.comparing(c -> c.getCheckcounter() - c.getCorrectnesCounter())); // anzahl pruefungen - anzahl korrekte Antworten = falsche Antworten sortiert
            getIndexAndCheckLastUse(vocab);
        } else {
            System.out.println("hardening NOT % 2");
            Optional<Vocab> vocab = vocabulary.stream().min(Comparator.comparing(Vocab::getCheckcounter)); // anzahl pruefungen --> sonst gehen die neuen sachen unter
            getIndexAndCheckLastUse(vocab);
        }
    }

    private void getIndexAndCheckLastUse(Optional<Vocab> vocab) {
        if (vocab.isPresent()) { // wenn etwas gfunden wurde
            if (vocabulary.indexOf(vocab.get()) == currentVocabIndex) { // wenns das selbe ist wie eben schon geprueft machts keinen sinn -> lottery
                currentVocabIndex = ValueHelper.getLottery(vocabulary.size(), currentVocabIndex);
                //Hardening wird absichtlich nicht hochgezaehlt da der sinn der sache nicht erfuellt wurde
                System.out.println("Lottery");
            } else {
                currentVocabIndex = vocabulary.indexOf(vocab.get()); // der schlechteste wird geprueft
                System.out.println("Hardening");
                hardeningCount = hardeningCount + 1;
            }
        }else{ // im Fehlerfall und vocab NICHT present ist
            currentVocabIndex = ValueHelper.getLottery(vocabulary.size(), currentVocabIndex);
        }
    }

    public void stopSession() {
        ValueWriter valueWriter = new ValueWriter();
        valueWriter.writeData(vocabulary, this.dataFilePath);
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
}
