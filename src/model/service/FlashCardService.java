package model.service;

import java.sql.ResultSet;  		// definiert wie die Rückgabewerte strukturiert sind
import java.sql.Statement;  		// Aufbau des handeln der SQL query

public class FlashCardService extends AbstractModelService
{
	public FlashCardService(String dbPath) 
	{
		super(dbPath);
		if (!this.checkTableStructure()) {
			this.createTableStructure();
		}
	}
	
	public boolean checkTableStructure() 
	{
		try {
			Statement statement = this.getStatement();  
		
			ResultSet resultSet = statement.executeQuery("SELECT name FROM sqlite_master WHERE type='table'");

			if (resultSet.next()) {
				return true;
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {				
				this.closeConnection();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		return false;
	}
	
	
	private void createTableStructure() {	
		try {
			Statement statement = this.getStatement();
			String[] sql = this.initializeTables();
			
			for (String query : sql) {
				statement.execute(query);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	private String[] initializeTables() 
	{
		String[] sql = {
				"CREATE TABLE user(id INT PRIMARY KEY NOT NULL,name VARCHAR(80) UNIQUE NOT NULL,created TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL)",
				"CREATE TABLE category(id INT PRIMARY KEY NOT NULL,name VARCHAR(80),description VARCHAR(500),created TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,user_id INT,FOREIGN KEY(user_id) REFERENCES user(id))",
				"CREATE TABLE exercise(id INT PRIMARY KEY NOT NULL,date DATE NOT NULL,user_id INT,FOREIGN KEY(user_id) REFERENCES user(id))",
				"CREATE TABLE exam(id INT PRIMARY KEY NOT NULL,date DATE NOT NULL,user_id INT,FOREIGN KEY(user_id) REFERENCES user(id))",
				"CREATE TABLE deck(id INT PRIMARY KEY NOT NULL,name VARCHAR(80),description VARCHAR(500),created TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,user_id INT,FOREIGN KEY(user_id) REFERENCES user(id))",
				"CREATE TABLE exercise_has_deck(deck_id INT,exercise_id INT,FOREIGN KEY(deck_id) REFERENCES deck(id),FOREIGN KEY(exercise_id) REFERENCES exercise(id))",
				"CREATE TABLE exam_has_deck(deck_id INT,exam_id INT,FOREIGN KEY(deck_id) REFERENCES deck(id),FOREIGN KEY(exam_id) REFERENCES exam(id))",
				"CREATE TABLE deck_has_category(deck_id INT,category_id INT,FOREIGN KEY(deck_id) REFERENCES deck(id),FOREIGN KEY(category_id) REFERENCES category(id))",
				"CREATE TABLE card(id INT PRIMARY KEY NOT NULL,side_a VARCHAR(255),side_b VARCHAR(255),created TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,deck_id INT,FOREIGN KEY(deck_id) REFERENCES deck(id))"
		};
		
		return sql;
	}
	
	
	
	
	
}
