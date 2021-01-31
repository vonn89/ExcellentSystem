/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.View.Dialog;

import com.excellentsystem.AuriSteel.DAO.PenerimaanBahanDAO;
import com.excellentsystem.AuriSteel.Function;
import com.excellentsystem.AuriSteel.Koneksi;
import com.excellentsystem.AuriSteel.Main;
import static com.excellentsystem.AuriSteel.Main.df;
import static com.excellentsystem.AuriSteel.Main.tglLengkap;
import static com.excellentsystem.AuriSteel.Main.tglSql;
import com.excellentsystem.AuriSteel.Model.PenerimaanBahan;
import java.sql.Connection;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author excellent
 */
public class AddPenerimaanBahanController {

    @FXML
    private TableView<PenerimaanBahan> penerimaanTable;
    @FXML
    private TableColumn<PenerimaanBahan, Boolean> checkColumn;
    @FXML
    private TableColumn<PenerimaanBahan, String> noPenerimaanColumn;
    @FXML
    private TableColumn<PenerimaanBahan, String> tglPenerimaanColumn;
    @FXML
    private TableColumn<PenerimaanBahan, String> kodeGudangColumn;
    @FXML
    private TableColumn<PenerimaanBahan, String> kodeBahanColumn;
    @FXML
    private TableColumn<PenerimaanBahan, String> kodeKategoriColumn;
    @FXML
    private TableColumn<PenerimaanBahan, String> namaBahanColumn;
    @FXML
    private TableColumn<PenerimaanBahan, String> keteranganColumn;
    @FXML
    private TableColumn<PenerimaanBahan, Number> beratTimbanganColumn;
    @FXML
    private TableColumn<PenerimaanBahan, Number> beratKotorColumn;
    @FXML
    private TableColumn<PenerimaanBahan, Number> beratBersihColumn;
    @FXML
    private TableColumn<PenerimaanBahan, Number> panjangColumn;
    @FXML
    private TableColumn<PenerimaanBahan, String> kodeUserColumn;
    @FXML
    private TableColumn<PenerimaanBahan, String> statusColumn;

    @FXML
    private CheckBox checkAll;
    @FXML
    private TextField searchField;
    @FXML
    public Button selectButton;

    public ObservableList<PenerimaanBahan> allPenerimaan = FXCollections.observableArrayList();
    private ObservableList<PenerimaanBahan> filterData = FXCollections.observableArrayList();
    private Main mainApp;
    private Stage stage;
    private Stage owner;

