package com.cyg.framework.utils;

import org.springframework.context.MessageSource;


/**
* @Description: 获取消息。委托给spring messageSource
* @author 王道兵 
* @date 2014-7-25 下午1:36:33
*/
public class MessageUtil {

    private static MessageSource messageSource;

    /**
     * 根据消息键和参数 获取消息
     * @param code 消息键
     * @param args 参数
     * @return
     */
    public static String getMessage(String code, Object... args) {
        if (messageSource == null) {
            messageSource = SpringUtil.getBean(MessageSource.class);
        }
        return messageSource.getMessage(code, args, null);
    }

}
