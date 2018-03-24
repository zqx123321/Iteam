package cn.ouctechnology.controller;

import cn.ouctechnology.captcha.Captcha;
import cn.ouctechnology.captcha.GifCaptcha;
import cn.ouctechnology.domain.Admin;
import cn.ouctechnology.domain.Dept;
import cn.ouctechnology.service.IAdminService;
import cn.ouctechnology.service.IApplicationService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private IAdminService adminService;

    @Autowired
    private IApplicationService service;

    @RequestMapping("/doLogin")
    public ModelAndView doLogin(String account, String password, String check, HttpServletRequest req) {
        ModelAndView mv = new ModelAndView("login");
        mv.addObject("res", adminService.login(account, password, check, req));
        return mv;
    }

    @RequestMapping("/login")
    public String login(HttpServletResponse resp) {
        return "login";
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest req, HttpServletResponse resp) {
        req.getSession().invalidate();
        return "login";
    }

    @RequestMapping("/admin")
    public String admin(HttpServletResponse resp) {
        return "admin/index";
    }

    @ResponseBody
    @RequestMapping("/captcha")
    public void captcha(HttpServletRequest req, HttpServletResponse resp) {
        //设置输出图片
        resp.setContentType("image/gif");
        resp.setHeader("Pragma", "No-cache");
        resp.setHeader("Cache-Control", "no-cache");
        resp.setDateHeader("Expires", 0);

        // gif验证码, 宽、高、位数
        Captcha captcha = new GifCaptcha(130, 38, 5);
        String code = captcha.text();
        // 存入servletContext
        req.getSession().setAttribute("captcha", code);

        //输入图片
        try {
            captcha.out(resp.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @ResponseBody
    @RequestMapping("/admin/doAdd")
    public String doAdd(Admin admin, Long deptId, HttpServletResponse resp) {
        admin.setDept(service.getDept(deptId));
        admin.setAdminClass(2);
        admin.setPassword(DigestUtils.sha1Hex(admin.getAccount()));
        return adminService.add(admin);
    }

    @RequestMapping("/admin/add")
    public ModelAndView add(HttpServletResponse resp) {
        List<Dept> list = service.list();
        ModelAndView mv = new ModelAndView("/admin/add");
        mv.addObject("depts", list);
        return mv;
    }
}
