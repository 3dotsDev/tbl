package tb.bsc.translatorcheck;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import tb.bsc.translatorcheck.Exception.TranslatorException;
import tb.bsc.translatorcheck.logic.dto.Suggestions;
import tb.bsc.translatorcheck.logic.dto.Vocab;

import java.nio.file.Path;

public class TranslatorAdminController {

    @FXML
    private StackedBarChart<?, ?> chartData;

    @FXML
    private TableView<Vocab> tblData;
    @FXML
    private TableColumn<Vocab, Integer> tcId;
    @FXML
    private TableColumn<Vocab, Integer> tcFailCount;
    @FXML
    private TableColumn<Vocab, Integer> tcCheckCount;
    @FXML
    private TableColumn<Vocab, Integer> tcCorectnessCount;
    @FXML
    private TableView<Vocab> tblValues;
    @FXML
    private TableColumn<Vocab, Integer> tcLang;
    @FXML
    private TableColumn<Vocab, String> tcText;

    private ObservableList<Vocab> viewData = FXCollections.observableArrayList();
    private ObservableList<Suggestions> detailData = FXCollections.observableArrayList();

    public void initialize() {
        try {

            tcId.setCellValueFactory(new PropertyValueFactory<Vocab, Integer>("id"));
            tcCheckCount.setCellValueFactory(new PropertyValueFactory<Vocab, Integer>("checkcounter"));
            tcCorectnessCount.setCellValueFactory(new PropertyValueFactory<Vocab, Integer>("correctnesCounter"));
            tcFailCount.setCellValueFactory(new PropertyValueFactory<Vocab, Integer>("calculatedFailCount"));

            CheckSession session = new CheckSession(Path.of("data.json"));
            for (Vocab vocab : session.getVocabulary()) {
                for (Suggestions suggestion : vocab.getSuggestions()) {
                    detailData.add(suggestion);
                }
                viewData.add(vocab);
            }
            viewData.addAll(session.getVocabulary());
            tblData.setItems(viewData);
        } catch (TranslatorException e) {
            throw new RuntimeException(e);
        }


    }
}

