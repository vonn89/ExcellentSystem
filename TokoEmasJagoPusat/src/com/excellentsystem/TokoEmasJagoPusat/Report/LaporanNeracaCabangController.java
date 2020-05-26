/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.Report;

import com.excellentsystem.TokoEmasJagoPusat.DAO.CabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.KeuanganCabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.PembayaranPiutangDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.PiutangDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.StokBarangCabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.StokRosokCabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.TokoCabang.KeuanganDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.TokoCabang.PembayaranPenjualanDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.TokoCabang.PemesananHeadDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.TokoCabang.PenjualanHeadDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.TokoCabang.StokBarangDiCabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.Function;
import com.excellentsystem.TokoEmasJagoPusat.KoneksiCabang;
import com.excellentsystem.TokoEmasJagoPusat.KoneksiPusat;
import static com.excellentsystem.TokoEmasJagoPusat.Main.rp;
import static com.excellentsystem.TokoEmasJagoPusat.Main.sistem;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglBarang;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglNormal;
import com.excellentsystem.TokoEmasJagoPusat.Model.Cabang;
import com.excellentsystem.TokoEmasJagoPusat.Model.Helper.Neraca;
import com.excellentsystem.TokoEmasJagoPusat.Model.KeuanganCabang;
import com.excellentsystem.TokoEmasJagoPusat.Model.PembayaranPiutang;
import com.excellentsystem.TokoEmasJagoPusat.Model.Piutang;
import com.excellentsystem.TokoEmasJagoPusat.Model.StokBarangCabang;
import com.excellentsystem.TokoEmasJagoPusat.Model.StokRosokCabang;
import com.excellentsystem.TokoEmasJagoPusat.Model.TokoCabang.Keuangan;
import com.excellentsystem.TokoEmasJagoPusat.Model.TokoCabang.PembayaranPenjualan;
import com.excellentsystem.TokoEmasJagoPusat.Model.TokoCabang.PemesananHead;
import com.excellentsystem.TokoEmasJagoPusat.Model.TokoCabang.PenjualanHead;
import com.excellentsystem.TokoEmasJagoPusat.Model.TokoCabang.StokBarangDiCabang;
import java.awt.image.BufferedImage;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

public class LaporanNeracaCabangController {

    @SuppressWarnings("rawtypes")
    private JasperPrint jasperPrint;
    @FXML private ImageView imageView;
    @FXML private TextField pageField;
    @FXML private Label totalPageLabel;
    @FXML private Slider zoomLevel;
    private double zoomFactor;
    private double imageHeight;
    private double imageWidth;

    @FXML private DatePicker tglAwalPicker;
    @FXML private DatePicker tglAkhirPicker;
    @FXML private ComboBox<String> cabangCombo;
    @FXML private ComboBox<String> jenisLaporanCombo;
    private ObservableList<String> cabang = FXCollections.observableArrayList();
    
