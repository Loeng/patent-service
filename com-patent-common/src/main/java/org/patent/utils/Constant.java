package org.patent.utils;

/**
 * 常量
 * 
 * @author liuwf
 * @email liuwf@hojy.com
 * @date 2017-03-23 22:02
 */
public class Constant {

  /** 超级管理员ID 默认为1 */
  public static final int SUPER_ADMIN = 1;

  /**
   * 菜单目录
   * 
   * @author hojy-liuwf
   *
   */
  public enum MenuType {
    /**
     * 目录
     */
    CATALOG(0),
    /**
     * 菜单
     */
    MENU(1),
    /**
     * 按钮
     */
    BUTTON(2);

    private int value;

    private MenuType(int value) {
      this.value = value;
    }

    public int getValue() {
      return value;
    }
  }

  /**
   * 定时任务状态
   */
  public enum ScheduleStatus {
    /**
     * 正常
     */
    NORMAL(0),
    /**
     * 暂停
     */
    PAUSE(1);

    private int value;

    private ScheduleStatus(int value) {
      this.value = value;
    }

    public int getValue() {
      return value;
    }
  }

  /**
   * 云服务商
   */
  public enum CloudService {
    /**
     * 七牛云
     */
    QINIU(1),
    /**
     * 阿里云
     */
    ALIYUN(2),
    /**
     * 腾讯云
     */
    QCLOUD(3);

    private int value;

    private CloudService(int value) {
      this.value = value;
    }

    public int getValue() {
      return value;
    }
  }

}
