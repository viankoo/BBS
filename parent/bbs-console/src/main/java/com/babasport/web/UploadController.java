package com.babasport.web;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.guwei.tools.Constants;
import com.guwei.tools.FastDFSTool;

/**
 * 文件上传  控制器
 * @author vian
 *
 */
@Controller
public class UploadController {
	// 上传单个文件
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
	
	// 上传多个文件
	@RequestMapping(value = "/uploadFiles.do")
	@ResponseBody
	public List<String> uploadFiles(@RequestParam MultipartFile[] mpfs)
			throws FileNotFoundException, IOException, Exception {
		
		System.out.println(mpfs.length);
		
		List<String> filePaths = new ArrayList<>();
		for (MultipartFile mpf : mpfs) {
			String uploadFile = FastDFSTool.uploadFile(mpf.getBytes(),
					mpf.getOriginalFilename());
			System.out.println(uploadFile);
			filePaths.add(Constants.FDFS_SERVER+uploadFile);
		}
		
		return filePaths;
	}
	
	
	// 富文本编辑器上传多个图片
	@RequestMapping(value = "/uploadFcks.do")
	@ResponseBody
	public Map<String,Object> uploadFcks(HttpServletRequest request,HttpServletResponse response)
			throws FileNotFoundException, IOException, Exception {
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		
		//将servletrequest转成spring提供的
		MultipartRequest mr = (MultipartRequest) request;
		
		MultiValueMap<String,MultipartFile> multiFileMap = mr.getMultiFileMap();
		for (Entry<String, List<MultipartFile>> en : multiFileMap.entrySet()) {
			List<MultipartFile> files = en.getValue();
			for (MultipartFile mpf : files) {
				String uploadFile = FastDFSTool.uploadFile(mpf.getBytes(),
						mpf.getOriginalFilename());
				//固定参数
				hashMap.put("error", 0);
				hashMap.put("url", Constants.FDFS_SERVER + uploadFile);
				return hashMap;
				
			}
		}
		return null;		
	}
}
