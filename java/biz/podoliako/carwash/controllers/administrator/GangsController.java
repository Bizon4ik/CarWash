package biz.podoliako.carwash.controllers.administrator;

import biz.podoliako.carwash.models.entity.User;
import biz.podoliako.carwash.models.entity.WasherManInBox;
import biz.podoliako.carwash.services.UserService;
import biz.podoliako.carwash.view.WasherManInBoxWithName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.naming.NamingException;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping(value = "admin/gangs")
public class GangsController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/chooseBox", method = RequestMethod.GET)
    public String chooseBox(HttpSession session,
                            Model model) {

        Integer carWashId = (Integer) session.getAttribute("ChoosenCarWashId");

        try {
            List<Set<WasherManInBoxWithName>> washerManInBoxesWithNames = userService.getWasherMansWorkingInBoxesNow(carWashId);
            model.addAttribute("washerManInBoxesWithNames", washerManInBoxesWithNames);

        } catch (SQLException e) {
            model.addAttribute("globalError", e.getMessage());
            return "admin/main";
        } catch (NamingException e) {
            model.addAttribute("globalError", e.getMessage());
            return "admin/main";
        }

        return "admin/gangs/chooseBox";
    }

    @RequestMapping(value = "/box/{boxNumber}", method = RequestMethod.GET)
    public String gangInBox(@PathVariable String boxNumber,
                            HttpSession session,
                            Model model){

        try {
            Integer boxNum = Integer.valueOf(boxNumber);
            model.addAttribute("boxNumber", boxNum);

            Integer carWashId = (Integer) session.getAttribute("ChoosenCarWashId");
            Set<WasherManInBoxWithName> allowedWasherMansInBoxSet = userService.getAvailableAndCurrentWasherManInBox(carWashId, boxNum);
            model.addAttribute("allowedWasherMansInBoxSet", allowedWasherMansInBoxSet);

            Set<WasherManInBox> washerManInBoxNow = userService.getAllWasherManInBox(carWashId, boxNum);
            if(washerManInBoxNow.size() == 0 ){
                WasherManInBox w = new WasherManInBox();
                w.setUserId(-1);
                washerManInBoxNow.add(w);
            }
            model.addAttribute("washerManInBoxNow", washerManInBoxNow);

        }catch (NamingException e) {
            model.addAttribute("globalError", e.getMessage());
            return "admin/main";
        }
        catch (SQLException e){
            model.addAttribute("globalError", e.getMessage());
            return "admin/main";
        }

        return  "/admin/gangs/gangInBox";
    }


    @RequestMapping(value = "/box/{boxNumber}", method = RequestMethod.POST)
    public String modificationGangInBox (@PathVariable String boxNumber,
                                    HttpSession session,
                                    String[] washerManIds,
                                    RedirectAttributes redirectAttributes,
                                    Model model){

        try {
            Integer boxNum = Integer.valueOf(boxNumber);
            Integer carWashId = (Integer) session.getAttribute("ChoosenCarWashId");
            User currentUserId = (User) session.getAttribute("CurrentCarWashUser");

            userService.modifyWasherManTeamInBox(carWashId, boxNum, washerManIds, currentUserId.getId());
            redirectAttributes.addFlashAttribute("globalMsg", "Команда в боксе " + boxNumber + " обновлена");


        }catch (SQLException e) {
            model.addAttribute("globalError", e.getMessage());
            System.out.println("SQL exception: " + e);
            return "admin/main";
        } catch (NamingException e) {
            model.addAttribute("globalError", e.getMessage());
            System.out.println("Naming exception: " + e);
            return "admin/main";
        }

        return  "redirect:/admin/gangs/chooseBox";
    }


}
