package com.guwei.tools;

import java.io.UnsupportedEncodingException;

/**
 * 解决get请求中文乱码问题
 * @author vian
 *
 */
public class Encoding {

	public static String encodeGetRequest(String str){ 
		if (str!=null) {
			try {
				str = new String(str.getBytes("iso-8859-1"),"utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return str;
	}
}
