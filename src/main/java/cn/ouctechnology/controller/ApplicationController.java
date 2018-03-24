package cn.ouctechnology.controller;

import cn.ouctechnology.domain.Admin;
import cn.ouctechnology.domain.Application;
import cn.ouctechnology.domain.Dept;
import cn.ouctechnology.service.IApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Controller
public class ApplicationController {

    @Autowired
    private IApplicationService service;

    @RequestMapping(value = {"/index", "/"})
    public ModelAndView index(HttpServletRequest req, HttpServletResponse resp) {
        List<Dept> list = service.list();
        Long current = System.currentTimeMillis() / 1000;
        req.getSession().setAttribute("TOKEN_IN_SESSION", current);
        ModelAndView mv = new ModelAndView("index");
        mv.addObject("depts", list);
        return mv;
    }

    @ResponseBody
    @RequestMapping("/application_upload")
    public String upload(HttpServletRequest req, @RequestParam(value = "file") MultipartFile file) {
        return service.upload(req, file);
    }

    @ResponseBody
    @RequestMapping("/application_save")
    public String add(HttpServletRequest req, HttpServletResponse resp, Application application) {
        return service.add(req, application);
    }

    @ResponseBody
    @RequestMapping("/admin/list")
    public String list(int page, int limit, String type, HttpServletResponse resp, HttpServletRequest req) throws UnsupportedEncodingException {
        resp.setCharacterEncoding("UTF-8");
        type = new String(type.getBytes("ISO8859-1"), "UTF-8");
        Admin admin = (Admin) req.getSession().getAttribute("admin");
        String res;
        if (admin.getAdminClass() == 1) {
            res = service.listApplication(page, limit, type, 0L);
        } else {
            res = service.listApplication(page, limit, type, admin.getDept().getId());
        }
        return res;
    }


    @RequestMapping("/admin/applications")
    public String applications() {
        return "admin/applications";
    }


    @ResponseBody
    @RequestMapping("/admin/applications_refuse")
    public String refuse(Long id, HttpServletResponse response) {
        return service.refuse(id);
    }


    @ResponseBody
    @RequestMapping("/admin/applications_agree")
    public String agree(Long id, HttpServletResponse response) {
        return service.agree(id);
    }

    @RequestMapping("/admin/applications_detail")
    public ModelAndView detail(Long id, HttpServletRequest req) {
        ModelAndView mv = new ModelAndView("/admin/appdetail");
        Application app = service.get(id, req);
        mv.addObject("app", app);
        return mv;
    }

    @ResponseBody
    @RequestMapping("/admin/applications_delete")
    public String delete(Long id, HttpServletResponse response) {
        return service.delete(id);
    }

    @ResponseBody
    @RequestMapping("/admin/changePwd")
    public String changePwd(Long id, String pwd) {
        return service.changePwd(id, pwd);
    }
//
//    @ResponseBody
//    @RequestMapping("/getApplications")
//    public String getApplications(Long id) {
//        return service.getApplicatios();
//    }

}
