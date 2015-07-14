package biz.podoliako.carwash.models.impl;

import biz.podoliako.carwash.dao.CategoryDao;
import biz.podoliako.carwash.dao.DaoFactory;
import biz.podoliako.carwash.dao.pojo.Category;
import biz.podoliako.carwash.models.CategoryModel;
import biz.podoliako.carwash.models.pojo.CategoryFormErrors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Component("CategoryModel")
public class CategoryModelImpl implements CategoryModel {

    private DaoFactory daoFactory;

    @Autowired
    public CategoryModelImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    public void addCategory(Category category) throws SQLException {
        category.setDateOfCreation(new Date());
        daoFactory.getCategoryDao().addCategory(category);
    }

    @Override
    public List<Category> selectAllCategory(Integer ownerId) throws SQLException {
        return daoFactory.getCategoryDao().selectAllCategories(ownerId);
    }

    @Override
    public void deleteCategory(String id) throws SQLException {
        Integer categoryId = Integer.valueOf(id);
        daoFactory.getCategoryDao().deleteCategory(categoryId);

    }

    @Override
    public void modifyCategory() {

    }

    @Override
    public CategoryFormErrors validateCategoryParam(Category category) throws SQLException {
       CategoryFormErrors categoryFormErrors = new CategoryFormErrors();

       categoryFormErrors.setNameErrorMsg(validateCategoryName(category.getName()));
       categoryFormErrors.setDescriptionErrorMsg(validateCategoryDescription(category.getDescription()));

      return  categoryFormErrors;
    }

    private String validateCategoryDescription(String description) {
        if (description == null) return "Укажите описание категории";
        description = description.trim();
        if (description.equals("")) return "Укажите описание категории";
        if (description.length() > CategoryDao.DESCRIPTION_MAX_LENGTH) return "Слишком длинное описание";
        return null;
    }

    private String validateCategoryName(String name) throws SQLException {
        if (name == null) return "Укажите название категории";
        name = name.trim();
        if (name.equals("")) return "Укажите название категории";
        if (name.length() > CategoryDao.NAME_MAX_LENGTH) return "Слишком большое название";

        if(daoFactory.getCategoryDao().isCategoryNameExist(name)) return "Категория с таким название уже есть";
        return null;
    }
}
