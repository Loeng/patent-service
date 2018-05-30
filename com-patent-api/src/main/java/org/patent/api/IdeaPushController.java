package org.patent.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.patent.entity.AcountEntity;
import org.patent.entity.IdeaEntity;
import org.patent.oss.OSSFactory;
import org.patent.service.AcountService;
import org.patent.service.IdeaPushService;
import org.patent.utils.ApiRRException;
import org.patent.utils.ApiResult;
import org.patent.utils.ApiResultCode;
import org.patent.validator.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;


/**
 * 需求发布的相关接口
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/api")
public class IdeaPushController {
	private static final Logger logger = LoggerFactory.getLogger(IdeaPushController.class);

	@Autowired
	IdeaPushService ideaPushService;
	
	@Autowired
	AcountService acountService;

	private int randmonNumber;

	/**
	 * 插入一条信发布的需求
	 * @param request
	 * @return
	 */
	@RequestMapping("/insertNewIdeas")
	public ApiResult insertIdeaPush(HttpServletRequest request) {
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String acountId = request.getParameter("acountId");
		String ideaImgUrl = request.getParameter("ideaImageUrl");
		String fileUrl = request.getParameter("fileUrl");

		Assert.isBlank(acountId, ApiResultCode.ACCOUNTID_IS_EMPTY, ApiResultCode.ADMINID_IS_EMPTY_CODE);
		Assert.isBlank(title, ApiResultCode.ACCOUNTID_IS_EMPTY, ApiResultCode.ADMINID_IS_EMPTY_CODE);
		Assert.isBlank(content, ApiResultCode.ACCOUNTID_IS_EMPTY, ApiResultCode.ACOUNT_INPUT_ERROR_CODE);

		IdeaEntity ideaEntity = new IdeaEntity();
		ideaEntity.setAcountId(Long.valueOf(acountId));
		ideaEntity.setTitle(title);
		ideaEntity.setContent(content);
		if (StringUtils.isNotBlank(fileUrl)) {
			ideaEntity.setIdeaImage(ideaImgUrl);
		}
		if (StringUtils.isNotBlank(fileUrl)) {
			ideaEntity.setIdeaFile(fileUrl);
		}
		ideaEntity.setCreateTime(new Date());
		ideaPushService.insertNewIdea(ideaEntity);

		logger.info("插入需求信息成功,账户id:{};接口名:{}", new Object[] {acountId, "insertNewIdeas"});
		return ApiResult.R();
	}

	/**
	 * 上传文件
	 * @param fileFiles
	 * @param request
	 * @return
	 */
	@RequestMapping("/uploadImages")
	public ApiResult uploadImages(@RequestParam("File") CommonsMultipartFile[] fileFiles,
			HttpServletRequest request) {
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String acountId = request.getParameter("acountId");
		String allUpload = request.getParameter("all");

		Assert.isBlank(acountId, ApiResultCode.ACCOUNTID_IS_EMPTY, ApiResultCode.ADMINID_IS_EMPTY_CODE);
		Assert.isBlank(title, ApiResultCode.ACCOUNTID_IS_EMPTY, ApiResultCode.ADMINID_IS_EMPTY_CODE);
		Assert.isBlank(content, ApiResultCode.ACCOUNTID_IS_EMPTY, ApiResultCode.ACOUNT_INPUT_ERROR_CODE);

		if (fileFiles == null) {
			throw new ApiRRException(ApiResultCode.FILE_IS_EMPTY, ApiResultCode.FILE_INPUT_ERROR_CODE);
		}
		StringBuffer sBuffer = new StringBuffer();
		StringBuffer sBuffer2 = new StringBuffer();
		IdeaEntity ideaEntity = new IdeaEntity();
		ideaEntity.setAcountId(Long.valueOf(acountId));
		ideaEntity.setTitle(title);
		ideaEntity.setContent(content);
		if (allUpload.equals("1")) {//两者都传了
			int imageSize = Integer.valueOf(request.getParameter("imageSize"));
			for (int i = 0; i < imageSize; i++) {
				String suffix = fileFiles[i]
						.getOriginalFilename()
						.substring(fileFiles[i]
								.getOriginalFilename()
								.lastIndexOf(".")); 
				randmonNumber = (int) (Math.random()*1000);
				String url = OSSFactory.build().upload(fileFiles[i].getBytes(), "image"+randmonNumber+suffix);
				System.out.println("url----------->"+url);
				if (i == (imageSize-1)) {
					sBuffer.append(url);
				}else {
					sBuffer.append(url+",");
				}
			}
			ideaEntity.setIdeaImage(sBuffer.toString());

			for (int i = imageSize; i < fileFiles.length; i++) {
				String suffix = fileFiles[i]
						.getOriginalFilename()
						.substring(fileFiles[i]
								.getOriginalFilename()
								.lastIndexOf(".")); 
				randmonNumber = (int) (Math.random()*1000);
				String url = OSSFactory.build().upload(fileFiles[i].getBytes(), "file"+randmonNumber+suffix);
				System.out.println("fileurl----------->"+url);
				if (i == (fileFiles.length-1)) {
					sBuffer2.append(url);
				}else {
					sBuffer2.append(url+",");
				}
			}
			ideaEntity.setIdeaFile(sBuffer2.toString());
			ideaEntity.setCreateTime(new Date());
			ideaPushService.insertNewIdea(ideaEntity);
			return ApiResult.R();
		}else {
			uploadImageOnly(fileFiles,sBuffer,ideaEntity);
			return ApiResult.R();
		}
	}


	private void uploadImageOnly(CommonsMultipartFile[] fileFiles, StringBuffer sBuffer, IdeaEntity ideaEntity) {
		StringBuffer sBuffer2 = new StringBuffer();
		String houzui = "";
		for (int i = 0; i < fileFiles.length; i++) {
			randmonNumber = (int) (Math.random()*1000);
			String suffix = fileFiles[i]
					.getOriginalFilename()
					.substring(fileFiles[i]
							.getOriginalFilename()
							.lastIndexOf(".")); 
			houzui = suffix;
			if (".GIF".endsWith(suffix.toUpperCase()) || ".PNG".equals(suffix.toUpperCase()) || ".JPG".equals(suffix.toUpperCase())
					|| ".BMP".equals(suffix.toUpperCase())) {
				String url = OSSFactory.build().upload(fileFiles[i].getBytes(), "image"+randmonNumber+suffix);
				System.out.println("url----------->"+url);
				if (i == (fileFiles.length-1)) {
					sBuffer.append(url);
				}else {
					sBuffer.append(url+",");
				}
			}else {
				String url = OSSFactory.build().upload(fileFiles[i].getBytes(), "file"+randmonNumber+suffix);
				System.out.println("fileurl----------->"+url);
				if (i == (fileFiles.length-1)) {
					sBuffer2.append(url);
				}else {
					sBuffer2.append(url+",");
				}
			}
		}
		if (".GIF".endsWith(houzui.toUpperCase()) || ".PNG".equals(houzui.toUpperCase()) || ".JPG".equals(houzui.toUpperCase())
				|| ".BMP".equals(houzui.toUpperCase())) {
			ideaEntity.setIdeaImage(sBuffer.toString());
		}else {
			ideaEntity.setIdeaFile(sBuffer2.toString());
		}

		ideaEntity.setCreateTime(new Date());
		ideaPushService.insertNewIdea(ideaEntity);
	}


	@RequestMapping("/queryProfessByKeyWords")
	private ApiResult queryProfessByKeywords(HttpServletRequest request) {
		String keywords = request.getParameter("keyWords");
		Assert.isBlank(keywords, ApiResultCode.KEY_WORDS_EMPTY, ApiResultCode.KEY_WORDS_EMPTY_CODE);
		List<AcountEntity> resultList = new ArrayList<>();
		resultList = acountService.queryList(keywords);
		HashMap<String, Object> resultMap = new HashMap<>();
		List<HashMap<String, Object>> hashList= new ArrayList<>();
		if (resultList != null && resultList.size()>0) {
			for(int i = 0;i<resultList.size();i++) {
				AcountEntity acountEntity2 = resultList.get(i);
				HashMap<String, Object> hashMap = new HashMap<>();
				hashMap.put("imgUrl", acountEntity2.getImgUrl());
				hashMap.put("nickName", acountEntity2.getNickName());
				hashMap.put("job",acountEntity2.getJob());
				hashMap.put("workExprience",acountEntity2.getWorkExprience());
				hashMap.put("goodAt", acountEntity2.getGoodAt());
				hashMap.put("infomation", acountEntity2.getInfomation());
				hashMap.put("acountName", acountEntity2.getAcountName());
				hashList.add(hashMap);
			}
		}
		resultMap.put("userInfoList", hashList);
		return ApiResult.R().setResult(resultMap);
	}
}
