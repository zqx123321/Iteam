package cn.ouctechnology.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor{
    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object o) throws Exception {
        //获取请求的url
        StringBuffer urlBuffer = req.getRequestURL();
        String url = urlBuffer.toString();
        //判断url是否包含admin
        if(!url.contains("admin")){
            return true;
        }
        //判断session
        HttpSession session  = req.getSession();
        //从session中取出用户身份信息
        Object username = session.getAttribute("admin");
        if(username != null){
            //身份存在，放行
            return true;
        }
        //执行这里表示用户身份需要认证，跳转登陆页面
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
        //return false表示拦截，不向下执行
        //return true表示放行
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
