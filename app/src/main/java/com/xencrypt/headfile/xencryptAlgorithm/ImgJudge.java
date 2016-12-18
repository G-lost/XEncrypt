package com.xencrypt.headfile.xencryptAlgorithm;

import java.io.FileInputStream;
import java.io.IOException;

public class ImgJudge {
	/**
	  * byte����ת����16�����ַ���
	  * @param src
	  * @return
	  */
	 public static String bytesToHexString(byte[] src){      
	        StringBuilder stringBuilder = new StringBuilder();      
	        if (src == null || src.length <= 0) {      
	            return null;      
	        }      
	        for (int i = 0; i < src.length; i++) {      
	            int v = src[i] & 0xFF;      
	            String hv = Integer.toHexString(v);      
	            if (hv.length() < 2) {      
	                stringBuilder.append(0);      
	            }      
	            stringBuilder.append(hv);      
	        }      
	        return stringBuilder.toString();      
	    } 
	 
	 /**
	  * �����ļ�����ȡͼƬ�ļ���ʵ����
	  * @param is
	  * @return
	  */
	 public static String getTypeByStream(FileInputStream is){
	     byte[] b = new byte[4];   
	        try {
	   is.read(b, 0, b.length);
	  } catch (IOException e) {
	   e.printStackTrace();
	  }
	        String type = bytesToHexString(b).toUpperCase();
	        if(type.contains("FFD8FF")){
	         return "JPG";
	        }else if(type.contains("89504E47")){
	         return "PNG";
	        }else if(type.contains("47494638")){
	         return "GIF";
	        }else if(type.contains("49492A00")){
	         return "TIF";
	        }else if(type.contains("424D")){
	         return "BMP";
	        }
	        return type;
	    }
	public static String judge(String filename) throws IOException{
	     FileInputStream is = new FileInputStream(filename);   
	        String type = getTypeByStream(is);
	        return type;
	  /*
	   * JPEG (jpg)���ļ�ͷ��FFD8FF 
	PNG (png)���ļ�ͷ��89504E47 
	GIF (gif)���ļ�ͷ��47494638 
	TIFF (tif)���ļ�ͷ��49492A00  
	Windows Bitmap (bmp)���ļ�ͷ��424D
	   */
	    } 
}
