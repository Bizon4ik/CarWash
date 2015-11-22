package biz.podoliako.carwash.dao;


import biz.podoliako.carwash.view.GeneralStatInBox;
import biz.podoliako.carwash.view.statistic.OrderForDetailStatisticInBox;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface StatisticDao {
    GeneralStatInBox selectGeneralStatisticForBox(Date from, Date to, Integer washId, int i);

    List<OrderForDetailStatisticInBox> selectDetailStatisticForBox(Integer washId, Integer boxNumber, Date from, Date to);

    BigDecimal selectGeneralSalaryForBox(Date from, Date to, Integer washId, int i);
}
