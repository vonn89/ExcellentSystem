/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.View.Report;

import com.excellentsystem.AuriSteel.DAO.KategoriTransaksiDAO;
import com.excellentsystem.AuriSteel.DAO.KeuanganDAO;
import com.excellentsystem.AuriSteel.Function;
import com.excellentsystem.AuriSteel.Koneksi;
import com.excellentsystem.AuriSteel.Main;
import static com.excellentsystem.AuriSteel.Main.sistem;
import com.excellentsystem.AuriSteel.Model.Helper.UntungRugiSummary;
import com.excellentsystem.AuriSteel.Model.KategoriTransaksi;
import com.excellentsystem.AuriSteel.Model.Keuangan;
import com.excellentsystem.AuriSteel.Model.Otoritas;
import com.excellentsystem.AuriSteel.PrintOut.Report;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;

/**
 * FXML Controller class
 *
 * @author excellent
 */
public class LaporanUntungRugiSummaryController  {

    private DecimalFormat df = new DecimalFormat("###,##0");
    private DecimalFormat pr = new DecimalFormat("###,##0.##");
    @FXML private GridPane pane; 
    @FXML private ComboBox<String> periodeBeforeCombo;
    @FXML private ComboBox<String> periodeCombo;
    
    @FXML private Label revenueLabel;
    @FXML private Label costOfRevenueLabel;
    @FXML private Label grossIncomeLabel;
    @FXML private Label salesExpenseLabel;
    @FXML private Label generalAdministrationLabel;
    @FXML private Label operatingExpenseLabel;
    @FXML private Label incomeOperatingLabel;
    @FXML private Label otherIncomeLabel;
    @FXML private Label otherChargesLabel;
    @FXML private Label totalOtherIncomeChargesLabel;
    @FXML private Label incomeBeforeTaxLabel;
    @FXML private Label incomeTaxLabel;
    @FXML private Label netIncomeLabel;
    
    @FXML private Label grossIncomePercentageLabel;
    @FXML private Label operatingExpensePercentageLabel;
    @FXML private Label incomeOperatingPercentageLabel;
    @FXML private Label incomeBeforeTaxPercentageLabel;
    @FXML private Label netIncomePercentageLabel;
    
    @FXML private Label revenue2Label;
    @FXML private Label costOfRevenue2Label;
    @FXML private Label grossIncome2Label;
    @FXML private Label salesExpense2Label;
    @FXML private Label generalAdministration2Label;
    @FXML private Label operatingExpense2Label;
    @FXML private Label incomeOperating2Label;
    @FXML private Label otherIncome2Label;
    @FXML private Label otherCharges2Label;
    @FXML private Label totalOtherIncomeCharges2Label;
    @FXML private Label incomeBeforeTax2Label;
    @FXML private Label incomeTax2Label;
    @FXML private Label netIncome2Label;
    
    @FXML private Label grossIncomePercentage2Label;
    @FXML private Label operatingExpensePercentage2Label;
    @FXML private Label incomeOperatingPercentage2Label;
    @FXML private Label incomeBeforeTaxPercentage2Label;
    @FXML private Label netIncomePercentage2Label;
    
