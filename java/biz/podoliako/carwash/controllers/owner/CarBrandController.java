package biz.podoliako.carwash.controllers.owner;


import biz.podoliako.carwash.models.entity.CarBrand;
import biz.podoliako.carwash.models.entity.User;
import biz.podoliako.carwash.services.CarBrandService;
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

@Controller
@RequestMapping("owner/carbrand")
public class CarBrandController {

    @Autowired
    CarBrandService carBrandService;

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addCarBrandGet(Model model){

        model.addAttribute("carBrand", new CarBrand());

        return  "owner/carbrand/addCarBrand";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addCarBrandPost(@Valid @ModelAttribute("carBrand") CarBrand carBrand,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes,
                                  HttpSession session,
                                  Model model){

        if (bindingResult.hasErrors()){
            return  "owner/carbrand/addCarBrand";
        }

        User user = (User) session.getAttribute("CurrentCarWashUser");
        carBrand.setOwnerId(user.getOwnerId());
        carBrand.setCreatedBy(user.getId());

        try {
            carBrandService.addCarBrad(carBrand);
            redirectAttributes.addFlashAttribute("globalMsg", "АвтоБренд "+ carBrand.getName().trim().toUpperCase() +" добавлен");
        } catch (SQLException e) {
            model.addAttribute("globalError", e.getMessage());
        }


        return "redirect:/owner/carbrand/add";
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String allCarBrandsPost(HttpSession session,
                                   Model model) {
        User user = (User) session.getAttribute("CurrentCarWashUser");

        try {
            List<CarBrand> carBrandList = carBrandService.getAllCarBrands(user.getOwnerId());
            model.addAttribute("carBrandList", carBrandList);
        } catch (SQLException e) {
            model.addAttribute("globalError", e.getMessage());
        }


        return "owner/carbrand/allCarBrands";
    }

}
