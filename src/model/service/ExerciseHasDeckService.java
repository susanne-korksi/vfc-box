package model.service;

import java.sql.ResultSet;
import java.sql.Statement;

import model.ExerciseHasDeck;

public class ExerciseHasDeckService extends AbstractModelService {

	public ExerciseHasDeckService(String dbPath) {
		super(dbPath);

	}
	
	/**
	 * @param int id
	 * @return ExerciseHasDeck|null
	 */
	public ExerciseHasDeck getDeckHaseCategory(Long exercise_id, Long deck_id) 
	{
		if (exercise_id == null || deck_id == null) {
			return null;
		}
		
		ExerciseHasDeck exerciseHasDeck = new ExerciseHasDeck();
		
		try {
			Statement statement = this.getStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM exercise_has_deck "
															+ "WHERE exercise_id = '" + exercise_id + "'"
															+ "AND deck_id = '" + deck_id + "'");
			
			if (!resultSet.next()) {
				return null;
			}
			
			exerciseHasDeck.setExerciseId(resultSet.getLong("exercise_id"));
			exerciseHasDeck.setDeckId(resultSet.getLong("deck_id"));
			
		} catch (Exception e) {
			return null;
		}
		
		return exerciseHasDeck;
	}
	
	/**
	 * 
	 * @param ExerciseHasDeck exerciseHasDeck
	 * @return boolean
	 */
	public boolean createDeckHasCategory(Long exercise_id, Long deck_id) 
	{	
		if (exercise_id == null || deck_id == null) {
			return false;
		}
		
		try {

			String sql = "INSERT INTO exercise_has_deck (exercise_id, deck_id) VALUES "
					+ "('" + exercise_id + "',"
					+ "'" + deck_id + "')";
			
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
