/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.sentralindahperdana.View.Report;

import com.excellentsystem.sentralindahperdana.DAO.BarangDAO;
import com.excellentsystem.sentralindahperdana.DAO.BebanPenjualanDAO;
import com.excellentsystem.sentralindahperdana.DAO.PekerjaanDAO;
import com.excellentsystem.sentralindahperdana.DAO.PengirimanDetailDAO;
import com.excellentsystem.sentralindahperdana.DAO.PenjualanDetailDAO;
import com.excellentsystem.sentralindahperdana.DAO.RencanaAnggaranBebanMaterialDAO;
import com.excellentsystem.sentralindahperdana.DAO.RencanaAnggaranBebanPenjualanDAO;
import com.excellentsystem.sentralindahperdana.DAO.ReturDetailDAO;
import com.excellentsystem.sentralindahperdana.DAO.SatuanDAO;
import com.excellentsystem.sentralindahperdana.Function;
import com.excellentsystem.sentralindahperdana.Koneksi;
import com.excellentsystem.sentralindahperdana.Main;
import static com.excellentsystem.sentralindahperdana.Main.df;
import static com.excellentsystem.sentralindahperdana.Main.tglBarang;
import static com.excellentsystem.sentralindahperdana.Main.tglLengkap;
import static com.excellentsystem.sentralindahperdana.Main.tglNormal;
import static com.excellentsystem.sentralindahperdana.Main.tglSql;
import com.excellentsystem.sentralindahperdana.Model.Barang;
import com.excellentsystem.sentralindahperdana.Model.BebanMaterial;
import com.excellentsystem.sentralindahperdana.Model.BebanPenjualan;
import com.excellentsystem.sentralindahperdana.Model.Pekerjaan;
import com.excellentsystem.sentralindahperdana.Model.PengirimanDetail;
import com.excellentsystem.sentralindahperdana.Model.PenjualanDetail;
import com.excellentsystem.sentralindahperdana.Model.PenjualanHead;
import com.excellentsystem.sentralindahperdana.Model.RencanaAnggaranBebanMaterial;
import com.excellentsystem.sentralindahperdana.Model.RencanaAnggaranBebanPenjualan;
import com.excellentsystem.sentralindahperdana.Model.ReturDetail;
import com.excellentsystem.sentralindahperdana.Model.Satuan;
import com.excellentsystem.sentralindahperdana.PrintOut.Report;
import com.excellentsystem.sentralindahperdana.View.Dialog.AddPenjualanController;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author excellent
 */
public class LaporanProyekController {
    
    @FXML private TextField noPenjualanField;
    @FXML private Button addPenjualanButton;
    @FXML private Label namaCustomerLabel;
    @FXML private Label namaProyekLabel;
    @FXML private Label lokasiPengerjaanLabel;
    @FXML private Label tglPenjualanLabel;
    @FXML private Label tglMulaiLabel;
    @FXML private Label tglSelesaiLabel;
    @FXML private ComboBox<PenjualanDetail> detailComboBox;
    
    @FXML private TableView<RencanaAnggaranBebanMaterial> RABTable;
    @FXML private TableColumn<RencanaAnggaranBebanMaterial, String> kodeBarangRABColumn;
    @FXML private TableColumn<RencanaAnggaranBebanMaterial, String> namaBarangRABColumn;
    @FXML private TableColumn<RencanaAnggaranBebanMaterial, String> satuanRABColumn;
    @FXML private TableColumn<RencanaAnggaranBebanMaterial, Number> qtyRABColumn;
    @FXML private TableColumn<RencanaAnggaranBebanMaterial, Number> estimasiHargaBeliRABColumn;
    @FXML private TableColumn<RencanaAnggaranBebanMaterial, Number> hargaJualRABColumn;
    @FXML private Label totalAnggaranPembelianLabel;
    @FXML private Label totalAnggaranMaterialLabel;
    
    @FXML private TableView<RencanaAnggaranBebanPenjualan> RABPTable;
    @FXML private TableColumn<RencanaAnggaranBebanPenjualan, String> kategoriRABPColumn;
    @FXML private TableColumn<RencanaAnggaranBebanPenjualan, Number> jumlahRpRABPColumn;
    @FXML private Label totalAnggaranBebanPenjualanLabel;
    
