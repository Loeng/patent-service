package org.patent.oss;


import org.patent.service.SysConfigService;
import org.patent.utils.ConfigConstant;
import org.patent.utils.Constant;
import org.patent.utils.SpringContextUtils;

/**
 * 文件上传Factory
 * 
 */
public final class OSSFactory {
	private static SysConfigService sysConfigService;

	static {
		OSSFactory.sysConfigService = (SysConfigService) SpringContextUtils.getBean("sysConfigService");
	}

	public static CloudStorageService build() {
		// 获取云存储配置信息
		CloudStorageConfig config = sysConfigService.getConfigObject(ConfigConstant.CLOUD_STORAGE_CONFIG_KEY, CloudStorageConfig.class);
			return new QiniuCloudStorageService(config);
	}

}
