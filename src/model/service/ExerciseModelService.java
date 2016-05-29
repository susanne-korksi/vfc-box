package model.service;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

import model.Exercise;

public class ExerciseModelService extends AbstractModelService {

	public ExerciseModelService(String dbPath) {
		super(dbPath);

	}
	
	/**
	 * @param Long id
	 * @return Exercise|null
	 */
	public Exercise getExerciseById(Long id) 
	{
		if (id == null) {
			return null;
		}
		
		Exercise exercise = new Exercise();
		
		try {
			Statement statement = this.getStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM exercise WHERE id = '" + id + "'");
			
			if (!resultSet.next()) {
				return null;
			}
			
			exercise.setId(resultSet.getLong("id"));
			exercise.setDate(DeckModelService.sdf.parse(resultSet.getString("date")));
			exercise.setUserId(resultSet.getLong("user_id"));
			
		} catch (Exception e) {
			return null;
		}
		
		return exercise;
	}
	
	/**
	 * 
	 * @param Exercise exercise
	 * @return boolean
	 */
	public boolean createtExercise(Exercise exercise) 
	{	
		if (exercise == null) {
			return false;
		}
		
		try {
						
			Date now = new Date();  	
			String date = ExerciseModelService.sdf.format(now);
			Long longTime = new Long(now.getTime()/1000);
			
			String sql = "INSERT INTO exercise (id, date, user) VALUES "
					+ "('" + longTime + "',"
					+ "'" + date + "',"
					+ "'" + exercise.getUserId() + "')";
		
			exercise.setId(longTime);
			exercise.setDate(now);
			
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

}
