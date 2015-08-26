package biz.podoliako.carwash.services;

import biz.podoliako.carwash.models.entity.Category;
import biz.podoliako.carwash.models.pojo.CategoryFormErrors;


import java.sql.SQLException;
import java.util.List;


public interface CategoryService {
    public void addCategory(Category category) throws SQLException;

    public List<Category> selectAllCategory(Integer ownerId) throws SQLException;

    public void deleteCategory(String id) throws SQLException;

    public void modifyCategory();

    public CategoryFormErrors validateCategoryParam(Category carWash) throws SQLException;
}
