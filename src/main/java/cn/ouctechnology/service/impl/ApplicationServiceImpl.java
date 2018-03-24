package cn.ouctechnology.service.impl;

import cn.ouctechnology.dao.IAdminDao;
import cn.ouctechnology.dao.IApplicationDao;
import cn.ouctechnology.dao.IDeptDao;
import cn.ouctechnology.dao.IUploadDao;
import cn.ouctechnology.domain.Admin;
import cn.ouctechnology.domain.Application;
import cn.ouctechnology.domain.Dept;
import cn.ouctechnology.domain.Upload;
import cn.ouctechnology.service.IApplicationService;
import cn.ouctechnology.utils.FileUploadUtils;
import cn.ouctechnology.utils.MailUtil;
import com.alibaba.fastjson.JSON;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ApplicationServiceImpl implements IApplicationService {
    @Autowired
    private IUploadDao uploadDao;

    @Autowired
    private IApplicationDao applicationDao;

    @Autowired
    private IAdminDao adminDao;

    @Autowired
    private IDeptDao deptDao;

    @Override
    public String upload(HttpServletRequest req, MultipartFile file) {
        Map<String, Object> resMap = new HashMap<>();
        Object obj = req.getSession().getAttribute("TOKEN_IN_SESSION");
        if (obj == null || file == null) {
            resMap.put("success", 0);
            return JSON.toJSONString(resMap);
        }
        String path = req.getRealPath("upload");
        Long token = (Long) obj;
        String url = FileUploadUtils.upload(path, file);
        url = "upload" + File.separator + url;
        if (url == null) {
            resMap.put("success", 0);
            return JSON.toJSONString(resMap);
        }
        Upload upload = new Upload();
        upload.setToken(token);
        upload.setUrl(url);
        uploadDao.save(upload);
        resMap.put("success", 1);
        resMap.put("url", url);
        return JSON.toJSONString(resMap);
    }

    @Override
    public List<Dept> list() {
        return deptDao.list();
    }

    @Override
    public String add(HttpServletRequest req, Application application) {
        Map<String, Object> resMap = new HashMap<>();
        Object obj = req.getSession().getAttribute("TOKEN_IN_SESSION");
        if (obj == null) {
            resMap.put("success", 0);
            return JSON.toJSONString(resMap);
        }
        try {
            Long token = (Long) obj;
            application.setToken(token);
            SimpleDateFormat sdf = new SimpleDateFormat();
            sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
            String submit = sdf.format(new Date());
            application.setSubmit(submit);
            application.setType("未审核");
            applicationDao.save(application);
            StringBuilder sb = new StringBuilder();
            sb.append("尊敬的");
            sb.append(application.getClubOwner());
            sb.append(",您提交的社团：");
            sb.append(application.getClubName());
            sb.append(" 账号申请已经成功提交，请耐心等待管理员审核，审核结果会以邮件的形式通知。");
            MailUtil.sendMail(application.getClubEmail(), "社团账号申请提交成功", sb.toString());
            sendEmailToAdmin(application.getDeptId());
            resMap.put("success", 1);
            req.getSession().invalidate();
            return JSON.toJSONString(resMap);
        } catch (Exception e) {
            e.printStackTrace();
            resMap.put("success", 0);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return JSON.toJSONString(resMap);
        }
// } finally {
//            return JSON.toJSONString(resMap);
//        }
    }

    private void sendEmailToAdmin(Long deptId) {
        List<Admin> lists = adminDao.list();
        try {
            for (Admin list : lists) {
                if (list.getDept().getId() != deptId && list.getAdminClass() != 1) continue;
                StringBuilder sb = new StringBuilder();
                sb.append("尊敬的");
                sb.append(list.getName());
                sb.append(",有一条新的社团账号申请待您审核，<a herf='#'>点我审核</a>");
                MailUtil.sendMail(list.getEmail(), "有一条新的社团账号申请待您审核", sb.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    @Override
    public String listApplication(int page, int limit, String type, Long deptId) {
        List<Application> res = applicationDao.list(type, page, limit, deptId);
        Long count = applicationDao.count(type, deptId);
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("code", 0);
        resMap.put("msg", "");
        resMap.put("count", count);
        resMap.put("data", res);
        return JSON.toJSONString(resMap);
    }

    @Override
    public String agree(Long id) {
        Application application = applicationDao.get(id);
        application.setType("已同意");
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyy-MM-dd HH:mm");
        String s = sdf.format(date);
        application.setDeal(s);
        //并发问题？
        Long maxAccount = applicationDao.getMaxAccount();
        application.setAgreeAccount(maxAccount + 1);
        StringBuilder sb = new StringBuilder();
        sb.append("尊敬的");
        sb.append(application.getClubOwner());
        sb.append(",您提交的社团：");
        sb.append(application.getClubName());
        sb.append(" 账号申请已经通过审核，您的社团账号和密码均为：");
        sb.append(maxAccount);
        sb.append("您可以使用此账号登录IteamApp。");
        Map<String, Integer> resMap = new HashMap();
        try {
            applicationDao.update(application);
            MailUtil.sendMail(application.getClubEmail(), "社团账号申请审核通过", sb.toString());
            resMap.put("success", 1);
        } catch (Exception e) {
            e.printStackTrace();
            resMap.put("success", 0);
        } finally {
            String jsonString = JSON.toJSONString(resMap);
            return jsonString;
        }

    }

    @Override
    public String refuse(Long id) {
        Application application = applicationDao.get(id);
        application.setType("已拒绝");
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.applyPattern("yyyy-MM-dd HH:mm");
        String s = sdf.format(date);
        application.setDeal(s);
        StringBuilder sb = new StringBuilder();
        sb.append("尊敬的");
        sb.append(application.getClubOwner());
        sb.append(",您提交的社团：");
        sb.append(application.getClubName());
        sb.append(" 账号申请审核未通过，请尝试重新申请或者与学院管理员联系。");
        Map<String, Integer> resMap = new HashMap();
        try {
            applicationDao.update(application);
            MailUtil.sendMail(application.getClubEmail(), "社团账号申请审核未通过", sb.toString());
            resMap.put("success", 1);
        } catch (Exception e) {
            e.printStackTrace();
            resMap.put("success", 0);
        } finally {
            String jsonString = JSON.toJSONString(resMap);
            return jsonString;
        }
    }

    @Override
    public Application get(Long id, HttpServletRequest req) {
        Application application = applicationDao.get(id);
        List<Upload> list = uploadDao.getByToken(application.getToken());
        req.setAttribute("uploads", list);
        return application;
    }

    @Override
    public String delete(Long id) {
        Application application = applicationDao.get(id);
        applicationDao.delete(application);
        Map<String, Integer> resMap = new HashMap();
        resMap.put("success", 1);
        String jsonString = JSON.toJSONString(resMap);
        return jsonString;
    }

    @Override
    public String changePwd(Long id, String pwd) {
        Admin admin = adminDao.get(id);
        admin.setPassword(DigestUtils.sha1Hex(pwd));
        adminDao.update(admin);
        Map<String, Integer> resMap = new HashMap();
        resMap.put("success", 1);
        String jsonString = JSON.toJSONString(resMap);
        return jsonString;
    }

    @Override
    public Dept getDept(Long id) {
        return deptDao.get(id);
    }
}
