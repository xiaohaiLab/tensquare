package com.tensquare.web.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.http.protocol.RequestContent;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description TODO
 * @Author "zhouhai"
 * @Date2019/3/1122:14
 **/
public class WebFilter extends ZuulFilter {


    @Override
    public String filterType() {
        return null;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {

        //得到request上下文
        RequestContext currentContext = RequestContext.getCurrentContext();

        HttpServletRequest request = currentContext.getRequest();

        String header = request.getHeader("Authorization");

        if (header != null && !"".equals(header)) {
            //把头信息继续向下传
            currentContext.addZuulRequestHeader("Authorization",header);

        }

        return null;
    }
}