    @FXML private Label totalAnggaranPenjualanLabel;
    @FXML private Label penjualanAnggaranLabel;
    @FXML private Label perkiraanKeuntunganKotorLabel;
    @FXML private Label perkiraanKeuntunganKotorPersenLabel;
    
    @FXML private TableView<BebanMaterial> barangTerkirimTable;
    @FXML private TableColumn<BebanMaterial, String> kodeBarangTerkirimColumn;
    @FXML private TableColumn<BebanMaterial, String> namaBarangTerkirimColumn;
    @FXML private TableColumn<BebanMaterial, String> satuanTerkirimColumn;
    @FXML private TableColumn<BebanMaterial, Number> qtyTerkirimColumn;
    @FXML private TableColumn<BebanMaterial, Number> nilaiPokokTerkirimColumn;
    @FXML private Label totalBebanMaterialLabel;
    
    @FXML private TreeTableView<BebanPenjualan> bebanPenjualanTable;
    @FXML private TreeTableColumn<BebanPenjualan, String> kategoriBebanColumn;
    @FXML private TreeTableColumn<BebanPenjualan, Number> jumlahRpBebanColumn;
    @FXML private Label totalBebanPenjualanLabel;
    
    @FXML private Label HPPLabel;
    @FXML private Label penjualanLabel;
    @FXML private Label keuntunganKotorLabel;
    @FXML private Label keuntunganKotorPersenLabel;
    
    @FXML private Label totalHPPLabel;
    @FXML private Label totalPenjualanLabel;
    @FXML private Label UntungRugiKotorLabel;
    
