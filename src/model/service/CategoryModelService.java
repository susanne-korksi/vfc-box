package model.service;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

import model.Category;

public class CategoryModelService extends AbstractModelService {

	public CategoryModelService(String dbPath) {
		super(dbPath);

	}
	
	/**
	 * @param int id
	 * @return Category|null
	 */
	public Category getCategoryById(Long id) 
	{
		if (id == null) {
			return null;
		}
		
		Category category = new Category();
		
		try {
			Statement statement = this.getStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM category WHERE id = '" + id + "'");
			
			if (!resultSet.next()) {
				return null;
			}
			
			category.setId(resultSet.getLong("id"));
			category.setName(resultSet.getString("name"));
			category.setDescription(resultSet.getString("description"));
			category.setCreated(CategoryModelService.sdf.parse(resultSet.getString("created"))); 
			category.setModified(CategoryModelService.sdf.parse(resultSet.getString("modified")));
			category.setUserId(resultSet.getLong("user_id"));
			
		} catch (Exception e) {
			return null;
		}
		
		return category;
	}
	
	/**
	 * @param User user
	 * @return Category[]|null
	 */
	public Category getAllCategorysByUser(Long user_id) 
	{
		if (user_id == null) {
			return null;
		}
		
		Category category = new Category();
		
		try {
			Statement statement = this.getStatement();
			ResultSet resultSet = statement.executeQuery(""
					+ "SELECT * "
					+ "FROM category "
					+ "WHERE user_id = '" + user_id + "'");
			
			if (!resultSet.next()) {
				return null;
			}
			
		} catch (Exception e) {
			return null;
		}
		
		return category;
	}
	
	/**
	 * @param Category category
	 * @return boolean
	 */
	public boolean updateCategory(Category category) 
	{
		if (category == null) {
			return false;
		}
		
		try {
			String modified = CategoryModelService.sdf.format(category.getModified());
			
			String sql = "UPDATE category SET "
					+ "name = '" + category.getName() + "',"
					+ "description = '" + category.getDescription() + "',"
					+ "modified = '" + modified + "'"
					+ "WHERE id = " + category.getId();
		
			Statement statement = this.getStatement();
			if (statement.executeUpdate(sql) == 1) {
				return true;
			}
		} catch (Exception e) {
			/** @TODO implement logging */
			return false;
		}
		
		return false;
	}
	
	/**
	 * 
	 * @param Category category
	 * @return boolean
	 */
	public boolean createtCategory(Category category) 
	{	
		if (category == null) {
			return false;
		}
		
		try {
						
			Date now = new Date();  	
			String created = CategoryModelService.sdf.format(now);
			Long longTime = new Long(now.getTime()/1000);
			
			String sql = "INSERT INTO category (id, name, description, created, modified, user_id) VALUES "
					+ "('" + longTime + "',"
					+ "'" + category.getName() + "'," 
					+ "'" + category.getDescription() + "',"
					+ "'" + created + "'," 
					+ "'" + created + "'," 
					+ "'" + category.getUserId() + "')"; 
		
			category.setId(longTime);
			category.setCreated(now);
			category.setModified(now);
			
			Statement statement = this.getStatement();
			if (statement.executeUpdate(sql) == 1) {
				System.out.println("drin");
				return true;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
		
		return false;
	}
	
	
	/**
	 * @param Category category
	 * @return boolean
	 */
	public boolean deleteCategory(Long category_id) 
	{
		if (category_id == null) {
			return false;
		}
		
		try {
			String sql = "DELETE FROM category WHERE id = " + category_id;
		
			Statement statement = this.getStatement();
			if (statement.executeUpdate(sql) == 1) {
				return true;
			}
		} catch (Exception e) {
			/** @TODO implement logging */
			return false;
		}
		
		return false;
	}

}
