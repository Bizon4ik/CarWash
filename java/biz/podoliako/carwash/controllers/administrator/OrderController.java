package biz.podoliako.carwash.controllers.administrator;


import biz.podoliako.carwash.models.entity.Car;
import biz.podoliako.carwash.models.entity.CarBrand;
import biz.podoliako.carwash.models.entity.Category;
import biz.podoliako.carwash.models.entity.User;
import biz.podoliako.carwash.services.CategoryService;
import biz.podoliako.carwash.services.OrderService;
import biz.podoliako.carwash.view.OrderForm;
import biz.podoliako.carwash.view.OrderFormData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping(value = "admin/order")
public class OrderController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    OrderService orderService;

    @RequestMapping(value = "/getCarNumber", method = RequestMethod.GET)
    public String getCarNumberAndCategoryGET(HttpSession session,
                                             Model model){

        User user = (User) session.getAttribute("CurrentCarWashUser");

        try {
            List<Category> categoryList = categoryService.selectAllCategory(user.getOwnerId());

            model.addAttribute("car", new Car());
            model.addAttribute("categoryList", categoryList);
        } catch (SQLException e) {
            model.addAttribute("globalError", e.getMessage());
            return "admin/main";
        }

        return "admin/order/carNumberAndCategory";
    }

    @RequestMapping(value = "/getCarNumber", method = RequestMethod.POST)
    public String getCarNumberAndCategoryPOST(@Valid @ModelAttribute("car") Car car,
                                             BindingResult bindResult,
                                             HttpSession session,
                                             RedirectAttributes redirectAttributes,
                                             Model model){

        User user = (User) session.getAttribute("CurrentCarWashUser");
        Integer carWashId = (Integer) session.getAttribute("ChoosenCarWashId");

        try {
            if (bindResult.hasErrors()){
                List<Category> categoryList = categoryService.selectAllCategory(user.getOwnerId());
                model.addAttribute("categoryList", categoryList);
                return "admin/order/carNumberAndCategory";
            }

        } catch (SQLException e) {
            model.addAttribute("globalError", e.getMessage());
            return "admin/main";
        }
        OrderForm orderForm = orderService.createBaseOrderForm(car);
        orderForm.setUserId(user.getId());
        orderForm.setOwnerId(user.getOwnerId());
        orderForm.setCarWashId(carWashId);
        session.setAttribute("orderForm", orderForm);

        if (car.getCategoryId() != orderForm.getCategoryId()) {
            redirectAttributes.addFlashAttribute("globalError", "Категория была изменена на историческую");
        }

        return "redirect:/admin/order/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addOrderGet(HttpSession session,
                              Model model){

        OrderForm orderForm = (OrderForm) session.getAttribute("orderForm");
        if (orderForm == null) {
            return "redirect:/admin/order/getCarNumber";
        }

        try {
            OrderFormData orderFormData = orderService.getDataForOrderFrom(orderForm);
            model.addAttribute("orderForm", orderForm);
            model.addAttribute("orderFormData", orderFormData);

        } catch (SQLException e) {
            model.addAttribute("globalError", e.getMessage());
            return "admin/main";
        }

        return "admin/order/orderForm";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addOrderPost(@Valid @ModelAttribute("orderForm") OrderForm orderForm,
                               BindingResult bindingResult,
                               HttpSession session,
                               Model model) {

        OrderForm orderFormInSession = (OrderForm) session.getAttribute("orderForm");

        try {
                if (bindingResult.hasErrors()){

                    OrderFormData orderFormData = orderService.getDataForOrderFrom(orderFormInSession);

                    model.addAttribute("orderFormData", orderFormData);

                    return "admin/order/orderForm";
                }
            OrderForm finalOrderForm = orderService.uniteOrderForms(orderFormInSession, orderForm);

            if (finalOrderForm.getOrderId() == null){
                orderService.addOrder(finalOrderForm);
            }else {
                orderService.updateOrder(finalOrderForm);
            }


            session.removeAttribute("orderForm");

        } catch (SQLException e) {
            model.addAttribute("globalError", e.getMessage());
            return "admin/main";
        }

        return "redirect:/admin/main";
    }

    @RequestMapping(value = "/edit/{idOrder}", method = RequestMethod.GET)
    public String addOrderEdit(@PathVariable String idOrder,
                                HttpSession session,
                                RedirectAttributes redirectAttributes,
                                Model model) {
        /*
        Добавить проверку на что заказ закрыт!!!

        */

        try {

            OrderForm orderForm = orderService.createOrderFormForChanges(idOrder);

            User user = (User) session.getAttribute("CurrentCarWashUser");
            Integer carWashId = (Integer) session.getAttribute("ChoosenCarWashId");

            orderForm.setUserId(user.getId());
            orderForm.setOwnerId(user.getOwnerId());
            orderForm.setCarWashId(carWashId);
            session.setAttribute("orderForm", orderForm);

            OrderFormData orderFormData = orderService.getDataForOrderFrom(orderForm);

            model.addAttribute("orderForm", orderForm);
            model.addAttribute("orderFormData", orderFormData);

        }catch (NumberFormatException e) {
            redirectAttributes.addFlashAttribute("globalError", "Не корректный номер заказа");
            return "redirect:/admin/main";
        }catch (Exception e) {
            redirectAttributes.addFlashAttribute("globalError", e.getMessage());
            return "redirect:/admin/main";
        }

        return "admin/order/orderForm";
    }


}
