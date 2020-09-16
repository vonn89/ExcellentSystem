/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.jagobangunpersadafx.Printout;

import static com.excellentsystem.jagobangunpersadafx.Main.tglSql;
import com.excellentsystem.jagobangunpersadafx.Model.Neraca;
import com.excellentsystem.jagobangunpersadafx.Model.RAPDetail;
import com.excellentsystem.jagobangunpersadafx.Model.RAPHead;
import com.excellentsystem.jagobangunpersadafx.Model.RAPRealisasi;
import com.excellentsystem.jagobangunpersadafx.Model.SAP;
import com.excellentsystem.jagobangunpersadafx.Model.SDP;
import com.excellentsystem.jagobangunpersadafx.Model.SKLHead;
import com.excellentsystem.jagobangunpersadafx.Model.SPPHead;
import com.excellentsystem.jagobangunpersadafx.Model.STJHead;
import com.excellentsystem.jagobangunpersadafx.Model.UntungRugi;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
 * @author ASUS
 */
public class PrintOut {
    
//    public void printProformaInvoice(List<PemesananDetail> pemesanan, double jumlahRp)throws Exception{
//        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("ProformaInvoice.jrxml"));
//        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(pemesanan);
//        Map hash = new HashMap();
//        hash.put("terbilang",angka(jumlahRp));
//        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
//        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,hash, beanColDataSource);
//        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
//    }
    public void printSuratTandaJadi(STJHead stj)throws Exception{
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("STJ.jrxml"));
        Map parameters = new HashMap<>();
        DateFormat tgl = new SimpleDateFormat("dd MMMMM yyyy");
        parameters.put("tglSTJ",tgl.format(tglSql.parse(stj.getTglSTJ())));
        parameters.put("tahunPBB",new SimpleDateFormat("yyyy").format(tglSql.parse(stj.getTglSTJ())));
        parameters.put("header", ImageIO.read(getClass().getResource("JBP Kop Surat.jpg")));
        parameters.put("watermark", ImageIO.read(getClass().getResource("JBP watermark.jpg")));
        JasperDesign subreport = JRXmlLoader.load(getClass().getResourceAsStream("STJDetail.jrxml"));
        JasperReport jasperSubReport = JasperCompileManager.compileReport(subreport);
        parameters.put("SubReportParam", jasperSubReport);
        List<STJHead> allstj = new ArrayList<>();
        allstj.add(stj);
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(allstj);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,parameters, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }
    public void printSuratDownPayment(SDP sdp)throws Exception{
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("SDP.jrxml"));
        Map parameters = new HashMap<>();
        DateFormat tgl = new SimpleDateFormat("dd MMMMM yyyy");
        parameters.put("tglSDP",tgl.format(tglSql.parse(sdp.getTglSDP())));
        parameters.put("header", ImageIO.read(getClass().getResource("JBP Kop Surat.jpg")));
        parameters.put("watermark", ImageIO.read(getClass().getResource("JBP watermark.jpg")));
        List<SDP> allsdp = new ArrayList<>();
        allsdp.add(sdp);
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(allsdp);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,parameters, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }
    public void printSuratKeteranganLunas(SKLHead skl)throws Exception{
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("SKL.jrxml"));
        Map parameters = new HashMap<>();
        DateFormat tgl = new SimpleDateFormat("dd MMMMM yyyy");
        parameters.put("tglSKL",tgl.format(tglSql.parse(skl.getTglSKL())));
        parameters.put("header", ImageIO.read(getClass().getResource("JBP Kop Surat.jpg")));
        parameters.put("watermark", ImageIO.read(getClass().getResource("JBP watermark.jpg")));
        JasperDesign subreport = JRXmlLoader.load(getClass().getResourceAsStream("SKLDetail.jrxml"));
        JasperReport jasperSubReport = JasperCompileManager.compileReport(subreport);
        parameters.put("SubReportParam", jasperSubReport);
        List<SKLHead> allskl = new ArrayList<>();
        allskl.add(skl);
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(allskl);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,parameters, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }
    public void printSuratAngsuranPembayaran(SAP sap)throws Exception{
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("SAP.jrxml"));
        Map parameters = new HashMap<>();
        parameters.put("header", ImageIO.read(getClass().getResource("JBP Kop Surat.jpg")));
        parameters.put("watermark", ImageIO.read(getClass().getResource("JBP watermark.jpg")));
        DateFormat tgl = new SimpleDateFormat("dd MMMMM yyyy");
        parameters.put("tglSAP",tgl.format(tglSql.parse(sap.getTglSAP())));
        List<SAP> allsap = new ArrayList<>();
        allsap.add(sap);
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(allsap);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,parameters, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }
    public void printSuratPelunasanAngsuran(SPPHead spp)throws Exception{
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("SPP.jrxml"));
        Map parameters = new HashMap<>();
        DateFormat tgl = new SimpleDateFormat("dd MMMMM yyyy");
        parameters.put("tglSPP", tgl.format(tglSql.parse(spp.getTglSPP())));
        parameters.put("header", ImageIO.read(getClass().getResource("JBP Kop Surat.jpg")));
        parameters.put("watermark", ImageIO.read(getClass().getResource("JBP watermark.jpg")));
        JasperDesign subreport = JRXmlLoader.load(getClass().getResourceAsStream("SPPDetail.jrxml"));
        JasperReport jasperSubReport = JasperCompileManager.compileReport(subreport);
        parameters.put("SubReportParam", jasperSubReport);
        List<SPPHead> allspp = new ArrayList<>();
        allspp.add(spp);
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(allspp);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,parameters, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }
    public void printRencanaAnggaranProyek(RAPHead rap)throws Exception{
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("RAP.jrxml"));
        Map parameters = new HashMap<>();
        List<RAPDetail> allDetail = new ArrayList<>();
        for(RAPDetail d : rap.getListRapDetail()){
            d.setRapHead(rap);
            allDetail.add(d);
        }
        allDetail.sort(Comparator.comparing(RAPDetail::getKategori));
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(allDetail);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,parameters, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }
    public void printRealisasiAnggaranProyek(RAPHead rap)throws Exception{
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("RealisasiAnggaran.jrxml"));
        Map parameters = new HashMap<>();
        List<RAPRealisasi> allDetail = new ArrayList<>();
        for(RAPRealisasi d : rap.getListRapRealisasi()){
            d.setRapHead(rap);
            allDetail.add(d);
        }
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(allDetail);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,parameters, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }
    public void printLaporanUntungRugi(List<UntungRugi> keuangan, String tglMulai, String tglAkhir)throws Exception{
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanUntungRugi.jrxml"));
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(keuangan);
        Map hash = new HashMap();
        hash.put("tglMulai",tglMulai);
        hash.put("tglAkhir",tglAkhir);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,hash, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }
    public void printLaporanNeraca(List<Neraca> neraca, String tglAkhir, 
            String totalAktiva, String totalPassiva)throws Exception{
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("LaporanNeraca.jrxml"));
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(neraca);
        Map hash = new HashMap();
        hash.put("tglAkhir",tglAkhir);
        hash.put("totalAktiva",totalAktiva);
        hash.put("totalPassiva",totalPassiva);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,hash, beanColDataSource);
        JRViewerFx jrViewerFx = new JRViewerFx(jasperPrint);
    }
}
