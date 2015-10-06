package biz.podoliako.carwash.services.impl;


import biz.podoliako.carwash.dao.DaoFactory;
import biz.podoliako.carwash.services.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service("StatisticService")
public class StatisticServiceImpl implements StatisticService{

    private DaoFactory daoFactory;

    @Autowired
    public StatisticServiceImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }


    @Override
    public Date addToDateTime(Date date, Integer hour, Integer min) {
        Long day = date.getTime();
        Long newTime = day+(hour*60*60*1000)+(min*60*1000);

        return new Date(newTime);

    }
}
