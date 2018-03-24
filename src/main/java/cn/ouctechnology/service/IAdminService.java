package cn.ouctechnology.service;

import cn.ouctechnology.domain.Admin;

import javax.servlet.http.HttpServletRequest;

public interface IAdminService {
    String login(String account, String password, String text, HttpServletRequest req);

    String add(Admin admin);
}
