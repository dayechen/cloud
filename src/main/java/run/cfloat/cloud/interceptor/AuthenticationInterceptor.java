package run.cfloat.cloud.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import io.jsonwebtoken.io.IOException;
import run.cfloat.cloud.annotation.PassToken;
import run.cfloat.cloud.service.CommonService;

// jwt验证
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object)
            throws IOException, java.io.IOException {
        final var handlerMethod = (HandlerMethod) object;
        final var method = handlerMethod.getMethod();
        // 看到PassToken注解就直接放行
        if (method.isAnnotationPresent(PassToken.class)) {
            return true;
        }
        String token = request.getHeader("token");
        if (token == null) {
            return this.toError(response);
        }
        // 开始解析token
        final var uid = CommonService.parserToken(token);
        if (uid.length() == 0) {
            return this.toError(response);
        }
        // 设置用户id
        request.setAttribute("uid", uid);
        return true;

    }

    private boolean toError(HttpServletResponse response) throws IOException, java.io.IOException {
        response.setStatus(401); // 设置状态码
        response.setContentType(MediaType.APPLICATION_JSON_VALUE); // 设置ContentType
        response.setCharacterEncoding("UTF-8"); // 避免乱码
        response.getWriter().println("{\"code\":401,\"message\":\"登录失败\"}");
        response.getWriter().flush();
        return false;
    }
}
