package com.modest.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Hashtable;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

/**
 * 利用zxing开源工具生成二维码QRCode
 */
public class QRCodeUtil {
    private static final int BLACK = 0xff000000;
    private static final int WHITE = 0xFFFFFFFF;


    /**
     * 生成QRCode二维码<br> 
     * 在编码时需要将com.google.zxing.qrcode.encoder.Encoder.java中的<br>
     *  static final String DEFAULT_BYTE_MODE_ENCODING = "ISO8859-1";<br>
     *  修改为UTF-8，否则中文编译后解析不了<br>
     *  // 如果不用更改源码，将字符串转换成ISO-8859-1编码  <br>
        contents = new String(contents.getBytes("UTF-8"), "ISO-8859-1");<br>
     */
    public static void encode(String contents, File file, BarcodeFormat format, int width, int height, Map<EncodeHintType, ?> hints) {
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(contents, format, width, height);
            writeToFile(bitMatrix, "png", file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 生成QRCode二维码<br> 
     * 在编码时需要将com.google.zxing.qrcode.encoder.Encoder.java中的<br>
     *  static final String DEFAULT_BYTE_MODE_ENCODING = "ISO8859-1";<br>
     *  修改为UTF-8，否则中文编译后解析不了<br>
     *  // 如果不用更改源码，将字符串转换成ISO-8859-1编码  <br>
        contents = new String(contents.getBytes("UTF-8"), "ISO-8859-1");<br>
     */
    public static void encode(String contents, OutputStream outputStream, BarcodeFormat format, int width, int height, Map<EncodeHintType, ?> hints) {
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(contents, format, width, height);
            writeToOutputStream(bitMatrix, "png", outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成二维码图片<br>
     * 
     * @param matrix
     * @param format 图片格式
     * @param file 生成二维码图片位置
     * @throws IOException
     */
    public static void writeToFile(BitMatrix matrix, String format, File file) throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        ImageIO.write(image, format, file);
    }
    
    /**
     * 生成二维码图片<br>
     * 
     * @param matrix
     * @param format 图片格式
     * @param file 生成二维码图片位置
     * @throws IOException
     */
    public static void writeToOutputStream(BitMatrix matrix, String format, OutputStream outputStream) throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        ImageIO.write(image, format, outputStream);
    }

    /**
     * 生成二维码内容<br>
     * 
     * @param matrix
     * @return
     */
    public static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        //创建一张bitmap图片，采用图片效果TYPE_INT_ARGB  
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) == true ? BLACK : WHITE);
            }
        }
        return image;
    }

    /**
     * 解析QRCode二维码
     */
    @SuppressWarnings("unchecked")
    public static void decode(File file) {
        try {
            BufferedImage image;
            try {
                image = ImageIO.read(file);
                if (image == null) {
                    System.out.println("Could not decode image");
                }
                LuminanceSource source = new BufferedImageLuminanceSource(image);
                BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                Result result;
                @SuppressWarnings("rawtypes")
                Hashtable hints = new Hashtable();
                //解码设置编码方式为：utf-8
                hints.put(DecodeHintType.CHARACTER_SET, "utf-8");
                result = new MultiFormatReader().decode(bitmap, hints);
                String resultStr = result.getText();
                System.out.println("解析后内容：" + resultStr);
            } catch (IOException ioe) {
                System.out.println(ioe.toString());
            } catch (ReaderException re) {
                System.out.println(re.toString());
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }
    
    
    /**
     * @param args
     * @throws UnsupportedEncodingException 
     */
    public static void main(String[] args) throws UnsupportedEncodingException {
    	QRCodeUtil test = new QRCodeUtil();
        File file = new File("d:/test.png");
        File file1 = new File("d:/test1.png");
        String contents = "你好我是庄濮向";
        // 如果不用更改源码，将字符串转换成ISO-8859-1编码  
        contents = new String(contents.getBytes("UTF-8"), "ISO-8859-1");
        test.encode(contents, file, BarcodeFormat.QR_CODE, 1280, 720, null);
        
        test.encode("http://www.baidu.com", file1, BarcodeFormat.QR_CODE, 1280, 720, null);
        
        test.decode(file);
    }
}
