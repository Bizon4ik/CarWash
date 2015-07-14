package biz.podoliako.carwash.controllers;

import biz.podoliako.carwash.models.AuthorizationModel;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

@Controller
public class LoginController {

    @RequestMapping(value ={"/login", "/"}, method = RequestMethod.GET)
    public String login() {
        return "/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginValidation(@RequestParam("login") String login,
                                  @RequestParam("password") String password,
                                  HttpServletRequest request,
                                  Model model) throws SQLException {

        ApplicationContext context =
                new ClassPathXmlApplicationContext("spring-context.xml");

        AuthorizationModel authorizationModel = context.getBean("AuthorizationModel", AuthorizationModel.class);

        String userRole = authorizationModel.getUserRoleAndSetSessions(login, password, request);

        if ("owner".equals(userRole)) {
            return "redirect:/owner/main";
        }else {
            model.addAttribute("incorrectLogin", "Неверное имя пользователя и/или пароль");
            return "/login";
        }

    }
}
