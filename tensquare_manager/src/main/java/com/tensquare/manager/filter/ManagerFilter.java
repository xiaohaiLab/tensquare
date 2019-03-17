package com.tensquare.manager.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description TODO
 * @Author "zhouhai"
 * @Date2019/3/1121:50
 **/
@Component
public class ManagerFilter extends ZuulFilter {

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 在请求前执行
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }


    /**
     * 多个过滤器的执行顺序，数字越小，表示越先执行
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 当前过滤器是否开启true
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 过滤器内执行的操作，return任何object的值都表示继续执行
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        System.out.println("经过后台过滤器了！");
        RequestContext requestContext = RequestContext.getCurrentContext();
        //request域
        HttpServletRequest request = requestContext.getRequest();

        if (request.getMethod().equals("OPTIONS")) {
            return null ;
        }

        if (request.getRequestURI().indexOf("login")>0) {
            return  null;
        }

        //得到头信息
        String header = request.getHeader("Authorization");

        if (header!=null && !"".equals(header) ) {

            if (header.startsWith("Bearer ")) {
                String token = header.substring(7);
                try {
                    Claims claims = jwtUtil.parseJWT(token);
                    String roles = (String) claims.get("roles");

                    if (roles.equals("admin")) {
                        //把头信息转发下去，并且放行
                        requestContext.addZuulRequestHeader("Authorization",header);
                        return null;
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                    //终止运行
                    requestContext.setSendZuulResponse(false);
                }
            }
        }
        //终止运行
        requestContext.setSendZuulResponse(false);
        requestContext.setResponseStatusCode(403);
        requestContext.setResponseBody("权限不足");
        requestContext.getResponse().setContentType("text/html;charset=utf-8");
        return null;
    }
}
