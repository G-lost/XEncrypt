package com.xencrypt.headfile.xencryptAlgorithm;


import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import static android.R.attr.key;

public class ImgDecrypt {
	public static void imgDecrypt(String Filename,String Key) throws IOException
	{
		File file2=new File(Key);//
		FileInputStream Fin=new FileInputStream(file2);
		ByteArrayOutputStream Bout=new ByteArrayOutputStream(1024);  
		int size=0;  
		byte[] temp=new byte[1024];
        while((size=Fin.read(temp))!=-1)  
        {  
            Bout.write(temp,0,size);  
        }            
        Fin.close();            
        byte[] key=Bout.toByteArray();//˽ԿΪ16λbyte����
		SMS4 sms4=new SMS4();
		byte[] buffer1=new byte[16];
		byte[] buffer2=new byte[16];		
		File file=new File(Filename);
		FileInputStream Fin2=new FileInputStream(file);
		ByteArrayOutputStream Bout2=new ByteArrayOutputStream(1024);  
		size=0;  
		byte[] temp2=new byte[1024];
        while((size=Fin2.read(temp2))!=-1)  
        {  
            Bout2.write(temp2,0,size);  
        }
        byte[] b=Bout2.toByteArray();
        byte[] plaintext2=new byte[b.length];
        int num=b.length/16;//�������ļ���Ϊ16�ֽ�һ�����ν���
		for(int i=0;i<num;i++)
		{
			buffer1=Arrays.copyOfRange(b, i*16,i*16+16);
			sms4.sms4(buffer1, 16, key, buffer2, 0);
			System.arraycopy(buffer2, 0, plaintext2, i*16, 16);
		}
		String format=Formatcode.imagedecode(plaintext2[b.length-1]);
		String imagename= Environment.getExternalStorageDirectory().getPath()+"/result."+format;
//		ByteArrayInputStream in=new ByteArrayInputStream(plaintext2);
//		BufferedImage image1 = ImageIO.read(in);
//		ImageIO.write(image1, format, new File(imagename));//�����ļ�д��imagename
		File file3=new File(imagename);
		FileOutputStream Fout3 = new FileOutputStream(file3);
		Fout3.write(plaintext2,0,plaintext2.length);


	}
}
