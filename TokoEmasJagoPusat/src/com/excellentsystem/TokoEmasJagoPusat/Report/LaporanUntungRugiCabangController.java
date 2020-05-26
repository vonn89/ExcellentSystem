/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.Report;

import com.excellentsystem.TokoEmasJagoPusat.DAO.CabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.KeuanganCabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.PenjualanAntarCabangDetailDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.PenjualanCiokCabangDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.TokoCabang.KeuanganDAO;
import com.excellentsystem.TokoEmasJagoPusat.DAO.TokoCabang.PenjualanDetailDAO;
import com.excellentsystem.TokoEmasJagoPusat.Function;
import com.excellentsystem.TokoEmasJagoPusat.KoneksiCabang;
import com.excellentsystem.TokoEmasJagoPusat.KoneksiPusat;
import static com.excellentsystem.TokoEmasJagoPusat.Main.rp;
import static com.excellentsystem.TokoEmasJagoPusat.Main.sistem;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglBarang;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglNormal;
import com.excellentsystem.TokoEmasJagoPusat.Model.Cabang;
import com.excellentsystem.TokoEmasJagoPusat.Model.Helper.UntungRugi;
import com.excellentsystem.TokoEmasJagoPusat.Model.KeuanganCabang;
import com.excellentsystem.TokoEmasJagoPusat.Model.PenjualanAntarCabangDetail;
import com.excellentsystem.TokoEmasJagoPusat.Model.PenjualanCiokCabang;
import com.excellentsystem.TokoEmasJagoPusat.Model.TokoCabang.Keuangan;
import com.excellentsystem.TokoEmasJagoPusat.Model.TokoCabang.PenjualanDetail;
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

