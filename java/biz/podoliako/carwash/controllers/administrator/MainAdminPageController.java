package biz.podoliako.carwash.controllers.administrator;


import biz.podoliako.carwash.models.entity.User;
import biz.podoliako.carwash.services.OrderService;
import biz.podoliako.carwash.view.OrderInBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class MainAdminPageController {

    @Autowired
    OrderService orderService;

    @RequestMapping(value = "/admin/main", method = RequestMethod.GET)
    public String mainPageGet(HttpSession session,
                              Model model){

        Integer carWashId = (Integer) session.getAttribute("ChoosenCarWashId");

        List<OrderInBox> orderInBoxList = orderService.getCurrentOrdersInAllBoxes(carWashId);
        model.addAttribute("orderInBoxList", orderInBoxList);

        return "admin/main";

    }

    @RequestMapping(value = "/admin/main/close/{orderId}", method = RequestMethod.GET)
    public String closeOrder(@PathVariable String orderId,
                             RedirectAttributes redirectAttributes,
                             HttpSession session,
                             Model model){

        Integer carWashId = (Integer) session.getAttribute("ChoosenCarWashId");
        User user = (User) session.getAttribute("CurrentCarWashUser");
        try {
            orderService.closeOrder(orderId, carWashId, user.getId());
            redirectAttributes.addFlashAttribute("globalMsg", "Заказ №" + orderId.trim() + " закрыт успешко");
            return "redirect:/admin/main";
        }catch (NumberFormatException e) {
            redirectAttributes.addFlashAttribute("globalError", "Не коректный номер заказа");
            return "redirect:/admin/main";
        }

    }

    @RequestMapping(value = "/admin/main/delete/{orderId}", method = RequestMethod.GET)
    public String deleteOrder(@PathVariable String orderId,
                             RedirectAttributes redirectAttributes,
                             HttpSession session,
                             Model model){

        Integer carWashId = (Integer) session.getAttribute("ChoosenCarWashId");
        User user = (User) session.getAttribute("CurrentCarWashUser");
        try {
            orderService.deleteOrder(orderId, carWashId, user.getId());
            redirectAttributes.addFlashAttribute("globalMsg", "Заказ №"+ orderId.trim() + " удален успешко");
            return "redirect:/admin/main";
        }catch (NumberFormatException e) {
            redirectAttributes.addFlashAttribute("globalError", "Не коректный номер заказа");
            return "redirect:/admin/main";
        }

    }

}
