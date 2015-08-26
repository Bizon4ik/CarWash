package biz.podoliako.carwash.controllers.owner;


import biz.podoliako.carwash.models.entity.CarWash;
import biz.podoliako.carwash.services.CarWashService;
import biz.podoliako.carwash.models.entity.Role;
import biz.podoliako.carwash.models.entity.User;
import biz.podoliako.carwash.services.UserService;
import biz.podoliako.carwash.services.entity.AddAuthorizedUserForm;
import biz.podoliako.carwash.services.entity.AddUserForm;
import biz.podoliako.carwash.view.AddUserFormView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/owner/user")
@SessionAttributes("CurrentCarWashUser")
public class UserController {

    @Autowired
    CarWashService carWashService;

    @Autowired
    UserService userService;

    @RequestMapping(value = "/addAdmin", method = RequestMethod.GET)
    public String addAdminGet (@ModelAttribute("CurrentCarWashUser") User authorization,
                           Model model){

        try {
            AddUserFormView addAdminForm = userService.getAddAdminForm(authorization.getOwnerId());
            model.addAttribute("addUserFormView", addAdminForm);
        }catch (Exception e) {
            model.addAttribute("globalError", e.getMessage());
        }

        model.addAttribute("addUserForm", new AddAuthorizedUserForm());

        return "owner/user/addUser";
    }

    @RequestMapping(value = "/addAdmin", method = RequestMethod.POST)
    public String addAdminPost(@Valid @ModelAttribute("addUserForm") AddAuthorizedUserForm addUserForm,
                              BindingResult bindResult,
                              HttpSession session,
                              RedirectAttributes redirectAttributes,
                              Model model) {

        User user = (User) session.getAttribute("CurrentCarWashUser");

        addUserForm.setOwnerId(user.getOwnerId());
        model.addAttribute("addUserForm", addUserForm);

       try {

            if (bindResult.hasErrors()) {
                AddUserFormView addAdminForm = userService.getAddAdminForm(user.getOwnerId());
                model.addAttribute("addUserFormView", addAdminForm);

                return "owner/user/addUser";
            }

            addUserForm.setRole(Role.administrator);
            addUserForm.setCreatedBy(user.getId());
            userService.addUser(addUserForm);
            redirectAttributes.addFlashAttribute("globalMsg", "Пользователь " + addUserForm.getName() + " успешно создан");

        }catch (SQLException e) {
            model.addAttribute("globalError", e.getMessage());
        }
            return "redirect:/owner/main";

    }

    @RequestMapping(value = "/addWasherMan", method = RequestMethod.GET)
    public String addWasherManGet (@ModelAttribute("CurrentCarWashUser") User authorization,
                              Model model){

        try {
            AddUserFormView addAdminForm = userService.getAddWasherManForm(authorization.getOwnerId());
            model.addAttribute("addUserFormView", addAdminForm);
        }catch (Exception e) {
            model.addAttribute("globalError", e.getMessage());
        }

        model.addAttribute("addUserForm", new AddUserForm());

        return "owner/user/addUser";
    }

    @RequestMapping(value = "/addWasherMan", method = RequestMethod.POST)
    public String addWasherManPost(@Valid @ModelAttribute("addUserForm") AddUserForm addUserForm,
                               BindingResult bindResult,
                               HttpSession session,
                               RedirectAttributes redirectAttributes,
                               Model model) {
        User user = (User) session.getAttribute("CurrentCarWashUser");

        addUserForm.setOwnerId(user.getOwnerId());
        model.addAttribute("addUserForm", addUserForm);

        try {
            if (bindResult.hasErrors()) {
                AddUserFormView addAdminForm = userService.getAddWasherManForm(user.getOwnerId());
                model.addAttribute("addUserFormView", addAdminForm);

                return "owner/user/addUser";
            }
            addUserForm.setRole(Role.washerMan);
            addUserForm.setCreatedBy(user.getId());
            userService.addUser(addUserForm);

            redirectAttributes.addFlashAttribute("globalMsg", "Мойщик " + addUserForm.getName() + " успешно создан");

        }catch (SQLException e) {
            model.addAttribute("globalError", e.getMessage());
        }
        return "redirect:/owner/main";

    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String allUsersGet (HttpSession session,
                                   Model model){

        User user = (User) session.getAttribute("CurrentCarWashUser");

        try {
            Map<CarWash, List<User>>  list = userService.getAllUsers(user.getOwnerId());

            model.addAttribute("allUsers", list);
        }catch (Exception e) {
            model.addAttribute("globalError", e.getMessage());
        }



        return "/owner/user/allUser";
    }
}
