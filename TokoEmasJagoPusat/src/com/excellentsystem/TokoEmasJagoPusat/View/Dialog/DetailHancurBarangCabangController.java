/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.View.Dialog;

import com.excellentsystem.TokoEmasJagoPusat.DAO.HancurDetailDAO;
import com.excellentsystem.TokoEmasJagoPusat.Function;
import static com.excellentsystem.TokoEmasJagoPusat.Function.getTableCell;
import com.excellentsystem.TokoEmasJagoPusat.KoneksiPusat;
import com.excellentsystem.TokoEmasJagoPusat.Main;
import static com.excellentsystem.TokoEmasJagoPusat.Main.gr;
import static com.excellentsystem.TokoEmasJagoPusat.Main.rp;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglLengkap;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglSql;
import com.excellentsystem.TokoEmasJagoPusat.Model.BarangCabang;
import com.excellentsystem.TokoEmasJagoPusat.Model.HancurDetail;
import com.excellentsystem.TokoEmasJagoPusat.Model.HancurHead;
import java.sql.Connection;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Excellent
 */
public class DetailHancurBarangCabangController  {

    @FXML private TableView<HancurDetail> hancurDetailTable;
    @FXML private TableColumn<HancurDetail, String> kodeBarcodeColumn;
    @FXML private TableColumn<HancurDetail, String> namaBarangColumn;
    @FXML private TableColumn<HancurDetail, String> kodeKategoriColumn;
    @FXML private TableColumn<HancurDetail, String> kodeJenisColumn;
    @FXML private TableColumn<HancurDetail, Number> beratColumn;
    @FXML private TableColumn<HancurDetail, Number> beratAsliColumn;
    @FXML private TableColumn<HancurDetail, Number> persentaseEmasColumn;
    @FXML private TableColumn<HancurDetail, Number> beratPersenColumn;
    @FXML private TableColumn<HancurDetail, String> asalBarangColumn;
    @FXML private TableColumn<HancurDetail, String> tglBarcodeColumn;
    
    @FXML private TextField noHancurField;
    @FXML private TextField tglHancurField;
    @FXML private TextField kodeCabangField;
    @FXML private TextField kodeUserField;
    
    @FXML private Label totalQtyLabel;
    @FXML private Label totalBeratLabel;
    @FXML private Label totalBeratAsliLabel;
    @FXML private Label totalBeratPersenLabel;
    
