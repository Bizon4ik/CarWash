package biz.podoliako.carwash.controllers.owner;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@RequestMapping("/owner")
@SessionAttributes("CurrentCarWashUser")
public class MainPageController {

    @RequestMapping("/main")
    public String main(){
        return "owner/main";
    }
}
