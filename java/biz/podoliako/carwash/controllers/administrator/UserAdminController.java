package biz.podoliako.carwash.controllers.administrator;

import biz.podoliako.carwash.models.entity.CarWash;
import biz.podoliako.carwash.models.entity.Role;
import biz.podoliako.carwash.models.entity.User;
import biz.podoliako.carwash.services.UserService;
import biz.podoliako.carwash.services.entity.AddUserForm;
import biz.podoliako.carwash.view.AddUserFormView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping(value = "admin/user")
public class UserAdminController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/addWasherMan", method = RequestMethod.GET)
    public String addWasherManGet (HttpSession session,
                                   Model model){

        User user = (User) session.getAttribute("CurrentCarWashUser");
        try {
            AddUserFormView addAdminForm = userService.getAddWasherManForm(user.getOwnerId());
            model.addAttribute("addUserFormView", addAdminForm);
        }catch (SQLException e) {
            model.addAttribute("globalError", e.getMessage());
            return "admin/main";
        }

        model.addAttribute("addUserForm", new AddUserForm());

        return "admin/user/addUser";
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

                return "admin/user/addUser";
            }
            addUserForm.setRole(Role.washerMan);
            addUserForm.setCreatedBy(user.getId());
            userService.addUser(addUserForm);

            redirectAttributes.addFlashAttribute("globalMsg", "Мойщик " + addUserForm.getName() + " успешно создан");

        }catch (SQLException e) {
            model.addAttribute("globalError", e.getMessage());
            return "admin/main";
        }
        return "redirect:/admin/main";

    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String allUsersGet (HttpSession session,
                               Model model){

        User user = (User) session.getAttribute("CurrentCarWashUser");
        Integer carWashId = (Integer) session.getAttribute("ChoosenCarWashId");

        try {
            Set<User> userSet = userService.getAllWasherManInCarWash(carWashId);

            model.addAttribute("userSet", userSet);
        }catch (Exception e) {
            model.addAttribute("globalError", e.getMessage());
        }



        return "/admin/user/allWasherMans";
    }
}
