package net.kuper.jftp.server.auth;

import net.kuper.jftp.server.auth.annotation.Admin;
import net.kuper.jftp.server.entity.FtpAdmin;
import net.kuper.jftp.server.utils.HttpServletUtils;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

/**
 * 有@User注解的方法参数，注入当前登录用户
 * 需要结合AuthorizationAspect同时使用
 */
@Component
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter
                .getParameterType()
                .isAssignableFrom(FtpAdmin.class) && parameter.hasParameterAnnotation(Admin.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer container,
                                  NativeWebRequest request, WebDataBinderFactory factory) {
        HttpServletRequest req = HttpServletUtils.getHttpServletRequest();
        Object userEntity = req.getSession().getAttribute("ftp-admin");
        return userEntity;
    }

}
