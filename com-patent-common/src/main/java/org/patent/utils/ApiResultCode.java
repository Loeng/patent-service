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

  // 设备异常
  public final static int IMEI_INPUT_ERROR_CODE = 90201;
  public final static String IMEI_IS_EMPTY = "IMEI不允许为空";
  public final static int IMEI_IS_ERROR_CODE = 90202;
  public final static String IMEI_IS_ERROR = "IMEI格式错误";
  public final static int DEVICEID_IS_EMPTY_CODE = 90203;
  public final static String DEVICEID_IS_EMPTY = "设备ID不允许为空";
  public final static int DEVICENAME_IS_EMPTY_CODE = 90204;
  public final static String DEVICENAME_IS_EMPTY = "产品名称不允许为空";
  public final static int DEVICETYPE_IS_EMPTY_CODE = 90205;
  public final static String DEVICETYPE_IS_EMPTY = "产品型号不允许为空";
  public final static int SOFTVERSION_IS_EMPTY_CODE = 90206;
  public final static String SOFTVERSION_IS_EMPTY = "软件版本不允许为空";
  public final static int HARDVERSION_IS_EMPTY_CODE = 90207;
  public final static String HARDVERSION_IS_EMPTY = "硬件版本不允许为空";
  public final static int DEVICE_NOT_REGISTER_CODE = 90208;
  public final static String DEVICE_NOT_REGISTER = "设备未注册，请先注册";
  public final static int DEVICE_IS_REGISTER_CODE = 90209;
  public final static String DEVICE_IS_REGISTER = "设备已注册";
  public final static int UPDATE_DEVICE_FAILUE_CODE = 90210;
  public final static String UPDATE_DEVICE_FAILUE = "修改设备信息失败";
  public final static int DEVICE_OFFLINE_CODE = 90211;
  public final static String DEVICE_OFFLINE_CODE_MSG = "设备不在线，请稍后尝试";
  public final static int DEVICE_PHONE_ERROR_CODE = 90212;
  public final static String DEVICE_PHONE_ERROR_MSG = "设备号码错误，请修改";
  public final static int DEVICE_VERSION_IS_EMPTY_CODE = 90213;
  public final static String DEVICE_VERSION_IS_EMPTY_MSG = "设备是否有更新版本信息不能为空";

  // 验证码异常
  public final static int CHECKCODE_INPUT_ERROR_CODE = 90301;
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


  // 解绑异常
  public final static int BAND_INPUT_ERROR_CODE = 90501;
  public final static String RELATION_IS_EMPTY = "绑定关系不允许为空";
  public final static int IMEI_HAS_BIND_ADMIN_CODE = 90502;
  public final static String IMEI_HAS_BIND_ADMIN = "该设备已绑定管理员";
  public final static int IMEI_NOTHAS_BIND_ADMIN_CODE = 90503;
  public final static String IMEI_NOTHAS_BIND_ADMIN = "该设备未绑定管理员，请先绑定管理员";
  public final static int UNBIND_FAILUE_CODE = 90504;
  public final static String UNBIND_FAILUE = "解绑失败";
  public final static int ACCOUNTNAME_IS_BINDED_CODE = 90505;
  public final static String ACCOUNTNAME_IS_BINDED = "该账户已与该设备绑定";
  public final static int APPLY_BINDED_ERROR_CODE = 90506;
  public final static String APPLY_BINDED_ERROR_MSG = "申请绑定失败";
  public final static int APPLY_STATUS_ERROR_CODE = 90507;
  public final static String APPLY_STATUS_ERROR_MSG = "审核结果不允许为空";
  public final static int APPLY_DATA_ERROR_CODE = 90508;
  public final static String APPLY_DATA_ERROR_MSG = "用户未申请绑定";

  // 上传文件异常
  public final static int FILE_INPUT_ERROR_CODE = 90601;
  public final static String FILE_IS_EMPTY = "上传文件不允许为空";
  public final static int FILE_FORMAT_ERROR_CODE = 90602;
  public final static String FILE_FORMAT_ERROR = "只允许上传JPG,PNG,GIF,BMP格式文件";
  public final static int FILE_SIZE_COVER_CODE = 90603;
  public final static String FILE_SIZE_COVER = "上传文件最大为1MB";

  // 设备上传定位信息异常
  public final static int LOCATION_INPUT_ERROR_CODE = 90701;
  public final static String LOCATION_TYPE_IS_EMPTY = "设备定位方式不能为空";
  public final static int LOCATION_TIME_IS_EMPTY_CODE = 90702;
  public final static String LOCATION_TIME_IS_EMPTY = "设备定位时间不能为空";
  public final static int LOCATION_TIME_FORMAT_ERROR_CODE = 90703;
  public final static String LOCATION_TIME_FORMAT_ERROR = "设备定位时间格式错误";
  public final static int LOCATION_DATA_NOT_JSON_CODE = 90704;
  public final static String LOCATION_DATA_NOT_JSON = "定位信息非JSON格式";
  public final static int LOCATION_DATA_IS_EMPTY_CODE = 90705;
  public final static String LOCATION_DATA_IS_EMPTY = "定位信息为空";
  public final static int LOCATIONDATE_IS_EMPTY_CODE = 90706;
  public final static String LOCATIONDATE_IS_EMPTY = "定位日期不允许为空";
  public final static int LOCATION_TIME_ERROR_CODE = 90707;
  public final static String LOCATION_TIME_ERROR = "设备定位时间错误";

  // 记步信息异常
  public final static int STEP_INPUT_ERROR_CODE = 90801;
  public final static String STEP_TIME_IS_EMPTY = "计步日期不能为空";
  public final static int STEP_TIME_FORMART_ERROR_CODE = 90802;
  public final static String STEP_TIME_FORMART_ERROR = "计步日期格式错误";
  public final static int STEP_STARTTIME_IS_EMPTY_CODE = 90803;
  public final static String STEP_STARTTIME_IS_EMPTY = "查询计步起始日期不能为空";
  public final static int STEP_ENDTIME_IS_EMPTY_CODE = 90804;
  public final static String STEP_ENDTIME_IS_EMPTY = "查询计步结束日期不能为空";
  public final static int STEPS_NUMBERS_CODE = 90805;
  public final static String STEPS_NUMBERS_IS_EMPTY = "计步步数不能为空";

  // token异常
  public final static int TOEKN_INPUT_ERROR_CODE = 90901;
  public final static String TOEKN_IS_EMPTY = "Token不允许为空";
  public final static int TOEKN_IS_ERROR_CODE = 90902;
  public final static String TOEKN_IS_ERROR = "账户信息失效，请重新登录";
  public final static int TOEKN_VERIFY_ERROR_CODE = 90903;
  public final static String TOEKN_VERIFY_ERROR = "请求成功，通信验证令牌失效";
  public final static int TOEKN_VERIFY_SUCCESS_CODE = 90904;
  public final static String TOEKN_VERIFY_SUCCESS = "请求成功，通信验证令牌有效";

  // 闹钟
  public final static int ALARMTIME_INPUT_ERROR_CODE = 91001;
  public final static String ALARMTIME_ID_IS_EMPTY = "闹钟id不能为空";
  public final static int ALARMTIME_IS_EMPTY_CODE = 91002;
  public final static String ALARMTIME_IS_EMPTY = "闹铃时间不能为空";
  public final static int ALARMTIME_CYCLE_IS_EMPTY_CODE = 91003;
  public final static String ALARMTIME_CYCLE_IS_EMPTY = "闹铃重复方式不能为空";
  public final static int ALARMTIME_ISACTIVE_IS_EMPTY_CODE = 91004;
  public final static String ALARMTIME_ISACTIVE_IS_EMPTY = "闹铃是否激活不能为空";
  public final static int ALARMTIME_FORMART_ERROR_CODE = 91005;
  public final static String ALARMTIME_FORMART_ERROR = "闹铃时间格式错误";

  // 围栏
  public final static int FENCE_INPUT_ERROR_CODE = 91101;
  public final static String FENCE_ID_IS_EMPTY = "围栏id不能为空";
  public final static int FENCE_LNG_IS_EMPTY_CODE = 91102;
  public final static String FENCE_LNG_IS_EMPTY = "围栏经度不能为空";
  public final static int FENCE_LAT_IS_EMPTY_CODE = 91103;
  public final static String FENCE_LAT_IS_EMPTY = "围栏纬度不能为空";
  public final static int FENCE_RADIUS_IS_EMPTY_CODE = 91104;
  public final static String FENCE_RADIUS_IS_EMPTY = "围栏半径不能为空";
  public final static int FENCE_TYPE_IS_EMPTY_CODE = 91105;
  public final static String FENCE_TYPE_IS_EMPTY = "围栏类型不能为空";
  public final static int FENCE_STATE_IS_EMPTY_CODE = 91106;
  public final static String FENCE_STATE_IS_EMPTY = "围栏状态不能为空";
  public final static int FENCE_STARTTIME_IS_EMPTY_CODE = 91107;
  public final static String FENCE_STARTTIME_IS_EMPTY = "围栏起始时间不能为空";
  public final static int FENCE_ENDTIME_IS_EMPTY_CODE = 91108;
  public final static String FENCE_ENDTIME_IS_EMPTY = "围栏结束时间不能为空";
  public final static int FENCE_ALARMTYPE_IS_EMPTY_CODE = 91109;
  public final static String FENCE_ALARMTYPE_IS_EMPTY = "围栏报警类型不能为空";
  public final static int FENCE_LNG_LAT_ERROR_CODE = 91110;
  public final static String FENCE_LNG_LAT_ERROR_MSG = "围栏经纬度输入错误";

  // 指令异常
  public final static int CMD_INPUT_ERROR_CODE = 91201;
  public final static String CMD_IS_EMPTY = "指令不能为空";
  public final static int CMD_MSG_IS_EMPTY_CODE = 91202;
  public final static String CMD_MSG_IS_EMPTY = "指令描述不能为空";
  public final static int PARAMETERS_NOT_JSON_CODE = 91203;
  public final static String PARAMETERS_NOT_JSON = "指令参数非json格式";

  // Google异常
  public final static int GOOGLE_DATA_EXCEPTION_CODE = 91301;
  public final static String GOOGLE_DATA_EXCEPTION = "请求GOOGLE异常";

  // 版本异常
  public final static int VERSION_INPUT_ERROR_CODE = 91401;
  public final static String VERSION_IS_EMPTY = "外部版本号不允许为空";
  public final static int VERSION_IS_EMPTY_CODE = 91402;
  public final static String VERSION_IS_EMPTY_MSG = "版本号不允许为空";

  // 设置定时关机/开机异常
  public final static int DEVICE_POWER_STATE_IS_EMPTY_CODE = 91501;
  public final static String DEVICE_POWER_STATE_IS_EMPTY = "开关机开启状态不能为空";
  public final static int DEVICE_POWER_ID_IS_EMPTY_CODE = 91502;
  public final static String DEVICE_POWER_ID_IS_EMPTY = "开关机数据ID不能为空";
  public final static int DEVICE_POWER_DATA_IS_EMPTY_CODE = 91503;
  public final static String DEVICE_POWER_DATA_IS_EMPTY = "开关机数据查询失败";

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

  // 上课禁用异常
  public final static int FORBIDDEN_TIME_CYCLE_CODE = 92101;
  public final static String FORBIDDEN_TIME_CYCLE_MSG = "上课禁用重复频率不能为空";
  public final static int FORBIDDEN_TIME_ID_CODE = 92102;
  public final static String FORBIDDEN_TIME_ID_MSG = "上课禁用ID不能为空";
  public final static int FORBIDDEN_TIME_STATE_CODE = 92103;
  public final static String FORBIDDEN_TIME_STATE_MSG = "上课禁用总状态不能为空";

  // 意见反馈异常
  public final static int OPINION_IS_EMPTY_CODE = 92201;
  public final static String OPINION_IS_EMPTY_MSG = "意见反馈的内容不能为空";

  // 强制关机异常
  public final static int FORCE_SHUT_DOWN_TOO_QUICK_CODE = 92301;
  public final static String FORCE_SHUT_DOWN_TOO_QUICK_MSG = "10秒内重复操作关机";
  public final static int SHUT_DOWN_ALREADY_CODE = 92302;
  public final static String SHUT_DOWN_ALREADY_MSG = "设备已关机";
  public final static int IMEI_NOT_ONLINE_CODE = 92303;
  public final static String IMEI_NOT_ONLINE_MSG = "设备环信不在线状态";
  public final static int IMEI_NOT_UPLOAD_BOOTSTATE_CODE = 92304;
  public final static String IMEI_NOT_UPLOAD_BOOTSTATE_MSG = "设备未上传开启关闭状态";

  // 联系人异常
  public final static int CONTRACT_NAME_IS_EMPTY_CODE = 92401;
  public final static String CONTRACT_NAME_IS_EMPTY_MSG = "联系人名称不能为空";
  public final static int ACOUNT_IS_NOT_ADMIN = 92402;
  public final static String ACOUNT_IS_NOT_ADMIN_MSG = "非管理员，没有权限操作";
  public final static int CONTRACT_IS_SAVE_CODE = 92403;
  public final static String CONTRACT_IS_SAVE_MSG = "联系人已存在";
  public final static int CONTRACT_OLD_PHONR_CODE = 92404;
  public final static String CONTRACT_OLD_PHONR_MSG = "联系人旧手机号码不允许为空";
  public final static int CONTRACT_NEW_PHONR_CODE = 92405;
  public final static String CONTRACT_NEW_PHONR_MSG = "联系人新手机号码不允许为空";
  public final static int CONTRACT_IS_NOT_SAVE_CODE = 92406;
  public final static String CONTRACT_IS_NOT_SAVE_MSG = "联系人不存在";

  // 天气
  public final static int WEATHER_DATA_IS_EMPTY = 93001;
  public final static String WEATHER_DATA_IS_EMPTY_MSG = "获取实况天气失败";

  // 历史消息
  public final static int MSGTYPE_IS_EMPTY = 94001;
  public final static String MSGTYPE_IS_EMPTY_MSG = "消息类型不允许为空";
  public final static int MSG_PAGE_IS_EMPTY = 94002;
  public final static String MSG_PAGE_IS_EMPTY_MSG = "页码不允许为空";
  public final static int MSG_LIMIT_IS_EMPTY = 94003;
  public final static String MSG_LIMIT_IS_EMPTY_MSG = "每页显示条数不允许为空";
  public final static int MSG_ID_IS_EMPTY = 94004;
  public final static String MSG_ID_IS_EMPTY_MSG = "消息ID不允许为空";

  // 业务并发数据同步
  public final static int DATA_SYNC_ERROR = 95001;
  public final static String DATA_SYNC_ERROR_MSG = "业务禁止并发行为";

  // 喜马拉雅讲故事模块异常
  public final static int STROY_ID_EMPTY_CODE = 96001;
  public final static String STROY_ID_EMPTY_MSG = "喜马拉雅故事ID不能为空";
  public final static int STROY_TIME_EMPTY_CODE = 96002;
  public final static String STROY_TIME_EMPTY_MSG = "喜马拉雅故事时长不能为空";
  public final static int STROY_URL_EMPTY_CODE = 96003;
  public final static String STROY_URL_EMPTY_MSG = "喜马拉雅故事封面URL不能为空";
  public final static int STROY_NAME_EMPTY_CODE = 96004;
  public final static String STROY_NAME_EMPTY_MSG = "喜马拉雅故事名字不能为空";
  public final static int STROY_ALREADY_EXIST_CODE = 96005;
  public final static String STROY_ALREADY_EXIST_MSG = "该设备已订阅该故事";
  public final static int STROY_DOWNLOADED_SUCCESS_CODE = 96006;
  public final static String STROY_DOWNLOADED_SUCCESS_MSG = "设备下载故事成功/失败标识不能为空";
  public final static int MORE_THAN_STROY_MAX_CODE = 96007;
  public final static String MORE_THAN_STROY_MAX_MSG = "订阅数量已超最大限额(20)!";
  public final static int MORE_THAN_STROY_SIZE_MAX_CODE = 96008;
  public final static String MORE_THAN_STROY_SIZE_MAX_MSG = "订阅故事总容量过大,建议先清除再订阅!";
  public final static int STORY_HAS_DELETE_CODE = 96009;
  public final static String STORY_HAS_DELETE_MSG = "订阅的故事已被取消!";

  // 设置手表连接wifi异常
  public final static int WIFI_SSID_IS_EMPTY_CODE = 96101;
  public final static String WIFI_SSID_IS_EMPTY_MSG = "wifi的SSID不能为空";
  public final static int WIFI_PASSWORD_IS_EMPTY_CODE = 96102;
  public final static String WIFI_PASSWORD_IS_EMPTY_MSG = "wifi的密码不能为空";
  public final static int WIFI_STATE_IS_EMPTY_CODE = 96103;
  public final static String WIFI_STATE_IS_EMPTY_MSG = "wifi的启用状态不能为空";
  public final static int WIFI_DATA_LIST_IS_EMPTY_CODE = 96104;
  public final static String WIFI_DATA_LIST_IS_EMPTY_MSG = "手表上报的wifi数据不能为空";

  // 设备加好友异常
  public final static int ALREADY_FRIENDS_CODE = 96201;
  public final static String ALREADY_FRIENDS_MSG = "已经是好友";
  public final static int DEVICE_RESPONSE_ACCEPTTED_IS_EMPTY_CODE = 96202;
  public final static String DEVICE_RESPONSE_ACCEPTTED_IS_EMPTY_MSG = "设备响应接收/拒绝好友请求不能为空";
  public final static int INVITED_DEVICE_HAS_NO_ADMIN_CODE = 96203;
  public final static String INVITED_DEVICE_HAS_NO_ADMIN_MSG = "被添加设备没有绑定管理员";


  // iot hub 异常错误
  public final static int IOT_ERROR_CODE = 97001;
  public final static String IOT_ERROR_CODE_MSG = "iot注册失败";
  public final static int IOT_DEVICE_IS_REGISTER_CODE = 97002;
  public final static String IOT_DEVICE_IS_REGISTER = "iot设备已注册";
  public final static int IOT_DEVICE_NOT_REGISTER_CODE = 97003;
  public final static String IOT_DEVICE_NOT_REGISTER = "iot设备未注册，请先注册";

  // 行为统计异常错误
  public final static int ACTION_DATA_IS_EMPTY_CODE = 98001;
  public final static String ACTION_DATA_IS_EMPTY_MSG = "行为统计数据不能为空";
  public final static int ACTION_DATAFOAMAT_IS_EMPTY_CODE = 98002;
  public final static String ACTION_DATAFOAMAT_IS_EMPTY_MSG = "行为统计数据格式错误，必须为JsonArray数据格式";

  // 设备上报通讯记录异常
  public final static int PHONENUM_IS_EMPTY_CODE = 98101;
  public final static String PHONENUM_IS_EMPTY_MSG = "电话号码不能为空";
  public final static int CALL_IN_OUT_IS_EMPTY_CODE = 98102;
  public final static String CALL_IN_OUT_IS_EMPTY_MSG = "呼入/呼出不能为空";
  public final static int CALL_SUCCESS_IS_EMPTY_CODE = 98103;
  public final static String CALL_SUCCESS_IS_EMPTY_MSG = "接听成功/失败不能为空";
  public final static int CALL_TIME_IS_EMPTY_CODE = 98104;
  public final static String CALL_TIME_IS_EMPTY_MSG = "拨打电话时间不能为空";

  // 邀请好友绑定设备异常
  public final static int BAND_REQUEST_HAS_SEND_CODE = 98201;
  public final static String BAND_REQUEST_HAS_SEND_MSG = "邀请已发送过";
  public final static int BAND_REQUEST_REPLY_CODE = 98202;
  public final static String BAND_REQUEST_REPLY_MSG = "被邀请人回复不能为空";

}