public class LaporanUntungRugiCabangController {

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
                List<UntungRugi> ur = new ArrayList<>();
                List<PenjualanDetail> listPenjualanDetail = new ArrayList<>();
                List<PenjualanAntarCabangDetail> listPenjualanCabangDetail = new ArrayList<>();
                List<PenjualanCiokCabang> listPenjualanCiokCabang = new ArrayList<>();
                List<Keuangan> listKeuanganCabang = new ArrayList<>();
                List<KeuanganCabang> listKeuanganPusat = new ArrayList<>();
                Cabang c = CabangDAO.get(conPusat, cabangCombo.getSelectionModel().getSelectedItem());
                try(Connection conCabang = KoneksiCabang.getConnection(c)){
                    double penjualanUmum = KeuanganDAO.getTotal(conCabang, "Kasir", "Penjualan Umum", tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString());
                    double hppPenjualanUmum = KeuanganDAO.getTotal(conCabang, "Kasir", "HPP Penjualan Umum", tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString());
                    
                    double penjualanAntarCabang = KeuanganDAO.getTotal(conCabang, "Kasir", "Penjualan Antar Cabang", tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString());
                    double hppPenjualanAntarCabang = KeuanganDAO.getTotal(conCabang, "Kasir", "HPP Penjualan Antar Cabang", tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString());
                    
                    double penjualanCiok = KeuanganCabangDAO.getTotal(conPusat, tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), 
                            cabangCombo.getSelectionModel().getSelectedItem(), "Kasir", "Penjualan Ciok");
                    double hppPenjualanCiok = KeuanganCabangDAO.getTotal(conPusat, tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), 
                            cabangCombo.getSelectionModel().getSelectedItem(), "Kasir", "HPP Penjualan Ciok");
                    
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
                    
                    double pendapatanBebanPenyesuaian = KeuanganCabangDAO.getTotal(conPusat, tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), 
                            cabangCombo.getSelectionModel().getSelectedItem(), "Kasir", "Pendapatan/Beban Penyesuaian Barang");
                    double bebanPenyusutanResetStokBarang = KeuanganCabangDAO.getTotal(conPusat, tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), 
                            cabangCombo.getSelectionModel().getSelectedItem(), "Kasir", "Beban Penyusutan Reset Stok Barang");
                    double bebanPenyusutanSPBarang = KeuanganCabangDAO.getTotal(conPusat, tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), 
                            cabangCombo.getSelectionModel().getSelectedItem(), "Kasir", "Beban Penyusutan SP Barang");
                    double pendapatanBebanKursEmas = KeuanganCabangDAO.getTotal(conPusat, tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), 
                            cabangCombo.getSelectionModel().getSelectedItem(), "Kasir", "Pendapatan/Beban Kurs Emas");
                    
                    ur.add(new UntungRugi("Penjualan", "", ""));
                    ur.add(new UntungRugi(" Penjualan Umum", " "+rp.format(penjualanUmum), ""));
                    ur.add(new UntungRugi(" Penjualan Antar Cabang", " "+rp.format(penjualanAntarCabang), ""));
                    ur.add(new UntungRugi(" Penjualan Ciok", " "+rp.format(penjualanCiok), ""));
                    ur.add(new UntungRugi("Total Penjualan", "", rp.format(penjualanUmum+penjualanAntarCabang+penjualanCiok)));
                    ur.add(new UntungRugi("", "", ""));

                    ur.add(new UntungRugi("Harga Pokok Penjualan", "", ""));
                    ur.add(new UntungRugi(" Penjualan Umum", " "+rp.format(hppPenjualanUmum), ""));
                    ur.add(new UntungRugi(" Penjualan Antar Cabang", " "+rp.format(hppPenjualanAntarCabang), ""));
                    ur.add(new UntungRugi(" Penjualan Ciok", " "+rp.format(hppPenjualanCiok), ""));
                    ur.add(new UntungRugi("Total Harga Pokok Penjualan", "", rp.format(hppPenjualanUmum+hppPenjualanAntarCabang+hppPenjualanCiok)));
                    ur.add(new UntungRugi("", "", ""));

                    ur.add(new UntungRugi("Untung-Rugi Kotor", "", rp.format(
                        (penjualanUmum+penjualanAntarCabang+penjualanCiok)+
                        (hppPenjualanUmum+hppPenjualanAntarCabang+hppPenjualanCiok)
                    )));
                    ur.add(new UntungRugi("", "", ""));
                    
                    ur.add(new UntungRugi("Pendapatan-Beban", "", ""));
                    ur.add(new UntungRugi(" Pendapatan Kartu Member", " "+rp.format(pendapatanKartuMember), ""));
                    ur.add(new UntungRugi(" Pendapatan Servis", " "+rp.format(pendapatanServis), ""));
                    ur.add(new UntungRugi(" Pendapatan Lain-lain", " "+rp.format(pendapatanLainLain), ""));
                    ur.add(new UntungRugi(" Beban Poin", " "+rp.format(bebanPoin), ""));
                    ur.add(new UntungRugi(" Beban Gaji", " "+rp.format(bebanGaji), ""));
                    ur.add(new UntungRugi(" Beban Administrasi", " "+rp.format(bebanAdministrasi), ""));
                    ur.add(new UntungRugi(" Beban Operasional", " "+rp.format(bebanOperasional), ""));
                    ur.add(new UntungRugi(" Beban Pajak", " "+rp.format(bebanPajak), ""));
                    ur.add(new UntungRugi(" Beban Penyusutan", " "+rp.format(bebanPenyusutan), ""));
                    ur.add(new UntungRugi(" Beban Lain-lain", " "+rp.format(bebanLainLain), ""));
                    ur.add(new UntungRugi(" Pendapatan/Beban Penyesuaian Barang", " "+rp.format(pendapatanBebanPenyesuaian), ""));
                    ur.add(new UntungRugi(" Beban Penyusutan Reset Stok Barang", " "+rp.format(bebanPenyusutanResetStokBarang), ""));
                    ur.add(new UntungRugi(" Beban Penyusutan SP Barang", " "+rp.format(bebanPenyusutanSPBarang), ""));
                    ur.add(new UntungRugi(" Pendapatan/Beban Kurs Emas", " "+rp.format(pendapatanBebanKursEmas), ""));
                    ur.add(new UntungRugi("Total Pendapatan-Beban", "", rp.format(
                            pendapatanKartuMember+pendapatanServis+pendapatanLainLain+
                            bebanPoin+bebanGaji+bebanAdministrasi+bebanOperasional+bebanPajak+bebanPenyusutan+bebanLainLain+
                            pendapatanBebanPenyesuaian+bebanPenyusutanResetStokBarang+bebanPenyusutanSPBarang+pendapatanBebanKursEmas)));
                    ur.add(new UntungRugi("", "", ""));

                    ur.add(new UntungRugi("Untung-Rugi Bersih", "", rp.format(
                        (penjualanUmum+penjualanAntarCabang+penjualanCiok)+
                        (hppPenjualanUmum+hppPenjualanAntarCabang+hppPenjualanCiok)+
                        (pendapatanKartuMember+pendapatanServis+pendapatanLainLain+
                        bebanPoin+bebanGaji+bebanAdministrasi+bebanOperasional+bebanPajak+bebanPenyusutan+bebanLainLain+
                        pendapatanBebanPenyesuaian+bebanPenyusutanResetStokBarang+bebanPenyusutanSPBarang+pendapatanBebanKursEmas)
                    )));
                    if(jenisLaporanCombo.getSelectionModel().getSelectedItem().equals("Detail")){
                        listPenjualanDetail = PenjualanDetailDAO.getAllByDateAndStatus(
                            conCabang, tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), "true");
                        Collections.sort(listPenjualanDetail, (o1, o2) -> {
                            int sComp = ((PenjualanDetail) o1).getKodeKategori().compareTo(((PenjualanDetail) o2).getKodeKategori());
                            if (sComp != 0) 
                                return sComp;
                            return ((PenjualanDetail) o1).getKodeJenis().compareTo(((PenjualanDetail) o2).getKodeJenis());
                        });
                        listPenjualanCabangDetail = PenjualanAntarCabangDetailDAO.getAllByDateAndCabangAsalAndCabangTujuanAndStatusTerimaAndStatusBatal(
                                conPusat, tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), cabangCombo.getSelectionModel().getSelectedItem(), 
                                "%", "%", "false");
                        Collections.sort(listPenjualanCabangDetail, (o1, o2) -> {
                            int sComp = ((PenjualanAntarCabangDetail) o1).getKodeKategori().compareTo(((PenjualanAntarCabangDetail) o2).getKodeKategori());
                            if (sComp != 0) 
                                return sComp;
                            return ((PenjualanAntarCabangDetail) o1).getKodeJenis().compareTo(((PenjualanAntarCabangDetail) o2).getKodeJenis());
                        });
                        
                        listPenjualanCiokCabang = PenjualanCiokCabangDAO.getAllByDateAndCabangAndStatus(
                                conPusat, tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), cabangCombo.getSelectionModel().getSelectedItem(), 
                                "true");
                        
                        listKeuanganCabang.addAll(KeuanganDAO.getAllByDateAndKasirAndTipeKeuanganAndKategoriAndSales(conCabang, 
                                tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), 
                                "Kasir", "Pendapatan Kartu Member", "%", "%"));
                        listKeuanganCabang.addAll(KeuanganDAO.getAllByDateAndKasirAndTipeKeuanganAndKategoriAndSales(conCabang, 
                                tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), 
                                "Kasir", "Pendapatan Servis", "%", "%"));
                        listKeuanganCabang.addAll(KeuanganDAO.getAllByDateAndKasirAndTipeKeuanganAndKategoriAndSales(conCabang, 
                                tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), 
                                "Kasir", "Beban Poin", "%", "%"));
                        listKeuanganCabang.addAll(KeuanganDAO.getAllByDateAndKasirAndTipeKeuanganAndKategoriAndSales(conCabang, 
                                tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), 
                                "Kasir", "Pendapatan Lain-lain", "%", "%"));
                        listKeuanganCabang.addAll(KeuanganDAO.getAllByDateAndKasirAndTipeKeuanganAndKategoriAndSales(conCabang, 
                                tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), 
                                "Kasir", "Beban Gaji", "%", "%"));
                        listKeuanganCabang.addAll(KeuanganDAO.getAllByDateAndKasirAndTipeKeuanganAndKategoriAndSales(conCabang, 
                                tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), 
                                "Kasir", "Beban Administrasi", "%", "%"));
                        listKeuanganCabang.addAll(KeuanganDAO.getAllByDateAndKasirAndTipeKeuanganAndKategoriAndSales(conCabang, 
                                tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), 
                                "Kasir", "Beban Operasional", "%", "%"));
                        listKeuanganCabang.addAll(KeuanganDAO.getAllByDateAndKasirAndTipeKeuanganAndKategoriAndSales(conCabang, 
                                tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), 
                                "Kasir", "Beban Pajak", "%", "%"));
                        listKeuanganCabang.addAll(KeuanganDAO.getAllByDateAndKasirAndTipeKeuanganAndKategoriAndSales(conCabang, 
                                tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), 
                                "Kasir", "Beban Penyusutan", "%", "%"));
                        listKeuanganCabang.addAll(KeuanganDAO.getAllByDateAndKasirAndTipeKeuanganAndKategoriAndSales(conCabang, 
                                tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), 
                                "Kasir", "Beban Lain-lain", "%", "%"));
                        Collections.sort(listKeuanganCabang, (o1, o2) -> {
                            int sComp = ((Keuangan) o1).getTipeKeuangan().compareTo(((Keuangan) o2).getTipeKeuangan());
                            if (sComp != 0) 
                                return sComp;
                            return ((Keuangan) o1).getKategori().compareTo(((Keuangan) o2).getKategori());
                        });
                        
                        listKeuanganPusat.addAll(KeuanganCabangDAO.getAllByDateAndCabangAndKasirAndTipeKeuangan(conPusat, 
                                tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), 
                                cabangCombo.getSelectionModel().getSelectedItem(), "Kasir", "Pendapatan/Beban Penyesuaian Barang"));
                        listKeuanganPusat.addAll(KeuanganCabangDAO.getAllByDateAndCabangAndKasirAndTipeKeuangan(conPusat, 
                                tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), 
                                cabangCombo.getSelectionModel().getSelectedItem(), "Kasir", "Beban Penyusutan Reset Stok Barang"));
                        listKeuanganPusat.addAll(KeuanganCabangDAO.getAllByDateAndCabangAndKasirAndTipeKeuangan(conPusat, 
                                tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), 
                                cabangCombo.getSelectionModel().getSelectedItem(), "Kasir", "Beban Penyusutan SP Barang"));
                        listKeuanganPusat.addAll(KeuanganCabangDAO.getAllByDateAndCabangAndKasirAndTipeKeuangan(conPusat, 
                                tglAwalPicker.getValue().toString(), tglAkhirPicker.getValue().toString(), 
                                cabangCombo.getSelectionModel().getSelectedItem(), "Kasir", "Pendapatan/Beban Kurs Emas"));
                    }
                }
                
                JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanUntungRugiCabang.jrxml"));
                JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(ur);
                Map hash = new HashMap();
                hash.put("tanggal", tglNormal.format(tglBarang.parse(tglAwalPicker.getValue().toString()))+" - "+
                            tglNormal.format(tglBarang.parse(tglAkhirPicker.getValue().toString())));
                hash.put("cabang", cabangCombo.getSelectionModel().getSelectedItem());
                hash.put("jenisLaporan", jenisLaporanCombo.getSelectionModel().getSelectedItem());
                
                JasperDesign subreportPenjualanUmum = JRXmlLoader.load(getClass().getResourceAsStream("DetailPenjualanUmum.jrxml"));
                JasperReport jasperSubReportPenjualanUmum = JasperCompileManager.compileReport(subreportPenjualanUmum);
                hash.put("subReportDetailPenjualanUmum", jasperSubReportPenjualanUmum);
                hash.put("listPenjualanUmum", listPenjualanDetail);
                
                JasperDesign subreportPenjualanCabang = JRXmlLoader.load(getClass().getResourceAsStream("DetailPenjualanCabang.jrxml"));
                JasperReport jasperSubReportPenjualanCabang = JasperCompileManager.compileReport(subreportPenjualanCabang);
                hash.put("subReportDetailPenjualanCabang", jasperSubReportPenjualanCabang);
                hash.put("listPenjualanCabang", listPenjualanCabangDetail);
                
                JasperDesign subreportPenjualanCiok = JRXmlLoader.load(getClass().getResourceAsStream("DetailPenjualanCiok.jrxml"));
                JasperReport jasperSubReportPenjualanCiok = JasperCompileManager.compileReport(subreportPenjualanCiok);
                hash.put("subReportDetailPenjualanCiok", jasperSubReportPenjualanCiok);
                hash.put("listPenjualanCiok", listPenjualanCiokCabang);
                
                JasperDesign subReportPendapatanBebanCabang = JRXmlLoader.load(getClass().getResourceAsStream("DetailPendapatanBeban.jrxml"));
                JasperReport jasperSubReportPendapatanBebanCabang = JasperCompileManager.compileReport(subReportPendapatanBebanCabang);
                hash.put("subReportDetailKeuanganCabang", jasperSubReportPendapatanBebanCabang);
                hash.put("listKeuanganCabang", listKeuanganCabang);
                
                JasperDesign subReportPendapatanBebanPusat = JRXmlLoader.load(getClass().getResourceAsStream("DetailPendapatanBebanPusat.jrxml"));
                JasperReport jasperSubReportPendapatanBebanPusat = JasperCompileManager.compileReport(subReportPendapatanBebanPusat);
                hash.put("subReportDetailKeuanganPusat", jasperSubReportPendapatanBebanPusat);
                hash.put("listKeuanganPusat", listKeuanganPusat);
                
                double totalPendapatanBeban = 0;
                for(Keuangan k : listKeuanganCabang){
                    totalPendapatanBeban = totalPendapatanBeban + k.getJumlahRp();
                }
                for(KeuanganCabang k : listKeuanganPusat){
                    totalPendapatanBeban = totalPendapatanBeban + k.getJumlahRp();
                }
                
                hash.put("totalPendapatanBeban", totalPendapatanBeban);
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