    private ObservableList<HancurDetail> listHancurDetail = FXCollections.observableArrayList();
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    public void initialize() {
        kodeBarcodeColumn.setCellValueFactory(cellData -> cellData.getValue().kodeBarcodeProperty());
        namaBarangColumn.setCellValueFactory(cellData -> cellData.getValue().namaBarangProperty());
        kodeKategoriColumn.setCellValueFactory(cellData -> cellData.getValue().kodeKategoriProperty());
        kodeJenisColumn.setCellValueFactory(cellData -> cellData.getValue().kodeJenisProperty());
        beratColumn.setCellValueFactory(cellData -> cellData.getValue().beratProperty());
        beratColumn.setCellFactory(col -> getTableCell(gr));
        beratAsliColumn.setCellValueFactory(cellData -> cellData.getValue().beratAsliProperty());
        beratAsliColumn.setCellFactory(col -> getTableCell(gr));
        persentaseEmasColumn.setCellValueFactory(cellData -> cellData.getValue().persentaseRosokProperty());
        persentaseEmasColumn.setCellFactory(col -> getTableCell(gr));
        beratPersenColumn.setCellValueFactory(cellData -> cellData.getValue().beratPersenRosokProperty());
        beratPersenColumn.setCellFactory(col -> getTableCell(gr));
        tglBarcodeColumn.setCellValueFactory(cellData -> { 
            try {
                return new SimpleStringProperty(tglLengkap.format(tglSql.parse(cellData.getValue().getInputDate())));
            } catch (Exception ex) {
                return null;
            }
        });
        tglBarcodeColumn.setComparator(Function.sortDate(tglLengkap));
        asalBarangColumn.setCellValueFactory(cellData -> cellData.getValue().asalBarangProperty());
        
        final ContextMenu menu = new ContextMenu();
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            hancurDetailTable.refresh();
        });
        menu.getItems().addAll(refresh);
        hancurDetailTable.setContextMenu(menu);
        hancurDetailTable.setRowFactory(table -> {
            TableRow<HancurDetail> row = new TableRow<HancurDetail>(){
                @Override
                public void updateItem(HancurDetail item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(menu);
                    }else{
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem detail = new MenuItem("Detail Barang");
                        detail.setOnAction((ActionEvent event) -> {
                            detailBarang(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent event) -> {
                            hancurDetailTable.refresh();
                        });
                        rowMenu.getItems().add(detail);
                        rowMenu.getItems().addAll(refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY) && mouseEvent.getClickCount() == 2){
                    if(row.getItem()!=null)
                        detailBarang(row.getItem());
                    
                }
            });
            return row;
        });
        hancurDetailTable.setItems(listHancurDetail);
                
    }    
    public void setMainApp(Main main, Stage owner, Stage stage){
        this.mainApp = main;
        this.stage = stage;
        this.owner = owner;
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        stage.setHeight(mainApp.MainStage.getHeight()*0.9);
        stage.setWidth(mainApp.MainStage.getWidth()*0.9);
        stage.setX((mainApp.screenSize.getWidth() - stage.getWidth()) / 2);
        stage.setY((mainApp.screenSize.getHeight() - stage.getHeight()) / 2);
    }
    private void detailBarang(HancurDetail d){
        BarangCabang brg = new BarangCabang();
        brg.setKodeBarang(d.getKodeBarang());
        brg.setKodeBarcode(d.getKodeBarcode());
        brg.setNamaBarang(d.getNamaBarang());
        brg.setKodeKategori(d.getKodeKategori());
        brg.setKodeJenis(d.getKodeJenis());
        brg.setKodeIntern(d.getKodeIntern());
        brg.setKadar(d.getKadar());
        brg.setBerat(d.getBerat());
        brg.setBeratAsli(d.getBeratAsli());
        brg.setBeratPersen(d.getBeratPersen());
        brg.setNilaiPokok(d.getNilaiPokok());
        brg.setInputDate(d.getInputDate());
        brg.setInputBy(d.getInputBy());
        brg.setAsalBarang(d.getAsalBarang());
        brg.setStatus(d.getStatus());
        
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage ,child, "View/Dialog/DetailBarang.fxml");
        DetailBarangController controller = loader.getController();
        controller.setMainApp(mainApp, stage, child);
        controller.setBarang(brg);
    }
    public void setHancurBarangCabang(HancurHead h){
        Task<List<HancurDetail>> task = new Task<List<HancurDetail>>() {
            @Override 
            public List<HancurDetail> call() throws Exception{
                try(Connection conPusat = KoneksiPusat.getConnection()){
                    return HancurDetailDAO.getAllByNoHancur(conPusat, h.getNoHancur());
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((ev) -> {
            try{
                mainApp.closeLoading();
                listHancurDetail.clear();
                listHancurDetail.addAll(task.getValue());
                
                noHancurField.setText(h.getNoHancur());
                tglHancurField.setText(tglLengkap.format(tglSql.parse(h.getTglHancur())));
                kodeCabangField.setText(h.getKodeCabang());
                kodeUserField.setText(h.getKodeUser());
                
                hitungTotal();
            }catch(Exception e){
                mainApp.showMessage(Modality.NONE, "Error", e.toString());
            }
            
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            task.getException().printStackTrace();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    private void hitungTotal(){
        double qty = 0;
        double berat = 0;
        double beratAsli = 0;
        double beratPersen = 0;
        for(HancurDetail d : listHancurDetail){
            qty = qty + 1;
            berat = berat + d.getBerat();
            beratAsli = beratAsli + d.getBeratAsli();
            beratPersen = beratPersen + d.getBeratPersenRosok();
        }
        totalQtyLabel.setText(rp.format(qty));
        totalBeratLabel.setText(gr.format(berat));
        totalBeratAsliLabel.setText(gr.format(beratAsli));
        totalBeratPersenLabel.setText(gr.format(beratPersen));
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
}
