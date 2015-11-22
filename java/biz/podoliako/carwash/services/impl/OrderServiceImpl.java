package biz.podoliako.carwash.services.impl;



import biz.podoliako.carwash.dao.DaoFactory;
import biz.podoliako.carwash.models.PaymentMethod;
import biz.podoliako.carwash.models.entity.*;
import biz.podoliako.carwash.services.OrderService;
import biz.podoliako.carwash.services.entity.ServiceInOrder;
import biz.podoliako.carwash.services.entity.WasherManInBoxWithRate;
import biz.podoliako.carwash.view.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.*;

@Service("OrderService")
public class OrderServiceImpl implements OrderService {
    private DaoFactory daoFactory;

    @Autowired
    public OrderServiceImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public OrderFormData getDataForOrderFrom(OrderForm orderForm) throws SQLException {
        Category category = daoFactory.getCategoryDao().selectCategory(orderForm.getCategoryId());
        List<CarBrand> carBrandList = getCarBrandList(orderForm);


        List<Integer> availableBoxList = daoFactory.getOrderDao().selectAvailableBox(orderForm.getCarWashId());
        if (orderForm.getBoxNumber() != null){
            availableBoxList.add(orderForm.getBoxNumber());
        }
        List<CarWashService> carWashServiceList
                = daoFactory.getServiceDao().selectAllCarWashServiceByCategory(orderForm.getCategoryId(), orderForm.getCarWashId(), orderForm.getOwnerId());

        String carBrandName = getCarBrandName(orderForm);

        OrderFormData orderFormData = new OrderFormData();
        orderFormData.setCategory(category);
        orderFormData.setCarBrandList(carBrandList);
        orderFormData.setAvailableBoxList(availableBoxList);
        orderFormData.setCarWashServices(carWashServiceList);
        orderFormData.setCarBrandName(carBrandName);

        return orderFormData;
    }

    private String getCarBrandName(OrderForm orderForm) {
        if (orderForm.getCarBrandId() == null ){
            return "N/A";
        }else {
            CarBrand carBrand = daoFactory.getCarBrandDao().selectBrandById(orderForm.getCarBrandId());
            return carBrand.getName();
        }

    }

    private List<CarBrand> getCarBrandList(OrderForm orderForm) throws SQLException {
        if (orderForm.getCarBrandId() != null){
            return  null;
        }else {
            return daoFactory.getCarBrandDao().selectAllCarBrands(orderForm.getOwnerId());
        }

    }

    @Override
    public OrderForm createOrderFormForChanges(String id){
        Integer orderId = Integer.valueOf(id);
        OrderForm orderForm = new OrderForm();

        Order order = daoFactory.getOrderDao().selectOrderById(orderId);
        Car car = daoFactory.getClientCarDao().selectCarWithClient(order.getCarId());

        orderForm.setCarNumber(car.getNumber());
        orderForm.setCategoryId(car.getCategoryId());
        orderForm.setCarBrandId(car.getBrandId());
        orderForm.setBoxNumber(order.getBox());
        orderForm.setPaymentMethod(order.getPaymentMethod());

        List<ServiceInOrder> serviceInOrderList = getServiceInOrderList(orderId);

        orderForm.setServiceIdList(serviceInOrderList);
        orderForm.setOrderId(orderId);

        return orderForm;
    }

    @Override
    public void updateOrder(OrderForm finalOrderForm) {
        Integer orderId = finalOrderForm.getOrderId();
        updateBoxInOrder(orderId, finalOrderForm.getBoxNumber());
        deleteServicesInOrder(orderId);
        insertOrderedServices(finalOrderForm, orderId);
        deleteGangInOrder(orderId);
        insertGangInOrder(finalOrderForm, finalOrderForm.getCarWashId(), orderId);


    }

    private void deleteGangInOrder(Integer orderId) {
        daoFactory.getOrderDao().deleteWasherManInOrder(orderId);
    }

    @Override
    public void closeOrder(String id, Integer carWashId, Integer userId) {
        Integer orderId = Integer.valueOf(id.trim());

        daoFactory.getOrderDao().closeOrder(orderId, carWashId, userId, new Date());
    }

    @Override
    public void deleteOrder(String id, Integer carWashId, Integer userId) {
        Integer orderId = Integer.valueOf(id.trim());

        daoFactory.getOrderDao().deleteOrder(orderId, carWashId, userId, new Date());

    }

    @Override
    public Boolean isOrderOpen(String idOrder) {
        Integer id = Integer.valueOf(idOrder);
        return daoFactory.getOrderDao().isOrderOpen(id);
    }

    private void deleteServicesInOrder(Integer orderId) {
        daoFactory.getOrderDao().deleteServicesInOrder(orderId);
    }

