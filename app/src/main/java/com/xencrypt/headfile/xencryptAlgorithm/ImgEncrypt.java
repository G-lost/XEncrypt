package com.xencrypt.headfile.xencryptAlgorithm;

import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Key;
import java.util.Arrays;


public class ImgEncrypt {
	public static void imgEncrypt(String Filename,String Key) throws IOException
	{
		File file=new File(Key);//��ȡ˽Կ
		FileInputStream Fin=new FileInputStream(file);
		ByteArrayOutputStream Bout=new ByteArrayOutputStream(1024);  
		int size=0;  
		byte[] temp=new byte[1024];
        while((size=Fin.read(temp))!=-1)  
        {  
            Bout.write(temp,0,size);  
        }            
        Fin.close();            
        byte[] key=Bout.toByteArray();//˽ԿΪ16λbyte����  
        //System.out.println("bytes size got is:"+key.length);  
		//byte[] key={0x01,0x23,0x45,0x67,(byte) 0x89,(byte) 0xab,(byte) 0xcd,(byte) 0xef,(byte) 0xfe,(byte) 0xdc,(byte) 0xba,(byte) 0x98,0x76,0x54,0x32,0x10};
		//Din.read(key);
		//File file2=new File("D:\\key2.dat");
		//FileOutputStream fileOutputStream=new FileOutputStream(file2);
		//fileOutputStream.write(key,0,key.length);
		//System.out.println(key[0]);
//		BufferedImage image=ImageIO.read(new File(Filename));//��ͼƬת��Ϊbyte����
//		ByteArrayOutputStream out=new ByteArrayOutputStream();
//		ImageIO.write(image,ImgJudge.judge(Filename), out);
//		byte[] b=out.toByteArray();
        File file2=new File(Filename);
		FileInputStream Fin2=new FileInputStream(file2);
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
		int surplus=L%16;//byte���鳤��Ϊ16��������
		//System.out.println(surplus);
		//System.out.println(L);
		byte[] plaintext;
		if(surplus!=0)
		{
			int  supple=16-surplus;
			plaintext=new byte[supple+L];
			//System.out.println(plaintext.length);
			for(int i=L;i<L+supple-1;i++)
				plaintext[i]=(byte)0x00;
			plaintext[supple+L-1]=Formatcode.imagecode(Filename);
			System.arraycopy(b, 0, plaintext, 0, L);
			//plaintext=Arrays.copyOfRange(b, 0, L);
			//System.out.println(plaintext.length);		
		}
		else
		{
			plaintext=new byte[L+16];
			for(int i=L;i<L+15;i++)
				plaintext[i]=(byte)0x00;
			plaintext[L+15]=Formatcode.imagecode(Filename);
			plaintext=Arrays.copyOf(b, L+16);
		}
		//Arrays.copyOfRange(original, start, end)
		int L2=plaintext.length;
		int num=L2/16;
		byte[] ciphertext=new byte[plaintext.length];
		SMS4 sms4=new SMS4();
		for(int i=0;i<num;i++)//��byte�����Ϊ16�ֽ����μ���ֱ���ļ�����
		{
			buffer1=Arrays.copyOfRange(plaintext, i*16, i*16+16);
			sms4.sms4(buffer1, 16, key, buffer2, 1);
			System.arraycopy(buffer2, 0, ciphertext, i*16, 16);
		}
		FileOutputStream Fout=new FileOutputStream(Environment.getExternalStorageDirectory().getPath()+"/decrypt.dat");
		Fout.write(ciphertext);
	}
}
