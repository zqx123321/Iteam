package cn.ouctechnology.service;

import cn.ouctechnology.domain.Application;
import cn.ouctechnology.domain.Dept;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface IApplicationService {

    String upload(HttpServletRequest req, MultipartFile file);

    List<Dept> list();

    String add(HttpServletRequest req, Application application);

    String listApplication(int page, int limit, String type, Long deptId);

    String agree(Long id);

    String refuse(Long id);

    Application get(Long id, HttpServletRequest req);

    String delete(Long id);

    String changePwd(Long id, String pwd);

    Dept getDept(Long id);
}