    public void initialize(){
        Function.setNumberField(pageField, rp);
        tglAwalPicker.setConverter(Function.getTglConverter());
        tglAwalPicker.setValue(LocalDate.parse(sistem.getTglSystem()));
        tglAwalPicker.setDayCellFactory((final DatePicker datePicker) -> 
                Function.getDateCellMulai(tglAkhirPicker, LocalDate.parse(sistem.getTglSystem())));
        tglAkhirPicker.setConverter(Function.getTglConverter());
        tglAkhirPicker.setValue(LocalDate.parse(sistem.getTglSystem()));
        tglAkhirPicker.setDayCellFactory((final DatePicker datePicker) -> 
                Function.getDateCellAkhir(tglAwalPicker, LocalDate.parse(sistem.getTglSystem())));
        cabangCombo.setItems(cabang);
        getCabang();
        ObservableList<String> listJenisLaporan = FXCollections.observableArrayList();
        listJenisLaporan.add("Summary");
        listJenisLaporan.add("Detail");
        jenisLaporanCombo.setItems(listJenisLaporan);
        jenisLaporanCombo.getSelectionModel().selectFirst();
    }
    private void getCabang(){
        try(Connection conPusat = KoneksiPusat.getConnection()){
            List<Cabang> listCabang = CabangDAO.getAll(conPusat);
            for(Cabang c : listCabang){
                cabang.add(c.getKodeCabang());
            }
            cabangCombo.getSelectionModel().selectFirst();
        }catch(Exception e){
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Application error - \n" +e);
            alert.showAndWait();
        }
    }
    @FXML
    private void lihatLaporan(){
        if(cabangCombo.getSelectionModel().getSelectedItem()!=null){
            try(Connection conPusat = KoneksiPusat.getConnection()){
                List<Neraca> listNeraca = new ArrayList<>();
                double totalAktiva = 0;
                double totalPassiva = 0;
                List<KeuanganCabang> listBank = new ArrayList<>();
                List<KeuanganCabang> listBankXXX = new ArrayList<>();
                List<Keuangan> listKas = new ArrayList<>();
                List<PenjualanHead> listPiutangPenjualan = new ArrayList<>();
                List<StokBarangDiCabang> listStokBarangDiCabang = new ArrayList<>();
                List<StokBarangDiCabang> listStokBalenanDiCabang = new ArrayList<>();
                List<StokBarangCabang> listStokBalenanDiAmbil = new ArrayList<>();
                List<StokBarangCabang> listStokBalenanDiPusat = new ArrayList<>();
                List<StokBarangCabang> listStokBarangDiSP = new ArrayList<>();
                List<StokBarangCabang> listStokBarangSP = new ArrayList<>();
                List<StokBarangCabang> listStokBarangNew = new ArrayList<>();
                List<StokBarangCabang> listStokBarangDiPindah = new ArrayList<>();
                List<StokRosokCabang> listStokBarangRosok = new ArrayList<>();
                List<PemesananHead> listHutangTerimaDownPayment = new ArrayList<>();
                List<Piutang> listHutangModal = new ArrayList<>();
                List<Piutang> listHutangPembelian = new ArrayList<>();
                double saldoAwalKas = 0;
                double saldoAwalBank = 0;
                double saldoAwalBankXXX = 0;
                double saldoAkhirKas = 0;
                double saldoAkhirBank = 0;
                double saldoAkhirBankXXX = 0;
                Cabang c = CabangDAO.get(conPusat, cabangCombo.getSelectionModel().getSelectedItem());
                try(Connection conCabang = KoneksiCabang.getConnection(c)){
                    double kas = KeuanganDAO.getSaldoAfter(conCabang, "Kasir", "Kas", tglAkhirPicker.getValue().toString());
                    double bank = KeuanganCabangDAO.getSaldoAfter(conPusat, tglAkhirPicker.getValue().toString(), cabangCombo.getSelectionModel().getSelectedItem(), "Kasir", "Bank");
                    double bankxx = KeuanganCabangDAO.getSaldoAfter(conPusat, tglAkhirPicker.getValue().toString(), cabangCombo.getSelectionModel().getSelectedItem(), "Kasir", 
                            "Bank-"+cabangCombo.getSelectionModel().getSelectedItem());
                    double piutangPenjualan = KeuanganDAO.getSaldoAfter(conCabang, "Kasir", "Piutang Penjualan", tglAkhirPicker.getValue().toString());
                    double xxx = KeuanganDAO.getSaldoAfter(conCabang, "Kasir", cabangCombo.getSelectionModel().getSelectedItem(), tglAkhirPicker.getValue().toString());
                    double xxxbln = KeuanganDAO.getSaldoAfter(conCabang, "Kasir", cabangCombo.getSelectionModel().getSelectedItem()+"-BLN", tglAkhirPicker.getValue().toString());
                    double xxxambil = KeuanganCabangDAO.getSaldoAfter(conPusat, tglAkhirPicker.getValue().toString(), cabangCombo.getSelectionModel().getSelectedItem(), "Kasir", 
                            cabangCombo.getSelectionModel().getSelectedItem()+"-Ambil");
                    double blnxxx = KeuanganCabangDAO.getSaldoAfter(conPusat, tglAkhirPicker.getValue().toString(), cabangCombo.getSelectionModel().getSelectedItem(), "Kasir", 
                            "BLN-"+cabangCombo.getSelectionModel().getSelectedItem());
                    double spxxx = KeuanganCabangDAO.getSaldoAfter(conPusat, tglAkhirPicker.getValue().toString(), cabangCombo.getSelectionModel().getSelectedItem(), "Kasir", 
                            "SP-"+cabangCombo.getSelectionModel().getSelectedItem());
                    double xxxsp = KeuanganCabangDAO.getSaldoAfter(conPusat, tglAkhirPicker.getValue().toString(), cabangCombo.getSelectionModel().getSelectedItem(), "Kasir", 
                            cabangCombo.getSelectionModel().getSelectedItem()+"-SP");
                    double xxxnew = KeuanganCabangDAO.getSaldoAfter(conPusat, tglAkhirPicker.getValue().toString(), cabangCombo.getSelectionModel().getSelectedItem(), "Kasir", 
                            cabangCombo.getSelectionModel().getSelectedItem()+"-New");
                    double xxxpindah = KeuanganCabangDAO.getSaldoAfter(conPusat, tglAkhirPicker.getValue().toString(), cabangCombo.getSelectionModel().getSelectedItem(), "Kasir", 
                            cabangCombo.getSelectionModel().getSelectedItem()+"-Pindah");
                    double xxxrosok = KeuanganCabangDAO.getSaldoAfter(conPusat, tglAkhirPicker.getValue().toString(), cabangCombo.getSelectionModel().getSelectedItem(), "Kasir", 
                            cabangCombo.getSelectionModel().getSelectedItem()+"-Rosok");
                    double xxxlebur = KeuanganCabangDAO.getSaldoAfter(conPusat, tglAkhirPicker.getValue().toString(), cabangCombo.getSelectionModel().getSelectedItem(), "Kasir", 
                            cabangCombo.getSelectionModel().getSelectedItem()+"-Lebur");
                    double xxxciok = KeuanganCabangDAO.getSaldoAfter(conPusat, tglAkhirPicker.getValue().toString(), cabangCombo.getSelectionModel().getSelectedItem(), "Kasir", 
                            cabangCombo.getSelectionModel().getSelectedItem()+"-Ciok");
                    double hutangTerimaDownPayment = KeuanganDAO.getSaldoAfter(conCabang, "Kasir", "Hutang Terima Down Payment", tglAkhirPicker.getValue().toString());
                    double hutangPembelian = KeuanganCabangDAO.getSaldoAfter(conPusat, tglAkhirPicker.getValue().toString(), cabangCombo.getSelectionModel().getSelectedItem(), "Kasir", 
                            "Hutang Pembelian");
                    double hutangModal = KeuanganCabangDAO.getSaldoAfter(conPusat, tglAkhirPicker.getValue().toString(), cabangCombo.getSelectionModel().getSelectedItem(), "Kasir", 
                            "Hutang Modal");
                    double modal = KeuanganCabangDAO.getSaldoAfter(conPusat, tglAkhirPicker.getValue().toString(), cabangCombo.getSelectionModel().getSelectedItem(), "Kasir", 
                            "Modal");
                    modal = modal + KeuanganDAO.getSaldoAfter(conCabang, "Kasir", "Modal", tglAkhirPicker.getValue().toString());
                    //ur ditahan
                    double penjualanUmumDitahan = KeuanganDAO.getSaldoBefore(conCabang, "Kasir", "Penjualan Umum", tglAwalPicker.getValue().toString());
                    double hppPenjualanUmumDitahan = KeuanganDAO.getSaldoBefore(conCabang, "Kasir", "HPP Penjualan Umum", tglAwalPicker.getValue().toString());
                    double penjualanAntarCabangDitahan = KeuanganDAO.getSaldoBefore(conCabang, "Kasir", "Penjualan Antar Cabang", tglAwalPicker.getValue().toString());
                    double hppPenjualanAntarCabangDitahan = KeuanganDAO.getSaldoBefore(conCabang, "Kasir", "HPP Penjualan Antar Cabang", tglAwalPicker.getValue().toString());
                    double pendapatanKartuMemberDitahan = KeuanganDAO.getSaldoBefore(conCabang, "Kasir", "Pendapatan Kartu Member", tglAwalPicker.getValue().toString());
                    double pendapatanServisDitahan = KeuanganDAO.getSaldoBefore(conCabang, "Kasir", "Pendapatan Servis", tglAwalPicker.getValue().toString());
                    double bebanPoinDitahan = KeuanganDAO.getSaldoBefore(conCabang, "Kasir", "Beban Poin", tglAwalPicker.getValue().toString());
                    double pendapatanLainLainDitahan = KeuanganDAO.getSaldoBefore(conCabang, "Kasir", "Pendapatan Lain-lain", tglAwalPicker.getValue().toString());
                    double bebanGajiDitahan = KeuanganDAO.getSaldoBefore(conCabang, "Kasir", "Beban Gaji", tglAwalPicker.getValue().toString());
                    double bebanAdministrasiDitahan = KeuanganDAO.getSaldoBefore(conCabang, "Kasir", "Beban Administrasi", tglAwalPicker.getValue().toString());
                    double bebanOperasionalDitahan = KeuanganDAO.getSaldoBefore(conCabang, "Kasir", "Beban Operasional", tglAwalPicker.getValue().toString());
                    double bebanPajakDitahan = KeuanganDAO.getSaldoBefore(conCabang, "Kasir", "Beban Pajak", tglAwalPicker.getValue().toString());
                    double bebanPenyusutanDitahan = KeuanganDAO.getSaldoBefore(conCabang, "Kasir", "Beban Penyusutan", tglAwalPicker.getValue().toString());
                    double bebanLainLainDitahan = KeuanganDAO.getSaldoBefore(conCabang, "Kasir", "Beban Lain-lain", tglAwalPicker.getValue().toString());
                    double penjualanCiokDitahan = KeuanganCabangDAO.getSaldoBefore(conPusat, tglAwalPicker.getValue().toString(), cabangCombo.getSelectionModel().getSelectedItem(), "Kasir", 
                            "Penjualan Ciok");
                    double hppPenjualanCiokDitahan = KeuanganCabangDAO.getSaldoBefore(conPusat, tglAwalPicker.getValue().toString(), cabangCombo.getSelectionModel().getSelectedItem(), "Kasir", 
                            "HPP Penjualan Ciok");
                    double pendapatanBebanPenyesuaianDitahan = KeuanganCabangDAO.getSaldoBefore(conPusat, tglAwalPicker.getValue().toString(), cabangCombo.getSelectionModel().getSelectedItem(), "Kasir", 
                            "Pendapatan/Beban Penyesuaian Barang");
                    double bebanPenyusutanResetStokBarangDitahan = KeuanganCabangDAO.getSaldoBefore(conPusat, tglAwalPicker.getValue().toString(), cabangCombo.getSelectionModel().getSelectedItem(), "Kasir", 
                            "Beban Penyusutan Reset Stok Barang");
                    double bebanPenyusutanSPBarangDitahan = KeuanganCabangDAO.getSaldoBefore(conPusat, tglAwalPicker.getValue().toString(), cabangCombo.getSelectionModel().getSelectedItem(), "Kasir", 
                            "Beban Penyusutan SP Barang");
                    double pendapatanBebanKursEmasDitahan = KeuanganCabangDAO.getSaldoBefore(conPusat, tglAwalPicker.getValue().toString(), cabangCombo.getSelectionModel().getSelectedItem(), "Kasir", 
                            "Pendapatan/Beban Kurs Emas");
                    double urDitahan = penjualanUmumDitahan+penjualanAntarCabangDitahan+penjualanCiokDitahan+
                            hppPenjualanUmumDitahan+hppPenjualanAntarCabangDitahan+hppPenjualanCiokDitahan+
                            pendapatanKartuMemberDitahan+pendapatanServisDitahan+bebanPoinDitahan+pendapatanLainLainDitahan+bebanGajiDitahan+
                            bebanAdministrasiDitahan+bebanOperasionalDitahan+bebanPajakDitahan+bebanPenyusutanDitahan+bebanLainLainDitahan+
                            pendapatanBebanPenyesuaianDitahan+bebanPenyusutanResetStokBarangDitahan+bebanPenyusutanSPBarangDitahan+pendapatanBebanKursEmasDitahan;
                    modal = modal + urDitahan;
                    //ur berjalan
                    double penjualanUmum = KeuanganDAO.getTotal(conCabang, "Kasir", "Penjualan Umum", tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString());
                    double hppPenjualanUmum = KeuanganDAO.getTotal(conCabang, "Kasir", "HPP Penjualan Umum", tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString());
                    double penjualanAntarCabang = KeuanganDAO.getTotal(conCabang, "Kasir", "Penjualan Antar Cabang", tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString());
                    double hppPenjualanAntarCabang = KeuanganDAO.getTotal(conCabang, "Kasir", "HPP Penjualan Antar Cabang", tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString());
                    double pendapatanKartuMember = KeuanganDAO.getTotal(conCabang, "Kasir", "Pendapatan Kartu Member", tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString());
                    double pendapatanServis = KeuanganDAO.getTotal(conCabang, "Kasir", "Pendapatan Servis", tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString());
                    double bebanPoin = KeuanganDAO.getTotal(conCabang, "Kasir", "Beban Poin", tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString());
                    double pendapatanLainLain = KeuanganDAO.getTotal(conCabang, "Kasir", "Pendapatan Lain-lain", tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString());
                    double bebanGaji = KeuanganDAO.getTotal(conCabang, "Kasir", "Beban Gaji", tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString());
                    double bebanAdministrasi = KeuanganDAO.getTotal(conCabang, "Kasir", "Beban Administrasi", tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString());
                    double bebanOperasional = KeuanganDAO.getTotal(conCabang, "Kasir", "Beban Operasional", tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString());
                    double bebanPajak = KeuanganDAO.getTotal(conCabang, "Kasir", "Beban Pajak", tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString());
                    double bebanPenyusutan = KeuanganDAO.getTotal(conCabang, "Kasir", "Beban Penyusutan", tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString());
                    double bebanLainLain = KeuanganDAO.getTotal(conCabang, "Kasir", "Beban Lain-lain", tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString());
                    double penjualanCiok = KeuanganCabangDAO.getTotal(conPusat, tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), 
                            cabangCombo.getSelectionModel().getSelectedItem(), "Kasir", "Penjualan Ciok");
                    double hppPenjualanCiok = KeuanganCabangDAO.getTotal(conPusat, tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), 
                            cabangCombo.getSelectionModel().getSelectedItem(), "Kasir", "HPP Penjualan Ciok");
                    double pendapatanBebanPenyesuaian = KeuanganCabangDAO.getTotal(conPusat, tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), 
                            cabangCombo.getSelectionModel().getSelectedItem(), "Kasir", "Pendapatan/Beban Penyesuaian Barang");
                    double bebanPenyusutanResetStokBarang = KeuanganCabangDAO.getTotal(conPusat, tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), 
                            cabangCombo.getSelectionModel().getSelectedItem(), "Kasir", "Beban Penyusutan Reset Stok Barang");
                    double bebanPenyusutanSPBarang = KeuanganCabangDAO.getTotal(conPusat, tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), 
                            cabangCombo.getSelectionModel().getSelectedItem(), "Kasir", "Beban Penyusutan SP Barang");
                    double pendapatanBebanKursEmas = KeuanganCabangDAO.getTotal(conPusat, tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), 
                            cabangCombo.getSelectionModel().getSelectedItem(), "Kasir", "Pendapatan/Beban Kurs Emas");
                    
                    double ur = penjualanUmum+penjualanAntarCabang+penjualanCiok+
                            pendapatanBebanKursEmas+pendapatanBebanPenyesuaian+pendapatanKartuMember+pendapatanLainLain+pendapatanServis+
                            hppPenjualanUmum+hppPenjualanAntarCabang+hppPenjualanCiok+
                            bebanAdministrasi+bebanGaji+bebanLainLain+bebanOperasional+bebanPajak+bebanPenyusutan+bebanPenyusutanResetStokBarang+bebanPenyusutanSPBarang+bebanPoin;
                    
                    listNeraca.add(new Neraca("Kas/Bank", "", "Hutang", ""));
                    listNeraca.add(new Neraca(" Kas", " "+rp.format(kas), " Hutang Terima Down Payment", " "+rp.format(hutangTerimaDownPayment)));
                    listNeraca.add(new Neraca(" Bank", " "+rp.format(bank), " Hutang Pembelian", " "+rp.format(hutangPembelian)));
                    listNeraca.add(new Neraca(" Bank-"+cabangCombo.getSelectionModel().getSelectedItem(), " "+rp.format(bankxx), " Hutang Modal", " "+rp.format(hutangModal)));
                    listNeraca.add(new Neraca("Total Kas/Bank", rp.format(kas+bank+bankxx), "Total Hutang", rp.format(hutangTerimaDownPayment+hutangPembelian+hutangModal)));
                    listNeraca.add(new Neraca("", "", "", ""));
                    listNeraca.add(new Neraca("Piutang", "", "Modal", rp.format(modal)));
                    listNeraca.add(new Neraca(" Piutang Penjualan", " "+rp.format(piutangPenjualan), "", ""));
                    listNeraca.add(new Neraca("Total Piutang", rp.format(piutangPenjualan), "Untung/Rugi", rp.format(ur)));
                    listNeraca.add(new Neraca("", "", "", ""));
                    listNeraca.add(new Neraca("Stok Persediaan Barang", "", "", ""));
                    listNeraca.add(new Neraca(" "+cabangCombo.getSelectionModel().getSelectedItem(), " "+rp.format(xxx), "", ""));
                    listNeraca.add(new Neraca(" "+cabangCombo.getSelectionModel().getSelectedItem()+"-BLN", " "+rp.format(xxxbln), "", ""));
                    listNeraca.add(new Neraca(" "+cabangCombo.getSelectionModel().getSelectedItem()+"-Ambil", " "+rp.format(xxxambil), "", ""));
                    listNeraca.add(new Neraca(" BLN-"+cabangCombo.getSelectionModel().getSelectedItem(), " "+rp.format(blnxxx), "", ""));
                    listNeraca.add(new Neraca(" SP-"+cabangCombo.getSelectionModel().getSelectedItem(), " "+rp.format(spxxx), "", ""));
                    listNeraca.add(new Neraca(" "+cabangCombo.getSelectionModel().getSelectedItem()+"-SP", " "+rp.format(xxxsp), "", ""));
                    listNeraca.add(new Neraca(" "+cabangCombo.getSelectionModel().getSelectedItem()+"-New", " "+rp.format(xxxnew), "", ""));
                    listNeraca.add(new Neraca(" "+cabangCombo.getSelectionModel().getSelectedItem()+"-Pindah", " "+rp.format(xxxpindah), "", ""));
                    listNeraca.add(new Neraca(" "+cabangCombo.getSelectionModel().getSelectedItem()+"-Rosok", " "+rp.format(xxxrosok), "", ""));
                    listNeraca.add(new Neraca(" "+cabangCombo.getSelectionModel().getSelectedItem()+"-Lebur", " "+rp.format(xxxlebur), "", ""));
                    listNeraca.add(new Neraca(" "+cabangCombo.getSelectionModel().getSelectedItem()+"-Ciok", " "+rp.format(xxxciok), "", ""));
                    listNeraca.add(new Neraca("Total Stok Persediaan Barang", rp.format(xxx+xxxbln+xxxambil+blnxxx+spxxx+xxxsp+xxxnew+xxxpindah+xxxrosok+xxxlebur+xxxciok), "", ""));
                    listNeraca.add(new Neraca("", "", "", ""));
                    totalAktiva = kas+bank+bankxx+piutangPenjualan+xxx+xxxbln+xxxambil+blnxxx+spxxx+xxxsp+xxxnew+xxxpindah+xxxrosok+xxxlebur+xxxciok;
                    totalPassiva = hutangTerimaDownPayment+hutangPembelian+hutangModal+modal+ur;
                    
                    
                    if(jenisLaporanCombo.getSelectionModel().getSelectedItem().equals("Detail")){
                        saldoAwalKas = KeuanganDAO.getSaldoBefore(conCabang, "Kasir", "Kas", tglAwalPicker.getValue().toString());
                        saldoAkhirKas = KeuanganDAO.getSaldoAfter(conCabang, "Kasir", "Kas", tglAkhirPicker.getValue().toString());
                        listKas = KeuanganDAO.getAllByDateAndKasirAndTipeKeuanganAndKategoriAndSales(
                            conCabang, tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), "Kasir", "Kas", "%","%");
                        Collections.sort(listKas, (o1, o2) -> {
                            int sComp = ((Keuangan) o1).getKategori().compareTo(((Keuangan) o2).getKategori());
                            if (sComp != 0) 
                                return sComp;
                            return ((Keuangan) o1).getKategori().compareTo(((Keuangan) o2).getKategori());
                        });
                        saldoAwalBank = KeuanganCabangDAO.getSaldoBefore(conPusat, 
                                tglAwalPicker.getValue().toString(), cabangCombo.getSelectionModel().getSelectedItem(), "Kasir", "Bank");
                        saldoAkhirBank = KeuanganCabangDAO.getSaldoAfter(conPusat, 
                                tglAkhirPicker.getValue().toString(), cabangCombo.getSelectionModel().getSelectedItem(), "Kasir", "Bank");
                        listBank = KeuanganCabangDAO.getAllByDateAndCabangAndKasirAndTipeKeuangan(
                            conPusat, tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(),
                                cabangCombo.getSelectionModel().getSelectedItem(), "Kasir", "Bank");
                        Collections.sort(listBank, (o1, o2) -> {
                            int sComp = ((KeuanganCabang) o1).getKategori().compareTo(((KeuanganCabang) o2).getKategori());
                            if (sComp != 0) 
                                return sComp;
                            return ((KeuanganCabang) o1).getKategori().compareTo(((KeuanganCabang) o2).getKategori());
                        });
                        saldoAwalBankXXX = KeuanganCabangDAO.getSaldoBefore(conPusat, 
                                tglAwalPicker.getValue().toString(), cabangCombo.getSelectionModel().getSelectedItem(), 
                                "Kasir", "Bank-"+cabangCombo.getSelectionModel().getSelectedItem());
                        saldoAkhirBankXXX = KeuanganCabangDAO.getSaldoAfter(conPusat, 
                                tglAkhirPicker.getValue().toString(), cabangCombo.getSelectionModel().getSelectedItem(), 
                                "Kasir", "Bank-"+cabangCombo.getSelectionModel().getSelectedItem());
                        listBankXXX = KeuanganCabangDAO.getAllByDateAndCabangAndKasirAndTipeKeuangan(
                            conPusat, tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(),
                                cabangCombo.getSelectionModel().getSelectedItem(), "Kasir", "Bank-"+cabangCombo.getSelectionModel().getSelectedItem());
                        Collections.sort(listBankXXX, (o1, o2) -> {
                            int sComp = ((KeuanganCabang) o1).getKategori().compareTo(((KeuanganCabang) o2).getKategori());
                            if (sComp != 0) 
                                return sComp;
                            return ((KeuanganCabang) o1).getKategori().compareTo(((KeuanganCabang) o2).getKategori());
                        });
                        List<PenjualanHead> listPenjualan= PenjualanHeadDAO.getAllByDateAndStatus(
                            conCabang, "2000-01-01", tglAkhirPicker.getValue().toString(),"true");
                        List<PembayaranPenjualan> listPembayaran = PembayaranPenjualanDAO.getAllByDateAndStatus(
                            conCabang, "2000-01-01", tglAkhirPicker.getValue().toString(),"true");
                        for(PenjualanHead p : listPenjualan){
                            p.setPembayaran(0);
                            p.setSisaPembayaran(p.getGrandtotal());
                            for(PembayaranPenjualan pp : listPembayaran){
                                if(p.getNoPenjualan().equals(pp.getNoPenjualan())){
                                    p.setPembayaran(p.getPembayaran()+pp.getJumlahPembayaran());
                                    p.setSisaPembayaran(p.getSisaPembayaran()-pp.getJumlahPembayaran());
                                }
                            }
                            if(p.getSisaPembayaran()>0)
                                listPiutangPenjualan.add(p);
                        }
                        Collections.sort(listBankXXX, (o1, o2) -> {
                            int sComp = ((KeuanganCabang) o1).getKategori().compareTo(((KeuanganCabang) o2).getKategori());
                            if (sComp != 0) 
                                return sComp;
                            return ((KeuanganCabang) o1).getKategori().compareTo(((KeuanganCabang) o2).getKategori());
                        });
                        listStokBarangDiCabang = StokBarangDiCabangDAO.getAllByTanggalAndGudangAndKategoriAndJenis(
                                conCabang, tglAkhirPicker.getValue().toString(), cabangCombo.getSelectionModel().getSelectedItem(), "%", "%");
                        Collections.sort(listStokBarangDiCabang, (o1, o2) -> {
                            int sComp = ((StokBarangDiCabang) o1).getKodeKategori().compareTo(((StokBarangDiCabang) o2).getKodeKategori());
                            if (sComp != 0) 
                                return sComp;
                            return ((StokBarangDiCabang) o1).getKodeJenis().compareTo(((StokBarangDiCabang) o2).getKodeJenis());
                        });
                        listStokBalenanDiCabang = StokBarangDiCabangDAO.getAllByTanggalAndGudangAndKategoriAndJenis(
                                conCabang, tglAkhirPicker.getValue().toString(), cabangCombo.getSelectionModel().getSelectedItem()+"-BLN", "%", "%");
                        Collections.sort(listStokBalenanDiCabang, (o1, o2) -> {
                            int sComp = ((StokBarangDiCabang) o1).getKodeKategori().compareTo(((StokBarangDiCabang) o2).getKodeKategori());
                            if (sComp != 0) 
                                return sComp;
                            return ((StokBarangDiCabang) o1).getKodeJenis().compareTo(((StokBarangDiCabang) o2).getKodeJenis());
                        });
                        listStokBalenanDiAmbil = StokBarangCabangDAO.getAllNonBarcodeByCabangAndGudangAndKategoriAndJenis(
                                conPusat, tglAkhirPicker.getValue().toString(), cabangCombo.getSelectionModel().getSelectedItem(),
                                cabangCombo.getSelectionModel().getSelectedItem()+"-Ambil", "%", "%");
                        Collections.sort(listStokBalenanDiAmbil, (o1, o2) -> {
                            int sComp = ((StokBarangCabang) o1).getKodeKategori().compareTo(((StokBarangCabang) o2).getKodeKategori());
                            if (sComp != 0) 
                                return sComp;
                            return ((StokBarangCabang) o1).getKodeJenis().compareTo(((StokBarangCabang) o2).getKodeJenis());
                        });
                        listStokBalenanDiPusat = StokBarangCabangDAO.getAllNonBarcodeByCabangAndGudangAndKategoriAndJenis(
                                conPusat, tglAkhirPicker.getValue().toString(), cabangCombo.getSelectionModel().getSelectedItem(),
                                "BLN-"+cabangCombo.getSelectionModel().getSelectedItem(), "%", "%");
                        Collections.sort(listStokBalenanDiPusat, (o1, o2) -> {
                            int sComp = ((StokBarangCabang) o1).getKodeKategori().compareTo(((StokBarangCabang) o2).getKodeKategori());
                            if (sComp != 0) 
                                return sComp;
                            return ((StokBarangCabang) o1).getKodeJenis().compareTo(((StokBarangCabang) o2).getKodeJenis());
                        });
                        listStokBarangDiSP = StokBarangCabangDAO.getAllNonBarcodeByCabangAndGudangAndKategoriAndJenis(
                                conPusat, tglAkhirPicker.getValue().toString(), cabangCombo.getSelectionModel().getSelectedItem(),
                                "SP-"+cabangCombo.getSelectionModel().getSelectedItem(), "%", "%");
                        Collections.sort(listStokBarangDiSP, (o1, o2) -> {
                            int sComp = ((StokBarangCabang) o1).getKodeKategori().compareTo(((StokBarangCabang) o2).getKodeKategori());
                            if (sComp != 0) 
                                return sComp;
                            return ((StokBarangCabang) o1).getKodeJenis().compareTo(((StokBarangCabang) o2).getKodeJenis());
                        });
                        listStokBarangSP.addAll(StokBarangCabangDAO.getAllNonBarcodeByCabangAndGudangAndKategoriAndJenis(
                                conPusat, tglAkhirPicker.getValue().toString(), cabangCombo.getSelectionModel().getSelectedItem(),
                                cabangCombo.getSelectionModel().getSelectedItem()+"-SP", "%", "%"));
                        listStokBarangSP.addAll(StokBarangCabangDAO.getAllBarcodeByCabangAndGudangAndKategoriAndJenisAndBarcode(
                                conPusat, tglAkhirPicker.getValue().toString(), cabangCombo.getSelectionModel().getSelectedItem(),
                                cabangCombo.getSelectionModel().getSelectedItem()+"-SP", "%", "%", "%"));
                        Collections.sort(listStokBarangSP, (o1, o2) -> {
                            int sComp = ((StokBarangCabang) o1).getKodeKategori().compareTo(((StokBarangCabang) o2).getKodeKategori());
                            if (sComp != 0) 
                                return sComp;
                            return ((StokBarangCabang) o1).getKodeJenis().compareTo(((StokBarangCabang) o2).getKodeJenis());
                        });
                        listStokBarangNew.addAll(StokBarangCabangDAO.getAllNonBarcodeByCabangAndGudangAndKategoriAndJenis(
                                conPusat, tglAkhirPicker.getValue().toString(), cabangCombo.getSelectionModel().getSelectedItem(),
                                cabangCombo.getSelectionModel().getSelectedItem()+"-New", "%", "%"));
                        listStokBarangNew.addAll(StokBarangCabangDAO.getAllBarcodeByCabangAndGudangAndKategoriAndJenisAndBarcode(
                                conPusat, tglAkhirPicker.getValue().toString(), cabangCombo.getSelectionModel().getSelectedItem(),
                                cabangCombo.getSelectionModel().getSelectedItem()+"-New", "%", "%", "%"));
                        Collections.sort(listStokBarangNew, (o1, o2) -> {
                            int sComp = ((StokBarangCabang) o1).getKodeKategori().compareTo(((StokBarangCabang) o2).getKodeKategori());
                            if (sComp != 0) 
                                return sComp;
                            return ((StokBarangCabang) o1).getKodeJenis().compareTo(((StokBarangCabang) o2).getKodeJenis());
                        });
                        listStokBarangDiPindah = StokBarangCabangDAO.getAllBarcodeByCabangAndGudangAndKategoriAndJenisAndBarcode(
                                conPusat, tglAkhirPicker.getValue().toString(), cabangCombo.getSelectionModel().getSelectedItem(),
                                cabangCombo.getSelectionModel().getSelectedItem()+"-Pindah", "%", "%", "%");
                        Collections.sort(listStokBarangDiPindah, (o1, o2) -> {
                            int sComp = ((StokBarangCabang) o1).getKodeKategori().compareTo(((StokBarangCabang) o2).getKodeKategori());
                            if (sComp != 0) 
                                return sComp;
                            return ((StokBarangCabang) o1).getKodeJenis().compareTo(((StokBarangCabang) o2).getKodeJenis());
                        });
                        listStokBarangRosok = StokRosokCabangDAO.getAllByCabangAndGudang(
                                conPusat, tglAkhirPicker.getValue().toString(), cabangCombo.getSelectionModel().getSelectedItem(),"%");
                        Collections.sort(listStokBarangRosok, (o1, o2) -> {
                            int sComp = ((StokRosokCabang) o1).getKodeGudang().compareTo(((StokRosokCabang) o2).getKodeGudang());
                            if (sComp != 0) 
                                return sComp;
                            return ((StokRosokCabang) o1).getKodeGudang().compareTo(((StokRosokCabang) o2).getKodeGudang());
                        });
                        List<PemesananHead> listSemuaPemesanan = PemesananHeadDAO.getAllByDateAndStatusAmbilAndStatusBatal(
                                conCabang, "2000-01-01", tglAkhirPicker.getValue().toString(), "%", "%");
                        List<PemesananHead> listPemesananDiambil = PemesananHeadDAO.getAllByTglAmbil(
                                conCabang, "2000-01-01",  tglAkhirPicker.getValue().toString());
                        List<PemesananHead> listPemesananDibatal = PemesananHeadDAO.getAllByTglBatal(
                                conCabang, "2000-01-01",  tglAkhirPicker.getValue().toString());
                        for(PemesananHead p : listSemuaPemesanan){
                            boolean status = true;
                            for(PemesananHead pp : listPemesananDiambil){
                                if(p.getNoPemesanan().equals(pp.getNoPemesanan()))
                                    status = false;
                            }
                            for(PemesananHead pp : listPemesananDibatal){
                                if(p.getNoPemesanan().equals(pp.getNoPemesanan()))
                                    status = false;
                            }
                            if(status)
                                listHutangTerimaDownPayment.add(p);
                        }
                        List<Piutang> listSemuaPiutang = PiutangDAO.getAllByDateAndCabangAndTipeKasirAndKategoriAndStatus(
                                conPusat, "2000-01-01", tglAkhirPicker.getValue().toString(), cabangCombo.getSelectionModel().getSelectedItem(), "Kasir", "%", "%");
                        List<PembayaranPiutang> listPembayaranPiutang = PembayaranPiutangDAO.getAllByDateAndNoPiutangAndStatus(
                                conPusat, "2000-01-01", tglAkhirPicker.getValue().toString(), "%", "true");
                        for(Piutang p : listSemuaPiutang){
                            p.setTerbayar(0);
                            p.setSisaPiutang(p.getJumlahPiutang());
                            for(PembayaranPiutang pp: listPembayaranPiutang){
                                if(p.getNoPiutang().equals(pp.getNoPiutang())){
                                    p.setTerbayar(p.getTerbayar()+pp.getTerbayar());
                                    p.setSisaPiutang(p.getSisaPiutang()-pp.getTerbayar());
                                }
                            }
                            if(p.getSisaPiutang()>0){
                                if(p.getKategori().equals("Hutang Modal"))
                                    listHutangModal.add(p);
                                if(p.getKategori().equals("Hutang Pembelian"))
                                    listHutangPembelian.add(p);
                            }
                        }
                    }
                }
                
                JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanNeracaCabang.jrxml"));
                JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listNeraca);
                Map hash = new HashMap();
                hash.put("tanggal", tglNormal.format(tglBarang.parse(tglAwalPicker.getValue().toString()))+" - "+
                            tglNormal.format(tglBarang.parse(tglAkhirPicker.getValue().toString())));
                hash.put("cabang", cabangCombo.getSelectionModel().getSelectedItem());
                hash.put("totalAktiva", rp.format(totalAktiva));
                hash.put("totalPassiva", rp.format(totalPassiva));
                hash.put("jenisLaporan", jenisLaporanCombo.getSelectionModel().getSelectedItem());
                
                hash.put("subReportKeuangan", JasperCompileManager.compileReport(
                        JRXmlLoader.load(getClass().getResourceAsStream("DetailKeuangan.jrxml"))));
                hash.put("saldoAwalKas", saldoAwalKas);
                hash.put("saldoAkhirKas", saldoAkhirKas);
                hash.put("listKas", listKas);
                
                hash.put("subReportKeuanganCabang", JasperCompileManager.compileReport(
                        JRXmlLoader.load(getClass().getResourceAsStream("DetailKeuanganCabang.jrxml"))));
                hash.put("saldoAwalBank", saldoAwalBank);
                hash.put("saldoAkhirBank", saldoAkhirBank);
                hash.put("listBank", listBank);
                hash.put("saldoAwalBankXXX", saldoAwalBankXXX);
                hash.put("saldoAkhirBankXXX", saldoAkhirBankXXX);
                hash.put("listBankXXX", listBankXXX);
                
                hash.put("subReportPiutangPenjualan", JasperCompileManager.compileReport(
                        JRXmlLoader.load(getClass().getResourceAsStream("DetailPiutangPenjualan.jrxml"))));
                hash.put("listPiutangPenjualan", listPiutangPenjualan);
                
                hash.put("subReportStokBarang", JasperCompileManager.compileReport(
                        JRXmlLoader.load(getClass().getResourceAsStream("DetailStokBarang.jrxml"))));
                hash.put("listStokBarangXXX", listStokBarangDiCabang);
                hash.put("listStokBarangXXXBLN", listStokBalenanDiCabang);
                
                hash.put("subReportStokBarangCabang", JasperCompileManager.compileReport(
                        JRXmlLoader.load(getClass().getResourceAsStream("DetailStokBarangCabang.jrxml"))));
                hash.put("listStokBarangXXXAmbil", listStokBalenanDiAmbil);
                hash.put("listStokBarangBLNXXX", listStokBalenanDiPusat);
                hash.put("listStokBarangSPXXX", listStokBarangDiSP);
                hash.put("listStokBarangXXXSP", listStokBarangSP);
                hash.put("listStokBarangXXXNew", listStokBarangNew);
                hash.put("listStokBarangXXXPindah", listStokBarangDiPindah);
                
                hash.put("subReportStokRosokCabang", JasperCompileManager.compileReport(
                        JRXmlLoader.load(getClass().getResourceAsStream("DetailStokRosokCabang.jrxml"))));
                hash.put("listStokRosokCabang", listStokBarangRosok);
                                
                hash.put("subReportHutangTerimaDownPayment", JasperCompileManager.compileReport(
                        JRXmlLoader.load(getClass().getResourceAsStream("DetailHutangTerimaDownPayment.jrxml"))));
                hash.put("listHutangTerimaDownPayment", listHutangTerimaDownPayment);
                                
                hash.put("subReportHutang", JasperCompileManager.compileReport(
                        JRXmlLoader.load(getClass().getResourceAsStream("DetailHutang.jrxml"))));
                hash.put("listHutangModal", listHutangModal);
                hash.put("listHutangPembelian", listHutangPembelian);
                                
                JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
                jasperPrint = JasperFillManager.fillReport(jasperReport,hash, beanColDataSource);
                zoomFactor = 1.5d;
                zoomLevel.setValue(100d);
                imageView.setX(0);
                imageView.setY(0);
                imageHeight = jasperPrint.getPageHeight();
                imageWidth = jasperPrint.getPageWidth();
                zoomLevel.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
                    zoomFactor = newValue.doubleValue() * 1.5 / 100;
                    imageView.setFitHeight(imageHeight * zoomFactor);
                    imageView.setFitWidth(imageWidth * zoomFactor);
                });
                if(jasperPrint.getPages().size() > 0){
                    showImage(1);
                    pageField.setText("1");
                    totalPageLabel.setText(String.valueOf(jasperPrint.getPages().size()));
                }else{
                    imageView.setImage(null);
                    pageField.setText("0");
                    totalPageLabel.setText("0");
                }
            }catch(Exception e){
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Application error - \n" +e);
                alert.showAndWait();
            }
        }
    }
    @FXML
    private void changePage(){
        try {
            int pageNumber = Integer.parseInt(pageField.getText().replaceAll(",", ""));
            if(1<=pageNumber && pageNumber<=jasperPrint.getPages().size()){
                showImage(pageNumber);
                pageField.setText(rp.format(pageNumber));
            }else{
                pageField.setText("0");
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Application error - \n" +e);
            alert.showAndWait();
        }
    }
    @FXML
    public void prevPage(){
        try {
            if(Integer.parseInt(pageField.getText().replaceAll(",", ""))>1){
                int pageNumber = Integer.parseInt(pageField.getText().replaceAll(",", ""))-1;
                showImage(pageNumber);
                pageField.setText(rp.format(pageNumber));
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Application error - \n" +e);
            alert.showAndWait();
        }
    }
    @FXML
    public void nextPage(){
        try {
            if(Integer.parseInt(pageField.getText().replaceAll(",", ""))<jasperPrint.getPages().size()){
                int pageNumber = Integer.parseInt(pageField.getText().replaceAll(",", ""))+1;
                showImage(pageNumber);
                pageField.setText(rp.format(pageNumber));
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Application error - \n" +e);
            alert.showAndWait();
        }
    }
    @FXML
    private void firstPage(){
        try {
            if(jasperPrint.getPages().size() > 0){
                showImage(1);
                pageField.setText("1");
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Application error - \n" +e);
            alert.showAndWait();
        }
    }
    @FXML
    private void lastPage(){
        try {
            if(jasperPrint.getPages().size() > 0){
                showImage(jasperPrint.getPages().size());
                pageField.setText(rp.format(jasperPrint.getPages().size()));
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Application error - \n" +e);
            alert.showAndWait();
        }
    }
    private void showImage(int pageNumber)throws Exception{
        imageView.setFitHeight(imageHeight * zoomFactor);
        imageView.setFitWidth(imageWidth * zoomFactor);
        BufferedImage image = (BufferedImage) JasperPrintManager.printPageToImage(jasperPrint, pageNumber-1, 2);
        WritableImage fxImage = new WritableImage(jasperPrint.getPageWidth(), jasperPrint.getPageHeight());
        imageView.setImage(SwingFXUtils.toFXImage(image, fxImage));
    }
    @FXML
    private void print() {
        try{
            JasperPrintManager.printReport(jasperPrint, true);
        }catch(JRException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Application error - \n" +e);
            alert.showAndWait();
        }
    }
    
    
}
