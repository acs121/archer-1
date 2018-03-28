package com.archer.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class IOUtils {
	//构造方法私有化
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
