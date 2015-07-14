package biz.podoliako.carwash.controllers.owner;


import biz.podoliako.carwash.dao.pojo.CarWash;
import biz.podoliako.carwash.dao.pojo.CarWashService;
import biz.podoliako.carwash.dao.pojo.Category;
import biz.podoliako.carwash.dao.pojo.ServiceName;
import biz.podoliako.carwash.models.CarWashModel;
import biz.podoliako.carwash.models.CategoryModel;
import biz.podoliako.carwash.models.ServiceModel;
import biz.podoliako.carwash.models.pojo.Authorization;
import biz.podoliako.carwash.models.pojo.CarWashAddServiceForm;
import biz.podoliako.carwash.models.pojo.ServiceFormError;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/owner/service")
@SessionAttributes("authorization")
public class ServiceController {


    @RequestMapping(value="/add", method = RequestMethod.GET)
    public String addGet(){
        return "owner/service/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addPost(@ModelAttribute("serviceName") ServiceName serviceName,
                          @ModelAttribute("authorization") Authorization authorization,
                          Model model) {

        try {
            serviceName.setOwnerId(authorization.getOwnerid());

            ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
            ServiceModel serviceModel = context.getBean("ServiceModel", ServiceModel.class);

            String serviceNameErrors = serviceModel.validateServiceName(serviceName.getName());
            if (serviceNameErrors != null) {
                model.addAttribute("serviceNameErrors", serviceNameErrors);
                return "/owner/service/add";
            }

            serviceModel.addServiceName(serviceName);

            List<ServiceName> serviceNameList = serviceModel.selectAllServiceName(authorization.getCarWashOwnerId());
            model.addAttribute("serviceNameList", serviceNameList);
        }catch (Exception e) {
            model.addAttribute("globalError", e.getMessage());
            return "owner/service/add";
        }

        return "owner/service/all";
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String allGet(@ModelAttribute("authorization") Authorization authorization,
                         Model model){
        try {

            ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
            ServiceModel serviceModel = context.getBean("ServiceModel", ServiceModel.class);

            List<ServiceName> serviceNameList = serviceModel.selectAllServiceName(authorization.getCarWashOwnerId());

            model.addAttribute("serviceNameList", serviceNameList);

        }catch (Exception e) {
            model.addAttribute("globalError", e.getMessage());
        }
            return "owner/service/all";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String deleteGet(@ModelAttribute("authorization") Authorization authorization,
                             Model model){
        try {
            ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
            ServiceModel serviceModel = context.getBean("ServiceModel", ServiceModel.class);

            List<ServiceName> serviceNameList = serviceModel.selectAllServiceName(authorization.getCarWashOwnerId());
            model.addAttribute("serviceNameList", serviceNameList);

        }catch (Exception e) {
            model.addAttribute("globalError", e.getMessage());
        }
        Boolean delete = new Boolean(true);
        model.addAttribute("delete", delete);

        return "owner/service/all";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deletePost(@ModelAttribute("authorization") Authorization authorization,
                             @RequestParam(value = "listIdService", defaultValue = "") String[] listIdService,
                             Model model){

        try {
            ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
            ServiceModel serviceModel = context.getBean("ServiceModel", ServiceModel.class);

            for(String id: listIdService) {
                serviceModel.deleteServiceName(id);
            }
            model.addAttribute("globalMsg", "Услуги удалены успешно");

            List<ServiceName> serviceNameList = serviceModel.selectAllServiceName(authorization.getCarWashOwnerId());
            model.addAttribute("serviceNameList", serviceNameList);

        }catch (Exception e) {
            model.addAttribute("globalError", e.getMessage());
        }

        return "owner/service/all";
    }

    @RequestMapping(value = "/carwash/add", method = RequestMethod.GET)
    public String addCarWashServiceGet(@ModelAttribute("authorization") Authorization authorization,
                                       Model model) {

        try {
            ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
            CarWashModel carWashModel = context.getBean("CarWashModel", CarWashModel.class);
            CategoryModel categoryModel = context.getBean("CategoryModel", CategoryModel.class);
            ServiceModel serviceModel = context.getBean("ServiceModel", ServiceModel.class);

            model.addAttribute("carWashList", carWashModel.selectAllCarWashes(authorization.getOwnerid()));
            model.addAttribute("categoryList", categoryModel.selectAllCategory(authorization.getOwnerid()));
            model.addAttribute("serviceNameList", serviceModel.selectAllServiceName(authorization.getOwnerid()));


        }catch (Exception e) {
            model.addAttribute("globalError", e.getMessage());
        }

        return "owner/service/carwash/add";

    }

    @RequestMapping(value = "/carwash/add", method = RequestMethod.POST)
    public String addCarWashServicePost(@ModelAttribute("CarWashAddServiceForm") CarWashAddServiceForm form,
                                        @ModelAttribute("authorization") Authorization authorization,
                                        Model model) {

        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        ServiceModel serviceModel = context.getBean("ServiceModel", ServiceModel.class);

        try {
            ServiceFormError serviceFormError = serviceModel.validateCarWashService(form);

            if (serviceFormError.isHasErrors()){

                    CarWashModel carWashModel = context.getBean("CarWashModel", CarWashModel.class);
                    CategoryModel categoryModel = context.getBean("CategoryModel", CategoryModel.class);

                    model.addAttribute("carWashList", carWashModel.selectAllCarWashes(authorization.getOwnerid()));
                    model.addAttribute("categoryList", categoryModel.selectAllCategory(authorization.getOwnerid()));
                    model.addAttribute("serviceNameList", serviceModel.selectAllServiceName(authorization.getOwnerid()));

                    model.addAttribute("carWashAddServiceForm", form);
                    model.addAttribute("serviceFormError", serviceFormError);

                return "owner/service/carwash/add";
            }

          serviceModel.addCarWashService(form, authorization.getOwnerid());
          model.addAttribute("globalMsg", "Услуги удалены успешно");

        }catch (Exception e) {
            model.addAttribute("globalError", e.getMessage());
        }

        return "owner/service/carwash/all";
    }

    @RequestMapping(value = "/carwash/all", method = RequestMethod.GET)
    public String allCarWashServiceGet (@ModelAttribute("authorization") Authorization authorization,
                                    Model model){

        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        CarWashModel carWashModel = context.getBean("CarWashModel", CarWashModel.class);

        try {
            List<CarWash> carWashList = carWashModel.selectAllCarWashes(authorization.getOwnerid());
            model.addAttribute("carWashList", carWashList);
            model.addAttribute("url", "/owner/service/carwash/all");
        }catch (Exception e) {
            model.addAttribute("globalError", e.getMessage());
        }

        return "owner/general/chooseCarWashService";
    }

    @RequestMapping(value = "/carwash/all", method = RequestMethod.POST)
    public String allCarWashServicePost (@ModelAttribute("carWashId") String carWashId,
                                         @ModelAttribute("authorization") Authorization authorization,
                                         Model model) {

        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        ServiceModel serviceModel = context.getBean("ServiceModel", ServiceModel.class);
        CarWashModel carWashModel = context.getBean("CarWashModel", CarWashModel.class);
        CategoryModel categoryModel = context.getBean("CategoryModel", CategoryModel.class);

        try {
            Map<String, List<CarWashService>> carWashServiceList = serviceModel.selectAllCarWashServices(carWashId, authorization.getCarWashOwnerId());
            CarWash carWash = carWashModel.selectCarWash(Integer.valueOf(carWashId));
            List<Category> categoryList= categoryModel.selectAllCategory(authorization.getOwnerid());

            model.addAttribute("carWashName", carWash.getName());
            model.addAttribute("categoryList", categoryList);
            model.addAttribute("carWashServiceList", carWashServiceList);
        }catch (SQLException e) {
            model.addAttribute("globalError", e.getMessage());
        }

        return "owner/service/carwash/all";
    }

    @RequestMapping(value = "/carwash/delete", method = RequestMethod.GET)
    public String deleteCarWashServiceGet (@ModelAttribute("authorization") Authorization authorization,
                                        Model model){

        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        CarWashModel carWashModel = context.getBean("CarWashModel", CarWashModel.class);

        try {
            List<CarWash> carWashList = carWashModel.selectAllCarWashes(authorization.getOwnerid());
            model.addAttribute("carWashList", carWashList);
            model.addAttribute("url", "/owner/service/carwash/delete/choose");
        }catch (Exception e) {
            model.addAttribute("globalError", e.getMessage());
        }

        return "owner/general/chooseCarWashService";
    }

    @RequestMapping(value = "/carwash/delete/choose", method = RequestMethod.POST)
    public String deleteChooseCarWashServicePost (@ModelAttribute("carWashId") String carWashId,
                                         @ModelAttribute("authorization") Authorization authorization,
                                         Model model) {

        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        ServiceModel serviceModel = context.getBean("ServiceModel", ServiceModel.class);
        CarWashModel carWashModel = context.getBean("CarWashModel", CarWashModel.class);
        CategoryModel categoryModel = context.getBean("CategoryModel", CategoryModel.class);

        try {
            Map<String, List<CarWashService>> carWashServiceList = serviceModel.selectAllCarWashServices(carWashId, authorization.getCarWashOwnerId());
            CarWash carWash = carWashModel.selectCarWash(Integer.valueOf(carWashId));
            List<Category> categoryList= categoryModel.selectAllCategory(authorization.getOwnerid());

            model.addAttribute("carWash", carWash);
            model.addAttribute("categoryList", categoryList);
            model.addAttribute("carWashServiceList", carWashServiceList);
            model.addAttribute("delete", true);
        }catch (SQLException e) {
            model.addAttribute("globalError", e.getMessage());
        }

        return "owner/service/carwash/all";
    }

    @RequestMapping(value = "/carwash/delete", method = RequestMethod.POST)
    public String deleteCarWashServicePost (@ModelAttribute("carWashId") String carWashId,
                                            @ModelAttribute("listCarWashServiceNameId") String[] listCarWashServiceNameId,
                                            @ModelAttribute("authorization") Authorization authorization,
                                            Model model) {

        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        ServiceModel serviceModel = context.getBean("ServiceModel", ServiceModel.class);
        CarWashModel carWashModel = context.getBean("CarWashModel", CarWashModel.class);
        CategoryModel categoryModel = context.getBean("CategoryModel", CategoryModel.class);

        try {
            serviceModel.deleteCarWashService(listCarWashServiceNameId, authorization.getCarWashOwnerId());

            Map<String, List<CarWashService>> carWashServiceList = serviceModel.selectAllCarWashServices(carWashId, authorization.getCarWashOwnerId());
            CarWash carWash = carWashModel.selectCarWash(Integer.valueOf(carWashId));
            List<Category> categoryList= categoryModel.selectAllCategory(authorization.getOwnerid());

            model.addAttribute("carWashName", carWash.getName());
            model.addAttribute("categoryList", categoryList);
            model.addAttribute("carWashServiceList", carWashServiceList);
            model.addAttribute("delete", true);
            model.addAttribute("globalMsg", "Услуги удалены успешно");
        }catch (SQLException e) {
            model.addAttribute("globalError", e.getMessage());
        }

        return "owner/service/carwash/all";
    }
}
