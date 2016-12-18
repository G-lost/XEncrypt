package com.xencrypt.headfile.xencryptAlgorithm;

import java.io.IOException;

public class Formatcode {
	public static Byte imagecode(String filename) throws IOException
	{
		String string=ImgJudge.judge(filename);
		byte i = 0;
		if(string=="JPG")
			i=(byte)0x01;
		else if(string=="BMP")
			i=(byte)0x02;
		else if(string=="PNG")
			i=(byte)0x03;
		else if(string=="GIF")
			i=(byte)0x04;
		else if(string=="GIF")
			i=(byte)0x05;
		return i;
	}
	
	public static String imagedecode(byte i)
	{
		String string = null;
		switch(i)
		{
		case (byte)0x01: string="JPG"; break;
		case (byte)0x02: string="BMP"; break;
		case (byte)0x03: string="PNG"; break;
		case (byte)0x04: string="GIF"; break;
		case (byte)0x05: string="TIFF"; break;
		}
		return string;
	}
	
	

}
