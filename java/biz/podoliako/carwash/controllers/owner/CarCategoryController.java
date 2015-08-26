package biz.podoliako.carwash.controllers.owner;

import biz.podoliako.carwash.models.entity.Category;
import biz.podoliako.carwash.services.CategoryService;
import biz.podoliako.carwash.models.entity.User;
import biz.podoliako.carwash.models.pojo.CategoryFormErrors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/owner/category")
@SessionAttributes("CurrentCarWashUser")
public class CarCategoryController{

    @Autowired
    CategoryService categoryService;

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addGet(){
        return "owner/category/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addPost(@ModelAttribute("category") Category category,
                          @ModelAttribute("CurrentCarWashUser") User authorization,
                          Model model ) {
        try {
            category.setOwnerId(authorization.getOwnerId());

            CategoryFormErrors categoryFormErrors = categoryService.validateCategoryParam(category);

            if (categoryFormErrors.isHasErrors()) {
                model.addAttribute("categoryFormErrors", categoryFormErrors);
                return "/owner/category/add";
            }


                categoryService.addCategory(category);
        } catch (Exception e) {
            model.addAttribute("globalError", e.getMessage());
            return "owner/category/add";
        }

        model.addAttribute("globalMsg", "Категория \"" + category.getName() + "\" создана");
        return "owner/category/all";
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String allGet (@ModelAttribute("CurrentCarWashUser") User authorization,
                          Model model ) {

        try {
            List<Category> categoryList = categoryService.selectAllCategory(authorization.getOwnerId());
            model.addAttribute("categoryList", categoryList);
        } catch (SQLException e) {
            model.addAttribute("globalError", e.getMessage());
            return "owner/category/all";
        }

        return "owner/category/all";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String deleteGet(@ModelAttribute("CurrentCarWashUser") User authorization,
                            Model model)throws SQLException {

        model.addAttribute("delete", new Boolean(true));

        List<Category> categoryList = categoryService.selectAllCategory(authorization.getOwnerId());
        model.addAttribute("categoryList", categoryList);

        return "owner/category/all";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deletePost(@RequestParam(value = "listIdCategory", defaultValue = "") String[] listIdCategory,
                             @ModelAttribute("CurrentCarWashUser") User authorization,
                             Model model) {

        try {

            for (String id : listIdCategory) {
                if (!id.equals("")) {
                    categoryService.deleteCategory(id);
                }
            }

            model.addAttribute("globalMsg", "Категории успешно удалены");

            List<Category> categoryList = categoryService.selectAllCategory(authorization.getOwnerId());
            model.addAttribute("categoryList", categoryList);
        }catch (Exception e) {
            model.addAttribute("globalError", e.getMessage());
        }

        return "owner/category/all";
    }

}
