package com.archer.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class IOUtils {
	//���췽��˽�л�
		private IOUtils(){
			
		}
		public static void InStreamToOutStream(InputStream in,OutputStream out) throws IOException{
			int len;
			byte[] b=new byte[1024];
			while((len=in.read(b))!=-1){
				out.write(b, 0, len);
			}
		}
		
}
