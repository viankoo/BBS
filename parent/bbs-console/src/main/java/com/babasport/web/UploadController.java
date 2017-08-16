package com.babasport.web;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.guwei.tools.Constants;
import com.guwei.tools.FastDFSTool;

/**
 * 文件上传  控制器
 * @author vian
 *
 */
@Controller
public class UploadController {
	// 上传文件
	@RequestMapping(value = "/uploadFile.do")
	@ResponseBody
	public HashMap<String, String> uploadFile(MultipartFile mpf)
			throws FileNotFoundException, IOException, Exception {

		System.out.println(mpf.getOriginalFilename());

		// 将文件上传到分布式文件系统，并返回文件的存储路径及名称
		String uploadFile = FastDFSTool.uploadFile(mpf.getBytes(),
				mpf.getOriginalFilename());
		// 返回json格式的字符串（图片的绝对网络存放地址）
		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("path", Constants.FDFS_SERVER + uploadFile);
		return hashMap;
	}
}
