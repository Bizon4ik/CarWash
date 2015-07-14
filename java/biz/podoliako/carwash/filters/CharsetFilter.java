package biz.podoliako.carwash.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by bizon4ik on 25.05.15.
 */
public class CharsetFilter implements Filter {
    private String encoding;

    public void init(FilterConfig config) throws ServletException
    {
        encoding = config.getInitParameter("requestEncoding");

        if( encoding==null ) encoding="UTF-8";
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain       next)
            throws IOException, ServletException
    {
        System.out.println("name= " + (HttpServletRequest) request.getAttribute("name"));
        if(null == request.getCharacterEncoding())
            request.setCharacterEncoding(encoding);

        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");




        next.doFilter(request, response);
    }

    public void destroy(){}

}