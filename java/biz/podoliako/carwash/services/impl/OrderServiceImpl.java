package biz.podoliako.carwash.services.impl;



import biz.podoliako.carwash.dao.DaoFactory;
import biz.podoliako.carwash.models.PaymentMethod;
import biz.podoliako.carwash.models.entity.*;
import biz.podoliako.carwash.services.OrderService;
import biz.podoliako.carwash.services.entity.ServiceInOrder;
import biz.podoliako.carwash.view.OrderForm;
import biz.podoliako.carwash.view.OrderFormData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service("OrderService")
public class OrderServiceImpl implements OrderService {
    private DaoFactory daoFactory;

    @Autowired
    public OrderServiceImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public OrderFormData getOrderFormData(Integer carWashId, Integer categoryId, User user) throws SQLException {
        Category category = daoFactory.getCategoryDao().selectCategory(categoryId);
        List<CarBrand> carBrandList = daoFactory.getCarBrandDao().selectAllCarBrands(user.getOwnerId());
        List<Integer> availableBoxList = daoFactory.getOrderDao().selectAvailableBox(carWashId);
        List<CarWashService> carWashServiceList
                = daoFactory.getServiceDao().selectAllCarWashServiceByCategory(categoryId, carWashId, user.getOwnerId());

        OrderFormData orderFormData = new OrderFormData();
        orderFormData.setCategory(category);
        orderFormData.setCarBrandList(carBrandList);
        orderFormData.setAvailableBoxList(availableBoxList);
        orderFormData.setCarWashServices(carWashServiceList);


        return orderFormData;
    }

    @Override
    public OrderForm createOrderFormWithStaticFilds(Car car) {
        OrderForm orderForm = new OrderForm();
            orderForm.setCarNumber(car.getNumber());
            orderForm.setCategoryId(car.getCategoryId());
            orderForm.setPaymentMethod(PaymentMethod.Cash);

                List<ServiceInOrder> serviceInOrderList = new ArrayList<>();
                serviceInOrderList.add(new ServiceInOrder());
            orderForm.setServiceIdList(serviceInOrderList);
        return orderForm;
    }

    @Override
    public void addOrder(OrderForm orderForm) {


        Integer carId = addPreliminaryCar(orderForm);

        System.out.println(carId);

    }

    private Integer addPreliminaryCar(OrderForm orderForm) {
        Car car = new Car();
            car.setNumber(orderForm.getCarNumber().trim().toUpperCase());
            car.setBrandId(orderForm.getCarBrandId());
            car.setCategoryId(orderForm.getCategoryId());
            car.setIsFinal(false);

        return daoFactory.getClientCarDao().addCar(car);
    }
}
