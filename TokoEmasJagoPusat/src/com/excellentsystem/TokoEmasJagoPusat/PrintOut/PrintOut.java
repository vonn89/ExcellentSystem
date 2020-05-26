/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.PrintOut;

import static com.excellentsystem.TokoEmasJagoPusat.Main.printerBarcode;
import static com.excellentsystem.TokoEmasJagoPusat.Main.rp;
import static com.excellentsystem.TokoEmasJagoPusat.Main.tglBarang;
import com.excellentsystem.TokoEmasJagoPusat.Model.BarangCabang;
import com.excellentsystem.TokoEmasJagoPusat.Model.PenjualanFiktifDetail;
import com.excellentsystem.TokoEmasJagoPusat.Model.PenjualanFiktifHead;
import com.excellentsystem.TokoEmasJagoPusat.Model.PermintaanBarang;
import java.awt.print.PrinterJob;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.MediaSize;
import javax.print.attribute.standard.MediaSizeName;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import simple.escp.SimpleEscp;
import simple.escp.Template;
import simple.escp.data.DataSources;
import simple.escp.json.JsonTemplate;

/**
 *
 * @author excellent
 */
public class PrintOut {
    
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
    private String spaceAfter(String str, int space){      
        if(str.length()<space){   
            StringBuilder str1 = new StringBuilder();
            int i = space - str.length();
            str1.append(str); 
            for(int j=0;j<i;j++){
                str1.append(" ");
            }          
            return str1.toString(); 
        }else if(str.length()>space){
            return str.substring(0, space);
        }else
            return str;  
    }
    private String spaceBefore(String str, int space){      
        if(str.length()<space){   
            StringBuilder str1 = new StringBuilder();
            int i = space - str.length();
            for(int j=0;j<i;j++){
                str1.append(" ");
            }          
            str1.append(str); 
            return str1.toString(); 
        }else if(str.length()>space){
            return str.substring(0, space);
        }else
            return str;           
    }
    public void printSuratPenjualan(List<PenjualanFiktifHead> listPenjualan)throws Exception{
        DecimalFormat df = new DecimalFormat("#,##0.00");
        for(PenjualanFiktifHead p : listPenjualan){
            Faktur faktur = null;
            String tanggal = new SimpleDateFormat("dd-MMM-yyyy").
                    format(tglBarang.parse(p.getTglPenjualan().substring(0,10)));
            String jam = p.getTglPenjualan().substring(10, 16);
            String total = rp.format(p.getGrandtotal());
            faktur = new Faktur(p.getNoPenjualan(), tanggal, p.getNama(), p.getAlamat(),p.getNoTelp(),
                    "",jam,total,angka(p.getGrandtotal())+" Rupiah");
            int i = 0;
            for(PenjualanFiktifDetail d : p.getListPenjualanDetail()){
                faktur.tambahItemFaktur(new ItemFaktur(
                    d.getNamaBarang(), df.format(d.getBerat()), d.getKadar()+" %", 
                    d.getKodeIntern(), rp.format(d.getHargaJual())));
                i++;
                if(i==4)
                    break;
            }
            for(int j = i ; j < 4; j++){
                faktur.tambahItemFaktur(new ItemFaktur("","","","",""));
            }
            faktur.tambahItemFaktur(new ItemFaktur("","","","","")); 
            SimpleEscp simpleEscp = new SimpleEscp();          
            Template template = new JsonTemplate(getClass().getResourceAsStream("template.json"));
            simpleEscp.print(template, DataSources.from(faktur));
        }
    }
    public void printStrukPenjualanDirect(List<PenjualanFiktifHead> listPenjualan)throws Exception{
        DecimalFormat df = new DecimalFormat("#,##0.00");
        String data = "";
        for(PenjualanFiktifHead p : listPenjualan){
            String tanggal = new SimpleDateFormat("dd-MMM-yyyy").
                    format(tglBarang.parse(p.getTglPenjualan().substring(0,10)));
            String terbilang = angka(p.getGrandtotal())+" Rupiah";
            String jam = p.getTglPenjualan().substring(10, 16);
            data = data + "<ESC>P";
            data = data +   
                "<ESC>a0"+spaceBefore(p.getNoPenjualan(),50)+spaceBefore(tanggal,20)+"\n" +
                "<ESC>a0"+spaceAfter("", 60)+p.getNama()+"\n" +
                "<ESC>a0"+spaceAfter("", 60)+p.getAlamat()+"\n" +
                "<ESC>a0"+spaceAfter("", 60)+p.getNoTelp()+"\n" +
                "<ESC>a0"+spaceBefore(jam,50)+"\n\n\n";
            int i = 0;
            for(PenjualanFiktifDetail d : p.getListPenjualanDetail()){
                data = data +
                "<ESC>a0"+spaceAfter("", 13)+spaceAfter(d.getNamaBarang(),34)+
                        spaceAfter(df.format(d.getBerat()),6)+spaceAfter(d.getKadar(),6)+
                        spaceAfter(d.getKodeIntern(),6)+spaceBefore(rp.format(d.getHargaJual()),11)+"\n";
                i++;
                if(i==4)
                    break;
            }
            for(int j = i ; j < 4; j++){
                data = data +   "\n";
            }
            data = data +   
                "<ESC>a0\nTerbilang : "+spaceAfter(terbilang, 53)+spaceBefore(rp.format(p.getGrandtotal()), 11)+"\n";
            
            double cmDown = 1.5;
            int units = (int) (cmDown / 2.54f * (216));
            while (units > 0) {
                char n;
                if (units > 127)
                    n = (char) 127;
                else
                    n = (char) units;
                data = data + "<ESC>J"+n;
                units -= 127;
            }
        }
        data = data + "<ESC>FF";
        char ch = 27;
        data = data.replace("<ESC>", String.valueOf(ch));
        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
        int selectedService = 0;
        for(int i = 0; i < services.length;i++){
            if(services[i].getName().toUpperCase().contains("LX-310"))
                selectedService = i;
        }
        DocPrintJob job = services[selectedService].createPrintJob();
        try (InputStream is = new ByteArrayInputStream(data.getBytes())) {
            DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
            Doc doc = new SimpleDoc(is, flavor, null);
            job.print(doc, null);
        }
    }
    public static void printBarcode(List<BarangCabang> listBarang)throws Exception{
        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
        int selectedService = 0;
        for(int i = 0; i < services.length;i++){
            if(services[i].getName().toUpperCase().contains(printerBarcode))
                selectedService = i;
        }
        DecimalFormat gr = new DecimalFormat("###,##0.00");
        for(int i=0; i<listBarang.size();i++){
            BarangCabang b = listBarang.get(i);
            if(i==0||i%2==0){
                String commands = "<ESC>A<ESC>A3H0130V0010";
                commands=commands+"<ESC>H0000<ESC>V0007<ESC>M"+b.getKodeBarcode()+
                    "<ESC>H0000<ESC>V0030<ESC>BG01050"+b.getKodeBarcode()+
                    "<ESC>H0000<ESC>V0105<ESC>S"+b.getNamaBarang()+
                    "<ESC>H0020<ESC>V0130<ESC>L0102<ESC>M"+gr.format(b.getBerat())+"<ESC>L0102<ESC>S gr" +
                    "<ESC>H0000<ESC>V0170<ESC>L0101<ESC>S"+b.getKodeJenis()+"<ESC>H0110<ESC>V0170<ESC>S"+b.getKodeIntern();
                if(listBarang.size()==i+1){}else{
                    BarangCabang b2 = listBarang.get(i+1);
                    commands=commands+"<ESC>H0455<ESC>V0007<ESC>M"+b2.getKodeBarcode()+
                        "<ESC>H0455<ESC>V0030<ESC>BG01050"+b2.getKodeBarcode()+
                        "<ESC>H0455<ESC>V0105<ESC>S" +b2.getNamaBarang()+
                        "<ESC>H0475<ESC>V0130<ESC>L0102<ESC>M"+gr.format(b2.getBerat())+"<ESC>L0102<ESC>S gr" +
                        "<ESC>H0455<ESC>V0170<ESC>L0101<ESC>S"+b2.getKodeJenis()+"<ESC>H0565<ESC>V0170<ESC>S"+b2.getKodeIntern();
                }
                commands=commands+"<ESC>Q1<ESC>Z";
                char ch = 27;
                commands = commands.replace("<ESC>", String.valueOf(ch));
                PrintService pservice = services[selectedService];
                DocPrintJob job = pservice.createPrintJob();  
                DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
                Doc doc = new SimpleDoc(commands.getBytes(), flavor, null);
                job.print(doc, null);
            }
        } 
    }
    public void printPermintaanBarang(List<PermintaanBarang> p)throws Exception{
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("PermintaanBarang.jrxml"));
        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(p);
        Map hash = new HashMap();
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,hash, beanColDataSource);
        
        PrinterJob job = PrinterJob.getPrinterJob();
        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
        int selectedService = 0;
        for(int i = 0; i < services.length;i++){
            if(services[i].getName().toUpperCase().contains("SLIP"))
                selectedService = i;
        }
        job.setPrintService(services[selectedService]);
        PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
        MediaSizeName mediaSizeName = MediaSize.findMedia(4,4,MediaPrintableArea.INCH);
        printRequestAttributeSet.add(mediaSizeName);
        printRequestAttributeSet.add(new Copies(1));
        JRPrintServiceExporter exporter;
        exporter = new JRPrintServiceExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE, services[selectedService]);
        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, services[selectedService].getAttributes());
        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, printRequestAttributeSet);
        exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
        exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
        exporter.exportReport();
    }
}