    private void updateBoxInOrder(Integer id, Integer boxNumber) {
            daoFactory.getOrderDao().updateBoxInOrder(id, boxNumber);
    }

    private List<ServiceInOrder> getServiceInOrderList(Integer id) {
        return daoFactory.getOrderDao().selectAllServiceInOrder(id);
    }


    @Override
    public OrderForm createBaseOrderForm(Car car) {
        OrderForm orderForm = new OrderForm();
            orderForm.setCarNumber(car.getNumber());

            Integer categoryId = getCarCategoryId(car.getNumber());
            if (categoryId == null){
                orderForm.setCategoryId(car.getCategoryId());
            }else {
                orderForm.setCategoryId(categoryId);
            }


            Integer carBrandId = getCarBrandId(car);
            orderForm.setCarBrandId(carBrandId);

            orderForm.setPaymentMethod(PaymentMethod.Cash);

                List<ServiceInOrder> serviceInOrderList = new ArrayList<>();
                serviceInOrderList.add(new ServiceInOrder());
            orderForm.setServiceIdList(serviceInOrderList);

        return orderForm;
    }

    private Integer getCarCategoryId(String number) {
        Car car = daoFactory.getClientCarDao().selectCarByNumber(number);
        return car.getCategoryId();

    }

    private Integer getCarBrandId(Car car) {
        String number = car.getNumber().trim().toUpperCase();
        Car carInDB = daoFactory.getClientCarDao().selectCarByNumber(number);
        return carInDB.getBrandId();
    }

    @Override
    public void addOrder(OrderForm orderForm) {
        Car car = daoFactory.getClientCarDao().selectCarByNumber(orderForm.getCarNumber().trim().toUpperCase());
        Integer carId;

        if (car.getId() == null){
            carId = addPreliminaryCar(orderForm);
        }else {
            carId = car.getId();
        }

        Integer orderId = createOrder(orderForm, carId, orderForm.getUserId(), orderForm.getCarWashId());
        insertOrderedServices(orderForm, orderId);
        insertGangInOrder(orderForm, orderForm.getCarWashId(), orderId);

    }

    @Override
    public OrderInBox getOrderInBox(Integer carWashId, Integer box) {
        OrderInBox orderInBox = new OrderInBox();

        Order order = getOrder(carWashId, box);
            orderInBox.setOrder(order);


        if (order.getId() != null){
            CarViewWithClient carViewWithClient = getCarWithClient(order.getCarId());
            orderInBox.setCarViewWithClient(carViewWithClient);

            List<OrderedService> orderedServices = getOrderedServiceList(order.getId());
            orderInBox.setOrderedServices(orderedServices);

            BigDecimal totalPrice = getTotalPrice(orderedServices);
            orderInBox.setTotalPrice(totalPrice);

            Integer discount = 0;
            orderInBox.setDiscount(discount);

            BigDecimal action = new BigDecimal(0);
            orderInBox.setAction(action);

            Double discountCoefficient = (double) (100-discount)/100;
            BigDecimal priceAfterDiscount = totalPrice.multiply(new BigDecimal(discountCoefficient));

            BigDecimal priceAfterDiscountAndAction = priceAfterDiscount.subtract(action);
            orderInBox.setFinalPrice(priceAfterDiscountAndAction.setScale(2, BigDecimal.ROUND_HALF_EVEN));

        }

        orderInBox.setWasherManSet(daoFactory.getUserDao().selectWasherManInBoxWithName(carWashId, box));

        return orderInBox;
    }

    private BigDecimal getTotalPrice(List<OrderedService> orderedServices) {
            BigDecimal totalPrice = new BigDecimal(0);

            for (OrderedService s : orderedServices) {
                BigDecimal servicePrice = s.getPrice().add(s.getAdditionPrice());
                BigDecimal serviceCount = new BigDecimal(s.getCount());

                totalPrice = totalPrice.add(servicePrice.multiply(serviceCount));
            }

            return totalPrice;
    }

    private List<OrderedService> getOrderedServiceList(Integer orderId) {
        return daoFactory.getOrderDao().selectOrderedService(orderId);
    }

    private CarViewWithClient getCarWithClient(Integer carId) {
        return daoFactory.getClientCarDao().selectCarWithClient(carId);
    }

    private Order getOrder(Integer carWashId, Integer box) {
        return daoFactory.getOrderDao().selectOrder(carWashId, box);
    }

