package com.example.rigge.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @version v1.0
 * @ProjectName: rigge
 * @ClassName: MsmConstantUtils
 * @Description: TODO(一句话描述该类的功能)
 * @Author:Promiseme
 * @Date: 2022/9/7 0:02
 */
//实现InitializingBean接口，当spring进行初始化bean时，会执行afterPropertiesSet方法
@Component
public class MsmConstantUtils implements InitializingBean {
    @Value("${tencent.sms.keyId}")
    private String secretID ;

    @Value("${tencent.sms.keysecret}")
    private String secretKey ;

    @Value("${tencent.sms.smsSdkAppId}")
    private String appId;

    @Value("${tencent.sms.signName}")
    private String signName;

    @Value("${tencent.sms.templateId}")
    private String templateId;
    //六个相关的参数
    public static String SECRET_ID;
    public static String SECRET_KEY;
    public static String APP_ID;
    public static String SIGN_NAME;
    public static String TEMPLATE_ID;

    @Override
    public void afterPropertiesSet() throws Exception {
        SECRET_ID = secretID;
        SECRET_KEY = secretKey;
        APP_ID = appId;
        SIGN_NAME = signName;
        TEMPLATE_ID = templateId;
    }
}