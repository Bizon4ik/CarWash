package biz.podoliako.carwash.controllers.administrator;


import biz.podoliako.carwash.services.StatisticService;
import biz.podoliako.carwash.services.entity.CalendarPeriod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.Date;


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



        model.addAttribute("globalMsg", "все тип топ");
        System.out.println("----------------");
        System.out.println(calendarPeriod);

        return "admin/statistic/choose_period";
    }
}
