package cn.ouctechnology.service.impl;

import cn.ouctechnology.dao.IAdminDao;
import cn.ouctechnology.domain.Admin;
import cn.ouctechnology.service.IAdminService;
import com.alibaba.fastjson.JSON;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Service
public class AdminServiceImpl implements IAdminService {
    @Autowired
    private IAdminDao dao;

    @Override
    public String login(String account, String password, String check, HttpServletRequest req) {
        String res;
        String contextPath = req.getContextPath();
        if (!check.equalsIgnoreCase((String) req.getSession().getAttribute("captcha"))) {
            res = "<script>alert('验证码错误');location='./login'</script>";
            return res;
        }
        Admin admin = dao.getAdminByAccount(account);
        if (admin == null) {
            return  "<script>alert('用户名不存在');location='"+contextPath+"/login'</script>";
        }
        String realPassword = admin.getPassword();
        password = DigestUtils.sha1Hex(password);
        if (!realPassword.equals(password)) {
            res = "<script>alert('密码错误');location='"+contextPath+"/login'</script>";
        } else {
            res = "<script>alert('登陆成功');location='"+contextPath+"/admin'</script>";
            req.getSession().setAttribute("admin", admin);
            req.getSession().removeAttribute("captcha");
        }
        return res;
    }

    @Override
    public String add(Admin admin) {
        Map<String, Integer> resMap = new HashMap();
        Admin obj = dao.getAdminByAccount(admin.getAccount());
        if(obj!=null){
            resMap.put("success", 0);
        }else {
            dao.save(admin);
            resMap.put("success", 1);
        }
        String jsonString = JSON.toJSONString(resMap);
        return jsonString;
    }
}