    public void initialize() {
        checkColumn.setCellValueFactory(cellData -> cellData.getValue().isCheckedProperty());
        checkColumn.setCellFactory(CheckBoxTableCell.forTableColumn((Integer v) -> {
            return penerimaanTable.getItems().get(v).isCheckedProperty();
        }));

        noPenerimaanColumn.setCellValueFactory(cellData -> cellData.getValue().noPenerimaanProperty());
        noPenerimaanColumn.setCellFactory(col -> Function.getWrapTableCell(noPenerimaanColumn));

        tglPenerimaanColumn.setCellValueFactory(cellData -> {
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getTglPenerimaan())));
            } catch (Exception ex) {
                return null;
            }
        });
        tglPenerimaanColumn.setCellFactory(col -> Function.getWrapTableCell(tglPenerimaanColumn));
        tglPenerimaanColumn.setComparator(Function.sortDate(tglLengkap));

        kodeGudangColumn.setCellValueFactory(cellData -> cellData.getValue().kodeGudangProperty());
        kodeGudangColumn.setCellFactory(col -> Function.getWrapTableCell(kodeGudangColumn));

        kodeBahanColumn.setCellValueFactory(cellData -> cellData.getValue().kodeBahanProperty());
        kodeBahanColumn.setCellFactory(col -> Function.getWrapTableCell(kodeBahanColumn));

        kodeKategoriColumn.setCellValueFactory(cellData -> cellData.getValue().kodeKategoriProperty());
        kodeKategoriColumn.setCellFactory(col -> Function.getWrapTableCell(kodeKategoriColumn));

        namaBahanColumn.setCellValueFactory(cellData -> cellData.getValue().namaBahanProperty());
        namaBahanColumn.setCellFactory(col -> Function.getWrapTableCell(namaBahanColumn));

        keteranganColumn.setCellValueFactory(cellData -> cellData.getValue().keteranganProperty());
        keteranganColumn.setCellFactory(col -> Function.getWrapTableCell(keteranganColumn));

        beratTimbanganColumn.setCellValueFactory(celldata -> celldata.getValue().beratTimbanganProperty());
        beratTimbanganColumn.setCellFactory(col -> Function.getTableCell());

        beratKotorColumn.setCellValueFactory(celldata -> celldata.getValue().beratKotorProperty());
        beratKotorColumn.setCellFactory(col -> Function.getTableCell());

        beratBersihColumn.setCellValueFactory(celldata -> celldata.getValue().beratBersihProperty());
        beratBersihColumn.setCellFactory(col -> Function.getTableCell());

        panjangColumn.setCellValueFactory(celldata -> celldata.getValue().panjangProperty());
        panjangColumn.setCellFactory(col -> Function.getTableCell());

        kodeUserColumn.setCellValueFactory(cellData -> cellData.getValue().kodeUserProperty());
        kodeUserColumn.setCellFactory(col -> Function.getWrapTableCell(kodeUserColumn));

        statusColumn.setCellValueFactory(cellData -> {
            if (cellData.getValue().getStatus().equals("close")) {
                return new SimpleStringProperty("Done");
            } else if (cellData.getValue().getStatus().equals("open")) {
                return new SimpleStringProperty("Wait");
            } else if (cellData.getValue().getStatus().equals("false")) {
                return new SimpleStringProperty("Cancel");
            } else {
                return null;
            }
        });
        statusColumn.setCellFactory(col -> Function.getWrapTableCell(statusColumn));

        final ContextMenu rm = new ContextMenu();
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e) -> {
            penerimaanTable.refresh();
        });
        rm.getItems().addAll(refresh);
        penerimaanTable.setContextMenu(rm);
        penerimaanTable.setRowFactory((TableView<PenerimaanBahan> tableView) -> {
            final TableRow<PenerimaanBahan> row = new TableRow<PenerimaanBahan>() {
            };
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)
                        && mouseEvent.getClickCount() == 2) {
                    if (row.getItem() != null) {
                        if (row.getItem().isIsChecked()) {
                            row.getItem().setIsChecked(false);
                        } else {
                            row.getItem().setIsChecked(true);
                        }
                    }
                }
            });
            return row;
        });
        allPenerimaan.addListener((ListChangeListener.Change<? extends PenerimaanBahan> change) -> {
            searchPenerimaan();
        });
        searchField.textProperty().addListener(
                (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                    searchPenerimaan();
                });
        filterData.addAll(allPenerimaan);
        penerimaanTable.setItems(filterData);
    }

    public void setMainApp(Main mainApp, Stage owner, Stage stage) {
        this.mainApp = mainApp;
        this.owner = owner;
        this.stage = stage;
        stage.setHeight(mainApp.screenSize.getHeight() * 0.9);
        stage.setWidth(mainApp.screenSize.getWidth() * 0.9);
        stage.setX((mainApp.screenSize.getWidth() - stage.getWidth()) / 2);
        stage.setY((mainApp.screenSize.getHeight() - stage.getHeight()) / 2);
        stage.setOnCloseRequest((e) -> {
            mainApp.closeDialog(owner, stage);
        });
    }

    @FXML
    private void checkAllHandle() {
        for (PenerimaanBahan d : allPenerimaan) {
            d.setIsChecked(checkAll.isSelected());
        }
        penerimaanTable.refresh();
    }

    public void setPenerimaan(String gudang) {
        Task<List<PenerimaanBahan>> task = new Task<List<PenerimaanBahan>>() {
            @Override
            public List<PenerimaanBahan> call() throws Exception {
                try (Connection con = Koneksi.getConnection()) {
                    List<PenerimaanBahan> listPenerimaan = PenerimaanBahanDAO.getAllByKategoriAndGudangAndStatus(con,
                            "%", gudang, "open");
                    return listPenerimaan;
                }
            }

        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((ev) -> {
            mainApp.closeLoading();
            allPenerimaan.clear();
            allPenerimaan.addAll(task.getValue());
        });
        task.setOnFailed((e) -> {
            task.getException().printStackTrace();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            mainApp.closeLoading();
        });
        new Thread(task).start();
    }

    public void close() {
        mainApp.closeDialog(owner, stage);
    }

    private Boolean checkColumn(String column) {
        if (column != null) {
            if (column.toLowerCase().contains(searchField.getText().toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    private void searchPenerimaan() {
        try {
            filterData.clear();
            for (PenerimaanBahan temp : allPenerimaan) {
                if (searchField.getText() == null || searchField.getText().equals("")) {
                    filterData.add(temp);
                } else {
                    if (checkColumn(temp.getNoPenerimaan())
                            || checkColumn(tglLengkap.format(tglSql.parse(temp.getTglPenerimaan())))
                            || checkColumn(temp.getKodeBahan())
                            || checkColumn(temp.getKodeGudang())
                            || checkColumn(temp.getKodeKategori())
                            || checkColumn(temp.getNamaBahan())
                            || checkColumn(temp.getKeterangan())
                            || checkColumn(temp.getKodeUser())
                            || checkColumn(df.format(temp.getBeratKotor()))
                            || checkColumn(df.format(temp.getBeratBersih()))
                            || checkColumn(df.format(temp.getPanjang()))
                            || checkColumn(df.format(temp.getBeratTimbangan()))) {
                        filterData.add(temp);
                    }
                }
            }
        } catch (Exception e) {
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
            e.printStackTrace();
        }
    }

}
