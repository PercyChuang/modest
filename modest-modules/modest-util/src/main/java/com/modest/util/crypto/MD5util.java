package com.modest.util.crypto;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * md5摘要
 */
public class MD5util
{
	/**
	 * 生成字符串摘要
	 *
	 * @return
	 * String MD5生成的摘要
	 */
	public static String md5(final String str)
	{

		if (str == null)
		{
			return null;
		}

		MessageDigest messageDigest = null;

		try
		{
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(str.getBytes("UTF-8"));
		}
		catch (NoSuchAlgorithmException e)
		{
			return str;
		}
		catch (UnsupportedEncodingException e)
		{
			return str;
		}

		byte[] byteArray = messageDigest.digest();

		StringBuffer md5StrBuff = new StringBuffer();

		for (byte element : byteArray)
		{
			if (Integer.toHexString(0xFF & element).length() == 1)
			{
				md5StrBuff.append("0").append(Integer.toHexString(0xFF & element));
			}
			else
			{
				md5StrBuff.append(Integer.toHexString(0xFF & element));
			}
		}

		return md5StrBuff.toString();
	}

    /**
     *  宝付支付加签
     * @param str
     * @return
     */
    public static String getMD5ofStr(String str){

        return MD5util.getMD5ofStr(str, "utf-8");
    }

    public static String getMD5ofStr(String str, String encode) {
        try{
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(str.getBytes(encode));
            byte[] digest = md5.digest();

            StringBuffer hexString = new StringBuffer();
            String strTemp;
            for (int i = 0; i < digest.length; i++) {
                // byteVar &
                // 0x000000FF的作用是，如果digest[i]是负数，则会清除前面24个零，正的byte整型不受影响。
                // (...) | 0xFFFFFF00的作用是，如果digest[i]是正数，则置前24位为一，
                // 这样toHexString输出一个小于等于15的byte整型的十六进制时，倒数第二位为零且不会被丢弃，这样可以通过substring方法进行截取最后两位即可。
                strTemp = Integer.toHexString(
                        (digest[i] & 0x000000FF) | 0xFFFFFF00).substring(6);
                hexString.append(strTemp);
            }
            return hexString.toString();
        }catch(Exception e){
            e.printStackTrace();
            return "";
        }

    }



    public static void main(String[] arg) {
        String pwd = "123456";
        System.out.println(md5(pwd));

        //System.out.println(Integer.toHexString(0xFF & 0));
    }

}
