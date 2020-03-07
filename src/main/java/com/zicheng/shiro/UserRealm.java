package com.zicheng.shiro;

import com.zicheng.domain.User;
import com.zicheng.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 子诚
 * Description：自定义的Realm，继承shiro提供的AuthorizingRealm
 * 时间：2020/3/7 14:24
 */
public class UserRealm extends AuthorizingRealm {

    /**
     * 执行授权逻辑
     * AuthorizationInfo 用于聚合授权信息；
     * 当我们使用 AuthorizingRealm 时，
     * 如果身份验证成功，在进行授权时就通过doGetAuthorizationInfo 方法获取角色/权限信息用于授权验证。
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行了授权逻辑");


        //给资源进行授权
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        //添加资源的授权字符串
        //info.addStringPermission("user:add");

        //到数据库查询当前登录用户的授权字符串
        //获取当前登录用户
        Subject subject = SecurityUtils.getSubject();
        User user = (User)subject.getPrincipal();
        User dbUser = userSerivce.findById(user.getId());
        info.addStringPermission(dbUser.getPerms());
        return info;
    }
    @Autowired
    private UserService userSerivce;
    /**
     * 执行认证逻辑
     * AuthenticationInfo：用于进行认证用户名和密码的。以及密码是否加盐，是否加密
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("执行了认证逻辑");



        //编写shiro判断逻辑，判断用户名和密码
        //1、判断用户名
        UsernamePasswordToken token= (UsernamePasswordToken) authenticationToken;
        //根据页面上填写的用户名，获取到整个user
        User user = userSerivce.findByName(token.getUsername());

        if(null == user){
            //意味着用户名不存在
            return null;//shiro的底册会抛出一个UnknownAccountException:这个异常代表用户名不存在
        }
        //2、判断密码
        return new SimpleAuthenticationInfo(user,user.getPassword(),getName());
        /**
         * SimpleAuthenticationInfo中可以传三个参数也可以传四个参数。
         * 第一个参数：
         *       传入的都是com.zicheng.domain 包下的User类的user对象。
         * 第二个参数:
         *      传入的是从数据库中获取到的password，然后再与token中的password进行对比，匹配上了就通过，匹配不上就报异常。
         * 第三个参数:
         *      盐–用于加密密码对比。 若不需要，则可以设置为空 “ ”
         * 第四个参数：
         *      当前realm的名字。
         */
    }
}
