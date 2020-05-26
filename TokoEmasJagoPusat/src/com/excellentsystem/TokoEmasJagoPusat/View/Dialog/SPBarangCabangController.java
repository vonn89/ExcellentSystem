/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.View.Dialog;

import com.excellentsystem.TokoEmasJagoPusat.DAO.CabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.JenisDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.KategoriDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.RevisiBarangCabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.RosokCabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.StokBarangCabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.Function;
import static com.excellentsystem.TokoEmasJagoPusat.Function.getTableCell;
import static com.excellentsystem.TokoEmasJagoPusat.Function.pembulatan;
import com.excellentsystem.TokoEmasJagoPusat.KoneksiPusat;
import com.excellentsystem.TokoEmasJagoPusat.Main;
import static com.excellentsystem.TokoEmasJagoPusat.Main.gr;
import static com.excellentsystem.TokoEmasJagoPusat.Main.rp;
import static com.excellentsystem.TokoEmasJagoPusat.Main.sistem;
import static com.excellentsystem.TokoEmasJagoPusat.Main.user;
import com.excellentsystem.TokoEmasJagoPusat.Model.Cabang;
import com.excellentsystem.TokoEmasJagoPusat.Model.Jenis;
import com.excellentsystem.TokoEmasJagoPusat.Model.Kategori;
import com.excellentsystem.TokoEmasJagoPusat.Model.RevisiBarangCabang;
import com.excellentsystem.TokoEmasJagoPusat.Model.RosokCabang;
import com.excellentsystem.TokoEmasJagoPusat.Model.SPDetail;
import com.excellentsystem.TokoEmasJagoPusat.Model.StokBarangCabang;
import com.excellentsystem.TokoEmasJagoPusat.Service.Service;
import java.sql.Connection;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Excellent
 */
public class SPBarangCabangController {

    @FXML private TableView<StokBarangCabang> stokBalenanTable;
    @FXML private TableColumn<StokBarangCabang, String> jenisBalenanStokColumn;
    @FXML private TableColumn<StokBarangCabang, Number> qtyStokColumn;
    @FXML private TableColumn<StokBarangCabang, Number> beratStokColumn;
    
    @FXML public TableView<SPDetail> spDetailTable;
    @FXML private TableColumn<SPDetail, String> jenisBalenanColumn;
    @FXML private TableColumn<SPDetail, String> kodeJenisColumn;
    @FXML private TableColumn<SPDetail, Number> qtyColumn;
    @FXML private TableColumn<SPDetail, Number> beratColumn;
    
    @FXML public ComboBox<String> cabangCombo;
    @FXML public ComboBox<String> jenisSpCombo;
    
    @FXML private TextField jenisBalenanField;
    @FXML private ComboBox<String> kodeJenisCombo;
    @FXML private TextField qtyField;
    @FXML private TextField beratField;
    @FXML private Button addButton;
    
    @FXML private Label totalQtyLabel;
    @FXML private Label totalBeratLabel;
    @FXML public Button saveButton;
    