    @Override
    public List<OrderInBox> getCurrentOrdersInAllBoxes(Integer carWashId) {
        CarWash carWash = daoFactory.getCarWashDao().selectCarWash(carWashId);
        List<OrderInBox> orderInBoxesList = new ArrayList<>(carWash.getBoxAmount());

        for(int box=1; box<=carWash.getBoxAmount(); box++){
            orderInBoxesList.add(getOrderInBox(carWashId, box));
        }

        return orderInBoxesList;
    }

    @Override
    public OrderForm uniteOrderForms(OrderForm orderFormInSession, OrderForm orderForm) {
        if (orderFormInSession.getCarBrandId() == null) {
            orderFormInSession.setCarBrandId(orderForm.getCarBrandId());
        }

        orderFormInSession.setBoxNumber(orderForm.getBoxNumber());
        orderFormInSession.setPaymentMethod(orderForm.getPaymentMethod());
        orderFormInSession.setServiceIdList(orderForm.getServiceIdList());

        return orderFormInSession;
    }


    private void insertGangInOrder(OrderForm orderForm, Integer carWashId, Integer orderId) {
        BigDecimal budgetForSale = daoFactory.getOrderDao().getSalaryForOrder(orderId);

        Set<WasherManInBoxWithRate> washerManInBoxSet = daoFactory.getUserDao().selectAllWasherManInBoxWithRate(carWashId, orderForm.getBoxNumber());

        Map<Integer, BigDecimal> washerManIdWithSalaryForOrder = getWasherManIdWithSalary(budgetForSale, washerManInBoxSet, orderId);

        for (Map.Entry entry : washerManIdWithSalaryForOrder.entrySet()){
            daoFactory.getOrderDao().insertWasherManInOrder((Integer) entry.getKey(), (BigDecimal)entry.getValue(), orderId);
        }

    }

    private Map<Integer, BigDecimal> getWasherManIdWithSalary(BigDecimal budgetForSale,
                                                              Set<WasherManInBoxWithRate> washerManInBoxSet,

                                                              Integer orderId) {
        Integer amountOfWasherMans = washerManInBoxSet.size();
        Map<Integer, BigDecimal>  washerManIdWithSalary = new HashMap<>(amountOfWasherMans);
        Boolean isItDayOrder = daoFactory.getOrderDao().isItDayOrder(orderId);
        BigDecimal trainerBudget = new BigDecimal(0);
        List<Integer> trainerListId = new ArrayList<>(amountOfWasherMans);

        BigDecimal avgSalaryPerPerson = budgetForSale.divide(BigDecimal.valueOf(amountOfWasherMans));

        for (WasherManInBoxWithRate w : washerManInBoxSet){
            Integer manFee;
            if (isItDayOrder){
                manFee = w.getDayCommission();
            }else {
                manFee = w.getNightCommission();
            }

            if (manFee < 100) {
                washerManIdWithSalary.put(w.getId(),
                        avgSalaryPerPerson.multiply(BigDecimal.valueOf(manFee)).divide(BigDecimal.valueOf(100)));

                trainerBudget = trainerBudget.add(avgSalaryPerPerson.multiply(BigDecimal.valueOf(100-manFee)).divide(BigDecimal.valueOf(100)));

            }else {
                trainerListId.add(w.getId());
                if (manFee == 100){
                    washerManIdWithSalary.put(w.getId(), avgSalaryPerPerson);
                }else {
                    washerManIdWithSalary.put(w.getId(),
                            avgSalaryPerPerson.add(avgSalaryPerPerson.multiply(BigDecimal.valueOf(manFee-100)).divide(BigDecimal.valueOf(100))));
                }

            }

        }


        if (trainerBudget.compareTo(new BigDecimal(0)) != 0) {
            BigDecimal additionFee = trainerBudget.divide(BigDecimal.valueOf(trainerListId.size()));

            for (Integer id: trainerListId){
                System.out.println("id = " + id);
                BigDecimal finalSalary = washerManIdWithSalary.get(id);
                finalSalary = finalSalary.add(additionFee);
                washerManIdWithSalary.put(id, finalSalary);
            }

        }

        return washerManIdWithSalary;
    }

    private void insertOrderedServices(OrderForm orderForm, Integer orderId) {
            for (ServiceInOrder s: orderForm.getServiceIdList()) {
                    daoFactory.getOrderDao().insetOrderedService(s, orderId);
            }
    }

    private Integer createOrder(OrderForm orderForm, Integer carId, Integer userId, Integer carWashId) {
            Order order = new Order();
                  order.setCarWashId(carWashId);
                  order.setBox(orderForm.getBoxNumber());
                  order.setCarId(carId);
                  order.setCreatedById(userId);
                  order.setDateOfCreation(new Date());
                  order.setPaymentMethod(orderForm.getPaymentMethod());

            return daoFactory.getOrderDao().insertOrder(order);
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
