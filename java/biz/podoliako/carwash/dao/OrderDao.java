package biz.podoliako.carwash.dao;


import biz.podoliako.carwash.models.entity.Order;
import biz.podoliako.carwash.models.entity.WasherManInBox;
import biz.podoliako.carwash.services.entity.ServiceInOrder;
import biz.podoliako.carwash.view.OrderedService;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface OrderDao {
    final static String ORDER_TABLE = "orders";
    final static String ORDERED_SERVICES_TABLE = "ordered_services";
    final static String WASHER_MAN_IN_ORDER_TABLE = "washer_man_in_order";

    List<Integer> selectAvailableBox(Integer carWashId);

    Integer insertOrder(Order order);

    void insetOrderedService(ServiceInOrder s, Integer orderId);

    List<ServiceInOrder> selectAllServiceInOrder(Integer orderId);

    void insertWasherManInOrder(Integer washerManId, BigDecimal salary, Integer orderId);

    Order selectOrder(Integer washId, Integer box);

    List<OrderedService> selectOrderedService(Integer orderId);

    Order selectOrderById(Integer id);

    void updateBoxInOrder(Integer orderId, Integer boxNumber);

    void deleteServicesInOrder(Integer orderId);

    void closeOrder(Integer id, Integer washId, Integer userId, Date dateOfClose);

    void deleteOrder(Integer id, Integer washId, Integer userId,  Date date);

    Boolean isOrderOpen(Integer id);

    BigDecimal getSalaryForOrder(Integer id);

    Boolean isItDayOrder(Integer id);

    void deleteWasherManInOrder(Integer orderId);
}
