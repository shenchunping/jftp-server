package net.kuper.jftp.server.auth;

import net.kuper.jftp.server.auth.annotation.IgnoreAuth;
import net.kuper.jftp.server.entity.FtpAdmin;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        IgnoreAuth annotation;
        FtpAdmin admin = null;
        if (handler instanceof HandlerMethod) {
            annotation = ((HandlerMethod) handler).getMethodAnnotation(IgnoreAuth.class);
            admin = (FtpAdmin) request.getSession().getAttribute("ftp-admin");
        } else {
            return true;
        }
        // 不验证登录
        if (annotation != null) {
            return true;
        }
        // 需要验证登录
        // 过滤掉资源文件
        if ("/".equals(request.getServletPath())) {
            return true;
        }
        String[] suffix = new String[]{".html", ".css", "js", ".png", ".jpg", ".jpeg", ".svg", ".ico", ".ttf", ".woff"};
        for (String s : suffix) {
            boolean eq = request.getServletPath().endsWith(s);
            if (eq) {
                return true;
            }
        }
        // 判断登录

        if (admin == null) {
            return false;
        } else {
            return true;
        }
    }
}
