/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.sentralindahperdana.View.Dialog;

import com.excellentsystem.sentralindahperdana.DAO.BarangDAO;
import com.excellentsystem.sentralindahperdana.DAO.CustomerDAO;
import com.excellentsystem.sentralindahperdana.DAO.PekerjaanDAO;
import com.excellentsystem.sentralindahperdana.DAO.PenjualanDetailDAO;
import com.excellentsystem.sentralindahperdana.DAO.PenjualanHeadDAO;
import com.excellentsystem.sentralindahperdana.DAO.RencanaAnggaranBebanMaterialDAO;
import com.excellentsystem.sentralindahperdana.DAO.RencanaAnggaranBebanPenjualanDAO;
import com.excellentsystem.sentralindahperdana.DAO.SatuanDAO;
import com.excellentsystem.sentralindahperdana.Function;
import com.excellentsystem.sentralindahperdana.Koneksi;
import com.excellentsystem.sentralindahperdana.Main;
import static com.excellentsystem.sentralindahperdana.Main.df;
import static com.excellentsystem.sentralindahperdana.Main.sistem;
import static com.excellentsystem.sentralindahperdana.Main.tglLengkap;
import static com.excellentsystem.sentralindahperdana.Main.tglSql;
import com.excellentsystem.sentralindahperdana.Model.Barang;
import com.excellentsystem.sentralindahperdana.Model.Customer;
import com.excellentsystem.sentralindahperdana.Model.Pekerjaan;
import com.excellentsystem.sentralindahperdana.Model.PenjualanDetail;
import com.excellentsystem.sentralindahperdana.Model.PenjualanHead;
import com.excellentsystem.sentralindahperdana.Model.RencanaAnggaranBebanMaterial;
import com.excellentsystem.sentralindahperdana.Model.RencanaAnggaranBebanPenjualan;
import com.excellentsystem.sentralindahperdana.Model.Satuan;
import com.excellentsystem.sentralindahperdana.Service.Service;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class NewPenjualanController  {

    
    @FXML private TableView<PenjualanDetail> penjualanDetailTable;
    @FXML private TableColumn<PenjualanDetail, String> kodePekerjaanColumn;
    @FXML private TableColumn<PenjualanDetail, String> namaPekerjaanColumn;
    @FXML private TableColumn<PenjualanDetail, String> keteranganColumn;
    @FXML private TableColumn<PenjualanDetail, String> satuanColumn;
    @FXML private TableColumn<PenjualanDetail, Number> qtyColumn;
    @FXML private TableColumn<PenjualanDetail, Number> hargaColumn;
    @FXML private TableColumn<PenjualanDetail, Number> totalColumn;
    @FXML private TableColumn removeColumn = new TableColumn<>("");
    
    @FXML private Label noPenjualanField;
    @FXML private Label tglPenjualanField;
    
    @FXML private TextField namaField;
    @FXML private TextArea alamatField;
    
    @FXML private TextField namaProyekField;
    @FXML private TextArea lokasiPengerjaanField;
    
    @FXML private TextArea catatanField;
    @FXML private TextField totalPenjualanField;
    
    @FXML private Button addCustomerButton;
    @FXML private Button addItemButton;
    @FXML public Button saveButton;
    
    private PenjualanHead penjualan;
    private Stage stage;
    private Stage owner;
    private ObservableList<PenjualanDetail> allPenjualanDetail = FXCollections.observableArrayList();
    private Main mainApp;   
    private Callback<TableColumn, TableCell> getButtonCell(){
        return (final TableColumn p) -> new TableCell<PenjualanDetail, Boolean>(){
            final Button btn = new Button("");
            @Override
            public void updateItem( Boolean item, boolean empty ){
                super.updateItem( item, empty );
                if ( empty ){
                    setGraphic( null );
                }else{
                    btn.setGraphic(new ImageView(new Image(Main.class.getResourceAsStream("Resource/delete.png"),20,20,false,true)));
                    btn.setPrefSize(30, 30);
                    btn.setOnAction((ActionEvent e) ->{
                        PenjualanDetail p = (PenjualanDetail)getTableView().getItems().get( getIndex() );
                        allPenjualanDetail.remove(p);
                        hitungTotal();
                    });
                    if(saveButton.isVisible())
                        setGraphic(btn);
                    else
                        setGraphic(null);
                }
            }
        };
    }
    public void initialize() {
        kodePekerjaanColumn.setCellValueFactory(cellData -> cellData.getValue().kodePekerjaanProperty());
        namaPekerjaanColumn.setCellValueFactory(cellData -> cellData.getValue().namaPekerjaanProperty());
        keteranganColumn.setCellValueFactory(cellData -> cellData.getValue().keteranganProperty());
        satuanColumn.setCellValueFactory(cellData -> cellData.getValue().getPekerjaan().satuanProperty());
        hargaColumn.setCellValueFactory(cellData -> {
            return new SimpleDoubleProperty(cellData.getValue().getHarga());
        });
        hargaColumn.setCellFactory((c) -> Function.getTableCell());
        totalColumn.setCellValueFactory(cellData -> {
            return new SimpleDoubleProperty(cellData.getValue().getTotal());
        });
        totalColumn.setCellFactory(col -> Function.getTableCell());
        qtyColumn.setCellValueFactory(cellData -> cellData.getValue().qtyProperty());
        qtyColumn.setCellFactory((c) -> Function.getTableCell());
        
        removeColumn.setCellValueFactory( new PropertyValueFactory<>(""));
        removeColumn.setCellFactory(getButtonCell());
        penjualanDetailTable.setRowFactory(table -> {
            TableRow<PenjualanDetail> row = new TableRow<PenjualanDetail>(){
                @Override
                public void updateItem(PenjualanDetail item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setContextMenu(null);
                    }else{
                        final ContextMenu rowMenu = new ContextMenu();
                        MenuItem lihat = new MenuItem("Lihat Rencana Anggaran Penjualan");
                        lihat.setOnAction((ActionEvent event) -> {
                            editRAP(item);
                        });
                        MenuItem cekMaterial = new MenuItem("Cek Material");
                        cekMaterial.setOnAction((ActionEvent event) -> {
                            cekMaterial(item.getRencanaAnggaranBelanja(), item.getNoPenjualan(), item.getNoUrut());
                        });
                        MenuItem cekBeban = new MenuItem("Cek Beban Penjualan");
                        cekBeban.setOnAction((ActionEvent event) -> {
                            cekBebanPenjualan(item.getRencanaAnggaranBebanPenjualan(), item.getNoPenjualan(), item.getNoUrut());
                        });
                        rowMenu.getItems().addAll(lihat);
                        rowMenu.getItems().addAll(cekMaterial);
                        rowMenu.getItems().addAll(cekBeban);
                        setContextMenu(rowMenu);
                    }
                }
            };
            return row;
        });
        penjualanDetailTable.setItems(allPenjualanDetail);
    }
    public void setMainApp(Main mainApp,Stage owner, Stage stage) {
        try{
            this.mainApp = mainApp;
            this.owner = owner;
            this.stage = stage;
            stage.setOnCloseRequest((event) -> {
                mainApp.closeDialog(owner, stage);
            });
            stage.setHeight(mainApp.screenSize.getHeight()-80);
            stage.setWidth(mainApp.screenSize.getWidth()-80);
            stage.setX((mainApp.screenSize.getWidth() - stage.getWidth()) / 2);
            stage.setY((mainApp.screenSize.getHeight() - stage.getHeight()) / 2);
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    @FXML
    private void hitungTotal(){
        double totalPenjualan = 0;
        for(PenjualanDetail temp : allPenjualanDetail){
            totalPenjualan = totalPenjualan + temp.getTotal();
        }
        totalPenjualanField.setText(df.format(totalPenjualan));
    }
    public void setNewPenjualan(){
        Task<String> task = new Task<String>() {
            @Override 
            public String call() throws Exception{
                try (Connection con = Koneksi.getConnection()) {
                    return PenjualanHeadDAO.getId(con);
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((ev) -> {
            try{
                mainApp.closeLoading();
                penjualan = new PenjualanHead();
                penjualan.setNoPenjualan(task.getValue());
                penjualan.setTglPenjualan(tglSql.format(new Date()));
                noPenjualanField.setText(penjualan.getNoPenjualan());
                tglPenjualanField.setText(tglLengkap.format(tglSql.parse(penjualan.getTglPenjualan())));
            }catch(Exception e){
                mainApp.showMessage(Modality.NONE, "Error", e.toString());
            }
        });
        task.setOnFailed((e) -> {
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            mainApp.closeLoading();
        });
        new Thread(task).start();
    }
    public void setDetailPenjualan(String noPenjualan){
        Task<PenjualanHead> task = new Task<PenjualanHead>() {
            @Override 
            public PenjualanHead call() throws Exception{
                try (Connection con = Koneksi.getConnection()) {
                    penjualan = PenjualanHeadDAO.get(con, noPenjualan);
                    penjualan.setCustomer(CustomerDAO.get(con, penjualan.getKodeCustomer()));
                    penjualan.setAllDetail(PenjualanDetailDAO.getAllByNoPenjualan(con, noPenjualan));
                    List<Pekerjaan> listPekerjaan = PekerjaanDAO.getAllByStatus(con, "%");
                    List<Barang> listBarang = BarangDAO.getAllByStatus(con, "%");
                    List<Satuan> listSatuan = SatuanDAO.getAll(con);
                    for(PenjualanDetail d : penjualan.getAllDetail()){
                        for(Pekerjaan b : listPekerjaan){
                            if(d.getKodePekerjaan().equals(b.getKodePekerjaan()))
                                d.setPekerjaan(b);
                        }
                        d.setRencanaAnggaranBebanPenjualan(RencanaAnggaranBebanPenjualanDAO.getAllByNoPenjualanAndNoUrut(
                                con, d.getNoPenjualan(), d.getNoUrut()));
                        d.setRencanaAnggaranBelanja(RencanaAnggaranBebanMaterialDAO.getAllByNoPenjualanAndNoUrut(
                                con, d.getNoPenjualan(), d.getNoUrut()));
                        for(RencanaAnggaranBebanMaterial bm : d.getRencanaAnggaranBelanja()){
                            for(Barang b : listBarang){
                                if(bm.getKodeBarang().equals(b.getKodeBarang()))
                                    bm.setBarang(b);
                            }
                            List<Satuan> satuan = new ArrayList<>();
                            for(Satuan s : listSatuan){
                                if(bm.getKodeBarang().equals(s.getKodeBarang()))
                                    satuan.add(s);
                            }
                            bm.getBarang().setAllSatuan(satuan);
                        }
                    }
                    return penjualan;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((ev) -> {
            try{
                mainApp.closeLoading();
                addCustomerButton.setVisible(false);
                addItemButton.setVisible(false);
                catatanField.setDisable(true);
                namaProyekField.setDisable(true);
                lokasiPengerjaanField.setDisable(true);
                saveButton.setVisible(false);
                
                noPenjualanField.setText(penjualan.getNoPenjualan());
                tglPenjualanField.setText(tglLengkap.format(tglSql.parse(penjualan.getTglPenjualan())));
                namaField.setText(penjualan.getCustomer().getNama());
                alamatField.setText(penjualan.getCustomer().getAlamat());
                namaProyekField.setText(penjualan.getNamaProyek());
                lokasiPengerjaanField.setText(penjualan.getLokasiPengerjaan());
                catatanField.setText(penjualan.getCatatan());
                allPenjualanDetail.addAll(penjualan.getAllDetail());
                totalPenjualanField.setText(df.format(penjualan.getTotalPenjualan()));
            }catch(Exception e){
                mainApp.showMessage(Modality.NONE, "Error", e.toString());
            }
        });
        task.setOnFailed((e) -> {
            mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
            mainApp.closeLoading();
        });
        new Thread(task).start();
    }
    @FXML
    private void close(){
        mainApp.closeDialog(owner, stage);
    }
    @FXML
    private void addPekerjaan(){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/AddPekerjaan.fxml");
        AddPekerjaanController controller = loader.getController();
        controller.setMainApp(mainApp, stage, child);
        controller.addButton.setOnAction((ActionEvent event) -> {
            if(controller.pekerjaan==null)
                mainApp.showMessage(Modality.NONE, "Warning", "Pekerjaan belum dipilih");
            else if(controller.qtyField.getText().equals("0")||controller.qtyField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Qty tidak boleh kosong");
            else if(controller.totalField.getText().equals("0")||controller.totalField.getText().equals(""))
                mainApp.showMessage(Modality.NONE, "Warning", "Total tidak boleh kosong");
            else{
                mainApp.closeDialog(stage,child);
                double qty = Double.parseDouble(controller.qtyField.getText().replaceAll(",", ""));
                double harga = Double.parseDouble(controller.hargaJualField.getText().replaceAll(",", ""));
                PenjualanDetail detail = new PenjualanDetail();
                detail.setKodePekerjaan(controller.pekerjaan.getKodePekerjaan());
                detail.setNamaPekerjaan(controller.pekerjaan.getNamaPekerjaan());
                detail.setKeterangan(controller.keteranganField.getText());
                detail.setQty(qty);
                detail.setHarga(harga);
                detail.setTotal(qty*harga);
                detail.setWaktuPengerjaan(0);
                detail.setPekerjaan(controller.pekerjaan);
                detail.setRencanaAnggaranBelanja(new ArrayList<>());
                detail.setRencanaAnggaranBebanPenjualan(new ArrayList<>());
                setRAP(detail);
            }
        });
    }
    private void setRAP(PenjualanDetail d){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/RencanaAnggaranPenjualan.fxml");
        RencanaAnggaranPenjualanController controller = loader.getController();
        controller.setMainApp(mainApp, stage, child);
        controller.setPekerjaan(d);
        controller.saveButton.setOnAction((event) -> {
            if(Double.parseDouble(controller.perkiraanKeuntunganPersenField.getText().
                replaceAll(",", "").replaceAll(" %", ""))<d.getPekerjaan().getLaba())
                mainApp.showMessage(Modality.NONE, "Warning", "Keuntungan kotor tidak boleh dibawah "+d.getPekerjaan().getLaba()+" %");
            else{
                mainApp.closeDialog(stage,child);
                controller.penjualanDetail.setWaktuPengerjaan(
                    Double.parseDouble(controller.estimasiWaktuPengerjaanField.getText()));
                allPenjualanDetail.add(controller.penjualanDetail);
                hitungTotal();
            }
        });
    }
    private void editRAP(PenjualanDetail d){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/RencanaAnggaranPenjualan.fxml");
        RencanaAnggaranPenjualanController controller = loader.getController();
        controller.setMainApp(mainApp, stage, child);
        controller.setPekerjaan(d);
        if(saveButton.isVisible()){
            controller.saveButton.setOnAction((event) -> {
                if(Double.parseDouble(controller.perkiraanKeuntunganPersenField.getText().
                        replaceAll(",", "").replaceAll(" %", ""))<d.getPekerjaan().getLaba())
                    mainApp.showMessage(Modality.NONE, "Warning", "Keuntungan kotor tidak boleh dibawah "+d.getPekerjaan().getLaba()+" %");
                else{
                    mainApp.closeDialog(stage,child);
                    controller.penjualanDetail.setWaktuPengerjaan(
                        Double.parseDouble(controller.estimasiWaktuPengerjaanField.getText()));
                    allPenjualanDetail.remove(d);
                    allPenjualanDetail.add(controller.penjualanDetail);
                    penjualanDetailTable.refresh();
                    hitungTotal();
                }
            });
        }else{
            controller.setViewOnly();
        }
    }
    private void cekMaterial(List<RencanaAnggaranBebanMaterial> listRab, String noPenjualan, String noUrut){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/MaterialTerkirim.fxml");
        MaterialTerkirimController controller = loader.getController();
        controller.setMainApp(mainApp, stage, child);
        controller.setBarang(listRab, noPenjualan, noUrut);
    }
    private void cekBebanPenjualan(List<RencanaAnggaranBebanPenjualan> listRab, String noPenjualan, String noUrut){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/BebanPenjualanTerbayar.fxml");
        BebanPenjualanTerbayarController controller = loader.getController();
        controller.setMainApp(mainApp, stage, child);
        controller.setBebanPenjualan(listRab, noPenjualan, noUrut);
    }
    @FXML
    private void addCustomer(){
        Stage child = new Stage();
        FXMLLoader loader = mainApp.showDialog(stage, child, "View/Dialog/AddCustomer.fxml");
        AddCustomerController controller = loader.getController();
        controller.setMainApp(mainApp, stage, child);
        controller.customerTable.setRowFactory((param) -> {
            TableRow<Customer> row = new TableRow<Customer>(){};
            row.setOnMouseClicked((MouseEvent evt) -> {
                if(evt.getButton().equals(MouseButton.PRIMARY) && evt.getClickCount()==2){
                    mainApp.closeDialog(stage, child);
                    penjualan.setCustomer(null);
                    namaField.setText("");
                    alamatField.setText("");
                    if(row.getItem()!=null){
                        penjualan.setCustomer(row.getItem());
                        namaField.setText(penjualan.getCustomer().getNama());
                        alamatField.setText(penjualan.getCustomer().getAlamat());
                    }
                }
            });
            return row; 
        });
    }
    @FXML
    private void save(){
        if(penjualan.getCustomer()==null)
            mainApp.showMessage(Modality.NONE, "Warning", "Customer belum dipilih");
        else if(allPenjualanDetail.isEmpty())
            mainApp.showMessage(Modality.NONE, "Warning", "Pekerjaan tidak ada");
        else{
            MessageController controller = mainApp.showMessage(Modality.WINDOW_MODAL, "Confirmation",
                "Save penjualan "+penjualan.getNoPenjualan()+" ?");
            controller.OK.setOnAction((ActionEvent e) -> {
                mainApp.closeMessage();
                Task<String> task = new Task<String>() {
                    @Override 
                    public String call() throws Exception{
                        try (Connection con = Koneksi.getConnection()) {
                            String noPenjualan = PenjualanHeadDAO.getId(con);
                            penjualan.setNoPenjualan(noPenjualan);
                            penjualan.setTglPemesanan(tglSql.format(new Date()));
                            penjualan.setTglPenjualan("2000-01-01 00:00:00");
                            penjualan.setKodeCustomer(penjualan.getCustomer().getKodeCustomer());
                            penjualan.setNamaProyek(namaProyekField.getText());
                            penjualan.setLokasiPengerjaan(lokasiPengerjaanField.getText());
                            penjualan.setTglMulai("2000-01-01");
                            penjualan.setTglSelesai("2000-01-01");
                            penjualan.setTotalPenjualan(Double.parseDouble(totalPenjualanField.getText().replaceAll(",", "")));
                            penjualan.setPembayaran(0);
                            penjualan.setSisaPembayaran(Double.parseDouble(totalPenjualanField.getText().replaceAll(",", "")));
                            penjualan.setCatatan(catatanField.getText());
                            penjualan.setKodeUser(sistem.getUser().getUsername());
                            penjualan.setTglBatal("2000-01-01 00:00:00");
                            penjualan.setUserBatal("");
                            penjualan.setStatus("open");
                            int i = 1;
                            for(PenjualanDetail temp : allPenjualanDetail){
                                temp.setNoPenjualan(noPenjualan);
                                temp.setNoUrut(new DecimalFormat("000").format(i));
                                double totalAnggaranBebanPenjualan = 0;
                                for(RencanaAnggaranBebanPenjualan b : temp.getRencanaAnggaranBebanPenjualan()){
                                    b.setNoPenjualan(noPenjualan);
                                    b.setNoUrut(new DecimalFormat("000").format(i));
                                    totalAnggaranBebanPenjualan = totalAnggaranBebanPenjualan + b.getJumlahRp();
                                }
                                double totalAnggaranMaterial = 0;
                                for(RencanaAnggaranBebanMaterial b : temp.getRencanaAnggaranBelanja()){
                                    b.setNoPenjualan(noPenjualan);
                                    b.setNoUrut(new DecimalFormat("000").format(i));
                                    totalAnggaranMaterial = totalAnggaranMaterial + (b.getQty()*b.getHargaJual());
                                }
                                temp.setTotalAnggaranBebanMaterial(totalAnggaranMaterial);
                                temp.setTotalAnggaranBebanPenjualan(totalAnggaranBebanPenjualan);
                                temp.setTotalBebanMaterial(0);
                                temp.setTotalBebanPenjualan(0);
                                i++;
                            }
                            penjualan.setAllDetail(allPenjualanDetail);
                            return Service.saveNewPemesanan(con, penjualan);
                        }
                    }
                };
                task.setOnRunning((ex) -> {
                    mainApp.showLoadingScreen();
                });
                task.setOnSucceeded((WorkerStateEvent ex) -> {
                    try{
                        mainApp.closeLoading();
                        if(task.get().equals("true")){
                            mainApp.closeDialog(owner, stage);
                            mainApp.showMessage(Modality.NONE, "Success", "Data penjualan berhasil disimpan");
                            mainApp.showPemesanan();
                        }else{
                            mainApp.showMessage(Modality.NONE, "Error", task.get());
                        }
                    }catch(Exception exc){
                        mainApp.showMessage(Modality.NONE, "Error", exc.toString());
                    }
                });
                task.setOnFailed((ex) -> {
                    task.getException().printStackTrace();
                    mainApp.closeLoading();
                    mainApp.showMessage(Modality.NONE, "Error", task.getException().toString());
                });
                new Thread(task).start();
            });
        }
    }
}
