package biz.podoliako.carwash.filters;

import biz.podoliako.carwash.models.AuthorizationModel;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class AuthorizationFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String uri = request.getRequestURI();

        System.out.println("uri = " + uri);

        if (uri.indexOf("/resources/") >= 0) {
            filterChain.doFilter(servletRequest, servletResponse);
        }else {
            HttpSession session = request.getSession();

            if (session.getAttribute("authorization") == null && !uri.equals("/login") ) {

                ((HttpServletResponse)servletResponse).sendRedirect("/login");
            }else {
                filterChain.doFilter(servletRequest, servletResponse);
            }

        }

    }

    @Override
    public void destroy() {

    }
}
