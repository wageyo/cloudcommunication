package com.esd.cloudcommunication.common;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import jp.sourceforge.qrcode.QRCodeDecoder;

import com.swetake.util.Qrcode;

public class QRCoder {

	private static int DEFAULT_WIDTH;
	private static int UNIT_WIDTH = 12;

	/**
	 * 生成二维码(QRCode)图片
	 * 
	 * @param content
	 *            存储内容
	 * @param imgPath
	 *            图片路径
	 * @param imgType
	 *            图片类型
	 * @param size
	 *            二维码尺寸
	 * @throws Exception
	 */
	public boolean encoderQRCode(String content, String imgPath, String imgType,
			int size) throws Exception {

		BufferedImage bufImg = this.createQRCode(content, imgType, size);
		File imgFile = new File(imgPath);
		// 生成二维码QRCode图片
		boolean bl = ImageIO.write(bufImg, imgType, imgFile);
		if(bl){
			return Boolean.TRUE;
		}else{
			return Boolean.FALSE;
		}
	}

	/**
	 * 生成二维码(QRCode)图片
	 * 
	 * @param content
	 *            存储内容
	 * @param output
	 *            输出流
	 * @param imgType
	 *            图片类型
	 * @param size
	 *            二维码尺寸
	 * @throws Exception
	 */
	public void encoderQRCode(String content, OutputStream output,
			String imgType, int size) throws Exception {

		BufferedImage bufImg = this.createQRCode(content, imgType, size);
		// 生成二维码QRCode图片
		ImageIO.write(bufImg, imgType, output);
	}

	/**
	 * 生成二维码(QRCode)图片的公共方法
	 * 
	 * @param content
	 *            存储内容
	 * @param imgType
	 *            图片类型
	 * @param size
	 *            二维码尺寸
	 * @return
	 * @throws Exception
	 */
	private BufferedImage createQRCode(String content, String imgType, int size)
			throws Exception {

		Qrcode qrcodeHandler = new Qrcode();
		// 设置二维码排错率，可选L(7%)、M(15%)、Q(25%)、H(30%)，
		// 排错率越高可存储的信息越少，但对二维码清晰度的要求越小
		// QR码有容错能力，QR码图形如果有破损，仍然可以被机器读取内容，
		// 最高可以到7%~30%面积破损仍可被读取。
		// 相对而言，容错率愈高，QR码图形面积愈大。所以一般折衷使用15%容错能力。
		qrcodeHandler.setQrcodeErrorCorrect('M');
		qrcodeHandler.setQrcodeEncodeMode('B'); /* "N","A" or other */
		// 设置设置二维码尺寸，取值范围1-40，值越大尺寸越大，可存储的信息越大
		qrcodeHandler.setQrcodeVersion(size);
		// 获得内容的字节数组，设置编码格式
		byte[] contentBytes = content.getBytes("utf-8");
		boolean[][] bRect = qrcodeHandler.calQrcode(contentBytes);
		// 图片尺寸
		DEFAULT_WIDTH = 8 + bRect.length * UNIT_WIDTH;

		BufferedImage bufImg = new BufferedImage(DEFAULT_WIDTH, DEFAULT_WIDTH,
				BufferedImage.TYPE_INT_RGB);
		// createGraphics
		Graphics2D gs = bufImg.createGraphics();
		// 设置背景颜色
		gs.setBackground(Color.WHITE);
		gs.clearRect(0, 0, DEFAULT_WIDTH, DEFAULT_WIDTH);
		// 设定图像颜色> BLACK
		gs.setColor(Color.BLACK);
		int pixoff = 4;
		// 输出内容> 二维码
		if (contentBytes.length > 0 && contentBytes.length < 800) {
			for (int i = 0; i < bRect.length; i++) {
				for (int j = 0; j < bRect.length; j++) {
					if (bRect[j][i]) {
						gs.fillRect(j * UNIT_WIDTH + pixoff, i * UNIT_WIDTH
								+ pixoff, UNIT_WIDTH, UNIT_WIDTH);
					}
				}

			}
		} else {
			throw new Exception("QRCode content bytes length = " + bRect.length
					+ " not in [0, 800].");
		}
		gs.dispose();
		bufImg.flush();
		return bufImg;
	}

	/**
	 * 解析二维码（QRCode）
	 * 
	 * @param imgPath
	 *            图片路径
	 * @return
	 * @throws IOException
	 */
	public String decoderQRCode(String imgPath) throws IOException {
		// QRCode 二维码图片的文件
		File imageFile = new File(imgPath);
		BufferedImage bufImg = null;
		String content = null;
		bufImg = ImageIO.read(imageFile);
		QRCodeDecoder decoder = new QRCodeDecoder();
		byte[] bt = decoder.decode(new QRCoderImage(bufImg));
		content = new String(bt, "utf-8");
		return content;
	}

	/**
	 * 解析二维码（QRCode）
	 * 
	 * @param input
	 *            输入流
	 * @return
	 * @throws IOException
	 */
	public String decoderQRCode(InputStream input) throws IOException {
		BufferedImage bufImg = null;
		String content = null;
		bufImg = ImageIO.read(input);
		QRCodeDecoder decoder = new QRCodeDecoder();
		content = new String(decoder.decode(new QRCoderImage(bufImg)), "utf-8");
		return content;
	}
}