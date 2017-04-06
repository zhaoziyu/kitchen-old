package com.restaurant.dinner.admin;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 项目属性
 * 可以通过project.properties注入（注意：setter注入时不能加static修饰符）
 *
 * @date 2016-09-19
 * @author 赵梓彧 - kitchen_dev@163.com
 */
@Component
public class ProjectProperties {

    public final static String SESSION_USER_FULL_NAME = "session_user_full_name";
    public final static String SESSION_USER_ID = "session_user_id";

    // 项目名称
    public static String PROJECT_NAME;

    // 登录页的副标题
    public static String PROJECT_LOGIN_SUBHEAD;


    @Value("${project.name}")
    public void setProjectName(String content) {
        PROJECT_NAME = content;
    }

    @Value("${project.login.subhead}")
    public void setProjectLoginSubhead(String content) {
        PROJECT_LOGIN_SUBHEAD = content;
    }
}
