package biz.podoliako.carwash.controllers;

import biz.podoliako.carwash.services.AuthorizationService;
import biz.podoliako.carwash.models.entity.Role;
import biz.podoliako.carwash.models.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

@Controller
public class LoginController {

    @Autowired
    AuthorizationService authorizationService;

    @RequestMapping(value ={"/login", "/"}, method = RequestMethod.GET)
    public String login() {
        return "/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginValidation(@RequestParam("login") String login,
                                  @RequestParam("password") String password,
                                  HttpSession session,
                                  Model model) throws SQLException {
        String result = "";

        User user = authorizationService.getUser(login, password);

        if (user == null) {
            model.addAttribute("incorrectLogin", "Неверное имя пользователя и/или пароль");
            result = "/login";

        }else if(user.getRole() == Role.owner){
            authorizationService.setUserSession(user, session);
            result = "redirect:/owner/main";
        }else if(user.getRole() == Role.administrator) {
            authorizationService.setUserSession(user, session);

            if (user.getCarWashPermissionSet().size() == 1) {
                authorizationService.setChoosenCarWashSession(user.getCarWashPermissionSet().iterator().next(), session);
            }else {
                System.out.println("Administrator has permission for several CarWashes");
            }
            result = "redirect:/admin/main";
        }

        return  result;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession session){

        session.removeAttribute("CurrentCarWashUser");
        session.removeAttribute("ChoosenCarWashId");

        return "redirect:/login";
    }

}
