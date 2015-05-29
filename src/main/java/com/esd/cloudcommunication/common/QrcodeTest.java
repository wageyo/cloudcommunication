package com.esd.cloudcommunication.common;
import java.io.IOException;

public class QrcodeTest {

	
	public static void main(String[] args) {
		String imgPath = "2.png";
		String content = "你好恨你拉极乐空间";
		int size = 7;
		QRCoder qr = new QRCoder();
		try {
			qr.encoderQRCode(content, imgPath, "png", size);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void testdecoderQRCode() {
		String imgPath = System.getProperty("user.dir")
				+ "/WebRoot/upload/www1.png";
		QRCoder qr = new QRCoder();
		String con = "";
		try {
			con = qr.decoderQRCode(imgPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(con);
	}
}