    private UntungRugiSummary ur = new UntungRugiSummary();
    private Main mainApp;   
    public void initialize() {
        final ContextMenu rm = new ContextMenu();
        MenuItem print = new MenuItem("Print Laporan");
        print.setOnAction((ActionEvent event) -> {
            print();
        });
        MenuItem refresh = new MenuItem("Refresh");
        refresh.setOnAction((ActionEvent event) -> {
            getBefore();
            getNow();
        });
        for(Otoritas o : sistem.getUser().getOtoritas()){
            if(o.getJenis().equals("Print Laporan")&&o.isStatus())
                rm.getItems().addAll(print);
        }
        rm.getItems().addAll(refresh);
        pane.setOnContextMenuRequested((e) -> {
            rm.show(pane, e.getScreenX(), e.getScreenY());
        });
    }   
    public void setMainApp(Main mainApp) {
        try(Connection con = Koneksi.getConnection()){
            this.mainApp = mainApp;
            ObservableList<String> tahun = FXCollections.observableArrayList();
            tahun.add("2016");
            tahun.add("2017");
            tahun.add("2018");
            tahun.add("2019");
            tahun.add("2020");
            tahun.add("2021");
            tahun.add("2022");
            tahun.add("2023");
            tahun.add("2024");
            tahun.add("2025");
            periodeBeforeCombo.setItems(tahun);
            periodeCombo.setItems(tahun);
            periodeBeforeCombo.getSelectionModel().select(new SimpleDateFormat("yyyy").format(Function.getServerDate(con)));
            periodeCombo.getSelectionModel().select(new SimpleDateFormat("yyyy").format(Function.getServerDate(con)));
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }   
    @FXML
    private void getBefore(){
        try(Connection con = Koneksi.getConnection()){
            String tglAwal = periodeBeforeCombo.getSelectionModel().getSelectedItem()+"-01-01";
            String tglAkhir = periodeBeforeCombo.getSelectionModel().getSelectedItem()+"-12-31";
            double penjualan = 0;
            double hpp = 0;
            double salesExpense = 0;
            double generalAdministrationExpense = 0;
            double otherIncome = 0;
            double otherCharge = 0;
            double incomeTax = 0;
            List<KategoriTransaksi> listKategoriTransaksi = KategoriTransaksiDAO.getAllByStatus(con, "%");
            for(Keuangan k : KeuanganDAO.getAllByTipeKeuanganAndTanggal(con, "Penjualan", tglAwal, tglAkhir)){
                penjualan = penjualan + k.getJumlahRp();
            }
            for(Keuangan k : KeuanganDAO.getAllByTipeKeuanganAndTanggal(con, "Retur Penjualan", tglAwal,tglAkhir)){
                penjualan = penjualan - k.getJumlahRp();
            }
            for(Keuangan k : KeuanganDAO.getAllByTipeKeuanganAndTanggal(con, "HPP", tglAwal, tglAkhir)){
                if(k.getKategori().equals("Penjualan"))
                    hpp = hpp + k.getJumlahRp();
                else if(k.getKategori().equals("Penjualan Coil"))
                    hpp = hpp + k.getJumlahRp();
                else if(k.getKategori().equals("Retur Penjualan"))
                    hpp = hpp + k.getJumlahRp();
            }
            for(Keuangan k : KeuanganDAO.getAllByTipeKeuanganAndTanggal(con, "Pendapatan/Beban", tglAwal, tglAkhir)){
                String jenis = "Other charges";
                for(KategoriTransaksi kt : listKategoriTransaksi){
                    if(k.getKategori().equalsIgnoreCase(kt.getKodeKategori()))
                        jenis = kt.getJenisTransaksi();
                }
                if(jenis.equals("Sales expenses")){
                    salesExpense = salesExpense + k.getJumlahRp();
                }else if(jenis.equals("General and Administration expenses")){
                    generalAdministrationExpense = generalAdministrationExpense + k.getJumlahRp();
                }else if(jenis.equals("Other income")){
                    otherIncome = otherIncome + k.getJumlahRp();
                }else if(jenis.equals("Other charges")){
                    otherCharge = otherCharge + k.getJumlahRp();
                }else if(jenis.equals("Income tax")){
                    incomeTax = incomeTax + k.getJumlahRp();
                }
            }
            revenueLabel.setText(df.format(penjualan));
            costOfRevenueLabel.setText(df.format(hpp));
            grossIncomeLabel.setText(df.format(penjualan-hpp));
            salesExpenseLabel.setText(df.format(salesExpense*-1));
            generalAdministrationLabel.setText(df.format(generalAdministrationExpense*-1));
            operatingExpenseLabel.setText(df.format((salesExpense+generalAdministrationExpense)*-1));
            incomeOperatingLabel.setText(df.format(
                    penjualan - hpp + salesExpense + generalAdministrationExpense
            ));
            otherIncomeLabel.setText(df.format(otherIncome));
            otherChargesLabel.setText(df.format(otherCharge*-1));
            totalOtherIncomeChargesLabel.setText(df.format(otherIncome+otherCharge));
            incomeBeforeTaxLabel.setText(df.format(
                    penjualan - hpp + salesExpense + generalAdministrationExpense + otherIncome + otherCharge
            ));
            incomeTaxLabel.setText(df.format(incomeTax*-1));
            netIncomeLabel.setText(df.format(
                    penjualan - hpp+ salesExpense + generalAdministrationExpense + otherIncome + otherCharge + incomeTax
            ));

            grossIncomePercentageLabel.setText(pr.format((penjualan-hpp)/penjualan*100)+" %");
            operatingExpensePercentageLabel.setText(pr.format(((salesExpense+generalAdministrationExpense)*-1)/penjualan*100)+" %");
            incomeOperatingPercentageLabel.setText(pr.format(
                    (penjualan - hpp + salesExpense + generalAdministrationExpense)/penjualan*100)+" %");
            incomeBeforeTaxPercentageLabel.setText(pr.format((
                    penjualan - hpp + salesExpense + generalAdministrationExpense + otherIncome + otherCharge)
                    /penjualan*100)+" %");
            netIncomePercentageLabel.setText(pr.format((
                    penjualan - hpp+ salesExpense + generalAdministrationExpense + otherIncome + otherCharge + incomeTax
            )/penjualan*100)+" %");
    
            ur.setPenjualanBefore(penjualan);
            ur.setHppBefore(hpp);
            ur.setSalesExpenseBefore(salesExpense*-1);
            ur.setGeneralAdministrationExpenseBefore(generalAdministrationExpense*-1);
            ur.setOtherIncomeBefore(otherIncome);
            ur.setOtherChargeBefore(otherCharge*-1);
            ur.setIncomeTaxBefore(incomeTax*-1);
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    @FXML
    private void getNow(){
        try(Connection con = Koneksi.getConnection()){
            String tglAwal = periodeCombo.getSelectionModel().getSelectedItem()+"-01-01";
            String tglAkhir = periodeCombo.getSelectionModel().getSelectedItem()+"-12-31";
            double penjualan = 0;
            double hpp = 0;
            double salesExpense = 0;
            double generalAdministrationExpense = 0;
            double otherIncome = 0;
            double otherCharge = 0;
            double incomeTax = 0;
            
            List<KategoriTransaksi> listKategoriTransaksi = KategoriTransaksiDAO.getAllByStatus(con, "%");
            for(Keuangan k : KeuanganDAO.getAllByTipeKeuanganAndTanggal(con, "Penjualan", tglAwal, tglAkhir)){
                penjualan = penjualan + k.getJumlahRp();
            }
            for(Keuangan k : KeuanganDAO.getAllByTipeKeuanganAndTanggal(con, "Retur Penjualan", tglAwal,tglAkhir)){
                penjualan = penjualan - k.getJumlahRp();
            }
            for(Keuangan k : KeuanganDAO.getAllByTipeKeuanganAndTanggal(con, "HPP", tglAwal, tglAkhir)){
                if(k.getKategori().equals("Penjualan"))
                    hpp = hpp + k.getJumlahRp();
                else if(k.getKategori().equals("Penjualan Coil"))
                    hpp = hpp + k.getJumlahRp();
                else if(k.getKategori().equals("Retur Penjualan"))
                    hpp = hpp + k.getJumlahRp();
            }
            for(Keuangan k : KeuanganDAO.getAllByTipeKeuanganAndTanggal(con, "Pendapatan/Beban", tglAwal, tglAkhir)){
                String jenis = "Other charges";
                for(KategoriTransaksi kt : listKategoriTransaksi){
                    if(k.getKategori().equalsIgnoreCase(kt.getKodeKategori()))
                        jenis = kt.getJenisTransaksi();
                }
                if(jenis.equals("Sales expenses")){
                    salesExpense = salesExpense + k.getJumlahRp();
                }else if(jenis.equals("General and Administration expenses")){
                    generalAdministrationExpense = generalAdministrationExpense + k.getJumlahRp();
                }else if(jenis.equals("Other income")){
                    otherIncome = otherIncome + k.getJumlahRp();
                }else if(jenis.equals("Other charges")){
                    otherCharge = otherCharge + k.getJumlahRp();
                }else if(jenis.equals("Income tax")){
                    incomeTax = incomeTax + k.getJumlahRp();
                }
            }
            revenue2Label.setText(df.format(penjualan));
            costOfRevenue2Label.setText(df.format(hpp));
            grossIncome2Label.setText(df.format(penjualan-hpp));
            salesExpense2Label.setText(df.format(salesExpense*-1));
            generalAdministration2Label.setText(df.format(generalAdministrationExpense*-1));
            operatingExpense2Label.setText(df.format((salesExpense+generalAdministrationExpense)*-1));
            incomeOperating2Label.setText(df.format(
                    penjualan - hpp + salesExpense + generalAdministrationExpense
            ));
            otherIncome2Label.setText(df.format(otherIncome));
            otherCharges2Label.setText(df.format(otherCharge*-1));
            totalOtherIncomeCharges2Label.setText(df.format(otherIncome+otherCharge));
            incomeBeforeTax2Label.setText(df.format(
                    penjualan - hpp + salesExpense + generalAdministrationExpense + otherIncome + otherCharge
            ));
            incomeTax2Label.setText(df.format(incomeTax*-1));
            netIncome2Label.setText(df.format(
                    penjualan - hpp+ salesExpense + generalAdministrationExpense + otherIncome + otherCharge + incomeTax
            ));

            grossIncomePercentage2Label.setText(pr.format((penjualan-hpp)/penjualan*100)+" %");
            operatingExpensePercentage2Label.setText(pr.format(((salesExpense+generalAdministrationExpense)*-1)/penjualan*100)+" %");
            incomeOperatingPercentage2Label.setText(pr.format(
                    (penjualan - hpp + salesExpense + generalAdministrationExpense)/penjualan*100)+" %");
            incomeBeforeTaxPercentage2Label.setText(pr.format((
                    penjualan - hpp + salesExpense + generalAdministrationExpense + otherIncome + otherCharge)
                    /penjualan*100)+" %");
            netIncomePercentage2Label.setText(pr.format((
                    penjualan - hpp+ salesExpense + generalAdministrationExpense + otherIncome + otherCharge + incomeTax
            )/penjualan*100)+" %");
            
            ur.setPenjualan(penjualan);
            ur.setHpp(hpp);
            ur.setSalesExpense(salesExpense*-1);
            ur.setGeneralAdministrationExpense(generalAdministrationExpense*-1);
            ur.setOtherIncome(otherIncome);
            ur.setOtherCharge(otherCharge*-1);
            ur.setIncomeTax(incomeTax*-1);
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    @FXML
    private void print(){
        try{
            List<UntungRugiSummary> listUntungRugi = new ArrayList<>();
            listUntungRugi.clear();
            listUntungRugi.add(ur);
            Report report = new Report();
            report.printLaporanUntungRugiSummary(listUntungRugi, 
                    periodeBeforeCombo.getSelectionModel().getSelectedItem(),  
                    periodeCombo.getSelectionModel().getSelectedItem());
        }catch(Exception e){
            mainApp.showMessage(Modality.NONE, "Error", e.toString());
        }
    }
    
}
