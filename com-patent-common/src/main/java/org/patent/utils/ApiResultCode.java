package org.patent.utils;

/**
 * API返回编码工具类型
 * 
 */
public class ApiResultCode {

  // 请求成功,无任何异常
  public final static int SUCCESS_CODE = 80000;
  public final static String SUCCESS_MSG = "请求成功，无任何异常";

  // 请求失败，异常代码
  public final static int FAILUE_CODE = 80001;
  public final static String FAILUE_MSG = "请求失败，未知异常";
  public final static int FAILUE_ERROR_CODE = 90000;

  // 公共部分异常
  public static int TOKEN_IS_EMPTY = 20001;
  public static String TOKEN_IS_EMPTY_MSG = "token为空";
  public final static int INPUT_ERROR_CODE = 90001;
  public final static String INPUT_ERROR = "入参类型异常";
  public final static int DATA_FORMART_ERROR_CODE = 90002;
  public final static String DATA_FORMART_ERROR = "日期格式错误";
  public final static int TIME_FORMART_ERROR_CODE = 90003;
  public final static String TIME_FORMART_ERROR = "时间格式错误";
  public final static int DATE_IS_EMPTY_CODE = 90004;
  public final static String DATE_IS_EMPTY = "时间不能为空";
  public final static int INPUT_IS_SPECIAL_CODE = 90005;
  public final static String INPUT_IS_SPECIAL = "不允许输入特殊字符";
  public final static int DEAL_FLAG_IS_EMPTY_CODE = 90006;
  public final static String DEAL_FLAG_IS_EMPTY_MSG = "处理状态不能为空";
  public final static int SUCCESS_NUM_IS_EMPTY_CODE = 90007;
  public final static String SUCCESS_NUM_IS_EMPTY_MSG = "下载成功次数不能为空";

  // 账户异常
  public final static int ACOUNT_INPUT_ERROR_CODE = 90101;
  public final static String ACCOUNTID_IS_EMPTY = "账户ID不允许为空";
  public final static int ADMINID_IS_EMPTY_CODE = 90102;
  public final static String ADMINID_IS_EMPTY = "移交的账户ID不能为空";
  public final static int ACCOUNTNAME_IS_EMPTY_CODE = 90103;
  public final static String ACCOUNTNAME_IS_EMPTY = "账户名不能为空";
  public final static int ACCOUNT_IS_EMPTY_CODE = 90104;
  public final static String ACCOUNT_IS_EMPTY = "账号不允许为空";
  public final static int NICKNAME_IS_EMPTY_CODE = 90105;
  public final static String NICKNAME_IS_EMPTY = "账户昵称不允许为空";
  public final static int GENDER_IS_EMPTY_CODE = 90106;
  public final static String GENDER_IS_EMPTY = "性别不允许为空";
  public final static int BIRTHDAY_IS_EMPTY_CODE = 90107;
  public final static String BIRTHDAY_IS_EMPTY = "生日不允许为空";
  public final static int PASSWORD_IS_EMPTY_CODE = 90108;
  public final static String PASSWORD_IS_EMPTY = "密码不允许为空";
  public final static int NEWPASSWORD_IS_EMPTY_CODE = 90109;
  public final static String NEWPASSWORD_IS_EMPTY = "新密码不允许为空";
  public final static int ACCOUNT_NOT_REGISTER_CODE = 90110;
  public final static String ACCOUNT_NOT_REGISTER = "账户未注册，请先注册";
  public final static int ACCOUNT_IS_REGISTER_CODE = 90111;
  public final static String ACCOUNT_IS_REGISTER = "账户已注册";
  public final static int BIRTHDAY_FORMAT_EMPTY_CODE = 90112;
  public final static String BIRTHDAY_FORMAT_EMPTY = "生日日期格式错误";
  public final static int OLDPASSWORD_IS_ERROR_CODE = 90113;
  public final static String OLDPASSWORD_IS_ERROR = "原始密码错误";
  public final static int OLD_NEW_PASSWORD_IS_SAME_CODE = 90114;
  public final static String OLD_NEW_PASSWORD_IS_SAME = "原始密码和新密码不允许相同";
  public final static int PASSWORD_IS_ERROR_CODE = 90115;
  public final static String PASSWORD_IS_ERROR = "密码错误，请重新登录";
  public final static int UPDATE_ACOUNT_FAILUE_CODE = 90116;
  public final static String UPDATE_ACOUNT_FAILUE = "修改账户信息失败";
  public final static int ACOUNT_TYPE_EMPTY_CODE = 90117;
  public final static String ACOUNT_TYPE_EMPTY = "账户类型为空";
  public final static int COLLECTION_IS_EMPTY_code = 90118;
  public final static String COLLECTION_IS_EMPTY = "收藏者账号为空";


  // 验证码异常
  public final static int MOBILE_IS_EMPTY_CODE = 90301;
  public final static String MOBILE_IS_EMPTY = "手机号码不允许为空";
  public final static int CHECKCODE_IS_EMPTY_CODE = 90302;
  public final static String CHECKCODE_IS_EMPTY = "验证码不允许为空";
  public final static int PHONE_IS_ERROR_CODE = 90303;
  public final static String PHONE_IS_ERROR = "号码格式错误:)";
  public final static int SEND_SMS_ERROR_CODE = 90304;
  public final static String SEND_SMS_ERROR = "发送短信验证码失败";
  public final static int CHECKCODE_IS_ERROR_CODE = 90305;
  public final static String CHECKCODE_IS_ERROR = "验证码错误";
  public final static int CHECKCODE_IS_INNALID_CODE = 90306;
  public final static String CHECKCODE_IS_INNALID = "验证码失效，请重新获取";

