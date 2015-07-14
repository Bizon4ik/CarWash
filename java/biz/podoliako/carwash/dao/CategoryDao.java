package biz.podoliako.carwash.dao;

import biz.podoliako.carwash.dao.pojo.CarWash;
import biz.podoliako.carwash.dao.pojo.Category;

import java.sql.SQLException;
import java.util.List;

public interface CategoryDao {
    public final static Integer NAME_MAX_LENGTH = 100;
    public final static Integer DESCRIPTION_MAX_LENGTH = 200;


    public void addCategory(Category category) throws SQLException;

    public void deleteCategory(Integer categoryId) throws SQLException;

    public void modifyCategory();

    public boolean isCategoryNameExist(String name) throws SQLException;

    public CarWash selectCategory(Integer categoryId);

    public List<Category> selectAllCategories(Integer ownerId) throws SQLException;
}
