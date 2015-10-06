package biz.podoliako.carwash.services;


import biz.podoliako.carwash.models.entity.Car;
import biz.podoliako.carwash.models.entity.User;
import biz.podoliako.carwash.view.OrderForm;
import biz.podoliako.carwash.view.OrderFormData;
import biz.podoliako.carwash.view.OrderInBox;

import java.sql.SQLException;
import java.util.List;

public interface OrderService {
    OrderFormData getDataForOrderFrom(OrderForm orderForm) throws SQLException;

    OrderForm createBaseOrderForm(Car car);

    void addOrder(OrderForm orderForm);

    OrderInBox getOrderInBox(Integer washId, Integer box);

    List<OrderInBox> getCurrentOrdersInAllBoxes(Integer washId);

    OrderForm uniteOrderForms(OrderForm orderFormInSession, OrderForm orderForm);

    OrderForm createOrderFormForChanges(String idOrder);

    void updateOrder(OrderForm finalOrderForm);

    void closeOrder(String orderId, Integer washId, Integer userId);

    void deleteOrder(String orderId, Integer washId, Integer userId);

    Boolean isOrderOpen(String idOrder);
}
