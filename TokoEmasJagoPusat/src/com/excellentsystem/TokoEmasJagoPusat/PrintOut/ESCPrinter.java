/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.excellentsystem.TokoEmasJagoPusat.PrintOut;

import java.io.PrintStream;
import java.net.Socket;

/**
 *
 * @author excellent
 */
public class ESCPrinter {
    private String ip;
    private Socket printSocket;
    private PrintStream pstream;
    private boolean streamOpenSuccess;
    
    public ESCPrinter(String ip) {
        this.ip = ip;
    }
    
    public void close() {
        try {
            pstream.close();
            printSocket.close();
        }catch(Exception e){ 
            e.printStackTrace(); 
        }
    }
    
    public boolean initialize() throws Exception{
        streamOpenSuccess = false;
        
        printSocket = new Socket(ip,9100);
        pstream = new PrintStream(printSocket.getOutputStream());
        streamOpenSuccess = true;
        
        return streamOpenSuccess;
    }
    public void lineFeed() {
        pstream.print(CR);
        pstream.print(LF);
    }
    public void formFeed() {
        pstream.print(CR); 
        pstream.print(FF);
    }
    public void bold(boolean bold) {
        pstream.print(ESC);
        pstream.print("E");
        if(bold)
            pstream.print("1");
        else
            pstream.print("0");
    }
    public void underline(boolean underline) {
        pstream.print(ESC);
        pstream.print(ESCPrinter.underline);
        if(underline)
            pstream.print("1");
        else
            pstream.print("0");
    }
    public void setCharacterSpacing(int n){
        pstream.print(ESC);
        pstream.print(SP);
        pstream.print(n);
    }
    public void setLineSpacing(int n){
        pstream.print(ESC);
        pstream.print("3");
        pstream.print(n);
    }
    public void setDoubleHeightWidth(boolean doubleHeight,boolean doubleWidth){
        pstream.print(ESC);
        pstream.print("!");
        if(doubleHeight&&!doubleWidth)
            pstream.print(doubleheight);
        else if(!doubleHeight&&doubleWidth)
            pstream.print(doublewidth);
        else if(doubleHeight&&doubleWidth)
            pstream.print(doubleheightwidth);
        else if(!doubleHeight&&!doubleWidth)
            pstream.print(normal);//this will turn off bold and underline mode
    }
    public void setAlignment(String justify){//CENTER/LEFT/RIGHT
        pstream.print(ESC);
        pstream.print("a");
        if(justify.equals("LEFT"))
            pstream.print("0");
        else if(justify.equals("CENTER"))
            pstream.print("1");
        else if(justify.equals("RIGHT"))
            pstream.print("2");
    }
    public void advanceVertical(float centimeters) {
        //pre: centimeters >= 0 (cm)
        //post: advances vertical print position approx. y centimeters (not precise due to truncation)
        float inches = centimeters / CM_PER_INCH;
        int units = (int) (inches * (MAX_ADVANCE_9PIN));
        
        while (units > 0) {
            char n;
            if (units > MAX_UNITS)
                n = (char) MAX_UNITS; //want to move more than range of parameter allows (0 - 255) so move max amount
            else
                n = (char) units; //want to move a distance which fits in range of parameter (0 - 255)
                        
            pstream.print(ESC);
            pstream.print(J);
            pstream.print(n);
            
            units -= MAX_UNITS;
        }
    }
    
    public void print(String text) {
        pstream.print(text);
    }
    public boolean isInitialized() {
        return streamOpenSuccess;
    }
    private static int MAX_ADVANCE_9PIN = 216;
    private static int MAX_UNITS = 127; //for vertical positioning range is between 0 - 255 (0 <= n <= 255) according to epson ref. but 255 gives weird errors at 1.5f, 127 as max (0 - 128) seems to be working
    private static final float CM_PER_INCH = 2.54f;
    
    /* decimal ascii values for epson ESC/P commands */
    private static final char ESC = 27; //escape
    private static final char LF = 10; //line feed/new line
    private static final char FF = 12; //form feed
    private static final char SP = 32; //character spacing
    private static final char normal = 0;
    private static final char doubleheight = 16;
    private static final char doublewidth = 32;
    private static final char doubleheightwidth = 48; 
    private static final char underline = 45; 
    
    private static final char CR = 13; //carriage return
    private static final char J = 74; //used for advancing paper vertically
}
