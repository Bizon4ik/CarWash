package biz.podoliako.carwash.controllers.administrator;


import biz.podoliako.carwash.models.entity.Car;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
                                             Model model){

        User user = (User) session.getAttribute("CurrentCarWashUser");

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
        session.setAttribute("car", car);

        return "redirect:/admin/order/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addOrderGet(HttpSession session,
                              Model model){
        Car car = (Car) session.getAttribute("car");
        if (car == null) {
            return "redirect:/admin/order/getCarNumber";
        }

        User user = (User) session.getAttribute("CurrentCarWashUser");
        Integer carWashId = (Integer) session.getAttribute("ChoosenCarWashId");

        OrderForm orderForm = orderService.createOrderFormWithStaticFilds(car);

        OrderFormData orderFormData = null;
        try {
            orderFormData = orderService.getOrderFormData(carWashId, car.getCategoryId(), user);
        } catch (SQLException e) {
            model.addAttribute("globalError", e.getMessage());
            return "admin/main";
        }

        model.addAttribute("orderForm", orderForm);
        model.addAttribute("orderFormData", orderFormData);



        return "admin/order/orderForm";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addOrderPost(@Valid @ModelAttribute("orderForm") OrderForm orderForm,
                               BindingResult bindingResult,
                               HttpSession session,
                               Model model) {
        try {
                if (bindingResult.hasErrors()){
                    Car car = (Car) session.getAttribute("car");
                    if (car == null) {
                        return "redirect:/admin/order/getCarNumber";
                    }

                    User user = (User) session.getAttribute("CurrentCarWashUser");
                    Integer carWashId = (Integer) session.getAttribute("ChoosenCarWashId");

                    OrderFormData orderFormData = orderService.getOrderFormData(carWashId, car.getCategoryId(), user);

                    model.addAttribute("orderFormData", orderFormData);

                    return "admin/order/orderForm";
                }
            System.out.println(orderForm);

            orderService.addOrder(orderForm);

        } catch (SQLException e) {
            model.addAttribute("globalError", e.getMessage());
            return "admin/main";
        }



        return "admin/order/orderForm";
    }


}
