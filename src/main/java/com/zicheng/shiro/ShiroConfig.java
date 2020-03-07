package com.zicheng.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 子诚
 * Description：Shiro的配置类
 * 时间：2020/3/7 14:20
 */
@Configuration
public class ShiroConfig {
    /**
     * 创建ShiroFilterFactoryBean（shiro过滤器工厂实例）
     */
    @Bean(name = "shiroFilterFactoryBean")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("defaultWebSecurityManager") DefaultWebSecurityManager securityManager) {
        //创建ShiroFilterFactoryBean的实例
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //关联->安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        //修改跳转的登陆页面
        shiroFilterFactoryBean.setLoginUrl("/toLogin");
        //设置未授权提示页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/noAuth");


        //添加shiro的内置过滤器
        /**
         * shiro的内置过滤器,可以实现权限相关的拦截
         *      常用的过滤器：
         *      1、anon ：无需认证（无需登录），即可访问
         *      2、authc :必须认证才可以访问（可以理解为登陆了才能访问）
         *      3、user  :如果使用rememberMe（登陆框下面的记住我功能）的功能就可以直接访问
         *      4、perms :该资源必须得到资源权限才能好访问
         *      5、role: 该资源必须得到角色权限才能访问
         */
        Map<String, String> filterMap = new LinkedHashMap<String, String>();
        //将登陆请求，放行
        filterMap.put("/login", "anon");
        //anon:无需认证即可访问(要在下面的所有到需要认证的上面，顺序要注意)
        //filterMap.put("/test", "anon");

//        filterMap.put("/add", "authc");
//        filterMap.put("/update", "authc");

        //授权过滤器
        //注意：当前授权拦截后，shiro会自动跳转到未授权页面
        filterMap.put("/add", "perms[user:add]");
        filterMap.put("/update", "perms[user:update]");

        //所有资源链接都必须登陆认证才能访问,一定要写在最后面
        filterMap.put("/*", "authc");

        //setFilterChainDefinitionMap:设置过滤器定义Map
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 创建DefaultWebSecurityManager
     * <p>
     * getDefaultWebSecurityManager()
     */
    @Bean(name = "defaultWebSecurityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm) {
        //@Qualifier("userRealm"):代表着从spring的bean工厂中userRealm为合格者
        //创建 DefaultWebSecurityManager的实例 securityManager
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //关联UserRealm
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    /**
     * 创建Realm
     */
    @Bean(name = "userRealm")
    public UserRealm getRealm() {
        return new UserRealm();
    }

    /**
     * 配置ShiroDialect，用于thymeleaf和shiro标签配合使用
     */
    @Bean
    public ShiroDialect getShiroDialect() {
        return new ShiroDialect();
    }
}
