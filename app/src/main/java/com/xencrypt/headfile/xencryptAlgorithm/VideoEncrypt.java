package com.xencrypt.headfile.xencryptAlgorithm;

import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class VideoEncrypt {
	public static void videoEncrypt(String Filename,String Key) throws IOException
	{
		File file2=new File(Key);
		FileInputStream Fin=new FileInputStream(file2);
		ByteArrayOutputStream Bout=new ByteArrayOutputStream(1024);  
		int size=0;  
		byte[] temp=new byte[1024];
        while((size=Fin.read(temp))!=-1)  
        {  
            Bout.write(temp,0,size);  
        }                     
        byte[] key=Bout.toByteArray();  
		File file=new File(Filename);
		FileInputStream Fin2=new FileInputStream(file);
		ByteArrayOutputStream Bout2=new ByteArrayOutputStream(1024);  
		int size2=0;  
		byte[] temp2=new byte[1024];
        while((size2=Fin2.read(temp2))!=-1)  
        {  
            Bout2.write(temp2,0,size2);  
        }                       
        byte[] b=Bout2.toByteArray();  
        byte[] buffer1=new byte[16];
		byte[] buffer2=new byte[16];
		int L=b.length;
		int surplus=L%16;
		byte[] plaintext;
		if(surplus!=0)
		{
			int  supple=16-surplus;
			plaintext=new byte[supple+L];
			System.out.println(plaintext.length);
			for(int i=L;i<L+supple;i++)
				plaintext[i]=(byte)0x00;
			System.arraycopy(b, 0, plaintext, 0, L);	
		}
		else
		{
			plaintext=new byte[L];
			plaintext=Arrays.copyOf(b, L);
		}
		int L2=plaintext.length;
		int num=L2/16;
		byte[] ciphertext=new byte[plaintext.length];
		SMS4 sms4=new SMS4();
		for(int i=0;i<num;i++)
		{
			buffer1=Arrays.copyOfRange(plaintext, i*16, i*16+16);
			sms4.sms4(buffer1, 16, key, buffer2, 1);
			System.arraycopy(buffer2, 0, ciphertext, i*16, 16);
		}
		FileOutputStream Fout=new FileOutputStream(Environment.getExternalStorageDirectory().getPath()+"/videodecrypt.dat");
		Fout.write(ciphertext);
	}
}
