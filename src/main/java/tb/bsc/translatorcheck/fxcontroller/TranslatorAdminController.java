package tb.bsc.translatorcheck.fxcontroller;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import tb.bsc.translatorcheck.Exception.TranslatorException;
import tb.bsc.translatorcheck.logic.CheckSession;
import tb.bsc.translatorcheck.logic.dto.Vocab;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

public class TranslatorAdminController {

    private CheckSession session;
    private ControllerMode mode;
    private Integer idHelper;

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
    private TableColumn<Vocab, String> tcValueDE;
    @FXML
    private TableColumn<Vocab, String> tcValueEn;

    @FXML
    private TextField txtAddDe;

    @FXML
    private TextField txtAddEn;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnAdd;

    private ObservableList<Vocab> viewData = FXCollections.observableArrayList();

    public void initialize() {
        try {
            btnSave.setDisable(true);
            btnAdd.setDisable(true);
            txtAddEn.setDisable(true);
            txtAddDe.setDisable(true);
            defineColumne();
            loadData();
            mode = ControllerMode.CREATE;
        } catch (TranslatorException e) {
            ControllerHelper.createErrorAlert(e.getMessage());
        }
        btnAdd.setDisable(false);
    }

    private void loadData() throws TranslatorException {
        session = new CheckSession(Path.of("data.json"));
        viewData.addAll(session.getVocabulary());
        tblData.setItems(viewData);
    }

    private void defineColumne() {
        tcId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcCheckCount.setCellValueFactory(new PropertyValueFactory<>("checkcounter"));
        tcCorectnessCount.setCellValueFactory(new PropertyValueFactory<>("correctnesCounter"));
        tcFailCount.setCellValueFactory(new PropertyValueFactory<>("calculatedFailCount"));
        tcValueDE.setCellValueFactory(new PropertyValueFactory<>("valueDe"));
        tcValueEn.setCellValueFactory(new PropertyValueFactory<>("valueEn"));

        createRowFactory();
    }

    /**
     * Context infos -> https://web.archive.org/web/20170907200720/http://www.marshall.edu/genomicjava/2013/12/30/javafx-tableviews-with-contextmenus
     */
    private void createRowFactory() {
        tblData.setRowFactory(
                param -> {
                    final TableRow<Vocab> row = new TableRow<>();
                    final ContextMenu rowMenu = new ContextMenu();
                    MenuItem updateItem = new MenuItem("Eintrag anpassen");
                    updateItem.setOnAction(event -> {

                        tblData.setDisable(true);
                        btnSave.setDisable(false);
                        btnAdd.setDisable(true);
                        txtAddEn.setDisable(false);
                        txtAddDe.setDisable(false);
                        Vocab item = row.getItem();
                        txtAddEn.setText(item.getValueEn());
                        txtAddDe.setText(item.getValueDe());
                        idHelper = item.getId();
                        tblData.setDisable(true);
                        mode = ControllerMode.UPDATE;
                    });
                    MenuItem resetDataItem = new MenuItem("Statistik löschen");
                    resetDataItem.setOnAction(event -> {
                        ArrayList<Vocab> vocabs = session.clearCurrentItemStatistics(row.getItem());
                        reloadData(vocabs);
                    });
                    MenuItem deleteItem = new MenuItem("Eintrag Löschen");
                    deleteItem.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            Vocab item = row.getItem();
                            ArrayList<Vocab> vocabs = session.deleteCurrentItem(item);
                            reloadData(vocabs);
                        }
                    });
                    MenuItem refreshItem = new MenuItem("Grid neu Laden");
                    refreshItem.setOnAction(event -> {
                        ArrayList<Vocab> vocabs = session.getVocabulary();
                        reloadData(vocabs);
                    });
                    rowMenu.getItems().addAll(updateItem,resetDataItem, deleteItem, refreshItem);
                    row.contextMenuProperty().bind(
                            Bindings.when(Bindings.isNotNull(row.itemProperty()))
                                    .then(rowMenu)
                                    .otherwise((ContextMenu) null));
                    return row;
                }
        );
    }

    private void reloadData(ArrayList<Vocab> vocabs) {
        viewData.clear();
        viewData.addAll(vocabs);
    }

    public void btnSaveClick(ActionEvent actionEvent) {
        if (txtAddDe.getText().length() > 1 && txtAddEn.getText().length() > 1) {
            if (mode == ControllerMode.CREATE) {
                Vocab vocab = new Vocab();
                vocab.setValueDe(txtAddDe.getText());
                vocab.setValueEn(txtAddEn.getText());
                vocab.setId(session.getLastMaxId() + 1);
                vocab.setCheckcounter(0);
                vocab.setCorrectnesCounter(0);

                ArrayList<Vocab> vocabs = session.addCurrentItem(vocab);
                reloadData(vocabs);
                txtAddDe.clear();
                txtAddEn.clear();
                txtAddDe.requestFocus();
            } else if (mode == ControllerMode.UPDATE) {
                ArrayList<Vocab> vocabs = session.updateCurrentItem(idHelper, txtAddDe.getText(), txtAddEn.getText());
                reloadData(vocabs);
                txtAddDe.clear();
                txtAddEn.clear();
            }
        } else {
            ControllerHelper.getCustomerInfo("Werte fuer DE und EN muessen erfasst werden");
        }
        mode = ControllerMode.CREATE;
        tblData.setDisable(false);
        btnSave.setDisable(true);
        btnAdd.setDisable(false);
        txtAddEn.setDisable(true);
        txtAddDe.setDisable(true);
    }

    public void shutdown() {
        try {
            session.stopSession();
        } catch (TranslatorException | IOException e) {
            ControllerHelper.createErrorAlert(e.getMessage());
        }
    }

    public void btnAddClick(ActionEvent actionEvent) {
        tblData.setDisable(true);
        txtAddDe.setDisable(false);
        txtAddEn.setDisable(false);
        btnSave.setDisable(false);
        btnAdd.setDisable(true);
    }
}

