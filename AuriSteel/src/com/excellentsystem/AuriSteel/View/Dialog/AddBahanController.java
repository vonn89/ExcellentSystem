/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.AuriSteel.View.Dialog;

import com.excellentsystem.AuriSteel.DAO.BahanDAO;
import com.excellentsystem.AuriSteel.DAO.BahanDAO;
import com.excellentsystem.AuriSteel.Function;
import com.excellentsystem.AuriSteel.Koneksi;
import com.excellentsystem.AuriSteel.Main;
import static com.excellentsystem.AuriSteel.Main.df;
import static com.excellentsystem.AuriSteel.Main.tglBarang;
import com.excellentsystem.AuriSteel.Model.Bahan;
import com.excellentsystem.AuriSteel.Model.Bahan;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Xtreme
 */
public class AddBahanController {

    @FXML public TreeTableView<Bahan> bahanTable;
    @FXML private TreeTableColumn<Bahan, String> kodeBahanColumn;
    @FXML private TreeTableColumn<Bahan, String> namaBahanColumn;
    @FXML private TreeTableColumn<Bahan, Number> beratKotorColumn;
    @FXML private TreeTableColumn<Bahan, Number> beratBersihColumn;
    @FXML private TreeTableColumn<Bahan, Number> panjangColumn;
    @FXML private TextField searchField;
    private Main mainApp;  
    private Stage stage;
    private Stage owner;
    private final ObservableList<Bahan> allBahan = FXCollections.observableArrayList();
    private final ObservableList<Bahan> filterData = FXCollections.observableArrayList();
    private final TreeItem<Bahan> root = new TreeItem<>();
    public void initialize() {
        kodeBahanColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().kodeBahanProperty());
        namaBahanColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().namaBahanProperty());
        beratKotorColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().beratKotorProperty());
        beratKotorColumn.setCellFactory(col -> Function.getTreeTableCell());
        beratBersihColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().beratBersihProperty());
        beratBersihColumn.setCellFactory(col -> Function.getTreeTableCell());
        panjangColumn.setCellValueFactory(cellData -> cellData.getValue().getValue().panjangProperty());
        panjangColumn.setCellFactory(col -> Function.getTreeTableCell());
        allBahan.addListener((ListChangeListener.Change<? extends Bahan> change) -> {
            searchBahan();
        });
        searchField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            searchBahan();
        });
        filterData.addAll(allBahan);
    }
    public void setMainApp(Main mainApp,Stage owner,Stage stage) {
        this.mainApp = mainApp;
        this.owner = owner;
        this.stage = stage;
        stage.setOnCloseRequest((event) -> {
            mainApp.closeDialog(owner, stage);
        });
        stage.setHeight(mainApp.screenSize.getHeight()*0.9);
        stage.setWidth(mainApp.screenSize.getWidth()*0.9);
        stage.setX((mainApp.screenSize.getWidth() - stage.getWidth()) / 2);
        stage.setY((mainApp.screenSize.getHeight() - stage.getHeight()) / 2);
    }
    public void getBahan(String kodeGudang){
        Task<List<Bahan>> task = new Task<List<Bahan>>() {
            @Override 
            public List<Bahan> call() throws Exception{
                try (Connection con = Koneksi.getConnection()) {
//                    List<Bahan> listBahan = BahanDAO.getStokAllByDateAndGudang(con, 
//                            tglBarang.format(Function.getServerDate(con)), kodeGudang);
//                    System.out.println(new Date()+" getStok");
                    List<Bahan> listBahan = BahanDAO.getAllByStatus(con, "true");
                    System.out.println(new Date()+" getBahan");
//                    for(Bahan s : listBahan){
//                        for(Bahan b : listBahan){
//                            if(b.getKodeBahan().equals(s.getKodeBahan()))
//                                s.setBahan(b);
//                        }
//                        if(s.getBahan()==null)
//                            System.out.println(s.getKodeBahan());
//                        listStok.add(s);
//                    }
                    System.out.println(new Date()+" finish");
                    return listBahan;
                }
            }
        };
        task.setOnRunning((e) -> {
            mainApp.showLoadingScreen();
        });
        task.setOnSucceeded((ev) -> {
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
    @FXML
    private void searchBahan() {
        filterData.clear();
        for (Bahan temp : allBahan) {
            if (searchField.getText() == null || searchField.getText().equals(""))
                filterData.add(temp);
            else{

                if(checkColumn(temp.getKodeBahan())||
                    checkColumn(temp.getNamaBahan())||
                    checkColumn(temp.getKodeKategori())||
                    checkColumn(temp.getSpesifikasi())||
                    checkColumn(temp.getKeterangan())||
                    checkColumn(df.format(temp.getBeratKotor()))||
                    checkColumn(df.format(temp.getBeratBersih()))||
                    checkColumn(df.format(temp.getPanjang())))
                    filterData.add(temp);
            }
        }
        setTable();
    }
    private void setTable(){
        if(bahanTable.getRoot()!=null)
            bahanTable.getRoot().getChildren().clear();
        List<String> kategori = new ArrayList<>();
        for(Bahan temp: filterData){
            if(!kategori.contains(temp.getKodeKategori()))
                kategori.add(temp.getKodeKategori());
        }
        for(String temp : kategori){
            double totalBeratKotor = 0;
            double totalBeratBersih = 0;
            double totalPanjang = 0;
            Bahan head = new Bahan();
            Bahan b = new Bahan();
//            head.setBahan(b);
            TreeItem<Bahan> parent = new TreeItem<>(head);
            for(Bahan detail: filterData){
                if(temp.equals(detail.getKodeKategori())){
                    TreeItem<Bahan> child = new TreeItem<>(detail);
                    parent.getChildren().add(child);
                    totalBeratKotor = totalBeratKotor + detail.getBeratKotor();
                    totalBeratBersih = totalBeratBersih + detail.getBeratBersih();
                    totalPanjang = totalPanjang + detail.getPanjang();
                }
            }
            parent.getValue().setKodeBahan(temp);
            parent.getValue().setBeratKotor(totalBeratKotor);
            parent.getValue().setBeratBersih(totalBeratBersih);
            parent.getValue().setPanjang(totalPanjang);
            root.getChildren().add(parent);
        }
        bahanTable.setRoot(root);
    }
    public void close(){
        mainApp.closeDialog(owner, stage);
    }    
    
}
