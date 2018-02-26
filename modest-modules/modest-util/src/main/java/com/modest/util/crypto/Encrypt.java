/*
 * Copyright (c) 2014 hytz365, Inc. All rights reserved.
 *
 * @author lichunlin https://github.com/springlin2012
 *
 */
package com.modest.util.crypto;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.security.Key;
import java.security.SecureRandom;

/**
 * DES加密
 *
 * @author lcl 2014/12/19.
 * @version 1.0.0
 */
public class Encrypt {
    /**
     * 秘钥
     */
    private static String strKey;

    /**
     * 生成秘钥串
     */
    private static Key key;


    /**
     * 获得加密串
     * @param str
     * @return
     */
    public static String getEncryptStr(String... str) {
        String encryptStr = "";
        for(String s:str) {
            encryptStr += s+"|";
        }

        return encString(encryptStr.substring(0, encryptStr.length()-1)).replace("+", "\\|");
    }

    /**
     * 解密分解串
     * @param encryStr
     * @return
     */
    public static String[] getDecodeStr(String encryStr) {
        return decodeString(encryStr.replace("\\|", "+")).split("\\|");
    }


    //根据参数生成KEY
    public static void generateKey(){
        try{
            KeyGenerator _generator = KeyGenerator.getInstance("DES");
            _generator.init(new SecureRandom(strKey.getBytes()));
            key = _generator.generateKey();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //加密String明文输入, String密文输出
    public static String encString(String strMing){
        BASE64Encoder base64en = new BASE64Encoder();
        try {
            return base64en.encode(getEncCode(strMing.getBytes("UTF-8")));
        }catch(Exception e){
           e.printStackTrace();
        }

        return null;
    }

    //加密以byte[]明文输入,byte[]密文输出
    private static byte[] getEncCode(byte[] byteS){
        if (null == key)
            generateKey();

        byte[] byteFina = null;
        Cipher cipher;
        try{
            cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byteFina = cipher.doFinal(byteS);
        }catch(Exception e){
           e.printStackTrace();
        }finally{
            cipher = null;
        }

        return byteFina;
    }

    // 解密:以String密文输入,String明文输出
    public static String decodeString(String strMi){
        BASE64Decoder base64De = new BASE64Decoder();
        try {
            return new String(getDesCode(base64De.decodeBuffer(strMi)), "UTF-8");
        }catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }

    // 解密以byte[]密文输入,以byte[]明文输出
    private static byte[] getDesCode(byte[] byteD){
        if (null == key)
            generateKey();

        Cipher cipher;
        byte[] byteFina=null;
        try{
            cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byteFina = cipher.doFinal(byteD);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            cipher = null;
        }

        return byteFina;
    }



    public void setStrKey(String strKey) {
        this.strKey = strKey;
    }

    public static void main(String[] args) {
        Encrypt encrypt = new Encrypt();
        encrypt.setStrKey("qweqweqweqweqwe");

        String encryptStr = encrypt.encString("10100539");
        System.out.println("encrypt: "+ encryptStr);

        System.out.println("decode: "+ encrypt.decodeString("jk79f1GgKu8FKvNg1khJaA=="));
    }


}