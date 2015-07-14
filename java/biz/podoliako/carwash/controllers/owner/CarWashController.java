package biz.podoliako.carwash.controllers.owner;

import biz.podoliako.carwash.dao.pojo.CarWash;
import biz.podoliako.carwash.models.CarWashModel;
import biz.podoliako.carwash.models.pojo.Authorization;
import biz.podoliako.carwash.models.pojo.CarWashFormErrors;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/owner/carwash")
@SessionAttributes("authorization")
public class CarWashController {

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
                          @ModelAttribute("authorization") Authorization authorization,
                          Model model)  {

        try {
            carWash.setOwnerId(authorization.getOwnerid());

            ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
            CarWashModel carWashModel = context.getBean("CarWashModel", CarWashModel.class);

            carWashModel.setShiftTime(carWash, startHours, startMin, finishHours, finishMin);

            CarWashFormErrors carWashFormErrors = carWashModel.validateCarWashParam(carWash);
            if (carWashFormErrors.isHasErrors()) {
                model.addAttribute("carWashFormErrors", carWashFormErrors);
                return "owner/carWash/add";
            }

            carWashModel.addCarWash(carWash);
            model.addAttribute("globalMsg", "Мойка \"" + carWash.getName() + "\" создана");
            List<CarWash> carWashList = getAllCarWashList(authorization.getOwnerid());
            model.addAttribute("carWashList", carWashList);
        }catch (Exception e) {
            model.addAttribute("globalError", e.getMessage());
            return "owner/carWash/add";
        }

        return "/owner/carWash/all";

    }

    @RequestMapping(value="/all", method = RequestMethod.GET)
    public String allGet(@ModelAttribute("authorization") Authorization authorization,
                         Model model) throws SQLException {

        List<CarWash> carWashList = getAllCarWashList(authorization.getOwnerid());
        model.addAttribute("carWashList", carWashList);

        return "owner/carWash/all";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String deleteGet(@ModelAttribute("authorization") Authorization authorization,
                            Model model)throws SQLException {

            model.addAttribute("delete", new Boolean(true));
            List<CarWash> carWashList = getAllCarWashList(authorization.getOwnerid());
            model.addAttribute("carWashList", carWashList);

            return "owner/carWash/all";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deletePost(@RequestParam(value = "listIdCarWash", defaultValue = "") String[] listIdCarWash,
                             @ModelAttribute("authorization") Authorization authorization,
                             Model model) throws SQLException {

        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        CarWashModel carWashModel = context.getBean("CarWashModel", CarWashModel.class);

        for(String id : listIdCarWash) {
            if (!id.equals("")) {
                carWashModel.deleteCarWash(id);
            }
        }

        model.addAttribute("globalMsg", "Мойки успешно удалены");

        List<CarWash> carWashList = carWashModel.selectAllCarWashes(authorization.getOwnerid());
        model.addAttribute("carWashList", carWashList);

        return "owner/carWash/all";
    }

    private List<CarWash> getAllCarWashList(Integer ownerId) throws SQLException {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        CarWashModel carWashModel = context.getBean("CarWashModel", CarWashModel.class);

        return carWashModel.selectAllCarWashes(ownerId);

    }




}
