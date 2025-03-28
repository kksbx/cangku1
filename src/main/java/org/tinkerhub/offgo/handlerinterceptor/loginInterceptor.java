package org.tinkerhub.offgo.handlerinterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class loginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getSession().getAttribute("user")==null){
            System.out.println("未登录");
            return false;
        }
        else {
            System.out.println("已登录");
            return true;
        }
    }
}
