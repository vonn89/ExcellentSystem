/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.excellentsystem.sentralindahperdana.PrintOut;

import static com.excellentsystem.sentralindahperdana.Main.sistem;
import static com.excellentsystem.sentralindahperdana.Main.tglNormal;
import static com.excellentsystem.sentralindahperdana.Main.tglSql;
import com.excellentsystem.sentralindahperdana.Model.BebanPenjualan;
import com.excellentsystem.sentralindahperdana.Model.Hutang;
import com.excellentsystem.sentralindahperdana.Model.Keuangan;
import com.excellentsystem.sentralindahperdana.Model.LogBarang;
import com.excellentsystem.sentralindahperdana.Model.Neraca;
import com.excellentsystem.sentralindahperdana.Model.PembelianDetail;
import com.excellentsystem.sentralindahperdana.Model.PembelianHead;
import com.excellentsystem.sentralindahperdana.Model.PengirimanDetail;
import com.excellentsystem.sentralindahperdana.Model.PengirimanHead;
import com.excellentsystem.sentralindahperdana.Model.PenjualanDetail;
import com.excellentsystem.sentralindahperdana.Model.Piutang;
import com.excellentsystem.sentralindahperdana.Model.ReturDetail;
import com.excellentsystem.sentralindahperdana.Model.ReturPembelianDetail;
import com.excellentsystem.sentralindahperdana.Model.StokBarang;
import com.excellentsystem.sentralindahperdana.Model.UntungRugi;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import simple.escp.SimpleEscp;
import simple.escp.json.JsonTemplate;

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
    public void printInvoice(List<PenjualanDetail> penjualan,double jumlahRp)throws Exception{
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("Invoice.jrxml"));
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(penjualan);
        Map hash = new HashMap();
        hash.put("terbilang",angka(jumlahRp));
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,hash, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }
    public void printProformaInvoice(List<PenjualanDetail> pemesanan, double jumlahRp)throws Exception{
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("ProformaInvoice.jrxml"));
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(pemesanan);
        Map hash = new HashMap();
        hash.put("terbilang",angka(jumlahRp));
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,hash, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }
    public void printSuratJalan(List<PengirimanDetail> pengiriman)throws Exception{
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("SuratJalan.jrxml"));
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(pengiriman);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,null, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }
    public void printSuratJalan(PengirimanHead p) throws Exception{
        DecimalFormat df = new DecimalFormat("###,##0.##");
        Map<String, Object> map = new HashMap<>();
        map.put("noSuratJalan", p.getNoPengiriman());        
        map.put("tglPengiriman", tglNormal.format(tglSql.parse(p.getTglPengiriman())));     
        map.put("jenisKendaraan", p.getJenisKendaraan());   
        map.put("noPolisi", p.getNoPolisi());  
        map.put("namaSupir", p.getSupir());       
        map.put("nama", p.getPenjualanHead().getCustomer().getNama());     
        map.put("alamat", p.getPenjualanHead().getLokasiPengerjaan());                    
        List<Map<String, Object>> tables = new ArrayList<>();
        int i = 1;
        for (PengirimanDetail d : p.getAllDetail()) {
            Map<String, Object> line = new HashMap<>();
            line.put("no", i);
            line.put("namaBarang", d.getNamaBarang());
            line.put("qty", df.format(d.getQty()));
            line.put("satuan", d.getSatuan());
            line.put("keterangan", "");
            tables.add(line);
            i++;
        }
        while(tables.size()<12){
            Map<String, Object> line2 = new HashMap<>();
            line2.put("no", "");
            line2.put("namaBarang", "");
            line2.put("qty", "");
            line2.put("satuan", "");
            line2.put("keterangan", "");
            tables.add(line2);
        }
        map.put("table", tables);   
        SimpleEscp simpleEscp = new SimpleEscp();      
        JsonTemplate template = new JsonTemplate(getClass().getResourceAsStream("template.json"));
//        PrintPreviewPane printPreview = new PrintPreviewPane(template, map,null);
//        
//        getContentPane().setLayout(new BorderLayout());
//        getContentPane().add(printPreview, BorderLayout.CENTER);
//
//        setPreferredSize(getMaximumSize());
//        pack();
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setVisible(true);

        simpleEscp.print(template, map);
    }
    public void printLaporanNeraca(List<Neraca> neraca, String tglMulai, String tglAkhir, 
            String totalAktiva, String totalPassiva)throws Exception{
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanNeraca.jrxml"));
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(neraca);
        Map hash = new HashMap();
        hash.put("namaPerusahaan",sistem.getNama());
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
        hash.put("namaPerusahaan",sistem.getNama());
        hash.put("tglMulai",tglMulai);
        hash.put("tglAkhir",tglAkhir);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,hash, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }
    public void printLaporanProyek(List<PenjualanDetail> penjualan)throws Exception{
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanProyek.jrxml"));
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(penjualan);
        JasperReport SubReportAnggaranMaterial = JasperCompileManager.compileReport(JRXmlLoader.load(
                getClass().getResourceAsStream("LaporanProyekAnggaranMaterial.jrxml")));
        JasperReport SubReportAnggaranBebanPenjualan = JasperCompileManager.compileReport(JRXmlLoader.load(
                getClass().getResourceAsStream("LaporanProyekAnggaranBebanPenjualan.jrxml")));
        JasperReport SubReportBebanMaterial = JasperCompileManager.compileReport(JRXmlLoader.load(
                getClass().getResourceAsStream("LaporanProyekBebanMaterial.jrxml")));
        JasperReport SubReportBebanPenjualan = JasperCompileManager.compileReport(JRXmlLoader.load(
                getClass().getResourceAsStream("LaporanProyekBebanPenjualan.jrxml")));
        Map hash = new HashMap();
        hash.put("namaPerusahaan",sistem.getNama());
        hash.put("SubReportAnggaranMaterial", SubReportAnggaranMaterial);
        hash.put("SubReportAnggaranBebanPenjualan", SubReportAnggaranBebanPenjualan);
        hash.put("SubReportBebanMaterial", SubReportBebanMaterial);
        hash.put("SubReportBebanPenjualan", SubReportBebanPenjualan);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,hash, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }
    public void printLaporanKeuangan(List<Keuangan> keuangan, String tglMulai, String tglAkhir,
            String jenisLaporan, String saldoAwal, String saldoAkhir)throws Exception{
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanKeuangan.jrxml"));
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(keuangan);
        Map hash = new HashMap();
        hash.put("namaPerusahaan",sistem.getNama());
        hash.put("tglMulai",tglMulai);
        hash.put("tglAkhir",tglAkhir);
        hash.put("jenisLaporan",jenisLaporan);
        hash.put("saldoAwal",saldoAwal);
        hash.put("saldoAkhir",saldoAkhir);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,hash, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }
    public void printNeracaBarangJadi(List<Keuangan> keuangan, String tglAkhir,
            String jenisLaporan,  String saldoAkhir)throws Exception{
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("NeracaBarangJadi.jrxml"));
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(keuangan);
        Map hash = new HashMap();
        hash.put("namaPerusahaan",sistem.getNama());
        hash.put("tglAkhir",tglAkhir);
        hash.put("jenisLaporan",jenisLaporan);
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
        hash.put("namaPerusahaan",sistem.getNama());
        hash.put("tglMulai",tglMulai);
        hash.put("tglAkhir",tglAkhir);
        hash.put("saldoAwal",saldoAwal);
        hash.put("saldoAkhir",saldoAkhir);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,hash, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }
    public void printLaporanNeracaHutang(List<Hutang> hutang, String tglAkhir)throws Exception{
        if(hutang.get(0).getKategori().equals("Hutang Pembelian")){
            Collections.sort(hutang, (o1, o2) -> {
                return ((Hutang) o1).getSupplier().getKodeSupplier().compareTo(
                        ((Hutang) o2).getSupplier().getKodeSupplier());
            });
        }else if(hutang.get(0).getKategori().equals("Terima Pembayaran Down Payment")){
            Collections.sort(hutang, (o1, o2) -> {
                return ((Hutang) o1).getCustomer().getKodeCustomer().compareTo(
                        ((Hutang) o2).getCustomer().getKodeCustomer());
            });
        }
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanNeracaHutang.jrxml"));
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(hutang);
        Map hash = new HashMap();
        hash.put("namaPerusahaan",sistem.getNama());
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
        hash.put("namaPerusahaan",sistem.getNama());
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
                return ((Piutang) o1).getCustomer().getKodeCustomer().compareTo(
                        ((Piutang) o2).getCustomer().getKodeCustomer());
            });
        }
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanNeracaPiutang.jrxml"));
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(piutang);
        Map hash = new HashMap();
        hash.put("namaPerusahaan",sistem.getNama());
        hash.put("tglAkhir",tglAkhir);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,hash, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }
    public void printLaporanPembelian(List<PembelianHead> pembelian, String tglMulai, String tglAkhir
            )throws Exception{
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanPembelian.jrxml"));
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(pembelian);
        Map hash = new HashMap();
        hash.put("namaPerusahaan",sistem.getNama());
        hash.put("tglMulai",tglMulai);
        hash.put("tglAkhir",tglAkhir);
        JasperReport SubReport = JasperCompileManager.compileReport(JRXmlLoader.load(
                getClass().getResourceAsStream("LaporanPembelianDetail.jrxml")));
        hash.put("subReport", SubReport);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,hash, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }
    public void printLaporanBarangDibeli(List<PembelianDetail> pembelian, String tglMulai, String tglAkhir
            )throws Exception{
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanBarangDibeli.jrxml"));
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(pembelian);
        Map hash = new HashMap();
        hash.put("namaPerusahaan",sistem.getNama());
        hash.put("tglMulai",tglMulai);
        hash.put("tglAkhir",tglAkhir);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,hash, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }
    public void printLaporanReturPembelian(List<ReturPembelianDetail> returPembelian, String tglMulai, String tglAkhir
            )throws Exception{
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanReturPembelian.jrxml"));
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(returPembelian);
        Map hash = new HashMap();
        hash.put("namaPerusahaan",sistem.getNama());
        hash.put("tglMulai",tglMulai);
        hash.put("tglAkhir",tglAkhir);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,hash, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }
    public void printLaporanBarangDiReturBeli(List<ReturPembelianDetail> returPembelian, String tglMulai, String tglAkhir
            )throws Exception{
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanBarangDireturBeli.jrxml"));
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(returPembelian);
        Map hash = new HashMap();
        hash.put("namaPerusahaan",sistem.getNama());
        hash.put("tglMulai",tglMulai);
        hash.put("tglAkhir",tglAkhir);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,hash, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }
    public void printLaporanPenjualan(List<PenjualanDetail> penjualan, String tglMulai, String tglAkhir
            )throws Exception{
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanPenjualan.jrxml"));
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(penjualan);
        Map hash = new HashMap();
        hash.put("namaPerusahaan",sistem.getNama());
        hash.put("tglMulai",tglMulai);
        hash.put("tglAkhir",tglAkhir);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,hash, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }
    public void printLaporanItemTerjual(List<PenjualanDetail> penjualan, String tglMulai, String tglAkhir
            )throws Exception{
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanItemTerjual.jrxml"));
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(penjualan);
        Map hash = new HashMap();
        hash.put("namaPerusahaan",sistem.getNama());
        hash.put("tglMulai",tglMulai);
        hash.put("tglAkhir",tglAkhir);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,hash, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }
    public void printLaporanBarangDikirim(List<PengirimanDetail> pengiriman, String tglMulai, String tglAkhir
            )throws Exception{
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanBarangDikirim.jrxml"));
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(pengiriman);
        Map hash = new HashMap();
        hash.put("namaPerusahaan",sistem.getNama());
        hash.put("tglMulai",tglMulai);
        hash.put("tglAkhir",tglAkhir);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,hash, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }
    public void printLaporanBarangDiretur(List<ReturDetail> retur, String tglMulai, String tglAkhir
            )throws Exception{
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanBarangDiretur.jrxml"));
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(retur);
        Map hash = new HashMap();
        hash.put("namaPerusahaan",sistem.getNama());
        hash.put("tglMulai",tglMulai);
        hash.put("tglAkhir",tglAkhir);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,hash, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }
    public void printLaporanBebanPenjualanTerbayar(List<BebanPenjualan> bebanPenjualan, String tglMulai, String tglAkhir
            )throws Exception{
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanBebanPenjualanTerbayar.jrxml"));
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(bebanPenjualan);
        Map hash = new HashMap();
        hash.put("namaPerusahaan",sistem.getNama());
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
        hash.put("namaPerusahaan",sistem.getNama());
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,hash, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }
    public void printLaporanKartuStokBarang(List<StokBarang> stokBarang)throws Exception{
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanKartuStok.jrxml"));
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(stokBarang);
        Map hash = new HashMap();
        hash.put("namaPerusahaan",sistem.getNama());
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,hash, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }
    public void printLaporanLogBarang(List<LogBarang> logBarang, String tglMulai, String tglAkhir,
            String namaBarang)throws Exception{
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanLogBarang.jrxml"));
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(logBarang);
        Map hash = new HashMap();
        hash.put("namaPerusahaan",sistem.getNama());
        hash.put("tglMulai",tglMulai);
        hash.put("tglAkhir",tglAkhir);
        hash.put("namaBarang",namaBarang);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,hash, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }
}
