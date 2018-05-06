package org.patent.api.web.filter;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;

public class LogbackContextListener implements ServletContextListener {
  private static final Logger LOGGER = LoggerFactory.getLogger(LogbackContextListener.class);

  @Override
  public void contextInitialized(ServletContextEvent sce) {
    try {
      initLogback(sce);
      LOGGER.info("init logback finished");
    } catch (Exception e) {
      LOGGER.error("error", e);
    }
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {

  }

  void initLogback(ServletContextEvent sce) throws Exception {
    LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
    JoranConfigurator configurator = new JoranConfigurator();
    configurator.setContext(lc);
    lc.reset();
    configurator.doConfigure(LogbackContextListener.class.getResource("/logback.xml"));
  }
}
