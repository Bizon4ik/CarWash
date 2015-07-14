package biz.podoliako.carwash.controllers.owner;

import biz.podoliako.carwash.dao.pojo.Category;
import biz.podoliako.carwash.models.CarWashModel;
import biz.podoliako.carwash.models.CategoryModel;
import biz.podoliako.carwash.models.pojo.Authorization;
import biz.podoliako.carwash.models.pojo.CategoryFormErrors;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/owner/category")
@SessionAttributes("authorization")
public class CarCategoryController{

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addGet(){
        return "owner/category/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addPost(@ModelAttribute("category") Category category,
                          @ModelAttribute("authorization") Authorization authorization,
                          Model model ) {
        try {
            category.setOwnerId(authorization.getOwnerid());

            ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
            CategoryModel categoryModel = context.getBean("CategoryModel", CategoryModel.class);

            CategoryFormErrors categoryFormErrors = categoryModel.validateCategoryParam(category);

            if (categoryFormErrors.isHasErrors()) {
                model.addAttribute("categoryFormErrors", categoryFormErrors);
                return "/owner/category/add";
            }


                categoryModel.addCategory(category);
        } catch (Exception e) {
            model.addAttribute("globalError", e.getMessage());
            return "owner/category/add";
        }

        model.addAttribute("globalMsg", "Категория \"" + category.getName() + "\" создана");
        return "owner/category/all";
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String allGet (@ModelAttribute("authorization") Authorization authorization,
                          Model model ) {

        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        CategoryModel categoryModel = context.getBean("CategoryModel", CategoryModel.class);

        try {
            List<Category> categoryList = categoryModel.selectAllCategory(authorization.getOwnerid());
            model.addAttribute("categoryList", categoryList);
        } catch (SQLException e) {
            model.addAttribute("globalError", e.getMessage());
            return "owner/category/all";
        }

        return "owner/category/all";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String deleteGet(@ModelAttribute("authorization") Authorization authorization,
                            Model model)throws SQLException {

        model.addAttribute("delete", new Boolean(true));

        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        CategoryModel categoryModel = context.getBean("CategoryModel", CategoryModel.class);

        List<Category> categoryList = categoryModel.selectAllCategory(authorization.getOwnerid());
        model.addAttribute("categoryList", categoryList);

        return "owner/category/all";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deletePost(@RequestParam(value = "listIdCategory", defaultValue = "") String[] listIdCategory,
                             @ModelAttribute("authorization") Authorization authorization,
                             Model model) {

        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        CategoryModel categoryModel = context.getBean("CategoryModel", CategoryModel.class);

        try {

            for (String id : listIdCategory) {
                if (!id.equals("")) {
                    categoryModel.deleteCategory(id);
                }
            }

            model.addAttribute("globalMsg", "Категории успешно удалены");

            List<Category> categoryList = categoryModel.selectAllCategory(authorization.getOwnerid());
            model.addAttribute("categoryList", categoryList);
        }catch (Exception e) {
            model.addAttribute("globalError", e.getMessage());
        }

        return "owner/category/all";
    }



}
