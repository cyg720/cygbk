package com.cyg.framework.listener;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.cyg.framework.annotation.TableName;

/**
 * 
 * @ClassName StartupListener
 * @Description 监听 spring 容器启动完成
 * @author 陈一国
 * @Date 2016年11月23日 下午9:29:23
 * @version 1.0.0
 */
@Component
public class SpringListener implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger LOGGER = Logger.getLogger(SpringListener.class);
    
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        final ApplicationContext app = event.getApplicationContext();
        if (null == app.getParent() && "Root WebApplicationContext".equals(app.getDisplayName())) {
            LOGGER.debug("Root WebApplicationContext");
            Map<String, Object> beansWithAnnotation = app.getBeansWithAnnotation(TableName.class);
            for (Object o : beansWithAnnotation.values()) {
                LOGGER.debug(o.getClass().getName());
            }
        }
    }
}