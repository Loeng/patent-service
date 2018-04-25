package org.patent.easemob.ext;


import org.apache.commons.lang.StringUtils;

import io.watch.utils.Command;

public class FilterChain {

  private HojyMsgFilter headTail;

  public FilterChain() {
    headTail = new HeadTail();
  }

  public void addFilter(HojyMsgFilter filter) {
    synchronized (this) {
      HojyMsgFilter tmp = headTail;
      while (tmp.next != null) {
        tmp = tmp.next;
      }
      tmp.next = filter;
    }
  }

  public void beforeFilter(Object obj) {
    headTail.before(headTail, obj);
  }

  public void afterFilter(Object result, Object obj) {
    headTail.after(headTail, result, obj);
  }

  class HeadTail extends HojyMsgFilter {
    @Override
    public void beforeProcess(Object obj) {
      if (obj instanceof HojyEasemobMsg) {
        HojyEasemobMsg msg = (HojyEasemobMsg) obj;
        if (StringUtils.isBlank(msg.getEasemobMsg().getFrom())) {
          msg.getEasemobMsg().from(Command.CMD_SENDER);
        }
      }
    }

    @Override
    public void afterProcess(Object result, Object obj) {
      // do nothing
    }

  }
}