    private PenjualanHead penjualan;
    private ObservableList<RencanaAnggaranBebanMaterial> RAB = FXCollections.observableArrayList();
    private ObservableList<RencanaAnggaranBebanPenjualan> RABP = FXCollections.observableArrayList();
    private ObservableList<BebanMaterial> bebanMaterial = FXCollections.observableArrayList();
    final TreeItem<BebanPenjualan> root = new TreeItem<>();
    private ObservableList<BebanPenjualan> bebanPenjualan = FXCollections.observableArrayList();
    private Main mainApp;   
    public void initialize() {
        kodeBarangRABColumn.setCellValueFactory(cellData -> cellData.getValue().kodeBarangProperty());
        namaBarangRABColumn.setCellValueFactory(cellData -> cellData.getValue().namaBarangProperty());
        satuanRABColumn.setCellValueFactory(cellData -> {
            String satuan = cellData.getValue().getBarang().getSatuanDasar();
            for (Satuan temp : cellData.getValue().getBarang().getAllSatuan()) {
                if (temp.getKodeSatuan().equals(cellData.getValue().getSatuan())) 
                    satuan = cellData.getValue().getSatuan();
            }
            return new SimpleStringProperty(satuan);
        });
        estimasiHargaBeliRABColumn.setCellValueFactory(cellData -> {
            double harga = cellData.getValue().getHargaBeli();
            for (Satuan temp : cellData.getValue().getBarang().getAllSatuan()) {
                if (temp.getKodeSatuan().equals(cellData.getValue().getSatuan())) 
                    harga = harga * temp.getQty();
            }
            return new SimpleDoubleProperty(harga);
        });
        estimasiHargaBeliRABColumn.setCellFactory((c) -> Function.getTableCell());
        hargaJualRABColumn.setCellValueFactory(cellData -> {
            double harga = cellData.getValue().getHargaJual();
            for (Satuan temp : cellData.getValue().getBarang().getAllSatuan()) {
                if (temp.getKodeSatuan().equals(cellData.getValue().getSatuan())) 
                    harga = harga * temp.getQty();
            }
            return new SimpleDoubleProperty(harga);
        });
        hargaJualRABColumn.setCellFactory((c) -> Function.getTableCell());
        qtyRABColumn.setCellValueFactory(cellData -> {
            double q = cellData.getValue().getQty();
            for (Satuan temp : cellData.getValue().getBarang().getAllSatuan()) {
                if (temp.getKodeSatuan().equals(cellData.getValue().getSatuan())) 
                    q = q / temp.getQty();
            }
            return new SimpleDoubleProperty(q);
        });
        qtyRABColumn.setCellFactory((c) -> Function.getTableCell());
        RABTable.setItems(RAB);
        
        kategoriRABPColumn.setCellValueFactory(cellData -> cellData.getValue().kategoriProperty());
        jumlahRpRABPColumn.setCellValueFactory(cellData -> cellData.getValue().jumlahRpProperty());
        jumlahRpRABPColumn.setCellFactory((c) -> Function.getTableCell());
        RABPTable.setItems(RABP);
        
        kodeBarangTerkirimColumn.setCellValueFactory(cellData -> cellData.getValue().kodeBarangProperty());
        namaBarangTerkirimColumn.setCellValueFactory(cellData -> cellData.getValue().namaBarangProperty());
        satuanTerkirimColumn.setCellValueFactory(cellData -> {
            String satuan = cellData.getValue().getBarang().getSatuanDasar();
            for (Satuan temp : cellData.getValue().getBarang().getAllSatuan()) {
                if (temp.getKodeSatuan().equals(cellData.getValue().getSatuan())) 
                    satuan = cellData.getValue().getSatuan();
            }
            return new SimpleStringProperty(satuan);
        });
        qtyTerkirimColumn.setCellValueFactory(cellData -> {
            double q = cellData.getValue().getQty();
            for (Satuan temp : cellData.getValue().getBarang().getAllSatuan()) {
                if (temp.getKodeSatuan().equals(cellData.getValue().getSatuan())) 
                    q = q / temp.getQty();
            }
            return new SimpleDoubleProperty(q);
        });
        qtyTerkirimColumn.setCellFactory((c) -> Function.getTableCell());
        nilaiPokokTerkirimColumn.setCellValueFactory(cellData -> {
            return new SimpleDoubleProperty(cellData.getValue().getNilai()/cellData.getValue().getQty());
        });
        nilaiPokokTerkirimColumn.setCellFactory((c) -> Function.getTableCell());
        barangTerkirimTable.setItems(bebanMaterial);
        
        kategoriBebanColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().keteranganProperty());
        jumlahRpBebanColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().jumlahRpProperty());
        jumlahRpBebanColumn.setCellFactory((c) -> Function.getTreeTableCell());
        
        detailComboBox.setCellFactory((ListView<PenjualanDetail> l) -> new ListCell<PenjualanDetail>() {
            @Override
            protected void updateItem(PenjualanDetail item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setGraphic(null);
                } else {
                    setText(item.getNoUrut()+". "+item.getNamaPekerjaan()+" - "
                            +df.format(item.getQty())+" "+item.getPekerjaan().getSatuan()+" x "
                            +df.format(item.getHarga())+" = "+df.format(item.getTotal()));
                }
            }
        });
        detailComboBox.setConverter(new StringConverter<PenjualanDetail>() {
            @Override
            public String toString(PenjualanDetail d) {
                if (d == null)
                    return null;
                else 
                    return d.getNoUrut()+". "+d.getNamaPekerjaan()+" - "
                            +df.format(d.getQty())+" "+d.getPekerjaan().getSatuan()+" x "
                            +df.format(d.getHarga())+" = "+df.format(d.getTotal());
            }
            @Override
            public PenjualanDetail fromString(String d) {
                return null;
            }
        });
    }   
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }   
    @FXML
    private void getProyek(){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(mainApp.MainStage, child, "View/Dialog/AddPenjualan.fxml");
        AddPenjualanController controller = loader.getController();
        controller.setMainApp(mainApp, mainApp.MainStage, child);
        controller.penjualanHeadTable.setRowFactory(table -> {
            TableRow<PenjualanHead> row = new TableRow<PenjualanHead>() {};
            row.setOnMouseClicked((MouseEvent mouseEvent) -> {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)&&
                        mouseEvent.getClickCount() == 2 && row.getItem()!=null){
                    mainApp.closeDialog(mainApp.MainStage, child);
                    penjualan = row.getItem();
                    setProyek();
                }
            });
            return row;
        });
    }
    private void setProyek(){
        Task<Double> task = new Task<Double>() {
            @Override 
            public Double call() throws Exception{
                try (Connection con = Koneksi.getConnection()) {
                    noPenjualanField.setText(penjualan.getNoPenjualan());
                    List<Pekerjaan> listPekerjaan = PekerjaanDAO.getAllByStatus(con, "%");
                    List<Barang> listBarang = BarangDAO.getAllByStatus(con, "%");
                    List<Satuan> listSatuan = SatuanDAO.getAll(con);
                    
                    List<RencanaAnggaranBebanMaterial> listRAB = RencanaAnggaranBebanMaterialDAO.getAllByNoPenjualanAndNoUrut(
                            con, noPenjualanField.getText(), "%");
                    for(RencanaAnggaranBebanMaterial r : listRAB){
                        for(Barang b : listBarang){
                            if(r.getKodeBarang().equals(b.getKodeBarang()))
                                r.setBarang(b);
                        }
                        List<Satuan> satuan = new ArrayList<>();
                        for(Satuan s : listSatuan){
                            if(r.getKodeBarang().equals(s.getKodeBarang()))
                                satuan.add(s);
                        }
                        r.getBarang().setAllSatuan(satuan);
                    }
                    List<RencanaAnggaranBebanPenjualan> listRABP = RencanaAnggaranBebanPenjualanDAO.getAllByNoPenjualanAndNoUrut(
                            con, noPenjualanField.getText(), "%");
                    List<PengirimanDetail> listPengiriman = PengirimanDetailDAO.getAllByNoPenjualanAndNoUrutAndStatus(
                            con, noPenjualanField.getText(), "%", "true");
                    List<ReturDetail> listRetur = ReturDetailDAO.getAllByNoPenjualanAndNoUrutAndStatus(
                            con, noPenjualanField.getText(), "%", "true");
                    List<BebanPenjualan> listBebanPenjualan = BebanPenjualanDAO.getAllByNoPenjualanAndNoUrut(
                            con, noPenjualanField.getText(), "%", "true");
                    
                    penjualan.setAllDetail(PenjualanDetailDAO.getAllByNoPenjualan(con, noPenjualanField.getText()));
                    for(PenjualanDetail d : penjualan.getAllDetail()){
                        for(Pekerjaan p : listPekerjaan){
                            if(d.getKodePekerjaan().equals(p.getKodePekerjaan()))
                                d.setPekerjaan(p);
                        }
                        
                        List<RencanaAnggaranBebanMaterial> rab = new ArrayList<>();
                        for(RencanaAnggaranBebanMaterial r : listRAB){
                            if(r.getNoUrut().equals(d.getNoUrut()))
                                rab.add(r);
                        }
                        rab.sort(Comparator.comparing(RencanaAnggaranBebanMaterial::getNamaBarang));
                        d.setRencanaAnggaranBelanja(rab);
                        
                        List<RencanaAnggaranBebanPenjualan> rabp = new ArrayList<>();
                        for(RencanaAnggaranBebanPenjualan r : listRABP){
                            if(r.getNoUrut().equals(d.getNoUrut()))
                                rabp.add(r);
                        }
                        rabp.sort(Comparator.comparing(RencanaAnggaranBebanPenjualan::getKategori));
                        d.setRencanaAnggaranBebanPenjualan(rabp);
                        
                        List<BebanMaterial> bebanMaterial = new ArrayList<>();
                        for(Barang b : listBarang){
                            double qty = 0;
                            double nilai = 0;
                            String satuan = b.getSatuanDasar();
                            for(PengirimanDetail x : listPengiriman){
                                if(b.getKodeBarang().equals(x.getKodeBarang())&&
                                        x.getNoUrutPenjualan().equals(d.getNoUrut())){
                                    qty = qty + x.getQty();
                                    nilai = nilai + x.getNilai()*x.getQty();
                                    satuan = x.getSatuan();
                                }
                            }
                            for(ReturDetail x : listRetur){
                                if(b.getKodeBarang().equals(x.getKodeBarang())&&
                                        x.getNoUrutPenjualan().equals(d.getNoUrut())){
                                    qty = qty - x.getQty();
                                    nilai = nilai - x.getNilai()*x.getQty();
                                }
                            }
                            if(qty!=0){
                                BebanMaterial x = new BebanMaterial();
                                x.setNoPenjualan(d.getNoPenjualan());
                                x.setNoUrut(d.getNoUrut());
                                x.setKodeBarang(b.getKodeBarang());
                                x.setNamaBarang(b.getNamaBarang());
                                x.setQty(qty);
                                x.setSatuan(satuan);
                                x.setNilai(nilai);
                                x.setBarang(b);

                                List<Satuan> allSatuan = new ArrayList<>();
                                for(Satuan s : listSatuan){
                                    if(x.getKodeBarang().equals(s.getKodeBarang()))
                                        allSatuan.add(s);
                                }
                                x.getBarang().setAllSatuan(allSatuan);

                                bebanMaterial.add(x);
                            }
                        }
                        bebanMaterial.sort(Comparator.comparing(BebanMaterial::getNamaBarang));
                        d.setBebanMaterial(bebanMaterial);
                        
                        List<BebanPenjualan> bebanPenjualan = new ArrayList<>();
                        for(BebanPenjualan b : listBebanPenjualan){
                            if(b.getNoUrut().equals(d.getNoUrut()))
                                bebanPenjualan.add(b);
                        }
                        bebanPenjualan.sort(Comparator.comparing(BebanPenjualan::getKategori));
                        d.setBebanPenjualan(bebanPenjualan);
                    }
                    double hpp = 0;
                    for(PengirimanDetail d : listPengiriman){
                        hpp = hpp + d.getNilai()*d.getQty();
                    }
                    for(ReturDetail d : listRetur){
                        hpp = hpp - d.getNilai()*d.getQty();
                    }
                    for(BebanPenjualan b : listBebanPenjualan){
                        hpp = hpp + b.getJumlahRp();
                    }
                    return hpp;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((WorkerStateEvent ev) -> {
            try{
                namaCustomerLabel.setText(penjualan.getCustomer().getNama());
                namaProyekLabel.setText(penjualan.getNamaProyek());
                lokasiPengerjaanLabel.setText(penjualan.getLokasiPengerjaan());
                tglPenjualanLabel.setText(tglLengkap.format(tglSql.parse(penjualan.getTglPenjualan())));
                if(penjualan.getTglMulai().equals("2000-01-01"))
                    tglMulaiLabel.setText("-");
                else
                    tglMulaiLabel.setText(tglNormal.format(tglBarang.parse(penjualan.getTglMulai())));
                if(penjualan.getTglSelesai().equals("2000-01-01"))
                    tglSelesaiLabel.setText("-");
                else
                    tglSelesaiLabel.setText(tglNormal.format(tglBarang.parse(penjualan.getTglSelesai())));
                detailComboBox.setItems(FXCollections.observableList(penjualan.getAllDetail()));
                detailComboBox.getSelectionModel().selectFirst();
                totalPenjualanLabel.setText(df.format(penjualan.getTotalPenjualan()));
                totalHPPLabel.setText(df.format(task.get()));
                UntungRugiKotorLabel.setText(df.format(penjualan.getTotalPenjualan()-task.get()));
                mainApp.closeLoading();
            }catch(Exception e){
                e.printStackTrace();
                mainApp.showMessage(Modality.NONE, "Error", e.toString());
            }
        });
        task.setOnFailed((e) -> {
            task.getException().printStackTrace();
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            mainApp.closeLoading();
        });
        new Thread(task).start();
    }
    @FXML
    private void setDetail(){
        try{
            if(detailComboBox.getSelectionModel().getSelectedItem()!=null){
                PenjualanDetail detail = detailComboBox.getSelectionModel().getSelectedItem();
                RAB.clear();
                RAB.addAll(detail.getRencanaAnggaranBelanja());
                RABP.clear();
                RABP.addAll(detail.getRencanaAnggaranBebanPenjualan());
                double totalAnggaranPembelian = 0;
                double totalAnggaranMaterial = 0;
                double totalAnggaranBebanPenjualan = 0;
                for(RencanaAnggaranBebanMaterial d : RAB){
                    totalAnggaranPembelian = totalAnggaranPembelian + d.getHargaBeli()*d.getQty();
                    totalAnggaranMaterial = totalAnggaranMaterial + d.getHargaJual()*d.getQty();
                }
                for(RencanaAnggaranBebanPenjualan d : RABP){
                    totalAnggaranBebanPenjualan = totalAnggaranBebanPenjualan + d.getJumlahRp();
                }
                totalAnggaranPembelianLabel.setText(df.format(totalAnggaranPembelian));
                totalAnggaranMaterialLabel.setText(df.format(totalAnggaranMaterial));
                totalAnggaranBebanPenjualanLabel.setText(df.format(totalAnggaranBebanPenjualan));
                totalAnggaranPenjualanLabel.setText(df.format(totalAnggaranMaterial+totalAnggaranBebanPenjualan));
                penjualanAnggaranLabel.setText(df.format(detail.getTotal()));
                perkiraanKeuntunganKotorLabel.setText(df.format(detail.getTotal()-totalAnggaranMaterial-totalAnggaranBebanPenjualan));
                perkiraanKeuntunganKotorPersenLabel.setText(df.format(
                        (detail.getTotal()-totalAnggaranMaterial-totalAnggaranBebanPenjualan)/detail.getTotal()*100)+" %");

                bebanMaterial.clear();
                bebanMaterial.addAll(detail.getBebanMaterial());
                bebanPenjualan.clear();
                bebanPenjualan.addAll(detail.getBebanPenjualan());
                setTable();
                double totalBebanMaterial = 0;
                double totalBebanPenjualan = 0;
                for(BebanMaterial d : bebanMaterial){
                    totalBebanMaterial = totalBebanMaterial + d.getNilai();
                }
                for(BebanPenjualan b : bebanPenjualan){
                    totalBebanPenjualan = totalBebanPenjualan + b.getJumlahRp();
                }
                totalBebanMaterialLabel.setText(df.format(totalBebanMaterial));
                totalBebanPenjualanLabel.setText(df.format(totalBebanPenjualan));
                HPPLabel.setText(df.format(totalBebanMaterial+totalBebanPenjualan));
                penjualanLabel.setText(df.format(detail.getTotal()));
                keuntunganKotorLabel.setText(df.format(detail.getTotal()-totalBebanMaterial-totalBebanPenjualan));
                keuntunganKotorPersenLabel.setText(df.format(
                        (detail.getTotal()-totalBebanMaterial-totalBebanPenjualan)/detail.getTotal()*100)+" %");
            }
        }catch(Exception e){
            e.printStackTrace();
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    private void setTable()throws Exception{
        if(bebanPenjualanTable.getRoot()!=null)
            bebanPenjualanTable.getRoot().getChildren().clear();
        List<String> kategori = new ArrayList<>();
        for(BebanPenjualan temp: bebanPenjualan){
            if(!kategori.contains(temp.getKategori()))
                kategori.add(temp.getKategori());
        }
        for(String temp : kategori){
            BebanPenjualan b = new BebanPenjualan();
            b.setKeterangan(temp);
            double total = 0;
            TreeItem<BebanPenjualan> parent = new TreeItem<>(b);
            for(BebanPenjualan temp2: bebanPenjualan){
                if(temp.equals(temp2.getKategori())){
                    TreeItem<BebanPenjualan> child = new TreeItem<>(temp2);
                    parent.getChildren().addAll(child);
                    total = total + temp2.getJumlahRp();
                }
            }
            parent.getValue().setJumlahRp(total);
            root.getChildren().add(parent);
        }
        bebanPenjualanTable.setRoot(root);
    } 
    @FXML
    private void print(){
        if(penjualan==null){
            mainApp.showMessage(Modality.NONE, "Warning", "Penjualan belum dipilih");
        }else{
            try{
                for(PenjualanDetail d : penjualan.getAllDetail()){
                    d.setPenjualanHead(penjualan);
                }
                Report report = new Report();
                report.printLaporanProyek(penjualan.getAllDetail());
            }catch(Exception e){
                e.printStackTrace();
                mainApp.showMessage(Modality.NONE, "Error", e.toString());
            }
        }
    }
}
