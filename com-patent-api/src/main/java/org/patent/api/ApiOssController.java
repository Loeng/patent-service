package org.patent.api;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.patent.entity.SysOssEntity;
import org.patent.oss.OSSFactory;
import org.patent.service.SysOssService;
import org.patent.utils.ApiRRException;
import org.patent.utils.ApiResult;
import org.patent.utils.ApiResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@RestController
@RequestMapping("/api")
public class ApiOssController {
	
	@Autowired
	private SysOssService sysOssService;
	
	@RequestMapping("/uploadFile")
	public ApiResult uploadFile(HttpServletRequest request) throws IOException {
		MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
		MultipartFile file = multipartHttpServletRequest.getFile("file");
		if (file == null) {
			throw new ApiRRException(ApiResultCode.FILE_SIZE_COVER,ApiResultCode.FILE_SIZE_COVER_CODE);
		}
		String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		if (".GIF".endsWith(suffix.toUpperCase()) || ".PNG".equals(suffix.toUpperCase()) || ".JPG".equals(suffix.toUpperCase())
		        || ".BMP".equals(suffix.toUpperCase())) {
			String url = OSSFactory.build().uploadAliyunBySuffix(file.getBytes(), suffix);
			SysOssEntity sysOssEntity = new SysOssEntity();
			sysOssEntity.setCreateDate(new Date());
			sysOssEntity.setUrl(url);
			sysOssService.save(sysOssEntity);
			return ApiResult.R().setResult(sysOssEntity);
		}else {
			throw new ApiRRException(ApiResultCode.FILE_FORMAT_ERROR,ApiResultCode.FILE_FORMAT_ERROR_CODE);
		}
	}

}
