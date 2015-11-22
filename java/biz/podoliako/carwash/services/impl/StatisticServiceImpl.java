package biz.podoliako.carwash.services.impl;


import biz.podoliako.carwash.dao.DaoFactory;
import biz.podoliako.carwash.models.entity.CarWash;
import biz.podoliako.carwash.services.StatisticService;
import biz.podoliako.carwash.view.GeneralStatInBox;
import biz.podoliako.carwash.view.GeneralStatInCarWash;
import biz.podoliako.carwash.view.statistic.OrderForDetailStatisticInBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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

    @Override
    public GeneralStatInCarWash getGeneralStatForCarWash(Date from, Date to, Integer washId) {
        Integer boxAmount = daoFactory.getCarWashDao().selectCarWash(washId).getBoxAmount();
        GeneralStatInCarWash generalStatInCarWash = new GeneralStatInCarWash();
        for (int i=1; i<=boxAmount; i++ ){
            GeneralStatInBox generalStatInBox = daoFactory.getStatisticDao().selectGeneralStatisticForBox(from, to, washId, i);
            BigDecimal generalSalaryInBox = daoFactory.getStatisticDao().selectGeneralSalaryForBox(from, to, washId, i);
            generalStatInBox.setSalary(generalSalaryInBox);
            generalStatInCarWash.addGeneralStatInBox(generalStatInBox);
        }

        return generalStatInCarWash;
    }

    @Override
    public void validateBoxNumber(Integer carWashId, Integer boxNumber) {
        CarWash carWash = daoFactory.getCarWashDao().selectCarWash(carWashId);

        if (boxNumber<1 || boxNumber>carWash.getBoxAmount()) throw new NumberFormatException();

    }

    @Override
    public List<OrderForDetailStatisticInBox> getDetailStatForBox(Integer washId, Integer boxNumber, Date from, Date to) {

        return daoFactory.getStatisticDao().selectDetailStatisticForBox(washId, boxNumber, from, to);

    }


}