  // 环信异常
  public final static int IM_INPUT_ERROR_CODE = 90401;
  public final static String GROUPID_IS_EMPTY = "群组ID不允许为空";
  public final static int ADD_IMGROUP_ERROR_CODE = 90402;
  public final static String ADD_IMGROUP_ERROR = "环信创建群组失败";
  public final static int DELETE_IMGROUP_MEMBERS_FAILUE_CODE = 90403;
  public final static String DELETE_IMGROUP_MEMBERS_FAILUE = "删除环信群所有成员失败";
  public final static int DELETE_IMGROUP_MEMBER_FAILUE_CODE = 90404;
  public final static String DELETE_IMGROUP_MEMBER_FAILUE = "删除环信群成员失败";
  public final static int ADD_IMGROUP_MEMBER_FAILUE_CODE = 90405;
  public final static String ADD_IMGROUP_MEMBER_FAILUE = "添加环信群成员失败";
  public final static int IM_SEND_CMD_MSG_FAILUE_CODE = 90406;
  public final static String IM_SEND_CMD_MSG_FAILUE = "环信发送透传消息异常";
  public final static int CHANGE_IM_PASSWORD_FAILUE_CODE = 90407;
  public final static String CHANGE_IM_PASSWORD_FAILUE = "修改环信用户密码失败";
  public final static int IM_SEND_CMD_FAILUE_CODE = 90408;
  public final static String IM_SEND_CMD_FAILUE = "环信发送透传指令异常";
  public final static int ADD_IMUSER_ERROR_CODE = 90409;
  public final static String ADD_IMUSER_ERROR = "创建环信成员异常";
  public final static int MODIFY_IM_NICKNAME_ERROR_CODE = 90410;
  public final static String MODIFY_IM_NICKNAME_ERROR = "修改环信昵称异常";



  // 上传文件异常
  public final static int FILE_INPUT_ERROR_CODE = 90601;
  public final static String FILE_IS_EMPTY = "上传文件不允许为空";
  public final static int FILE_FORMAT_ERROR_CODE = 90602;
  public final static String FILE_FORMAT_ERROR = "只允许上传JPG,PNG,GIF,BMP格式文件";
  public final static int FILE_SIZE_COVER_CODE = 90603;
  public final static String FILE_SIZE_COVER = "上传文件最大为1MB";



  // token异常
  public final static int TOEKN_INPUT_ERROR_CODE = 90901;
  public final static String TOEKN_IS_EMPTY = "Token不允许为空";
  public final static int TOEKN_IS_ERROR_CODE = 90902;
  public final static String TOEKN_IS_ERROR = "账户信息失效，请重新登录";
  public final static int TOEKN_VERIFY_ERROR_CODE = 90903;
  public final static String TOEKN_VERIFY_ERROR = "请求成功，通信验证令牌失效";
  public final static int TOEKN_VERIFY_SUCCESS_CODE = 90904;
  public final static String TOEKN_VERIFY_SUCCESS = "请求成功，通信验证令牌有效";



  // Google异常
  public final static int GOOGLE_DATA_EXCEPTION_CODE = 91301;
  public final static String GOOGLE_DATA_EXCEPTION = "请求GOOGLE异常";

  // 版本异常
  public final static int VERSION_INPUT_ERROR_CODE = 91401;
  public final static String VERSION_IS_EMPTY = "外部版本号不允许为空";
  public final static int VERSION_IS_EMPTY_CODE = 91402;
  public final static String VERSION_IS_EMPTY_MSG = "版本号不允许为空";


  // 短信
  public final static int SMS_SEND_COUNT_CODE = 92001;
  public final static String SMS_SEND_COUNT_MSG = "短信通知发送次数已达最大上限";
  public final static int SMS_CODE_SEND_COUNT_CODE = 92002;
  public final static String SMS_CODE_SEND_COUNT_MSG = "短信验证码发送次数已达最大上限";
  public final static int GET_SMS_CMD_CODE = 92003;
  public final static String GET_SMS_CMD_MSG = "获取短信指令模板失败";
  public final static int SMS_FROM_CODE = 92004;
  public final static String SMS_FROM_MSG = "短信发送者不允许为空";
  public final static int SMS_CONTENT_CODE = 92005;
  public final static String SMS_CONTENT_MSG = "短信内容不允许为空";


  // 意见反馈异常
  public final static int OPINION_IS_EMPTY_CODE = 92201;
  public final static String OPINION_IS_EMPTY_MSG = "意见反馈的内容不能为空";
  public final static int KEY_WORDS_EMPTY_CODE= 92202;
  public final static String KEY_WORDS_EMPTY = "搜索关键词不能为空";



  // 历史消息
  public final static int MSGTYPE_IS_EMPTY = 94001;
  public final static String MSGTYPE_IS_EMPTY_MSG = "消息类型不允许为空";
  public final static int MSG_PAGE_IS_EMPTY = 94002;
  public final static String MSG_PAGE_IS_EMPTY_MSG = "页码不允许为空";
  public final static int MSG_LIMIT_IS_EMPTY = 94003;
  public final static String MSG_LIMIT_IS_EMPTY_MSG = "每页显示条数不允许为空";
  public final static int MSG_ID_IS_EMPTY = 94004;
  public final static String MSG_ID_IS_EMPTY_MSG = "消息ID不允许为空";


}
