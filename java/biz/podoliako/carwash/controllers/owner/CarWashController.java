package biz.podoliako.carwash.controllers.owner;

import biz.podoliako.carwash.models.entity.CarWash;
import biz.podoliako.carwash.services.CarWashService;
import biz.podoliako.carwash.models.entity.User;
import biz.podoliako.carwash.models.pojo.CarWashFormErrors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/owner/carwash")
@SessionAttributes("CurrentCarWashUser")
public class CarWashController {

    @Autowired
    CarWashService carWashService;


    @RequestMapping(value="/add", method = RequestMethod.GET)
    public String addGet(){
        return "owner/carWash/add";
    }


    @RequestMapping(value="/add", method = RequestMethod.POST)
    public String addPost(@ModelAttribute("carWash") CarWash carWash,
                          @RequestParam("startHours") String startHours,
                          @RequestParam("startMin") String startMin,
                          @RequestParam("finishHours") String finishHours,
                          @RequestParam("finishMin") String finishMin,
                          HttpSession session,
                          Model model)  {

        User user = (User) session.getAttribute("CurrentCarWashUser");

        try {
            carWash.setOwnerId(user.getOwnerId());

            carWashService.setShiftTime(carWash, startHours, startMin, finishHours, finishMin);

            CarWashFormErrors carWashFormErrors = carWashService.validateCarWashParam(carWash);
            if (carWashFormErrors.isHasErrors()) {
                model.addAttribute("carWashFormErrors", carWashFormErrors);
                return "owner/carWash/add";
            }

            carWashService.addCarWash(carWash);
            model.addAttribute("globalMsg", "Мойка \"" + carWash.getName() + "\" создана");
            List<CarWash> carWashList = carWashService.getAllCarWashes(user.getOwnerId());
            model.addAttribute("carWashList", carWashList);
        }catch (Exception e) {
            model.addAttribute("globalError", e.getMessage());
            return "owner/carWash/add";
        }

        return "/owner/carWash/all";

    }

    @RequestMapping(value="/all", method = RequestMethod.GET)
    public String allGet(@ModelAttribute("CurrentCarWashUser") User authorization,
                         Model model) throws SQLException {

        List<CarWash> carWashList = carWashService.getAllCarWashes(authorization.getOwnerId());

        model.addAttribute("carWashList", carWashList);

        return "owner/carWash/all";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String deleteGet(@ModelAttribute("CurrentCarWashUser") User authorization,
                            Model model)throws SQLException {

            model.addAttribute("delete", new Boolean(true));
            List<CarWash> carWashList = carWashService.getAllCarWashes(authorization.getOwnerId());
            model.addAttribute("carWashList", carWashList);

            return "owner/carWash/all";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deletePost(@RequestParam(value = "listIdCarWash", defaultValue = "") String[] listIdCarWash,
                             @ModelAttribute("CurrentCarWashUser") User authorization,
                             Model model) throws SQLException {

        for(String id : listIdCarWash) {
            if (!id.equals("")) {
                carWashService.deleteCarWash(id);
            }
        }

        model.addAttribute("globalMsg", "Мойки успешно удалены");

        List<CarWash> carWashList = carWashService.getAllCarWashes(authorization.getOwnerId());
        model.addAttribute("carWashList", carWashList);

        return "owner/carWash/all";
    }





}
