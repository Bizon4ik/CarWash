package biz.podoliako.carwash.filters;

import biz.podoliako.carwash.models.entity.Role;
import biz.podoliako.carwash.models.entity.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by bizon4ik on 13.08.15.
 */
public class AdminFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();

        User user = (User) session.getAttribute("CurrentCarWashUser");

        if (user.getRole() != Role.administrator) {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/login");
            requestDispatcher.forward(req, response);
        }else {
            chain.doFilter(req, response);
        }



    }

    @Override
    public void destroy() {

    }
}
