package com.gxz.interceptor;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.gxz.utils.JwtHelper;
import com.gxz.utils.Result;
import com.gxz.utils.ResultCodeEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

//登录保护拦截器:对于"headline”路径下所有方法，验证token是否有效
@Component//为了使用JwtHelper才将拦截器加入ioc容器
public class TokenValidInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtHelper jwtHelper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //从请求头中获取token并检查
        String token = request.getHeader("token");
        if(jwtHelper.isExpiration(token)){
            //无效返回504
            Result result = Result.build(null, ResultCodeEnum.NOTLOGIN);
            ObjectMapper objectMapper=new ObjectMapper();
            String json = objectMapper.writeValueAsString(result);//将result构造成json
            response.getWriter().print(json);//自己在response中写入json
            return false;
        }
        //2.有效则放行
        return true;
    }
}
