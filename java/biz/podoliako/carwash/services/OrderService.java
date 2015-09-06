package biz.podoliako.carwash.services;


import biz.podoliako.carwash.models.entity.Car;
import biz.podoliako.carwash.models.entity.User;
import biz.podoliako.carwash.view.OrderForm;
import biz.podoliako.carwash.view.OrderFormData;

import java.sql.SQLException;

public interface OrderService {
    OrderFormData getOrderFormData(Integer carWashId, Integer categoryId, User user) throws SQLException;

    OrderForm createOrderFormWithStaticFilds(Car car);

    void addOrder(OrderForm orderForm);
}
