package org.easemob.server.api;

/**
 * 文件上传下载接口
 * @author Administrator
 *
 */

public interface FileAPI {
	
	/**
	 * 
	 * @param file 上传的文件对象可以是地址，流等
	 * @return
	 */
	Object uploadFile(Object file);

	/**
	 * 
	 * @param fileUUID     文件唯一标识，从上传Response-entities-uuid中获取
	 * @param shareScreect 文件访问秘钥，从上传Response-entities-share-secret中获取
	 * @param isThumbnail  如果下载图片，是否为缩略图
	 * @return
	 */
	Object downloadFile(String fileUUID,String shareScreect,Boolean isThumbnail);
}
