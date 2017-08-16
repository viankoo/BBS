package com.guwei.tools;

import org.apache.commons.io.FilenameUtils;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.core.io.ClassPathResource;

/**
 * FastDFS 工具类
 * 
 * @author vian
 *
 */
public class FastDFSTool {

	public static String uploadFile(byte[] bytes, String originalFilename) {
		//获取src下fdfs_client.conf的上下文绝对路径
		ClassPathResource resource = new ClassPathResource("fdfs_client.conf");
		try {
			//初始化客户端
			ClientGlobal.init(resource.getClassLoader().getResource("fdfs_client.conf").getPath());
			//创建trackerClient追踪客户端
			TrackerClient trackerClient = new TrackerClient();
			//从客户端获取trackerServer追踪服务端
			TrackerServer trackerServer = trackerClient.getConnection();
			//创建storageClient存储客户端
			StorageClient1 storageClient1 = new StorageClient1(trackerServer, null);
			//通过存储客户端上传文件，返回文件路径
			String upload_file1 = storageClient1.upload_file1(bytes, FilenameUtils.getExtension(originalFilename), null);
			return upload_file1;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
