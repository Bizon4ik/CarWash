package biz.podoliako.carwash.dao.impl;

import biz.podoliako.carwash.dao.CategoryDao;
import biz.podoliako.carwash.models.entity.Category;
import biz.podoliako.carwash.models.entity.Role;
import biz.podoliako.carwash.models.entity.User;
import biz.podoliako.carwash.services.ConnectionDB;
import biz.podoliako.carwash.services.exeption.NamingRuntimeException;
import biz.podoliako.carwash.services.exeption.SQLRuntimeException;
import biz.podoliako.carwash.services.impl.ConnectDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.naming.NamingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Component("CategoryDao")
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class CategoryDaoImpl implements CategoryDao {

    private Connection conn = null;
    private ConnectionDB connectionDB;

    @Autowired
    public CategoryDaoImpl(ConnectDB connectDB) throws SQLException, NamingException {
        this.conn = connectDB.getConnection();
        this.connectionDB = connectDB;
    }

    @Override
    public void addCategory(Category category) throws SQLException {
        String query = "INSERT INTO " + CATEGORY_TABLE_NAME +
                       " (name, description, date_of_creation, owner_id) VALUES " +
                       " (   ?,      ?,              ?,             ?  )"     ;

        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setString(1, category.getName());
        preparedStatement.setString(2, category.getDescription());
        preparedStatement.setObject(3, category.getDateOfCreation());
        preparedStatement.setInt(4, category.getOwnerId());

        preparedStatement.execute();
    }

    @Override
    public void deleteCategory(Integer categoryId) throws SQLException {
        String query = "DELETE FROM " + CATEGORY_TABLE_NAME + " WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(query);
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

        PreparedStatement ps = conn.prepareStatement(query);
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) result = true;

        return result;
    }

    @Override
    public Category selectCategory(Integer categoryId) {
        Connection connection = null;
        PreparedStatement ps = null;
        Category category = new Category();
        try {
            connection = connectionDB.getConnection();

            String query = "SELECT * FROM " + CATEGORY_TABLE_NAME + " WHERE id = ?";

            ps = connection.prepareStatement(query);
            ps.setInt(1, categoryId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                category.setId(rs.getInt("id"));
                category.setName(rs.getString("name"));
                category.setDescription(rs.getString("description"));
                category.setDateOfCreation(rs.getTimestamp("date_of_creation"));
                category.setOwnerId(rs.getInt("owner_id"));
            }

        } catch (SQLException e) {
            throw new SQLRuntimeException("SQL exception в методе selectCategory (CategoryDaoImpl) " + e);
        } catch (NamingException e) {
            throw new NamingRuntimeException("Naming exception в методе selectCategory (CategoryDaoImpl) " + e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }

                if (connection != null) {

                    connection.close();
                }
            }catch (SQLException e) {
                throw new SQLRuntimeException("SQL exception,  Cannot close connection or PreparedStatement, в методе selectCategory (CategoryDaoImpl) " + e);
            }
        }

        return category;
    }

    @Override
    public List<Category> selectAllCategories(Integer ownerId) throws SQLException {
        List<Category> categoryList = new ArrayList<>();
        String query = "SELECT * FROM " + CATEGORY_TABLE_NAME + " WHERE owner_id = " + ownerId + " ORDER BY name";
        Statement statement = conn.createStatement();
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
