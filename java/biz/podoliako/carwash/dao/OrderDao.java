package biz.podoliako.carwash.dao;


import java.util.List;

public interface OrderDao {
    List<Integer> selectAvailableBox(Integer carWashId);
}
