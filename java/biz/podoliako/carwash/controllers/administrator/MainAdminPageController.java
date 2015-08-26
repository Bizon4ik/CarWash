package biz.podoliako.carwash.controllers.administrator;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainAdminPageController {

    @RequestMapping(value = "/admin/main", method = RequestMethod.GET)
    public String mainPageGet(){
        return "admin/main";

    }

}
