package biz.podoliako.carwash.dao.impl;

import biz.podoliako.carwash.dao.CategoryDao;
import biz.podoliako.carwash.models.entity.CarWash;
import biz.podoliako.carwash.models.entity.Category;
import biz.podoliako.carwash.services.impl.ConnectDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.naming.NamingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component("CategoryDao")
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class CategoryDaoImpl implements CategoryDao {

    public final static String CATEGORY_TABLE_NAME = "category";

    private Connection connection = null;

    @Autowired
    public CategoryDaoImpl(ConnectDB connectDB) throws SQLException, NamingException {
        this.connection = ConnectDB.getPoolConnection();
    }

    public CategoryDaoImpl(Connection connection){
        this.connection = connection;
    }

    @Override
    public void addCategory(Category category) throws SQLException {
        String query = "INSERT INTO " + CATEGORY_TABLE_NAME +
                       " (name, description, date_of_creation, owner_id) VALUES " +
                       " (   ?,      ?,              ?,             ?  )"     ;

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, category.getName());
        preparedStatement.setString(2, category.getDescription());
        preparedStatement.setObject(3, category.getDateOfCreation());
        preparedStatement.setInt(4, category.getOwnerId());

        preparedStatement.execute();
    }

    @Override
    public void deleteCategory(Integer categoryId) throws SQLException {
        String query = "DELETE FROM " + CATEGORY_TABLE_NAME + " WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, categoryId);
        ps.execute();
    }

    @Override
    public void modifyCategory() {

    }

    @Override
    public boolean isCategoryNameExist(String name) throws SQLException {
        Boolean result = false;
        String query = "SELECT * FROM " + CATEGORY_TABLE_NAME + " WHERE name = ?";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) result = true;

        return result;
    }

    @Override
    public CarWash selectCategory(Integer categoryId) {
        return null;
    }

    @Override
    public List<Category> selectAllCategories(Integer ownerId) throws SQLException {
        List<Category> categoryList = new ArrayList<>();
        String query = "SELECT * FROM " + CATEGORY_TABLE_NAME + " WHERE owner_id = " + ownerId + " ORDER BY name";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(query);

        while (rs.next()) {
            Category category = new Category();

            category.setId(rs.getInt("id"));
            category.setName(rs.getString("name"));
            category.setDescription(rs.getString("description"));
            category.setDateOfCreation(rs.getTimestamp("date_of_creation"));
            category.setOwnerId(rs.getInt("owner_id"));

            categoryList.add(category);
        }
        return categoryList;
    }
}
