package tb.bsc.translatorcheck.logic;

import tb.bsc.translatorcheck.Exception.TranslatorException;
import tb.bsc.translatorcheck.fxcontroller.ControllerHelper;
import tb.bsc.translatorcheck.logic.dto.Vocab;
import tb.bsc.translatorcheck.logic.helper.ValueHelper;
import tb.bsc.translatorcheck.logic.helper.ValueLoader;
import tb.bsc.translatorcheck.logic.helper.ValueWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;

public class CheckSession {

    private final Path dataFilePath;
    private final ArrayList<Vocab> vocabulary;
    private SessionState currentState = SessionState.STOPPED;
    private Instant start;
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
                throw new TranslatorException("Beim erstellen des Datenfiles ist etwas schief gelaufen" + ex.getMessage());
            }
        }

        try {
            vocabulary = new ValueLoader().loadData(dataFilePath);
        } catch (Exception e) {
            throw new TranslatorException("Das Datenfile kann nicht gelesen werden " + e.getMessage());
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

    public ArrayList<Vocab> clearCurrentItemStatistics(Vocab item) {
        item.setCorrectnesCounter(0).setCheckcounter(0);
        try {
            writeDataBack();
        } catch (TranslatorException | IOException e) {
            ControllerHelper.createErrorAlert(e.getMessage());
        }
        return vocabulary;
    }

    public ArrayList<Vocab> deleteCurrentItem(Vocab item) {
        vocabulary.remove(item);
        try {
            writeDataBack();
        } catch (TranslatorException | IOException e) {
            ControllerHelper.createErrorAlert(e.getMessage());
        }
        return vocabulary;
    }

    public ArrayList<Vocab> addCurrentItem(Vocab item) {
        vocabulary.add(item);
        try {
            writeDataBack();
        } catch (TranslatorException | IOException e) {
            ControllerHelper.createErrorAlert(e.getMessage());
        }
        return vocabulary;
    }

    public ArrayList<Vocab> updateCurrentItem(Integer id, String valueDe, String valueEn) {
        Optional<Vocab> first = vocabulary.stream().filter(c -> c.getId().equals(id)).findFirst();
        if (first.isPresent()) {
            first.get().setValueEn(valueEn).setValueDe(valueDe);
        }
        try {
            writeDataBack();
        } catch (TranslatorException | IOException e) {
            ControllerHelper.createErrorAlert(e.getMessage());
        }
        return vocabulary;
    }

    private void getRandomVocab() {
        if (hardeningCount % 10 != 0) { // modulo -> nur beim 10 durchlauf wird nicht auf die lottery zugegriffen sondern auf die werte welche am schlechtesten bewertet sind
            doHardening();
        } else {
            System.out.println("Hardening % 5");
            currentVocabIndex = ValueHelper.getLottery(vocabulary.size(), currentVocabIndex);
            hardeningCount = hardeningCount + 1;
        }
        System.out.println("Current Vocab Index" + currentVocabIndex);
        currentVocab = vocabulary.get(currentVocabIndex);
        System.out.println("Current Vocab ID Value" + currentVocab.getId());
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
        } else { // im Fehlerfall und vocab NICHT present ist
            currentVocabIndex = ValueHelper.getLottery(vocabulary.size(), currentVocabIndex);
        }
    }

    public int getLastMaxId() {
        Optional<Vocab> max = vocabulary.stream().max(Comparator.comparing(Vocab::getId));
        if (max.isPresent()) {
            return max.get().getId();
        }
        return 0;
    }

    public void stopSession() throws TranslatorException, IOException {
        writeDataBack();
        end = Instant.now();
        this.currentState = SessionState.STOPPED;
    }

    private void writeDataBack() throws TranslatorException, IOException {
        ValueWriter valueWriter = new ValueWriter();
        valueWriter.writeData(vocabulary, this.dataFilePath);
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

    public ArrayList<Vocab> getVocabulary() {
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
