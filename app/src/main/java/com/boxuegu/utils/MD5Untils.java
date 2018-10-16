package com.boxuegu.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Administrator on 2018/9/6 0006.
 */

public class MD5Untils {
    public static String getMD5String(String text) {
        MessageDigest messageDigest = null;
        try {
            messageDigest=MessageDigest.getInstance("MD5");
            byte[] bytes=messageDigest.digest(text.getBytes());
            StringBuffer stringBuffer=new StringBuffer();
            for (byte simgleByte : bytes){
                int i=simgleByte & 0xff;
                String hex=Integer.toHexString(i);
                if (hex.length()==1){
                    stringBuffer.append("0"+hex);
                }else {
                    stringBuffer.append(hex);
                }
            }
            return stringBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
