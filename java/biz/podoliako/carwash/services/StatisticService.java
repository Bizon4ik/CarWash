package biz.podoliako.carwash.services;


import biz.podoliako.carwash.services.entity.CalendarPeriod;

import java.util.Date;

public interface StatisticService {

    Date addToDateTime(Date date, Integer hour, Integer min);

}
