package model.service;

import java.sql.ResultSet;
import java.sql.Statement;

import model.ExamHasDeck;

public class ExamHasDeckService extends AbstractModelService {

	public ExamHasDeckService(String dbPath) {
		super(dbPath);

	}
	
	/**
	 * @param int id
	 * @return ExamHasDeck|null
	 */
	public ExamHasDeck getDeckHaseCategory(Long exam_id, Long deck_id) 
	{
		if (exam_id == null || deck_id == null) {
			return null;
		}
		
		ExamHasDeck examHasDeck = new ExamHasDeck();
		
		try {
			Statement statement = this.getStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM exam_has_deck "
															+ "WHERE exam_id = '" + exam_id + "'"
															+ "AND deck_id = '" + deck_id + "'");
			
			if (!resultSet.next()) {
				return null;
			}
			
			examHasDeck.setExamId(resultSet.getLong("exam_id"));
			examHasDeck.setDeckId(resultSet.getLong("deck_id"));
			
		} catch (Exception e) {
			return null;
		}
		
		return examHasDeck;
	}
	
	/**
	 * 
	 * @param ExamHasDeck examHasDeck
	 * @return boolean
	 */
	public boolean createDeckHasCategory(Long exam_id, Long deck_id) 
	{	
		if (exam_id == null || deck_id == null) {
			return false;
		}
		
		try {

			String sql = "INSERT INTO exam_has_deck (exam_id, deck_id) VALUES "
					+ "('" + exam_id + "',"
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
