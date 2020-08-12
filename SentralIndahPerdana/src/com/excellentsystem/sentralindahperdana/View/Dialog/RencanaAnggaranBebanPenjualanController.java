/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.sentralindahperdana.View.Dialog;

import com.excellentsystem.sentralindahperdana.DAO.KategoriTransaksiDAO;
import com.excellentsystem.sentralindahperdana.Function;
import com.excellentsystem.sentralindahperdana.Koneksi;
import com.excellentsystem.sentralindahperdana.Main;
import static com.excellentsystem.sentralindahperdana.Main.df;
import com.excellentsystem.sentralindahperdana.Model.KategoriTransaksi;
import com.excellentsystem.sentralindahperdana.Model.RencanaAnggaranBebanPenjualan;
import java.sql.Connection;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author excellent
 */
public class RencanaAnggaranBebanPenjualanController  {

    @FXML private TableView<RencanaAnggaranBebanPenjualan> bebanPenjualanTable;
    @FXML private TableColumn<RencanaAnggaranBebanPenjualan, String> kategoriColumn;
    @FXML private TableColumn<RencanaAnggaranBebanPenjualan, Number> jumlahRpColumn;
    @FXML private TableColumn removeColumn = new TableColumn<>("");
    @FXML private Label totalAnggaranLabel;
    @FXML private ComboBox<String> kategoriCombo;
    @FXML private TextField jumlahRpField;
    @FXML public Button closeButton;
    @FXML public HBox hbox;
    private Main mainApp;  
    private Stage stage;
    private Stage owner;
    public ObservableList<RencanaAnggaranBebanPenjualan> allBeban = FXCollections.observableArrayList();
    private Callback<TableColumn, TableCell> getButtonCell(){
        return (final TableColumn p) -> new TableCell<RencanaAnggaranBebanPenjualan, Boolean>(){
            final Button btn = new Button("");
            @Override
            public void updateItem( Boolean item, boolean empty ){
                super.updateItem( item, empty );
                if ( empty ){
                    setGraphic( null );
                }else{
                    Image imageDecline = new Image(Main.class.getResourceAsStream("Resource/delete.png"),20,20,false,true);
                    btn.setGraphic(new ImageView(imageDecline));
                    btn.setPrefSize(30, 30);
                    btn.setOnAction((ActionEvent e) ->{
                        RencanaAnggaranBebanPenjualan b = (RencanaAnggaranBebanPenjualan)getTableView().getItems().get( getIndex() );
                        deleteBeban(b);
                    });
                    if(hbox.isVisible())
                        setGraphic( btn );
                }
            }
        };
    }
    public void initialize() {
        kategoriColumn.setCellValueFactory(cellData -> cellData.getValue().kategoriProperty());
        jumlahRpColumn.setCellValueFactory(cellData -> cellData.getValue().jumlahRpProperty());
        jumlahRpColumn.setCellFactory((c) -> Function.getTableCell());
        removeColumn.setCellValueFactory( new PropertyValueFactory<>( "" ) );
        removeColumn.setCellFactory(getButtonCell());
        bebanPenjualanTable.setItems(allBeban);
        Function.setNumberField(jumlahRpField);
    }    
    public void setMainApp(Main mainApp,Stage owner, Stage stage) {
        this.mainApp = mainApp;
        this.owner = owner;
        this.stage = stage;
        stage.setOnCloseRequest((e) -> {
            mainApp.closeDialog(owner, stage);
        });
        try(Connection con = Koneksi.getConnection()){
            ObservableList<String> allKategori = FXCollections.observableArrayList();
            for(KategoriTransaksi k : KategoriTransaksiDAO.getAll(con)){
                if(k.getJenisTransaksi().equals("K"))
                    allKategori.add(k.getKodeKategori());
            }
            kategoriCombo.setItems(allKategori);
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    public void setAnggaranBeban(List<RencanaAnggaranBebanPenjualan> b){
        allBeban.clear();
        allBeban.addAll(b);
        hitungTotal();
    }
    @FXML
    private void addBeban(){
        if(kategoriCombo.getSelectionModel().getSelectedItem()==null){
            mainApp.showMessage(Modality.NONE, "Warning", "Kategori belum dipilih");
        }else if("".equals(jumlahRpField.getText())||"0".equals(jumlahRpField.getText())){
            mainApp.showMessage(Modality.NONE, "Warning", "Jumlah rp masih kosong");
        }else{
            RencanaAnggaranBebanPenjualan beban = null;
            for(RencanaAnggaranBebanPenjualan b : allBeban){
                if(b.getKategori().equals(kategoriCombo.getSelectionModel().getSelectedItem()))
                    beban = b;
            }
            if(beban!=null){
                beban.setJumlahRp(beban.getJumlahRp()+
                        Double.parseDouble(jumlahRpField.getText().replaceAll(",", "")));
                allBeban.remove(beban);
                allBeban.add(beban);
                bebanPenjualanTable.refresh();
            }else{
                beban = new RencanaAnggaranBebanPenjualan();
                beban.setKategori(kategoriCombo.getSelectionModel().getSelectedItem());
                beban.setJumlahRp(Double.parseDouble(jumlahRpField.getText().replaceAll(",", "")));
                allBeban.add(beban);
            }
            kategoriCombo.getSelectionModel().clearSelection();
            jumlahRpField.setText("0");
            hitungTotal();
        }
    }
    private void deleteBeban(RencanaAnggaranBebanPenjualan b){
        allBeban.remove(b);
        bebanPenjualanTable.refresh();
        hitungTotal();
    }
    private void hitungTotal(){
        double totalAnggaran = 0;
        for(RencanaAnggaranBebanPenjualan d : allBeban){
            totalAnggaran = totalAnggaran + d.getJumlahRp();
        }
        totalAnggaranLabel.setText(df.format(totalAnggaran));
    }
}