    private StokBarangCabang stokBalenan;
    private ObservableList<String> allCabang = FXCollections.observableArrayList();
    private ObservableList<String> allJenisSp = FXCollections.observableArrayList();
    private ObservableList<String> allKodeJenis = FXCollections.observableArrayList(); 
    private List<Jenis> allJenis; 
    private ObservableList<StokBarangCabang> listStokBalenanCabang = FXCollections.observableArrayList();
    public ObservableList<SPDetail> listSPDetail = FXCollections.observableArrayList();
    private Main mainApp;
    private Stage stage;
    private Stage owner;
    public void initialize() {
        jenisBalenanStokColumn.setCellValueFactory(cellData -> cellData.getValue().kodeJenisProperty());
        qtyStokColumn.setCellValueFactory(cellData -> cellData.getValue().stokAkhirProperty());
        qtyStokColumn.setCellFactory(col -> getTableCell(rp));
        beratStokColumn.setCellValueFactory(cellData -> cellData.getValue().beratAkhirProperty());
        beratStokColumn.setCellFactory(col -> getTableCell(gr));
        
        jenisBalenanColumn.setCellValueFactory(cellData -> cellData.getValue().kodeJenisBalenanProperty());
        kodeJenisColumn.setCellValueFactory(cellData -> cellData.getValue().kodeJenisProperty());
        qtyColumn.setCellValueFactory(cellData -> cellData.getValue().qtyProperty());
        qtyColumn.setCellFactory(col -> getTableCell(rp));
        beratColumn.setCellValueFactory(cellData -> cellData.getValue().beratProperty());
        beratColumn.setCellFactory(col -> getTableCell(gr));
        
        final ContextMenu menu = new ContextMenu();
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            spDetailTable.refresh();
        });
        menu.getItems().addAll(refresh);
        spDetailTable.setContextMenu(menu);
        spDetailTable.setRowFactory(table -> {
            TableRow<SPDetail> row = new TableRow<SPDetail>(){
                @Override
                public void updateItem(SPDetail item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(menu);
                    }else{
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem hapus = new MenuItem("Hapus Barang");
                        hapus.setOnAction((ActionEvent event) -> {
                            hapusBarang(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent event) -> {
                            spDetailTable.refresh();
                        });
                        rowMenu.getItems().add(hapus);
                        rowMenu.getItems().addAll(refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            return row;
        });
        final ContextMenu menu2 = new ContextMenu();
        MenuItem ambilRosok = new MenuItem("Ambil Rosok Cabang");
        ambilRosok.setOnAction((ActionEvent event) -> {
            ambilRosokCabang(null);
        });
        MenuItem refresh2 = new MenuItem("Refresh");
        refresh2.setOnAction((ActionEvent event) -> {
            stokBalenanTable.refresh();
        });
        menu2.getItems().addAll(ambilRosok);
        menu2.getItems().addAll(refresh2);
        stokBalenanTable.setContextMenu(menu2);
        stokBalenanTable.setRowFactory(table -> {
            TableRow<StokBarangCabang> row = new TableRow<StokBarangCabang>(){
                @Override
                public void updateItem(StokBarangCabang item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(menu2);
                    }else{
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem revisi = new MenuItem("Revisi Barang Cabang");
                        revisi.setOnAction((ActionEvent event) -> {
                            revisiBarangCabang(item);
                        });
                        MenuItem pindah = new MenuItem("Pindah Rosok Cabang");
                        pindah.setOnAction((ActionEvent event) -> {
                            pindahRosokCabang(item);
                        });
                        MenuItem ambilRosok = new MenuItem("Ambil Rosok Cabang");
                        ambilRosok.setOnAction((ActionEvent event) -> {
                            ambilRosokCabang(item);
                        });
                        MenuItem refresh = new MenuItem("Refresh");
                        refresh.setOnAction((ActionEvent event) -> {
                            stokBalenanTable.refresh();
                        });
                        rowMenu.getItems().addAll(revisi);
                        rowMenu.getItems().addAll(pindah);
                        rowMenu.getItems().addAll(ambilRosok);
                        rowMenu.getItems().addAll(refresh);
                        setContextMenu(rowMenu);
                    }
                }
            };
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&mouseEvent.getClickCount() == 2){
                    if(row.getItem()!=null)
                        selectBarang(row.getItem());
                }
            });
            return row;
        });
        
        cabangCombo.setItems(allCabang);
        jenisSpCombo.setItems(allJenisSp);
        kodeJenisCombo.setItems(allKodeJenis);
        stokBalenanTable.setItems(listStokBalenanCabang);
        spDetailTable.setItems(listSPDetail);
        
        Function.setNumberField(qtyField, rp);
        Function.setNumberField(beratField, gr);
        kodeJenisCombo.setOnAction((event) -> {
            setKodeJenis();
        });
        kodeJenisCombo.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  
                setKodeJenis();
        });
        kodeJenisCombo.getEditor().setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                setKodeJenis();
            }
        });
        qtyField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                beratField.requestFocus();
                beratField.selectAll();
            }
        });
        beratField.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  
                addButton.fire();
        });
        addButton.setOnKeyPressed((KeyEvent keyEvent) -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  
                addButton.fire();
        });
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
        setData();
    }
    private void setData(){
        Task<List<Cabang>> task = new Task<List<Cabang>>() {
            @Override 
            public List<Cabang> call() throws Exception{
                try(Connection conPusat = KoneksiPusat.getConnection()){
                    allKodeJenis.clear();
                    allJenis = JenisDAO.getAll(conPusat);
                    List<Kategori> listKategori = KategoriDAO.getAll(conPusat);
                    for(Jenis j : allJenis){
                        for(Kategori k : listKategori){
                            if(j.getKodeKategori().equals(k.getKodeKategori()))
                                j.setKategori(k);
                        }
                        allKodeJenis.add(j.getKodeJenis());
                    }
                    return CabangDAO.getAll(conPusat);
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((e) -> {
            mainApp.closeLoading();
            List<Cabang> listCabang = task.getValue();
            allCabang.clear();
            for(Cabang c : listCabang){
                allCabang.add(c.getKodeCabang());
            }
            allJenisSp.clear();
            allJenisSp.add("SP");
            allJenisSp.add("Clear");
            jenisSpCombo.getSelectionModel().select("SP");
        });
        task.setOnFailed((e) -> {
            mainApp.closeLoading();
            task.getException().printStackTrace();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
        });
        new Thread(task).start();
    }
    @FXML
    private void setCabang(){
        if(cabangCombo.getSelectionModel().getSelectedItem()!=null){
            listSPDetail.clear();
            spDetailTable.refresh();
            getStokBalenanCabang();
        }
    }
    public void getStokBalenanCabang(){
        if(cabangCombo.getSelectionModel().getSelectedItem()!=null){
            Task<List<StokBarangCabang>> task = new Task<List<StokBarangCabang>>() {
                @Override 
                public List<StokBarangCabang> call() throws Exception{
                    try(Connection conPusat = KoneksiPusat.getConnection()){
                        return StokBarangCabangDAO.getAllNonBarcodeByCabangAndGudangAndKategoriAndJenis(conPusat, 
                                sistem.getTglSystem(), cabangCombo.getSelectionModel().getSelectedItem(), 
                                "BLN-"+cabangCombo.getSelectionModel().getSelectedItem(), "%", "%");
                    }
                }
            };
            task.setOnRunning((e) -> {
                mainApp.showLoadingScreen();
            });
            task.setOnSucceeded((e) -> {
                mainApp.closeLoading();
                listStokBalenanCabang.clear();
                List<StokBarangCabang> listStokBarang = task.getValue();
                for(StokBarangCabang s : listStokBarang){
                    int qty = 0;
                    double berat = 0;
                    for(SPDetail d : listSPDetail){
                        if(d.getKodeJenisBalenan().equals(s.getKodeJenis())){
                            qty = qty + d.getQty();
                            berat = berat + d.getBerat();
                        }
                    }
                    s.setStokAkhir(s.getStokAkhir()-qty);
                    s.setBeratAkhir(pembulatan(s.getBeratAkhir()-berat));
                    
                    if(s.getStokAkhir()>0 || s.getBeratAkhir()>0)
                        listStokBalenanCabang.add(s);
                }
                stokBalenanTable.refresh();
            });
            task.setOnFailed((e) -> {
                mainApp.closeLoading();
                task.getException().printStackTrace();
                mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            });
            new Thread(task).start();
        }else{
            listStokBalenanCabang.clear();
            stokBalenanTable.refresh();
        }
    }
    private void selectBarang(StokBarangCabang s){
        stokBalenan = s;
        jenisBalenanField.setText(s.getKodeJenis());
        kodeJenisCombo.getSelectionModel().select(s.getKodeJenis());
        qtyField.setText(rp.format(s.getStokAkhir()));
        beratField.setText(gr.format(s.getBeratAkhir()));
        qtyField.requestFocus();
        qtyField.selectAll();
    }
    @FXML
    private void setKodeJenis(){
        boolean status = false;
        for(String j : allKodeJenis){
            if(j.equalsIgnoreCase(kodeJenisCombo.getEditor().getText().toUpperCase())){
                kodeJenisCombo.getSelectionModel().select(j);
                status = true;
            }
        }
        if(status){
            qtyField.requestFocus();
            qtyField.selectAll();
        }else{
            kodeJenisCombo.getEditor().selectAll();
        }
    }
    @FXML
    private void addBarang(){
        if(stokBalenan==null)
            mainApp.showMessage(Modality.NONE, "Warning", "Barang balenan belum dipilih");
        else if(jenisBalenanField.getText().equals(""))
            mainApp.showMessage(Modality.NONE, "Warning", "Barang balenan belum dipilih");
        else if(kodeJenisCombo.getSelectionModel().getSelectedItem()==null)
            mainApp.showMessage(Modality.NONE, "Warning", "Kode jenis belum dipilih");
        else if(qtyField.getText().equals("0"))
            mainApp.showMessage(Modality.NONE, "Warning", "Qty masih kosong");
        else if(beratField.getText().equals("0"))
            mainApp.showMessage(Modality.NONE, "Warning", "Berat masih kosong");
        else if(Integer.parseInt(qtyField.getText().replaceAll(",", ""))>stokBalenan.getStokAkhir())
            mainApp.showMessage(Modality.NONE, "Warning", "Stok balenan tidak mencukupi");
        else if(Double.parseDouble(beratField.getText().replaceAll(",", ""))>stokBalenan.getBeratAkhir())
            mainApp.showMessage(Modality.NONE, "Warning", "Berat balenan tidak mencukupi");
        else{
            Kategori kategori = null;
            for(Jenis j : allJenis){
                if(j.getKodeJenis().equals(kodeJenisCombo.getSelectionModel().getSelectedItem()))
                    kategori = j.getKategori();
            }
            if(kategori==null)
                mainApp.showMessage(Modality.NONE, "Warning", "Kode jenis masih salah");
            else{
                int qty = Integer.parseInt(qtyField.getText().replaceAll(",", ""));
                double berat = Double.parseDouble(beratField.getText().replaceAll(",", ""));
                double beratPersenBalenan = pembulatan(stokBalenan.getBeratPersenAkhir()/stokBalenan.getBeratAkhir()*berat);
                double beratPersen = pembulatan(berat*kategori.getPersentaseEmas()/100);
                double nilaiPokok = pembulatan(stokBalenan.getNilaiAkhir()/stokBalenan.getBeratAkhir()*berat);

                SPDetail s = new SPDetail();
                s.setNoSP("");
                s.setNoUrut(0);
                s.setKodeKategoriBalenan(stokBalenan.getKodeKategori());
                s.setKodeJenisBalenan(stokBalenan.getKodeJenis());
                s.setKodeKategori(kategori.getKodeKategori());
                s.setKodeJenis(kodeJenisCombo.getSelectionModel().getSelectedItem());
                s.setQty(qty);
                s.setBerat(berat);
                s.setBeratPersenBalenan(beratPersenBalenan);
                s.setBeratPersen(beratPersen);
                s.setNilaiPokok(nilaiPokok);
                s.setBeratPenyusutan(0);
                s.setNilaiPenyusutan(0);
                listSPDetail.add(s);
                spDetailTable.refresh();
                hitungTotal();
                getStokBalenanCabang();
                
                stokBalenan = null;
                jenisBalenanField.setText("");
                kodeJenisCombo.getSelectionModel().clearSelection();
                qtyField.setText("0");
                beratField.setText("0");
            }
        }
    }
    private void hapusBarang(SPDetail d){
        listSPDetail.remove(d);
        spDetailTable.refresh();
        hitungTotal();
        getStokBalenanCabang();
    }
    private void revisiBarangCabang(StokBarangCabang s){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/RevisiBarangCabang.fxml");
        RevisiBarangCabangController controller = loader.getController();
        controller.setMainApp(mainApp, stage, child);
        controller.setBarang(s);
        controller.saveButton.setOnAction((event) -> {
            if(controller.kodeCabangCombo.getSelectionModel().getSelectedItem()==null)
                mainApp.showMessage(Modality.NONE, "Warning", "Kode cabang belum dipilih");
            else if(controller.kodeGudangCombo.getSelectionModel().getSelectedItem()==null)
                mainApp.showMessage(Modality.NONE, "Warning", "Kode gudang belum dipilih");
            else if(controller.jenisRevisiCombo.getSelectionModel().getSelectedItem()==null)
                mainApp.showMessage(Modality.NONE, "Warning", "Jenis revisi belum dipilih");
            else if(controller.kodeJenisField.getText().equals("") || controller.stokBarangCabang==null)
                mainApp.showMessage(Modality.NONE, "Warning", "Barang belum dipilih");
            else if(controller.jenisRevisiCombo.getSelectionModel().getSelectedItem().equals("Ubah Jenis") && 
                    controller.kodeJenisRevisiCombo.getSelectionModel().getSelectedItem()==null)
                mainApp.showMessage(Modality.NONE, "Warning", "Kode jenis revisi masih kosong");
            else if(controller.jenisRevisiCombo.getSelectionModel().getSelectedItem().equals("Ubah Jenis") && 
                    Double.parseDouble(controller.qtyRevisiField.getText().replaceAll(",", ""))==0)
                mainApp.showMessage(Modality.NONE, "Warning", "Qty revisi masih kosong");
            else if(controller.jenisRevisiCombo.getSelectionModel().getSelectedItem().equals("Ubah Jenis") && 
                    Double.parseDouble(controller.beratRevisiField.getText().replaceAll(",", ""))==0)
                mainApp.showMessage(Modality.NONE, "Warning", "Berat revisi masih kosong");
            else if((controller.jenisRevisiCombo.getSelectionModel().getSelectedItem().equals("Penambahan Stok")|| 
                    controller.jenisRevisiCombo.getSelectionModel().getSelectedItem().equals("Pengurangan Stok")) && 
                    Double.parseDouble(controller.qtyRevisiField.getText().replaceAll(",", ""))==0)
                mainApp.showMessage(Modality.NONE, "Warning", "Qty revisi masih kosong");
            else if((controller.jenisRevisiCombo.getSelectionModel().getSelectedItem().equals("Penambahan Berat")|| 
                    controller.jenisRevisiCombo.getSelectionModel().getSelectedItem().equals("Pengurangan Berat")) && 
                    Double.parseDouble(controller.beratRevisiField.getText().replaceAll(",", ""))==0)
                mainApp.showMessage(Modality.NONE, "Warning", "Berat revisi masih kosong");
            else{
                Task<String> task = new Task<String>(){
                    @Override 
                    public String call() throws Exception{
                        try(Connection conPusat = KoneksiPusat.getConnection()){
                            RevisiBarangCabang r = new RevisiBarangCabang();
                            r.setNoRevisi(RevisiBarangCabangDAO.getId(conPusat));
                            r.setTglRevisi(Function.getSystemDate());
                            r.setKeterangan(controller.keteranganField.getText());
                            r.setJenisRevisi(controller.jenisRevisiCombo.getSelectionModel().getSelectedItem());
                            r.setKodeCabang(controller.kodeCabangCombo.getSelectionModel().getSelectedItem());
                            r.setKodeGudang(controller.kodeGudangCombo.getSelectionModel().getSelectedItem());
                            r.setKodeKategori(controller.stokBarangCabang.getKodeKategori());
                            r.setKodeJenis(controller.kodeJenisField.getText());
                            
                            if(r.getJenisRevisi().equals("Ubah Jenis")){
                                Jenis jenisRevisi = JenisDAO.get(conPusat, controller.kodeJenisField.getText());
                                Kategori kategoriRevisi = KategoriDAO.get(conPusat, jenisRevisi.getKodeKategori());
                                r.setBeratPersen(pembulatan(controller.stokBarangCabang.getBeratPersenAkhir()/controller.stokBarangCabang.getBeratAkhir()*r.getBeratRevisi()));
                                r.setKodeKategoriRevisi(kategoriRevisi.getKodeKategori());
                                r.setKodeJenisRevisi(controller.kodeJenisRevisiCombo.getSelectionModel().getSelectedItem());
                                r.setQtyRevisi(Integer.parseInt(controller.qtyRevisiField.getText().replaceAll(",", "")));
                                r.setBeratRevisi(Double.parseDouble(controller.beratRevisiField.getText().replaceAll(",", "")));
                                r.setBeratPersenRevisi(pembulatan(kategoriRevisi.getPersentaseEmas()*r.getBeratRevisi()/100));
                                r.setNilaiRevisi(pembulatan(controller.stokBarangCabang.getNilaiAkhir()/controller.stokBarangCabang.getBeratAkhir()*r.getBeratRevisi()));
                            }else if(r.getJenisRevisi().equals("Penambahan Stok") || r.getJenisRevisi().equals("Pengurangan Stok")){
                                r.setBeratPersen(0);
                                r.setKodeKategoriRevisi("");
                                r.setKodeJenisRevisi("");
                                r.setQtyRevisi(Integer.parseInt(controller.qtyRevisiField.getText().replaceAll(",", "")));
                                r.setBeratRevisi(0);
                                r.setBeratPersenRevisi(0);
                                r.setNilaiRevisi(0);
                            }else if(r.getJenisRevisi().equals("Penambahan Berat") || r.getJenisRevisi().equals("Pengurangan Berat")){
                                r.setBeratPersen(0);
                                r.setKodeKategoriRevisi("");
                                r.setKodeJenisRevisi("");
                                r.setQtyRevisi(0);
                                r.setBeratRevisi(Double.parseDouble(controller.beratRevisiField.getText().replaceAll(",", "")));
                                r.setBeratPersenRevisi(pembulatan(controller.stokBarangCabang.getBeratPersenAkhir()/controller.stokBarangCabang.getBeratAkhir()*r.getBeratRevisi()));
                                r.setNilaiRevisi(0);
                            }
                            
                            r.setKodeUser(user.getKodeUser());
                            r.setStatus("true");
                            r.setTglBatal("2000-01-01 00:00:00");
                            r.setUserBatal("");
                            return Service.saveRevisiBarangCabang(conPusat, r);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    mainApp.closeLoading();
                    getStokBalenanCabang();
                    String status = task.getValue();
                    if(status.equals("true")){
                        mainApp.closeDialog(stage, child);
                        mainApp.showMessage(Modality.NONE, "Success", "Revisi barang cabang berhasil disimpan");
                    }else
                        mainApp.showMessage(Modality.NONE, "Failed", status);
                });
                task.setOnFailed((e) -> {
                    mainApp.closeLoading();
                    mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                });
                new Thread(task).start();
            }
        });
    }
    private void pindahRosokCabang(StokBarangCabang s){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/PindahRosokCabang.fxml");
        PindahRosokCabangController controller = loader.getController();
        controller.setMainApp(mainApp, stage, child);
        controller.setBarang(s);
        controller.saveButton.setOnAction((event) -> {
            if(controller.kodeCabangCombo.getSelectionModel().getSelectedItem()==null)
                mainApp.showMessage(Modality.NONE, "Warning", "Kode cabang belum dipilih");
            else if(controller.kodeGudangCombo.getSelectionModel().getSelectedItem()==null)
                mainApp.showMessage(Modality.NONE, "Warning", "Kode gudang belum dipilih");
            else if(controller.kodeJenisField.getText().equals("") || controller.stokBarangCabang==null)
                mainApp.showMessage(Modality.NONE, "Warning", "Barang belum dipilih");
            else if(Double.parseDouble(controller.qtyField.getText().replaceAll(",", ""))==0)
                mainApp.showMessage(Modality.NONE, "Warning", "Qty masih kosong");
            else if(Double.parseDouble(controller.beratField.getText().replaceAll(",", ""))==0)
                mainApp.showMessage(Modality.NONE, "Warning", "Berat masih kosong");
            else if(Double.parseDouble(controller.persentaseEmasField.getText().replaceAll(",", ""))==0)
                mainApp.showMessage(Modality.NONE, "Warning", "Persentase emas masih kosong");
            else if(Double.parseDouble(controller.beratPersenField.getText().replaceAll(",", ""))==0)
                mainApp.showMessage(Modality.NONE, "Warning", "Berat persen masih kosong");
            else{
                Task<String> task = new Task<String>(){
                    @Override 
                    public String call() throws Exception{
                        try(Connection conPusat = KoneksiPusat.getConnection()){
                            RosokCabang r = new RosokCabang();
                            r.setNoRosok(RosokCabangDAO.getId(conPusat));
                            r.setTglRosok(Function.getSystemDate());
                            r.setKategori("Pindah Rosok Cabang");
                            r.setKeterangan(controller.keteranganField.getText());
                            r.setKodeCabang(controller.kodeCabangCombo.getSelectionModel().getSelectedItem());
                            r.setKodeGudang(controller.kodeGudangCombo.getSelectionModel().getSelectedItem());
                            r.setKodeKategori(controller.stokBarangCabang.getKodeKategori());
                            r.setKodeJenis(controller.kodeJenisField.getText());
                            
                            r.setQty(Integer.parseInt(controller.qtyField.getText().replaceAll(",", "")));
                            r.setBerat(Double.parseDouble(controller.beratField.getText().replaceAll(",", "")));
                            r.setBeratPersen(pembulatan(controller.stokBarangCabang.getBeratPersenAkhir()/controller.stokBarangCabang.getBeratAkhir()*r.getBerat()));
                            r.setPersentaseEmas(Double.parseDouble(controller.persentaseEmasField.getText().replaceAll(",", "")));
                            r.setBeratPersenRosok(pembulatan(r.getPersentaseEmas()*r.getBerat()/100));
                            r.setNilaiPokok(pembulatan(controller.stokBarangCabang.getNilaiAkhir()/controller.stokBarangCabang.getBeratAkhir()*r.getBerat()));
                            
                            r.setKodeUser(user.getKodeUser());
                            r.setStatus("true");
                            r.setTglBatal("2000-01-01 00:00:00");
                            r.setUserBatal("");
                            return Service.saveRosokBarangCabang(conPusat, r);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    mainApp.closeLoading();
                    getStokBalenanCabang();
                    String status = task.getValue();
                    if(status.equals("true")){
                        mainApp.closeDialog(stage, child);
                        mainApp.showMessage(Modality.NONE, "Success", "Pindah rosok cabang berhasil disimpan");
                    }else
                        mainApp.showMessage(Modality.NONE, "Failed", status);
                });
                task.setOnFailed((e) -> {
                    mainApp.closeLoading();
                    mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                });
                new Thread(task).start();
            }
        });
    }
    private void ambilRosokCabang(StokBarangCabang s){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/AmbilRosokCabang.fxml");
        AmbilRosokCabangController controller = loader.getController();
        controller.setMainApp(mainApp, stage, child);
        if(s!=null)
            controller.setBarang(s.getKodeCabang(), s.getKodeGudang(), s.getKodeJenis());
        controller.saveButton.setOnAction((event) -> {
            if(controller.kodeCabangCombo.getSelectionModel().getSelectedItem()==null)
                mainApp.showMessage(Modality.NONE, "Warning", "Kode cabang belum dipilih");
            else if(controller.kodeGudangCombo.getSelectionModel().getSelectedItem()==null)
                mainApp.showMessage(Modality.NONE, "Warning", "Kode gudang belum dipilih");
            else if(controller.kodeJenisCombo.getSelectionModel().getSelectedItem()==null)
                mainApp.showMessage(Modality.NONE, "Warning", "Kode jenis belum dipilih");
            else if(Double.parseDouble(controller.qtyField.getText().replaceAll(",", ""))==0)
                mainApp.showMessage(Modality.NONE, "Warning", "Qty masih kosong");
            else if(Double.parseDouble(controller.beratField.getText().replaceAll(",", ""))==0)
                mainApp.showMessage(Modality.NONE, "Warning", "Berat masih kosong");
            else if(Double.parseDouble(controller.persentaseEmasField.getText().replaceAll(",", ""))==0)
                mainApp.showMessage(Modality.NONE, "Warning", "Persentase emas masih kosong");
            else if(Double.parseDouble(controller.beratPersenField.getText().replaceAll(",", ""))==0)
                mainApp.showMessage(Modality.NONE, "Warning", "Berat persen masih kosong");
            else{
                Task<String> task = new Task<String>(){
                    @Override 
                    public String call() throws Exception{
                        try(Connection conPusat = KoneksiPusat.getConnection()){
                            Jenis j = JenisDAO.get(conPusat, controller.kodeJenisCombo.getSelectionModel().getSelectedItem());
                            Kategori k = KategoriDAO.get(conPusat, j.getKodeKategori());
                            String kodeKategori = k.getKodeKategori();
                            double persentaseEmas = k.getPersentaseEmas();
                            
                            RosokCabang r = new RosokCabang();
                            r.setNoRosok(RosokCabangDAO.getId(conPusat));
                            r.setTglRosok(Function.getSystemDate());
                            r.setKategori("Ambil Rosok Cabang");
                            r.setKeterangan(controller.keteranganField.getText());
                            r.setKodeCabang(controller.kodeCabangCombo.getSelectionModel().getSelectedItem());
                            r.setKodeGudang(controller.kodeGudangCombo.getSelectionModel().getSelectedItem());
                            r.setKodeKategori(kodeKategori);
                            r.setKodeJenis(controller.kodeJenisCombo.getSelectionModel().getSelectedItem());
                            
                            r.setQty(Integer.parseInt(controller.qtyField.getText().replaceAll(",", "")));
                            r.setBerat(Double.parseDouble(controller.beratField.getText().replaceAll(",", "")));
                            r.setBeratPersen(pembulatan(persentaseEmas*r.getBerat()/100));
                            r.setPersentaseEmas(Double.parseDouble(controller.persentaseEmasField.getText().replaceAll(",", "")));
                            r.setBeratPersenRosok(pembulatan(r.getPersentaseEmas()*r.getBerat()/100));
                            r.setNilaiPokok(pembulatan(controller.stokRosokCabang.getNilaiAkhir()/controller.stokRosokCabang.getBeratAkhir()*r.getBerat()));
                            
                            r.setKodeUser(user.getKodeUser());
                            r.setStatus("true");
                            r.setTglBatal("2000-01-01 00:00:00");
                            r.setUserBatal("");
                            return Service.saveRosokBarangCabang(conPusat, r);
                        }
                    }
                };
                task.setOnRunning((e) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((e) -> {
                    mainApp.closeLoading();
                    getStokBalenanCabang();
                    String status = task.getValue();
                    if(status.equals("true")){
                        mainApp.closeDialog(stage, child);
                        mainApp.showMessage(Modality.NONE, "Success", "Ambil rosok cabang berhasil disimpan");
                    }else
                        mainApp.showMessage(Modality.NONE, "Failed", status);
                });
                task.setOnFailed((e) -> {
                    task.getException().printStackTrace();
                    mainApp.closeLoading();
                    mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                });
                new Thread(task).start();
            }
        });
    }
    public void hitungTotal(){
        double qty = 0;
        double berat = 0;
        for(SPDetail d : listSPDetail){
            qty = qty + d.getQty();
            berat = berat + d.getBerat();
        }
        totalQtyLabel.setText(rp.format(qty));
        totalBeratLabel.setText(gr.format(berat));
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
}
