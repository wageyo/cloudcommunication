package com.esd.cloudcommunication.common;
import java.awt.image.BufferedImage;
 
import jp.sourceforge.qrcode.data.QRCodeImage;
 
public class QRCoderImage implements QRCodeImage {
 
    BufferedImage bufImg;
 
    public QRCoderImage(BufferedImage bufImg) {
        this.bufImg = bufImg;
    }
 
    public int getHeight() {
        return bufImg.getHeight();
    }
 
    public int getPixel(int x, int y) {
        return bufImg.getRGB(x, y); 
    }
 
    public int getWidth() {
        return bufImg.getWidth();
    }
 
}