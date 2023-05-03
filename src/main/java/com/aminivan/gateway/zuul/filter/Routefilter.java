package com.aminivan.gateway.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URL;

@Configuration
public class Routefilter extends ZuulFilter {
    private static Logger log = LoggerFactory.getLogger(Routefilter.class);
    @Override
    public String filterType() {
        return "route";
    }
    @Override
    public int filterOrder() {
        return 1;
    }
    @Override
    public boolean shouldFilter() {
        return true;
    }
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String header = request.getHeader("Instansi-Id");

        if ("123".equals(header)) {
            ctx.put("serviceId", "server-one-service");
            try {
                ctx.setRouteHost(new URL("http://localhost:8080/api/"));
                log.info(String.format("request host %s", ctx.getRouteHost().toString()));
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        } else if ("234".equals(header)) {
            ctx.put("serviceId", "server-two-service");
            try {
                ctx.setRouteHost(new URL("http://localhost:8001"));
                log.info(String.format("request host %s", ctx.getRouteHost().toString()));
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }
        else { // "YOUR_B_LOGIC"
            ctx.put("serviceId", "user-service");
            try {
                ctx.setRouteHost(new URL("UNAUTHORIZED"));
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }
        log.info(String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()));
        log.info("Inside route filter..");
        return null;
    }
}