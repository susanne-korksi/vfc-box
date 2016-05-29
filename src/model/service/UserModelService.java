package model.service;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

import model.User;

public class UserModelService extends AbstractModelService
{
	public UserModelService(String dbPath) {
		super(dbPath);
	}

	/**
	 * @param String name
	 * @return User|null
	 */
	public User getUserByUserName(String name) 
	{
		if (name.length() == 0) {
			return null;
		}
		
		User user = new User();
		
		try {
			Statement statement = this.getStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM user WHERE name = '" + name + "'");
			
			if (!resultSet.next()) {
				return null;
			}
			
			user.setId(resultSet.getLong("id"));
			user.setName(resultSet.getString("name"));
			user.setCreated(UserModelService.sdf.parse(resultSet.getString("created"))); 
			user.setModified(UserModelService.sdf.parse(resultSet.getString("modified"))); 
			
		} catch (Exception e) {
			return null;
		}
		
		return user;
	}
	
	/**
	 * @param User user
	 * @return boolean
	 */
	public boolean updateUser(User user) 
	{
		if (user.getId() == null) {
			return false;
		}
		
		try {
			String modified = UserModelService.sdf.format(user.getModified());
			
			String sql = "UPDATE user SET "
					+ "name = '" + user.getName() + "',"
					+ "modified = '" + modified + "'"
					+ "WHERE id = " + user.getId();
		
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
	 * @param User user
	 * @return boolean
	 */
	public boolean createtUser(User user) 
	{	
		try {
						
			Date now = new Date();  	
			String created = UserModelService.sdf.format(now);
			Long longTime = new Long(now.getTime()/1000);
			
			String sql = "INSERT INTO user (id, name, created, modified) VALUES "
					+ "('" + longTime + "',"
					+ "'" + user.getName() + "',"
					+ "'" + created + "'," 
					+ "'" + created + "')"; 
		
			user.setId(longTime);
			user.setCreated(now);
			user.setModified(now);
			
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
	 * @param User user
	 * @return
	 */
	public boolean deleteUser(Long user_id) 
	{
		if (user_id == null) {
			return false;
		}
		
		try {
			String sql = "DELETE FROM user WHERE id = " + user_id;
		
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
