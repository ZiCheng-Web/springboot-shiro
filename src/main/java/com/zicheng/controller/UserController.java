package com.zicheng.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.lang.annotation.IncompleteAnnotationException;

/**
 * 子诚
 * Description：
 * 时间：2020/3/6 7:02
 */
@Controller
public class UserController {
    /**
     * 跳转到增加用户页面
     */
    @RequestMapping("/add")
    public String add() {
        return "/user/add";
    }

    /**
     * 跳转到更新用户页面
     */
    @RequestMapping("/update")
    public String update() {
        return "/user/update";
    }
    /**
     * 跳转到未授权错误页面
     */
    @RequestMapping("/noAuth")
    public String noAuth(){
        return "/noAuth";
    }

    /**
     * 跳转到登陆页面
     */
    @RequestMapping("/toLogin")
    public String toLogin() {
        return "login";
    }

    /**
     * 登陆逻辑处理
     * UsernamePasswordToken是一个简单的包含username及password即用户名及密码的登录验证用token，
     * 这个类同时继承了HostAuthenticationToken及RememberMeAuthenticationToken，
     * 因此UsernamePasswordToken能够存储客户端的主机地址以及确定token是否能够被后续的方法使用。
     * 值得注意的是在这里password是以char数组的形式存储的，这样设计是因为String本身是不可变的，
     * 这可能会导致无效的密码被访问到。
     * 另外，开发者应在验证之后调用clear方法清除token从而杜绝该token在后续被访问的可能性。
     */
    @RequestMapping("/login")
    public String login(String username, String password, Model model, HttpSession session) {
        /**
         * 使用shiro编写认证操作
         */
        //1、获取subject
        Subject subject = SecurityUtils.getSubject();
        //2、获取用户数据
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        //3、执行登陆方法
        try {
            subject.login(token);
            //没有异常就是登陆成功,转发到主页面
            session.setAttribute("username",username);
            return "redirect:/test";
        } catch (UnknownAccountException e) {
            //任何异常，都代表着登陆失败
            // UnknownAccountException:这个异常代表用户名不存在
            model.addAttribute("msg", "用户名不存在");
            return "login";
        } catch (IncorrectCredentialsException e) {
            //IncorrectCredentialsException：代表着密码不正确
            model.addAttribute("msg", "密码错误");
            return "login";
        }

    }


    /**
     * 测试方法
     */
    @RequestMapping("/hello")
    @ResponseBody
    public String hello() {
        System.out.println("UserController.hello()");
        return "ok";
    }

    /**
     * 测试thymeleaf
     */
    @RequestMapping("/test")
    public String testThymeleaf(Model model) {

        //返回test.html
        return "test";
    }

}
