/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.AuriSteel.View;

import com.excellentsystem.AuriSteel.DAO.BahanDAO;
import com.excellentsystem.AuriSteel.Function;
import static com.excellentsystem.AuriSteel.Function.createRow;
import com.excellentsystem.AuriSteel.Koneksi;
import com.excellentsystem.AuriSteel.Main;
import static com.excellentsystem.AuriSteel.Main.df;
import static com.excellentsystem.AuriSteel.Main.sistem;
import com.excellentsystem.AuriSteel.Model.Bahan;
import com.excellentsystem.AuriSteel.Model.Otoritas;
import com.excellentsystem.AuriSteel.Services.Service;
import com.excellentsystem.AuriSteel.View.Dialog.DetailBahanBakuController;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableRow;
import javafx.scene.control.TreeTableView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class DataBahanController  {
    @FXML private TreeTableView<Bahan> bahanTable;
    @FXML private TreeTableColumn<Bahan, String> kodeBahanColumn;
    @FXML private TreeTableColumn<Bahan, String> namaBahanColumn;
    @FXML private TreeTableColumn<Bahan, Number> beratKotorColumn;
    @FXML private TreeTableColumn<Bahan, Number> beratBersihColumn;
    @FXML private TreeTableColumn<Bahan, Number> panjangColumn;
    @FXML private TreeTableColumn<Bahan, Number> hargaBeliColumn;
    
    @FXML private TextField searchField;
    @FXML private ComboBox<String> groupByCombo;
    @FXML private ComboBox<String> statusCombo;
    @FXML private Label totalBeratKotorLabel;
    @FXML private Label totalBeratBersihLabel;
    @FXML private Label totalPanjangLabel;
    @FXML private Label totalHargaLabel;
    
    private final TreeItem<Bahan> root = new TreeItem<>();
    private final ObservableList<Bahan> allBahan = FXCollections.observableArrayList();
    private final ObservableList<Bahan> filterData = FXCollections.observableArrayList();
    private Main mainApp; 
    public void initialize() {
        kodeBahanColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().kodeBahanProperty());
        kodeBahanColumn.setCellFactory(col -> Function.getWrapTreeTableCell(kodeBahanColumn));
        
        namaBahanColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().namaBahanProperty());
        namaBahanColumn.setCellFactory(col -> Function.getWrapTreeTableCell(namaBahanColumn));
        
        hargaBeliColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().hargaBeliProperty());
        hargaBeliColumn.setCellFactory(col -> Function.getTreeTableCell());
        
        beratKotorColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().beratKotorProperty());
        beratKotorColumn.setCellFactory(col -> Function.getTreeTableCell());
        
        beratBersihColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().beratBersihProperty());
        beratBersihColumn.setCellFactory(col -> Function.getTreeTableCell());
        
        panjangColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().panjangProperty());
        panjangColumn.setCellFactory(col -> Function.getTreeTableCell());
        
        allBahan.addListener((ListChangeListener.Change<? extends Bahan> change) -> {
            searchBahan();
        });
        searchField.textProperty().addListener(
            (ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchBahan();
        });
        filterData.addAll(allBahan);
        
        final ContextMenu rm = new ContextMenu();
        MenuItem export = new MenuItem("Export Excel");
        export.setOnAction((ActionEvent e)->{
            exportExcel();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent e)->{
            getBahan();
        });
        for(Otoritas o : sistem.getUser().getOtoritas()){
            if(o.getJenis().equals("Export Excel")&&o.isStatus())
                rm.getItems().add(export);
        }
        rm.getItems().addAll(refresh);
        bahanTable.setContextMenu(rm);
        bahanTable.setRowFactory((TreeTableView<Bahan> tableView) -> {
            final TreeTableRow<Bahan> row = new TreeTableRow<Bahan>(){
                @Override
                public void updateItem(Bahan item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(rm);
                    } else{
                        final ContextMenu rm = new ContextMenu();
                        MenuItem edit = new MenuItem("Edit Bahan");
                        edit.setOnAction((ActionEvent e)->{
                            editBahan(item);
                        });
                        MenuItem export = new MenuItem("Export Excel");
                        export.setOnAction((ActionEvent e)->{
                            exportExcel();
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent e)->{
                            getBahan();
                        });
                        if(item.getNamaBahan()!=null){
                            for(Otoritas o : sistem.getUser().getOtoritas()){
                                if(o.getJenis().equals("Edit Bahan")&&o.isStatus())
                                    rm.getItems().add(edit);
                                if(o.getJenis().equals("Export Excel")&&o.isStatus())
                                    rm.getItems().add(export);
                            }
                        }
                        rm.getItems().addAll(refresh);
                        setContextMenu(rm);
                    }
                }
            };
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&mouseEvent.getClickCount() == 2){
                    if(row.getItem()!=null && row.getItem().getNamaBahan()!=null){
                        for(Otoritas o : sistem.getUser().getOtoritas()){
                            if(o.getJenis().equals("Edit Barang")&&o.isStatus())
                                editBahan(row.getItem());
                        }
                    }
                }
            });
            return row;
        });
    }
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
        ObservableList<String> status = FXCollections.observableArrayList();
        status.add("Tersedia");
        status.add("Sudah Diproduksi/Dijual");
        status.add("Semua");
        statusCombo.setItems(status);
        statusCombo.getSelectionModel().select("Tersedia");
        ObservableList<String> group = FXCollections.observableArrayList();
        group.add("Kode Kategori");
        group.add("No Kontrak");
        groupByCombo.setItems(group);
        groupByCombo.getSelectionModel().select("Kode Kategori");
        getBahan();
    }
    @FXML
    private void getBahan(){
        Task<List<Bahan>> task = new Task<List<Bahan>>() {
            @Override 
            public List<Bahan> call() throws Exception{
                String status = "%";
                if(statusCombo.getSelectionModel().getSelectedItem().equals("Tersedia"))
                    status = "true";
                else if(statusCombo.getSelectionModel().getSelectedItem().equals("Sudah Diproduksi/Dijual"))
                    status = "false";
                try (Connection con = Koneksi.getConnection()) {
                    return BahanDAO.getAllByStatus(con, status);
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent e) -> {
            mainApp.closeLoading();
            allBahan.clear();
            allBahan.addAll(task.getValue());
        });
        task.setOnFailed((e) -> {
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            mainApp.closeLoading();
        });
        new Thread(task).start();
    }
    private Boolean checkColumn(String column){
        if(column!=null){
            if(column.toLowerCase().contains(searchField.getText().toLowerCase()))
                return true;
        }
        return false;
    }
    private void searchBahan() {
        filterData.clear();
        for (Bahan temp : allBahan) {
            if (searchField.getText() == null || searchField.getText().equals(""))
                filterData.add(temp);
            else{
                if(checkColumn(temp.getKodeBahan())||
                    checkColumn(temp.getNoKontrak())||
                    checkColumn(temp.getKodeKategori())||
                    checkColumn(temp.getNamaBahan())||
                    checkColumn(temp.getSpesifikasi())||
                    checkColumn(temp.getKeterangan())||
                    checkColumn(df.format(temp.getBeratBersih()))||
                    checkColumn(df.format(temp.getBeratKotor()))||
                    checkColumn(df.format(temp.getPanjang()))||
                    checkColumn(df.format(temp.getHargaBeli())))
                    filterData.add(temp);
            }
        }
        hitungTotal();
        setTable();
    }
    private void setTable(){
        if(bahanTable.getRoot()!=null)
            bahanTable.getRoot().getChildren().clear();
        List<String> groupBy = new ArrayList<>();
        for(Bahan b : filterData){
            if(groupByCombo.getSelectionModel().getSelectedItem().equals("Kode Kategori")){
                if(!groupBy.contains(b.getKodeKategori()))
                    groupBy.add(b.getKodeKategori());
            }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("No Kontrak")){
                if(!groupBy.contains(b.getNoKontrak()))
                    groupBy.add(b.getNoKontrak());
            }
        }
        for(String kategori : groupBy){
            double totalBeratKotorKategori = 0;
            double totalBeratBersihKategori = 0;
            double totalPanjangKategori = 0;
            double hargaBeliKategori = 0;
            TreeItem<Bahan> parentKategori = new TreeItem<>(new Bahan());
            
            for(Bahan b: filterData){
                boolean status = false;
                if(groupByCombo.getSelectionModel().getSelectedItem().equals("Kode Kategori") && kategori.equals(b.getKodeKategori())){
                    status = true;
                }else if(groupByCombo.getSelectionModel().getSelectedItem().equals("No Kontrak") && kategori.equals(b.getNoKontrak())){
                    status = true;
                }
                if(status){
                    TreeItem<Bahan> child = new TreeItem<>(b);
                    parentKategori.getChildren().add(child);

                    totalBeratKotorKategori = totalBeratKotorKategori + b.getBeratKotor();
                    totalBeratBersihKategori = totalBeratBersihKategori + b.getBeratBersih();
                    totalPanjangKategori = totalPanjangKategori + b.getPanjang();
                    hargaBeliKategori = hargaBeliKategori + b.getHargaBeli();
                }
            }
            
            parentKategori.getValue().setKodeBahan(kategori);
            parentKategori.getValue().setBeratKotor(totalBeratKotorKategori);
            parentKategori.getValue().setBeratBersih(totalBeratBersihKategori);
            parentKategori.getValue().setPanjang(totalPanjangKategori);
            parentKategori.getValue().setHargaBeli(hargaBeliKategori);
            root.getChildren().add(parentKategori);
        }
        bahanTable.setRoot(root);
    }
    private void hitungTotal(){
        double totalBeratKotor = 0;
        double totalBeratBersih = 0;
        double totalPanjang = 0;
        double totalHarga = 0;
        for(Bahan b : filterData){
            totalBeratKotor = totalBeratKotor + b.getBeratKotor();
            totalBeratBersih = totalBeratBersih + b.getBeratBersih();
            totalPanjang = totalPanjang + b.getPanjang();
            totalHarga = totalHarga + b.getHargaBeli();
        }
        totalBeratKotorLabel.setText(df.format(totalBeratKotor));
        totalBeratBersihLabel.setText(df.format(totalBeratBersih));
        totalPanjangLabel.setText(df.format(totalPanjang));
        totalHargaLabel.setText(df.format(totalHarga));
    }
    private void editBahan(Bahan b){
        Stage stage = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, stage, "View/Dialog/DetailBahanBaku.fxml");
        DetailBahanBakuController controller = loader.getController();
        controller.setMainApp(mainApp,mainApp.MainStage, stage);
        controller.setBahan(b);
        controller.saveButton.setOnAction((ActionEvent event) -> {
            Task<String> task = new Task<String>() {
                @Override 
                public String call() throws Exception{
                    try (Connection con = Koneksi.getConnection()) {
                        b.setNamaBahan(controller.namaBahanField.getText());
                        b.setSpesifikasi(controller.spesifikasiField.getText());
                        b.setKeterangan(controller.keteranganField.getText());
                        return Service.updateBahan(con, b);
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((e) -> {
                mainApp.closeLoading();
                getBahan();
                if(task.getValue().equals("true")){
                    mainApp.showMessage(Modality.NONE, "Success", "Data bahan baku berhasil disimpan");
                    mainApp.closeDialog(mainApp.MainStage,stage);
                }else{
                    mainApp.showMessage(Modality.NONE, "Error", task.getValue());
                }
            });
            task.setOnFailed((e) -> {
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                mainApp.closeLoading();
            });
            new Thread(task).start();
        });
    }
    private void exportExcel() {
        try{
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select location to export");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Excel Document 2007", "*.xlsx"),
                    new FileChooser.ExtensionFilter("Excel Document 1997-2007", "*.xls")
            );
            File file = fileChooser.showSaveDialog(mainApp.MainStage);
            if (file != null) {
                Workbook workbook;
                if (file.getPath().endsWith("xlsx")) {
                    workbook = new XSSFWorkbook();
                } else if (file.getPath().endsWith("xls")) {
                    workbook = new HSSFWorkbook();
                } else {
                    throw new IllegalArgumentException("The specified file is not Excel file");
                }
                Font bold = workbook.createFont();
                bold.setBold(true);
                
                CellStyle H1 = workbook.createCellStyle();
                H1.setFont(bold);
                CellStyle H2 = workbook.createCellStyle();
                H2.setFont(bold);
                H2.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
                H2.setFillPattern(CellStyle.SOLID_FOREGROUND);
                
                Sheet sheet = workbook.createSheet("Data Bahan");
                int rc = 0;
                int c = 10;
                createRow(workbook, sheet, rc, c, "Bold");
                sheet.getRow(rc).getCell(0).setCellValue("Status : "+statusCombo.getSelectionModel().getSelectedItem());
                rc++;
                createRow(workbook, sheet, rc, c, "Bold");
                sheet.getRow(rc).getCell(0).setCellValue("Filter : "+searchField.getText());
                rc++;
                createRow(workbook, sheet, rc, c, "Header");
                sheet.getRow(rc).getCell(0).setCellValue("No Kontrak"); 
                sheet.getRow(rc).getCell(1).setCellValue("Kode Bahan"); 
                sheet.getRow(rc).getCell(2).setCellValue("Nama Bahan"); 
                sheet.getRow(rc).getCell(3).setCellValue("Spesifikasi"); 
                sheet.getRow(rc).getCell(4).setCellValue("Keterangan"); 
                sheet.getRow(rc).getCell(5).setCellValue("Berat Kotor"); 
                sheet.getRow(rc).getCell(6).setCellValue("Berat Bersih"); 
                sheet.getRow(rc).getCell(7).setCellValue("Panjang"); 
                sheet.getRow(rc).getCell(8).setCellValue("Harga Beli"); 
                rc++;
                double beratKotor = 0;
                double beratBersih = 0;
                double panjang = 0;
                double hargaBeli = 0;
                for (Bahan b : filterData) {
                    createRow(workbook, sheet, rc, c, "Detail");
                    sheet.getRow(rc).getCell(0).setCellValue(b.getNoKontrak());
                    sheet.getRow(rc).getCell(1).setCellValue(b.getKodeBahan());
                    sheet.getRow(rc).getCell(2).setCellValue(b.getNamaBahan());
                    sheet.getRow(rc).getCell(3).setCellValue(b.getSpesifikasi());
                    sheet.getRow(rc).getCell(4).setCellValue(b.getKeterangan());
                    sheet.getRow(rc).getCell(5).setCellValue(b.getBeratKotor());
                    sheet.getRow(rc).getCell(6).setCellValue(b.getBeratBersih());
                    sheet.getRow(rc).getCell(7).setCellValue(b.getPanjang());
                    sheet.getRow(rc).getCell(8).setCellValue(b.getHargaBeli());
                    rc++;
                    
                    beratKotor = beratKotor + b.getBeratKotor();
                    beratBersih = beratBersih + b.getBeratBersih();
                    panjang = panjang + b.getPanjang();
                    hargaBeli = hargaBeli + b.getHargaBeli();
                }
                createRow(workbook, sheet, rc, c, "Header");
                sheet.getRow(rc).getCell(0).setCellValue("Total :");
                sheet.getRow(rc).getCell(5).setCellValue(beratKotor);
                sheet.getRow(rc).getCell(6).setCellValue(beratBersih);
                sheet.getRow(rc).getCell(7).setCellValue(panjang);
                sheet.getRow(rc).getCell(8).setCellValue(hargaBeli);
                for(int i=0 ; i<c ; i++){ sheet.autoSizeColumn(i);}
                try (FileOutputStream outputStream = new FileOutputStream(file)) {
                    workbook.write(outputStream);
                }
            }
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
            e.printStackTrace();
        }
    }
}
