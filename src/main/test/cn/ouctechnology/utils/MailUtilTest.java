package cn.ouctechnology.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class MailUtilTest {
    @Test
    public void sendMail() throws Exception {
        MailUtil.sendMail("1127582378@qq.com","今晚吃鸡","hahahhahh");
    }

}