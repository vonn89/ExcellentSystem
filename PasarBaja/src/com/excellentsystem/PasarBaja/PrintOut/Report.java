/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.PasarBaja.PrintOut;

import com.excellentsystem.PasarBaja.Model.Helper.Neraca;
import com.excellentsystem.PasarBaja.Model.Helper.UntungRugi;
import com.excellentsystem.PasarBaja.Model.Helper.UntungRugiSummary;
import com.excellentsystem.PasarBaja.Model.Hutang;
import com.excellentsystem.PasarBaja.Model.Keuangan;
import com.excellentsystem.PasarBaja.Model.LogBarang;
import com.excellentsystem.PasarBaja.Model.PemesananDetail;
import com.excellentsystem.PasarBaja.Model.PenjualanDetail;
import com.excellentsystem.PasarBaja.Model.PenjualanHead;
import com.excellentsystem.PasarBaja.Model.Piutang;
import com.excellentsystem.PasarBaja.Model.StokBarang;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

/**
 *
 * @author Xtreme
 */
public class Report {

    public String angka(double satuan){ 
          String[] huruf ={"","Satu","Dua","Tiga","Empat","Lima","Enam","Tujuh","Delapan","Sembilan","Sepuluh","Sebelas"}; 
          String hasil=""; 
          if(satuan<12) 
           hasil=hasil+huruf[(int)satuan]; 
          else if(satuan<20) 
           hasil=hasil+angka(satuan-10)+" Belas"; 
          else if(satuan<100) 
           hasil=hasil+angka(satuan/10)+" Puluh "+angka(satuan%10); 
          else if(satuan<200) 
           hasil=hasil+"Seratus "+angka(satuan-100); 
          else if(satuan<1000) 
           hasil=hasil+angka(satuan/100)+" Ratus "+angka(satuan%100); 
          else if(satuan<2000) 
           hasil=hasil+"Seribu "+angka(satuan-1000); 
          else if(satuan<1000000) 
           hasil=hasil+angka(satuan/1000)+" Ribu "+angka(satuan%1000); 
          else if(satuan<1000000000) 
           hasil=hasil+angka(satuan/1000000)+" Juta "+angka(satuan%1000000); 
          else if(satuan<1000000000000.0) 
           hasil=hasil+angka(satuan/1000000000)+" Milyar "+angka(satuan%1000000000); 
          else if(satuan>=100000000000.0) 
           hasil="Angka terlalu besar, harus kurang dari 1 Triliun!"; 
          return hasil; 
    }
    public void printProformaInvoice(List<PemesananDetail> pemesanan, double jumlahRp)throws Exception{
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("ProformaInvoice.jrxml"));
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(pemesanan);
        Map hash = new HashMap();
        hash.put("terbilang",angka(jumlahRp));
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,hash, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }
    public void printProformaInvoiceSoftcopy(List<PemesananDetail> pemesanan, double jumlahRp)throws Exception{
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("ProformaInvoiceSoftcopy.jrxml"));
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(pemesanan);
        Map hash = new HashMap();
        hash.put("terbilang",angka(jumlahRp));
        hash.put("top", ImageIO.read(getClass().getResource("atas.jpg")));
        hash.put("bottom", ImageIO.read(getClass().getResource("bawah.jpg")));
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,hash, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }
    public void printSPK(List<PemesananDetail> pemesanan)throws Exception{
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("SPK.jrxml"));
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(pemesanan);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,null, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }
    public void printInvoice(List<PenjualanDetail> penjualan,double jumlahRp)throws Exception{
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("Invoice.jrxml"));
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(penjualan);
        Map hash = new HashMap();
        hash.put("terbilang",angka(jumlahRp));
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,hash, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }
    public void printInvoiceSoftcopy(List<PenjualanDetail> penjualan, double jumlahRp)throws Exception{
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("InvoiceSoftcopy.jrxml"));
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(penjualan);
        Map hash = new HashMap();
        hash.put("terbilang",angka(jumlahRp));
        hash.put("top", ImageIO.read(getClass().getResource("atas.jpg")));
        hash.put("bottom", ImageIO.read(getClass().getResource("bawah.png")));
        hash.put("watermark", ImageIO.read(getClass().getResource("watermark.jpg")));
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,hash, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }
    public void printSuratJalan(List<PenjualanDetail> penjualan)throws Exception{
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("SuratJalan.jrxml"));
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(penjualan);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,null, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }
    public void printLaporanNeraca(List<Neraca> neraca, String tglMulai, String tglAkhir, 
            String totalAktiva, String totalPassiva)throws Exception{
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanNeraca.jrxml"));
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(neraca);
        Map hash = new HashMap();
        hash.put("namaPerusahaan", "Auri Steel Metalindo");
        hash.put("tglMulai",tglMulai);
        hash.put("tglAkhir",tglAkhir);
        hash.put("totalAktiva",totalAktiva);
        hash.put("totalPassiva",totalPassiva);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,hash, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }
    public void printLaporanUntungRugi(List<UntungRugi> keuangan, String tglMulai, String tglAkhir)throws Exception{
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanUntungRugi.jrxml"));
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(keuangan);
        Map hash = new HashMap();
        hash.put("namaPerusahaan", "Auri Steel Metalindo");
        hash.put("tglMulai",tglMulai);
        hash.put("tglAkhir",tglAkhir);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,hash, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }
    public void printLaporanUntungRugiSummary(List<UntungRugiSummary> keuangan, String tahunBefore, String tahun)throws Exception{
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanUntungRugiSummary.jrxml"));
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(keuangan);
        Map hash = new HashMap();
        hash.put("TahunBefore",tahunBefore);
        hash.put("Tahun",tahun);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,hash, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }
    public void printLaporanKeuangan(List<Keuangan> keuangan, String tglMulai, String tglAkhir,
            String jenisLaporan, String saldoAwal, String saldoAkhir)throws Exception{
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanKeuangan.jrxml"));
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(keuangan);
        Map hash = new HashMap();
        hash.put("namaPerusahaan","Auri Steel Metalindo");
        hash.put("tglMulai",tglMulai);
        hash.put("tglAkhir",tglAkhir);
        hash.put("jenisLaporan",jenisLaporan);
        hash.put("saldoAwal",saldoAwal);
        hash.put("saldoAkhir",saldoAkhir);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,hash, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }
    public void printLaporanHutang(List<Keuangan> hutang, String tglMulai, String tglAkhir,
            String saldoAwal, String saldoAkhir)throws Exception{
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanHutang.jrxml"));
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(hutang);
        Map hash = new HashMap();
        hash.put("namaPerusahaan","Auri Steel Metalindo");
        hash.put("tglMulai",tglMulai);
        hash.put("tglAkhir",tglAkhir);
        hash.put("saldoAwal",saldoAwal);
        hash.put("saldoAkhir",saldoAkhir);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,hash, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }
    public void printLaporanNeracaHutang(List<Hutang> hutang, String tglAkhir)throws Exception{
        if(hutang.get(0).getKategori().equals("Terima Pembayaran Down Payment")){
            Collections.sort(hutang, (o1, o2) -> {
                return ((Hutang) o1).getPemesananHead().getCustomer().getKodeCustomer().compareTo(
                        ((Hutang) o2).getPemesananHead().getCustomer().getKodeCustomer());
            });
        }
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanNeracaHutang.jrxml"));
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(hutang);
        Map hash = new HashMap();
        hash.put("namaPerusahaan","Auri Steel Metalindo");
        hash.put("tglAkhir",tglAkhir);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,hash, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }
    public void printLaporanPiutang(List<Keuangan> piutang, String tglMulai, String tglAkhir,
            String saldoAwal, String saldoAkhir)throws Exception{
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanPiutang.jrxml"));
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(piutang);
        Map hash = new HashMap();
        hash.put("namaPerusahaan","Auri Steel Metalindo");
        hash.put("tglMulai",tglMulai);
        hash.put("tglAkhir",tglAkhir);
        hash.put("saldoAwal",saldoAwal);
        hash.put("saldoAkhir",saldoAkhir);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,hash, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }
    public void printLaporanNeracaPiutang(List<Piutang> piutang, String tglAkhir)throws Exception{
        if(piutang.get(0).getKategori().equals("Piutang Penjualan")){
            Collections.sort(piutang, (o1, o2) -> {
                return ((Piutang) o1).getPenjualanHead().getCustomer().getKodeCustomer().compareTo(
                        ((Piutang) o2).getPenjualanHead().getCustomer().getKodeCustomer());
            });
        }
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanNeracaPiutang.jrxml"));
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(piutang);
        Map hash = new HashMap();
        hash.put("namaPerusahaan","Auri Steel Metalindo");
        hash.put("tglAkhir",tglAkhir);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,hash, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }
    public void printLaporanPenjualan(List<PenjualanHead> penjualan, String tglMulai, String tglAkhir, String groupBy
            )throws Exception{
        JasperDesign jasperDesign = null;
        if(groupBy.equals("Customer")){
            penjualan.sort(Comparator.comparing(PenjualanHead::getKodeCustomer));
            jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanPenjualanCustomer.jrxml"));
        }else if(groupBy.equals("Sales")){
            penjualan.sort(Comparator.comparing(PenjualanHead::getKodeSales));
            jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanPenjualanSales.jrxml"));
        }else{
            penjualan.sort(Comparator.comparing(PenjualanHead::getNoPenjualan));
            jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanPenjualan.jrxml"));
        }
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(penjualan);
        Map hash = new HashMap();
        hash.put("namaPerusahaan","Auri Steel Metalindo");
        hash.put("tglMulai",tglMulai);
        hash.put("tglAkhir",tglAkhir);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,hash, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }
    public void printLaporanBarangTerjual(List<PenjualanDetail> penjualan, String tglMulai, String tglAkhir, String groupBy
            )throws Exception{
        JasperDesign jasperDesign = null;
        if(groupBy.equals("Customer")){
            Collections.sort(penjualan, (o1, o2) -> {
                return ((PenjualanDetail) o1).getPenjualanHead().getKodeCustomer().compareTo(
                        ((PenjualanDetail) o2).getPenjualanHead().getKodeCustomer());
            });
            jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanBarangTerjualCustomer.jrxml"));
        }else if(groupBy.equals("Sales")){
            Collections.sort(penjualan, (o1, o2) -> {
                int sComp = ((PenjualanDetail) o1).getPenjualanHead().getKodeSales().compareTo(
                        ((PenjualanDetail) o2).getPenjualanHead().getKodeSales());
                if (sComp != 0) {
                    return sComp;
                }
                return ((PenjualanDetail) o1).getPenjualanHead().getKodeCustomer().compareTo(
                        ((PenjualanDetail) o2).getPenjualanHead().getKodeCustomer());
            });
            jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanBarangTerjualSales.jrxml"));
        }else if(groupBy.equals("Barang")){
            penjualan.sort(Comparator.comparing(PenjualanDetail::getKodeBarang));
            jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanBarangTerjualBarang.jrxml"));
        }else{
            penjualan.sort(Comparator.comparing(PenjualanDetail::getNoPenjualan));
            jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanBarangTerjual.jrxml"));
        }
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(penjualan);
        Map hash = new HashMap();
        hash.put("namaPerusahaan","Auri Steel Metalindo");
        hash.put("tglMulai",tglMulai);
        hash.put("tglAkhir",tglAkhir);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,hash, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }
    public void printLaporanBarang(List<StokBarang> stokBarang)throws Exception{
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanBarang.jrxml"));
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(stokBarang);
        Map hash = new HashMap();
        hash.put("namaPerusahaan","Auri Steel Metalindo");
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,hash, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }
    public void printLaporanKartuStokBarang(List<StokBarang> stokBarang)throws Exception{
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanKartuStokBarang.jrxml"));
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(stokBarang);
        Map hash = new HashMap();
        hash.put("namaPerusahaan","Auri Steel Metalindo");
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,hash, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }
    public void printLaporanLogBarang(List<LogBarang> logBarang, String tglMulai, String tglAkhir,
            String namaBarang)throws Exception{
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanLogBarang.jrxml"));
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(logBarang);
        Map hash = new HashMap();
        hash.put("namaPerusahaan","Auri Steel Metalindo");
        hash.put("tglMulai",tglMulai);
        hash.put("tglAkhir",tglAkhir);
        hash.put("namaBarang",namaBarang);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,hash, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }
}
