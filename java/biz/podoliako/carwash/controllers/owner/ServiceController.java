package biz.podoliako.carwash.controllers.owner;


import biz.podoliako.carwash.models.entity.*;
import biz.podoliako.carwash.services.CarWashService;
import biz.podoliako.carwash.services.CategoryService;
import biz.podoliako.carwash.services.ServiceService;
import biz.podoliako.carwash.models.pojo.CarWashAddServiceForm;
import biz.podoliako.carwash.models.pojo.ServiceFormError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/owner/service")
@SessionAttributes("CurrentCarWashUser")
public class ServiceController {

    @Autowired
    ServiceService serviceService;

    @Autowired
    CarWashService carWashService;

    @Autowired
    CategoryService categoryService;


    @RequestMapping(value="/add", method = RequestMethod.GET)
    public String addServiceNameGet(Model model){

        model.addAttribute("serviceName", new ServiceName());
        return "owner/service/add";

    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addServiceNamePost(@Valid @ModelAttribute("serviceName") ServiceName serviceName,
                                     BindingResult bindResult,
                                     HttpSession session,
                                     Model model) {


        User user = (User) session.getAttribute("CurrentCarWashUser");

        try {
            if (bindResult.hasErrors() ) {
                return "/owner/service/add";
            }

            serviceName.setOwnerId(user.getOwnerId());
            serviceName.setCreatedBy(user.getId());
            serviceService.addServiceName(serviceName);

            List<ServiceName> serviceNameList = serviceService.selectAllServiceName(user.getOwnerId());
            model.addAttribute("serviceNameList", serviceNameList);
        }catch (Exception e) {
            model.addAttribute("globalError", e.getMessage());
            return "owner/service/add";
        }

        return "owner/service/all";
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String allGet(@ModelAttribute("CurrentCarWashUser") User authorization,
                         Model model){
        try {

            List<ServiceName> serviceNameList = serviceService.selectAllServiceName(authorization.getOwnerId());

            model.addAttribute("serviceNameList", serviceNameList);

        }catch (Exception e) {
            model.addAttribute("globalError", e.getMessage());
        }
            return "owner/service/all";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String deleteGet(@ModelAttribute("CurrentCarWashUser") User authorization,
                             Model model){
        try {

            List<ServiceName> serviceNameList = serviceService.selectAllServiceName(authorization.getOwnerId());
            model.addAttribute("serviceNameList", serviceNameList);

        }catch (Exception e) {
            model.addAttribute("globalError", e.getMessage());
        }

        model.addAttribute("delete", true);

        return "owner/service/all";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deletePost(@ModelAttribute("CurrentCarWashUser") User authorization,
                             @RequestParam(value = "listIdService", defaultValue = "") String[] listIdService,
                             Model model){

        try {

            for(String id: listIdService) {
                serviceService.deleteServiceName(id);
            }
            model.addAttribute("globalMsg", "Услуги удалены успешно");

            List<ServiceName> serviceNameList = serviceService.selectAllServiceName(authorization.getOwnerId());
            model.addAttribute("serviceNameList", serviceNameList);

        }catch (Exception e) {
            model.addAttribute("globalError", e.getMessage());
        }

        return "owner/service/all";
    }

    @RequestMapping(value = "/carwash/add", method = RequestMethod.GET)
    public String addCarWashServiceGet(@ModelAttribute("CurrentCarWashUser") User authorization,
                                       Model model) {

        try {
            model.addAttribute("carWashList", carWashService.getAllCarWashes(authorization.getOwnerId()));
            model.addAttribute("categoryList", categoryService.selectAllCategory(authorization.getOwnerId()));
            model.addAttribute("serviceNameList", serviceService.selectAllServiceName(authorization.getOwnerId()));

        }catch (Exception e) {
            model.addAttribute("globalError", e.getMessage());
        }

        return "owner/service/carwash/add";

    }

    @RequestMapping(value = "/carwash/add", method = RequestMethod.POST)
    public String addCarWashServicePost(@ModelAttribute("CarWashAddServiceForm") CarWashAddServiceForm form,
                                        @ModelAttribute("CurrentCarWashUser") User authorization,
                                        RedirectAttributes redirectAttributes,
                                        Model model) {

        try {
            ServiceFormError serviceFormError = serviceService.validateCarWashService(form);

            if (serviceFormError.isHasErrors()){
                    model.addAttribute("carWashList", carWashService.getAllCarWashes(authorization.getOwnerId()));
                    model.addAttribute("categoryList", categoryService.selectAllCategory(authorization.getOwnerId()));
                    model.addAttribute("serviceNameList", serviceService.selectAllServiceName(authorization.getOwnerId()));

                    model.addAttribute("carWashAddServiceForm", form);
                    model.addAttribute("serviceFormError", serviceFormError);

                return "owner/service/carwash/add";
            }

          serviceService.addCarWashService(form, authorization.getOwnerId());
          redirectAttributes.addFlashAttribute("globalMsg", "Услуги добавлены успешно");

        }catch (Exception e) {
            model.addAttribute("globalError", e.getMessage());
        }

        return "redirect:/owner/service/carwash/add";
    }

    @RequestMapping(value = "/carwash/all", method = RequestMethod.GET)
    public String allCarWashServiceGet (@ModelAttribute("CurrentCarWashUser") User authorization,
                                    Model model){

        try {
            List<CarWash> carWashList = carWashService.getAllCarWashes(authorization.getOwnerId());
            model.addAttribute("carWashList", carWashList);
            model.addAttribute("url", "/owner/service/carwash/all");
        }catch (Exception e) {
            model.addAttribute("globalError", e.getMessage());
        }

        return "owner/general/chooseCarWashService";
    }

    @RequestMapping(value = "/carwash/all", method = RequestMethod.POST)
    public String allCarWashServicePost (@ModelAttribute("carWashId") String carWashId,
                                         @ModelAttribute("CurrentCarWashUser") User authorization,
                                         Model model) {
        try {
            Map<String, List<biz.podoliako.carwash.models.entity.CarWashService>> carWashServiceList = serviceService.selectAllCarWashServices(carWashId, authorization.getOwnerId());
            CarWash carWash = carWashService.selectCarWash(Integer.valueOf(carWashId));
            List<Category> categoryList= categoryService.selectAllCategory(authorization.getOwnerId());

            model.addAttribute("carWashName", carWash.getName());
            model.addAttribute("categoryList", categoryList);
            model.addAttribute("carWashServiceList", carWashServiceList);
        }catch (Exception e) {
            model.addAttribute("globalError", e.getMessage());
        }

        return "owner/service/carwash/all";
    }

    @RequestMapping(value = "/carwash/delete", method = RequestMethod.GET)
    public String deleteCarWashServiceGet (@ModelAttribute("CurrentCarWashUser") User authorization,
                                        Model model){

        try {
            List<CarWash> carWashList = carWashService.getAllCarWashes(authorization.getOwnerId());
            model.addAttribute("carWashList", carWashList);
            model.addAttribute("url", "/owner/service/carwash/delete/choose");
        }catch (Exception e) {
            model.addAttribute("globalError", e.getMessage());
        }

        return "owner/general/chooseCarWashService";
    }

    @RequestMapping(value = "/carwash/delete/choose", method = RequestMethod.POST)
    public String deleteChooseCarWashServicePost (@ModelAttribute("carWashId") String carWashId,
                                         @ModelAttribute("CurrentCarWashUser") User authorization,
                                         Model model) {

        try {
            Map<String, List<biz.podoliako.carwash.models.entity.CarWashService>> carWashServiceList = serviceService.selectAllCarWashServices(carWashId, authorization.getOwnerId());
            CarWash carWash = carWashService.selectCarWash(Integer.valueOf(carWashId));
            List<Category> categoryList= categoryService.selectAllCategory(authorization.getOwnerId());

            model.addAttribute("carWash", carWash);
            model.addAttribute("categoryList", categoryList);
            model.addAttribute("carWashServiceList", carWashServiceList);
            model.addAttribute("delete", true);
        }catch (Exception e) {
            model.addAttribute("globalError", e.getMessage());
        }

        return "owner/service/carwash/all";
    }

    @RequestMapping(value = "/carwash/delete", method = RequestMethod.POST)
    public String deleteCarWashServicePost (@ModelAttribute("carWashId") String carWashId,
                                            @ModelAttribute("listCarWashServiceNameId") String[] listCarWashServiceNameId,
                                            @ModelAttribute("CurrentCarWashUser") User authorization,
                                            Model model) {

        try {
            serviceService.deleteCarWashService(listCarWashServiceNameId, authorization.getOwnerId());

            Map<String, List<biz.podoliako.carwash.models.entity.CarWashService>> carWashServiceList = serviceService.selectAllCarWashServices(carWashId, authorization.getOwnerId());
            CarWash carWash = carWashService.selectCarWash(Integer.valueOf(carWashId));
            List<Category> categoryList= categoryService.selectAllCategory(authorization.getOwnerId());

            model.addAttribute("carWashName", carWash.getName());
            model.addAttribute("categoryList", categoryList);
            model.addAttribute("carWashServiceList", carWashServiceList);
            model.addAttribute("delete", true);
            model.addAttribute("globalMsg", "Услуги удалены успешно");
        }catch (Exception e) {
            model.addAttribute("globalError", e.getMessage());
        }

        return "owner/service/carwash/all";
    }
}
