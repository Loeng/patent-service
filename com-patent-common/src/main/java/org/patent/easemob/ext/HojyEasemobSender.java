package org.patent.easemob.ext;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;

import org.easemob.server.api.impl.EasemobSendMessage;
import io.swagger.client.model.Msg;
//import io.watch.context.LocationIndexHolder;
import org.patent.easemob.EasemobText;
import io.watch.easemob.EasemobTextMsg;
import io.watch.mqtt.plugin.HojyMqttSenderListener;

public class HojyEasemobSender extends EasemobSendMessage {

  private static final Logger logger = LoggerFactory.getLogger(HojyMqttSenderListener.class);

  public final static String HOJY_MSG_LIST = "HOJY_MSG_LIST";
  public final static String HOJY_MSG_TXT = "HOJY_MSG_TXT";
  private HojyEasemobSenderListener listener;

  public HojyEasemobSender() {

  }

  public HojyEasemobSender(HojyEasemobSenderListener listener) {
    this.listener = listener;
  }

  public Object aroundFilterSend(HojyEasemobMsg hojyMsg) {
    if (hojyMsg instanceof EasemobTextMsg) {
      EasemobTextMsg msg = (EasemobTextMsg) hojyMsg;
      EasemobText txt = (EasemobText) msg.getAttrMap().get(HOJY_MSG_TXT);
      if (txt.isEmit()) {
        emitAll(msg);
        return new String("ASYNC SEND");
      } else {
        return new String("ASYNC INSERT");
      }
    } else {
      if (this.listener != null && this.listener.onDatabus(hojyMsg.getEasemobMsg())) {
        return new String("ASYNC SEND");
      }
      return super.sendMessage(hojyMsg.getEasemobMsg());
    }
  }

  @SuppressWarnings("unchecked")
  private void emitAll(final EasemobTextMsg msg) {
    LocationIndexHolder.custom().execute(new Runnable() {
      @Override
      public void run() {
        List<Msg> msgList = (List<Msg>) msg.getAttrMap().remove(HOJY_MSG_LIST);
        Iterator<Msg> it = msgList.iterator();
        while (it.hasNext()) {
          Msg msg = it.next();
          it.remove();
          logger.info("emitAll msg:{}", msg);
          if (listener != null && listener.onDatabus(msg)) {
            logger.info("emitAll msg1111111111");
          }else{
            logger.info("emitAll 222222222222222");
            HojyEasemobSender.this.sendMessage(msg);
          }

        }
      }
    });

  }

}
