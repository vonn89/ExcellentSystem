/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.AuriSteel.PrintOut;

import static com.excellentsystem.AuriSteel.Main.df;
import com.excellentsystem.AuriSteel.Model.Bahan;
import com.excellentsystem.AuriSteel.Model.Helper.Neraca;
import com.excellentsystem.AuriSteel.Model.Helper.UntungRugi;
import com.excellentsystem.AuriSteel.Model.Helper.UntungRugiSummary;
import com.excellentsystem.AuriSteel.Model.Hutang;
import com.excellentsystem.AuriSteel.Model.Keuangan;
import com.excellentsystem.AuriSteel.Model.LogBahan;
import com.excellentsystem.AuriSteel.Model.LogBarang;
import com.excellentsystem.AuriSteel.Model.PembelianBahanDetail;
import com.excellentsystem.AuriSteel.Model.PembelianBahanHead;
import com.excellentsystem.AuriSteel.Model.PemesananBahanDetail;
import com.excellentsystem.AuriSteel.Model.PemesananBarangDetail;
import com.excellentsystem.AuriSteel.Model.PenjualanBahanDetail;
import com.excellentsystem.AuriSteel.Model.PenjualanBahanHead;
import com.excellentsystem.AuriSteel.Model.PenjualanBarangDetail;
import com.excellentsystem.AuriSteel.Model.PenjualanBarangHead;
import com.excellentsystem.AuriSteel.Model.PindahBahanDetail;
import com.excellentsystem.AuriSteel.Model.PindahBarangDetail;
import com.excellentsystem.AuriSteel.Model.Piutang;
import com.excellentsystem.AuriSteel.Model.ProduksiDetailBarang;
import com.excellentsystem.AuriSteel.Model.ProduksiHead;
import com.excellentsystem.AuriSteel.Model.StokBahan;
import com.excellentsystem.AuriSteel.Model.StokBarang;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
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

    public String angka(double satuan) {
        String[] huruf = {"", "Satu", "Dua", "Tiga", "Empat", "Lima", "Enam", "Tujuh", "Delapan", "Sembilan", "Sepuluh", "Sebelas"};
        String hasil = "";
        if (satuan < 12) {
            hasil = hasil + huruf[(int) satuan];
        } else if (satuan < 20) {
            hasil = hasil + angka(satuan - 10) + " Belas";
        } else if (satuan < 100) {
            hasil = hasil + angka(satuan / 10) + " Puluh " + angka(satuan % 10);
        } else if (satuan < 200) {
            hasil = hasil + "Seratus " + angka(satuan - 100);
        } else if (satuan < 1000) {
            hasil = hasil + angka(satuan / 100) + " Ratus " + angka(satuan % 100);
        } else if (satuan < 2000) {
            hasil = hasil + "Seribu " + angka(satuan - 1000);
        } else if (satuan < 1000000) {
            hasil = hasil + angka(satuan / 1000) + " Ribu " + angka(satuan % 1000);
        } else if (satuan < 1000000000) {
            hasil = hasil + angka(satuan / 1000000) + " Juta " + angka(satuan % 1000000);
        } else if (satuan < 1000000000000.0) {
            hasil = hasil + angka(satuan / 1000000000) + " Milyar " + angka(satuan % 1000000000);
        } else if (satuan >= 100000000000.0) {
            hasil = "Angka terlalu besar, harus kurang dari 1 Triliun!";
        }
        return hasil;
    }

    public void cetakBarcode(Bahan b) throws Exception {
        List<Bahan> listBahan = new ArrayList<>();
        listBahan.add(b);
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("BarcodeBahan.jrxml"));
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listBahan);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }


    public static void printBarcode(List<Bahan> listBahan) throws Exception {
        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
        int selectedService = 0;
        for (int i = 0; i < services.length; i++) {
            if (services[i].getName().toUpperCase().contains("ZD230")) {
                selectedService = i;
            }
        }
        for (Bahan b : listBahan) {
            String commands =  "^XA"
                    + "^JMB^FS"
                    + "^FO25,25^GB375,500,2,B,0^FS"
                    + "^FO25,25^GB375,550,2,B,0^FS"
                    + "^FO25,524^GB187,50,1,B,0^FS"
                    + "^FO212,524^GB187,50,1,B,0^FS"
                    + "^FO35,50^FB375,2,0,C^A0N,36,30^FD" + b.getKodeBahan() + "^FS"
                    + "^FO120,140^BQA,2,8^FDMM,A" + b.getKodeBahan() + "^FS"
                    + "^FO50,370^FB340,1,0,C^A0N,36,30^FD" + b.getKodeKategori() + "^FS"
                    + "^FO50,420^FB340,1,0,C^A0N,22,20^FDBerat Kotor : " + df.format(b.getBeratKotor()) + " kg^FS"
                    + "^FO50,450^FB340,1,0,C^A0N,22,20^FDBerat Bersih : " + df.format(b.getBeratBersih()) + " kg ^FS"
                    + "^FO50,480^FB340,1,0,C^A0N,22,20^FDPanjang : " + df.format(b.getPanjang()) + " m^FS"
                    + "^XZ";
            PrintService pservice = services[selectedService];
            DocPrintJob job = pservice.createPrintJob();
            DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
            Doc doc = new SimpleDoc(commands.getBytes(), flavor, null);
            job.print(doc, null);
        }
    }

    public void printProformaInvoice(List<PemesananBarangDetail> pemesanan, double jumlahRp) throws Exception {
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("ProformaInvoice.jrxml"));
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(pemesanan);
        Map hash = new HashMap();
        hash.put("terbilang", angka(jumlahRp));
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, hash, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }

    public void printProformaInvoiceSoftcopy(List<PemesananBarangDetail> pemesanan, double jumlahRp) throws Exception {
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("ProformaInvoiceSoftcopy.jrxml"));
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(pemesanan);
        Map hash = new HashMap();
        hash.put("terbilang", angka(jumlahRp));
        hash.put("top", ImageIO.read(getClass().getResource("atas.jpg")));
        hash.put("bottom", ImageIO.read(getClass().getResource("bawah.jpg")));
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, hash, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }

    public void printProformaInvoiceCoil(List<PemesananBahanDetail> pemesanan, double jumlahRp) throws Exception {
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("ProformaInvoiceCoil.jrxml"));
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(pemesanan);
        Map hash = new HashMap();
        hash.put("terbilang", angka(jumlahRp));
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, hash, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }

    public void printSPK(List<PemesananBarangDetail> pemesanan) throws Exception {
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("SPK.jrxml"));
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(pemesanan);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }

    public void printInvoice(List<PenjualanBarangDetail> penjualan, double jumlahRp) throws Exception {
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("Invoice.jrxml"));
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(penjualan);
        Map hash = new HashMap();
        hash.put("terbilang", angka(jumlahRp));
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, hash, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }

    public void printInvoiceSoftcopy(List<PenjualanBarangDetail> penjualan, double jumlahRp) throws Exception {
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("InvoiceSoftcopy.jrxml"));
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(penjualan);
        Map hash = new HashMap();
        hash.put("terbilang", angka(jumlahRp));
        hash.put("top", ImageIO.read(getClass().getResource("atas.jpg")));
        hash.put("bottom", ImageIO.read(getClass().getResource("bawah.png")));
        hash.put("watermark", ImageIO.read(getClass().getResource("watermark.jpg")));
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, hash, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }

    public void printInvoiceCoil(List<PenjualanBahanDetail> penjualan, double jumlahRp) throws Exception {
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("InvoiceCoil.jrxml"));
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(penjualan);
        Map hash = new HashMap();
        hash.put("terbilang", angka(jumlahRp));
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, hash, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }

    public void printSuratJalan(List<PenjualanBarangDetail> penjualan) throws Exception {
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("SuratJalan.jrxml"));
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(penjualan);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }

    public void printSuratJalanCoil(List<PenjualanBahanDetail> penjualan) throws Exception {
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("SuratJalanCoil.jrxml"));
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(penjualan);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }

    public void printSuratJalanPindahBarang(List<PindahBarangDetail> listPindahBarangDetail) throws Exception {
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("SuratPindahBarang.jrxml"));
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listPindahBarangDetail);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }

    public void printSuratJalanPindahBahan(List<PindahBahanDetail> listPindahBahanDetail) throws Exception {
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("SuratPindahBahan.jrxml"));
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listPindahBahanDetail);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }

    public void printLaporanNeraca(List<Neraca> neraca, String tglMulai, String tglAkhir,
            String totalAktiva, String totalPassiva) throws Exception {
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanNeraca.jrxml"));
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(neraca);
        Map hash = new HashMap();
        hash.put("namaPerusahaan", "Auri Steel Metalindo");
        hash.put("tglMulai", tglMulai);
        hash.put("tglAkhir", tglAkhir);
        hash.put("totalAktiva", totalAktiva);
        hash.put("totalPassiva", totalPassiva);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, hash, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }

    public void printLaporanUntungRugi(List<UntungRugi> keuangan, String tglMulai, String tglAkhir) throws Exception {
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanUntungRugi.jrxml"));
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(keuangan);
        Map hash = new HashMap();
        hash.put("namaPerusahaan", "Auri Steel Metalindo");
        hash.put("tglMulai", tglMulai);
        hash.put("tglAkhir", tglAkhir);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, hash, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }

    public void printLaporanUntungRugiSummary(List<UntungRugiSummary> keuangan, String tahunBefore, String tahun) throws Exception {
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanUntungRugiSummary.jrxml"));
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(keuangan);
        Map hash = new HashMap();
        hash.put("TahunBefore", tahunBefore);
        hash.put("Tahun", tahun);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, hash, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }

    public void printLaporanKeuangan(List<Keuangan> keuangan, String tglMulai, String tglAkhir,
            String jenisLaporan, String saldoAwal, String saldoAkhir) throws Exception {
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanKeuangan.jrxml"));
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(keuangan);
        Map hash = new HashMap();
        hash.put("namaPerusahaan", "Auri Steel Metalindo");
        hash.put("tglMulai", tglMulai);
        hash.put("tglAkhir", tglAkhir);
        hash.put("jenisLaporan", jenisLaporan);
        hash.put("saldoAwal", saldoAwal);
        hash.put("saldoAkhir", saldoAkhir);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, hash, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }

    public void printLaporanHutang(List<Keuangan> hutang, String tglMulai, String tglAkhir,
            String saldoAwal, String saldoAkhir) throws Exception {
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanHutang.jrxml"));
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(hutang);
        Map hash = new HashMap();
        hash.put("namaPerusahaan", "Auri Steel Metalindo");
        hash.put("tglMulai", tglMulai);
        hash.put("tglAkhir", tglAkhir);
        hash.put("saldoAwal", saldoAwal);
        hash.put("saldoAkhir", saldoAkhir);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, hash, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }

    public void printLaporanNeracaHutang(List<Hutang> hutang, String tglAkhir) throws Exception {
        if (hutang.get(0).getKategori().equals("Hutang Pembelian")) {
            Collections.sort(hutang, (o1, o2) -> {
                return ((Hutang) o1).getPembelianBahanHead().getSupplier().getKodeSupplier().compareTo(
                        ((Hutang) o2).getPembelianBahanHead().getSupplier().getKodeSupplier());
            });
        } else if (hutang.get(0).getKategori().equals("Terima Pembayaran Down Payment")) {
            Collections.sort(hutang, (o1, o2) -> {
                return ((Hutang) o1).getPemesananHead().getCustomer().getKodeCustomer().compareTo(
                        ((Hutang) o2).getPemesananHead().getCustomer().getKodeCustomer());
            });
        }
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanNeracaHutang.jrxml"));
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(hutang);
        Map hash = new HashMap();
        hash.put("namaPerusahaan", "Auri Steel Metalindo");
        hash.put("tglAkhir", tglAkhir);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, hash, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }

    public void printLaporanPiutang(List<Keuangan> piutang, String tglMulai, String tglAkhir,
            String saldoAwal, String saldoAkhir) throws Exception {
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanPiutang.jrxml"));
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(piutang);
        Map hash = new HashMap();
        hash.put("namaPerusahaan", "Auri Steel Metalindo");
        hash.put("tglMulai", tglMulai);
        hash.put("tglAkhir", tglAkhir);
        hash.put("saldoAwal", saldoAwal);
        hash.put("saldoAkhir", saldoAkhir);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, hash, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }

    public void printLaporanNeracaPiutang(List<Piutang> piutang, String tglAkhir) throws Exception {
        if (piutang.get(0).getKategori().equals("Piutang Penjualan")) {
            Collections.sort(piutang, (o1, o2) -> {
                return ((Piutang) o1).getPenjualanHead().getCustomer().getKodeCustomer().compareTo(
                        ((Piutang) o2).getPenjualanHead().getCustomer().getKodeCustomer());
            });
        }
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanNeracaPiutang.jrxml"));
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(piutang);
        Map hash = new HashMap();
        hash.put("namaPerusahaan", "Auri Steel Metalindo");
        hash.put("tglAkhir", tglAkhir);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, hash, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }

    public void printLaporanPembelian(List<PembelianBahanHead> pembelian, String tglMulai, String tglAkhir, String groupBy
    ) throws Exception {
        JasperDesign jasperDesign = null;
        if (groupBy.equals("Supplier")) {
            pembelian.sort(Comparator.comparing(PembelianBahanHead::getKodeSupplier));
            jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanPembelianSupplier.jrxml"));
        } else {
            pembelian.sort(Comparator.comparing(PembelianBahanHead::getNoPembelian));
            jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanPembelian.jrxml"));
        }
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(pembelian);
        Map hash = new HashMap();
        hash.put("namaPerusahaan", "Auri Steel Metalindo");
        hash.put("tglMulai", tglMulai);
        hash.put("tglAkhir", tglAkhir);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, hash, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }

    public void printLaporanBarangDibeli(List<PembelianBahanDetail> pembelian, String tglMulai, String tglAkhir, String groupBy
    ) throws Exception {
        JasperDesign jasperDesign = null;
        if (groupBy.equals("Supplier")) {
            Collections.sort(pembelian, (o1, o2) -> {
                return ((PembelianBahanDetail) o1).getPembelianCoilHead().getKodeSupplier().compareTo(((PembelianBahanDetail) o2).getPembelianCoilHead().getKodeSupplier());
            });
            jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanBarangDibeliSupplier.jrxml"));
        } else if (groupBy.equals("Barang")) {
            pembelian.sort(Comparator.comparing(PembelianBahanDetail::getNamaBahan));
            jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanBarangDibeliBarang.jrxml"));
        } else {
            pembelian.sort(Comparator.comparing(PembelianBahanDetail::getNoPembelian));
            jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanBarangDibeli.jrxml"));
        }
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(pembelian);
        Map hash = new HashMap();
        hash.put("namaPerusahaan", "Auri Steel Metalindo");
        hash.put("tglMulai", tglMulai);
        hash.put("tglAkhir", tglAkhir);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, hash, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }

    public void printLaporanPenjualan(List<PenjualanBarangHead> penjualan, String tglMulai, String tglAkhir, String groupBy
    ) throws Exception {
        JasperDesign jasperDesign = null;
        if (groupBy.equals("Customer")) {
            penjualan.sort(Comparator.comparing(PenjualanBarangHead::getKodeCustomer));
            jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanPenjualanCustomer.jrxml"));
        } else if (groupBy.equals("Sales")) {
            penjualan.sort(Comparator.comparing(PenjualanBarangHead::getKodeSales));
            jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanPenjualanSales.jrxml"));
        } else {
            penjualan.sort(Comparator.comparing(PenjualanBarangHead::getNoPenjualan));
            jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanPenjualan.jrxml"));
        }
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(penjualan);
        Map hash = new HashMap();
        hash.put("namaPerusahaan", "Auri Steel Metalindo");
        hash.put("tglMulai", tglMulai);
        hash.put("tglAkhir", tglAkhir);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, hash, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }

    public void printLaporanBarangTerjual(List<PenjualanBarangDetail> penjualan, String tglMulai, String tglAkhir, String groupBy
    ) throws Exception {
        JasperDesign jasperDesign = null;
        if (groupBy.equals("Customer")) {
            Collections.sort(penjualan, (o1, o2) -> {
                return ((PenjualanBarangDetail) o1).getPenjualanBarangHead().getKodeCustomer().compareTo(((PenjualanBarangDetail) o2).getPenjualanBarangHead().getKodeCustomer());
            });
            jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanBarangTerjualCustomer.jrxml"));
        } else if (groupBy.equals("Sales")) {
            Collections.sort(penjualan, (o1, o2) -> {
                int sComp = ((PenjualanBarangDetail) o1).getPenjualanBarangHead().getKodeSales().compareTo(((PenjualanBarangDetail) o2).getPenjualanBarangHead().getKodeSales());
                if (sComp != 0) {
                    return sComp;
                }
                return ((PenjualanBarangDetail) o1).getPenjualanBarangHead().getKodeCustomer().compareTo(((PenjualanBarangDetail) o2).getPenjualanBarangHead().getKodeCustomer());
            });
            jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanBarangTerjualSales.jrxml"));
        } else if (groupBy.equals("Barang")) {
            penjualan.sort(Comparator.comparing(PenjualanBarangDetail::getKodeBarang));
            jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanBarangTerjualBarang.jrxml"));
        } else {
            penjualan.sort(Comparator.comparing(PenjualanBarangDetail::getNoPenjualan));
            jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanBarangTerjual.jrxml"));
        }
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(penjualan);
        Map hash = new HashMap();
        hash.put("namaPerusahaan", "Auri Steel Metalindo");
        hash.put("tglMulai", tglMulai);
        hash.put("tglAkhir", tglAkhir);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, hash, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }

    public void printLaporanPenjualanCoil(List<PenjualanBahanHead> penjualan, String tglMulai, String tglAkhir,
            String groupBy) throws Exception {
        JasperDesign jasperDesign = null;
        if (groupBy.equals("Customer")) {
            penjualan.sort(Comparator.comparing(PenjualanBahanHead::getKodeCustomer));
            jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanPenjualanCoilCustomer.jrxml"));
        } else if (groupBy.equals("Sales")) {
            penjualan.sort(Comparator.comparing(PenjualanBahanHead::getKodeSales));
            jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanPenjualanCoilSales.jrxml"));
        } else {
            penjualan.sort(Comparator.comparing(PenjualanBahanHead::getNoPenjualan));
            jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanPenjualanCoil.jrxml"));
        }
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(penjualan);
        Map hash = new HashMap();
        hash.put("namaPerusahaan", "Auri Steel Metalindo");
        hash.put("tglMulai", tglMulai);
        hash.put("tglAkhir", tglAkhir);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, hash, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }

    public void printLaporanCoilTerjual(List<PenjualanBahanDetail> penjualan, String tglMulai, String tglAkhir,
            String groupBy) throws Exception {
        JasperDesign jasperDesign = null;
        if (groupBy.equals("Customer")) {
            Collections.sort(penjualan, (o1, o2) -> {
                return ((PenjualanBahanDetail) o1).getPenjualanBahanHead().getKodeCustomer().compareTo(((PenjualanBahanDetail) o2).getPenjualanBahanHead().getKodeCustomer());
            });
            jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanCoilTerjualCustomer.jrxml"));
        } else if (groupBy.equals("Sales")) {
            Collections.sort(penjualan, (o1, o2) -> {
                int sComp = ((PenjualanBahanDetail) o1).getPenjualanBahanHead().getKodeSales().compareTo(((PenjualanBahanDetail) o2).getPenjualanBahanHead().getKodeSales());
                if (sComp != 0) {
                    return sComp;
                }
                return ((PenjualanBahanDetail) o1).getPenjualanBahanHead().getKodeCustomer().compareTo(((PenjualanBahanDetail) o2).getPenjualanBahanHead().getKodeCustomer());
            });
            jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanCoilTerjualSales.jrxml"));
        } else if (groupBy.equals("Barang")) {
            penjualan.sort(Comparator.comparing(PenjualanBahanDetail::getNamaBahan));
            jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanCoilTerjualBarang.jrxml"));
        } else {
            penjualan.sort(Comparator.comparing(PenjualanBahanDetail::getNoPenjualan));
            jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanCoilTerjual.jrxml"));
        }
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(penjualan);
        Map hash = new HashMap();
        hash.put("namaPerusahaan", "Auri Steel Metalindo");
        hash.put("tglMulai", tglMulai);
        hash.put("tglAkhir", tglAkhir);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, hash, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }

    public void printLaporanBarang(List<StokBarang> stokBarang) throws Exception {
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanBarang.jrxml"));
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(stokBarang);
        Map hash = new HashMap();
        hash.put("namaPerusahaan", "Auri Steel Metalindo");
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, hash, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }

    public void printLaporanBahan(List<StokBahan> stokBahan) throws Exception {
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanBahan.jrxml"));
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(stokBahan);
        Map hash = new HashMap();
        hash.put("namaPerusahaan", "Auri Steel Metalindo");
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, hash, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }

    public void printLaporanKartuStokBarang(List<StokBarang> stokBarang) throws Exception {
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanKartuStokBarang.jrxml"));
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(stokBarang);
        Map hash = new HashMap();
        hash.put("namaPerusahaan", "Auri Steel Metalindo");
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, hash, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }

    public void printLaporanKartuStokBahan(List<StokBahan> stokBahan) throws Exception {
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanKartuStokBahan.jrxml"));
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(stokBahan);
        Map hash = new HashMap();
        hash.put("namaPerusahaan", "Auri Steel Metalindo");
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, hash, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }

    public void printLaporanLogBarang(List<LogBarang> logBarang, String tglMulai, String tglAkhir,
            String namaBarang) throws Exception {
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanLogBarang.jrxml"));
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(logBarang);
        Map hash = new HashMap();
        hash.put("namaPerusahaan", "Auri Steel Metalindo");
        hash.put("tglMulai", tglMulai);
        hash.put("tglAkhir", tglAkhir);
        hash.put("namaBarang", namaBarang);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, hash, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }

    public void printLaporanLogBahan(List<LogBahan> logBahan, String tglMulai, String tglAkhir,
            String namaBarang) throws Exception {
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanLogBahan.jrxml"));
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(logBahan);
        Map hash = new HashMap();
        hash.put("namaPerusahaan", "Auri Steel Metalindo");
        hash.put("tglMulai", tglMulai);
        hash.put("tglAkhir", tglAkhir);
        hash.put("namaBarang", namaBarang);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, hash, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }

    public void printLaporanProduksi(List<ProduksiHead> produksi, String tglMulai, String tglAkhir
    ) throws Exception {
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanProduksi.jrxml"));
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(produksi);
        Map hash = new HashMap();
        hash.put("namaPerusahaan", "Auri Steel Metalindo");
        hash.put("tglMulai", tglMulai);
        hash.put("tglAkhir", tglAkhir);
        JasperReport SubReport = JasperCompileManager.compileReport(JRXmlLoader.load(
                getClass().getResourceAsStream("LaporanProduksiDetail.jrxml")));
        hash.put("subReport", SubReport);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, hash, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }

    public void printLaporanBarangDiproduksi(List<ProduksiDetailBarang> produksi, String tglMulai, String tglAkhir,
            String groupBy) throws Exception {
        JasperDesign jasperDesign = null;
        if (groupBy.equals("Bahan")) {
            produksi.sort(Comparator.comparing(ProduksiDetailBarang::getKodeBarang));
            jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanBarangDiproduksiBahan.jrxml"));
        } else if (groupBy.equals("Barang")) {
            produksi.sort(Comparator.comparing(ProduksiDetailBarang::getKodeBarang));
            jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanBarangDiproduksiBarang.jrxml"));
        } else {
            produksi.sort(Comparator.comparing(ProduksiDetailBarang::getKodeProduksi));
            jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanBarangDiproduksi.jrxml"));
        }
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(produksi);
        Map hash = new HashMap();
        hash.put("namaPerusahaan", "Auri Steel Metalindo");
        hash.put("tglMulai", tglMulai);
        hash.put("tglAkhir", tglAkhir);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, hash, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }
}
