package biz.podoliako.carwash.controllers.administrator;


import biz.podoliako.carwash.services.StatisticService;
import biz.podoliako.carwash.services.entity.CalendarPeriod;
import biz.podoliako.carwash.view.GeneralStatInCarWash;
import biz.podoliako.carwash.view.statistic.OrderForDetailStatisticInBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping(value = "admin/statistic")
public class StatisticController {

    @Autowired
    private StatisticService statisticService;

    @RequestMapping(value = "/period", method = RequestMethod.GET)
    public String choosePeriod(Model model){

        model.addAttribute("calendar", new Boolean(true));
        CalendarPeriod calendarPeriod = new CalendarPeriod();
        model.addAttribute("calendarPeriod", calendarPeriod);

        return "admin/statistic/choose_period";
    }


    @RequestMapping(value = "/period", method = RequestMethod.POST)
    public String choosePeriodPost(@Valid @ModelAttribute CalendarPeriod calendarPeriod,
                                   BindingResult bindResult,
                                   Model model){

        if (bindResult.hasErrors()){
            model.addAttribute("calendar", new Boolean(true));
            return "admin/statistic/choose_period";
        }

        Date from = statisticService.addToDateTime(calendarPeriod.getFromDate(),
                                                   calendarPeriod.getFromHour(),
                                                   calendarPeriod.getFromMinute());

        Date to = statisticService.addToDateTime(calendarPeriod.getToDate(),
                                                 calendarPeriod.getToHour(),
                                                 calendarPeriod.getToMinute());

        if ((to.getTime() - from.getTime()) < 0 ) {
            model.addAttribute("calendar", new Boolean(true));
            model.addAttribute("globalError", "Выбран не коректный период");
            return "admin/statistic/choose_period";
        }

        return "redirect:/admin/statistic/generalStat/"+from.getTime()+"/"+to.getTime();
    }

    @RequestMapping(value = "/generalStat/{from}/{to}", method = RequestMethod.GET)
    public String generalStat(@PathVariable String from,
                              @PathVariable String to,
                              RedirectAttributes redirectAttributes,
                              HttpSession session,
                              Model model){

       try {
           Date fromD = new Date(Long.valueOf(from));
           Date toD = new Date (Long.valueOf(to));

           if ((toD.getTime() - fromD.getTime()) < 0 ) {
               throw new NumberFormatException();
           }

           Integer carWashId = (Integer) session.getAttribute("ChoosenCarWashId");
           GeneralStatInCarWash generalStatInCarWash = statisticService.getGeneralStatForCarWash(fromD, toD, carWashId);

          model.addAttribute("generalStatInCarWash", generalStatInCarWash);

       }catch (NumberFormatException e) {
           redirectAttributes.addFlashAttribute("globalError", "Выбран не коректный период");
           return "redirect:/admin/statistic/period";

       }

        return "admin/statistic/generalStat";
    }

    @RequestMapping(value = "/detailStatForBox/{box}/{from}/{to}", method = RequestMethod.GET)
    public String detailStatForBox (@PathVariable String box,
                                     @PathVariable String from,
                                     @PathVariable String to,
                                     RedirectAttributes redirectAttributes,
                                     HttpSession session,
                                     Model model){

        try {
            Integer carWashId = (Integer) session.getAttribute("ChoosenCarWashId");
            Integer boxNumber = Integer.valueOf(box);

            statisticService.validateBoxNumber(carWashId, boxNumber);

            Date fromD = new Date(Long.valueOf(from));
            Date toD = new Date (Long.valueOf(to));

            if ((toD.getTime() - fromD.getTime()) < 0 ) {
                throw new NumberFormatException();
            }

            List<OrderForDetailStatisticInBox> detailStatInBoxes = statisticService.getDetailStatForBox(carWashId, boxNumber, fromD, toD);
            model.addAttribute("fromD", fromD);
            model.addAttribute("toD", toD);
            model.addAttribute("boxNumber", boxNumber);
            model.addAttribute("detailStatInBoxes", detailStatInBoxes);

            return "admin/statistic/detailStatBox";

        }catch (NumberFormatException e){
            redirectAttributes.addFlashAttribute("globalError", "Выбран не коректный период или бокс");
            return "redirect:/admin/statistic/period";
        }


    }
}
