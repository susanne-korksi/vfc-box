package model.service;

import java.sql.ResultSet;
import java.sql.Statement;

import model.DeckHasCategory;

public class DeckHasCategoryService extends AbstractModelService {

	public DeckHasCategoryService(String dbPath) {
		super(dbPath);

	}
	
	/**
	 * @param int id
	 * @return DeckHasCategory|null
	 */
	public DeckHasCategory getDeckHaseCategory(Long deck_id, Long category_id) 
	{
		if (deck_id == null || category_id == null) {
			return null;
		}
		
		DeckHasCategory deckHasCategory = new DeckHasCategory();
		
		try {
			Statement statement = this.getStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM deck_has_category "
															+ "WHERE deck_id = '" + deck_id + "'"
															+ "AND category_id = '" + category_id + "'");
			
			if (!resultSet.next()) {
				return null;
			}
			
			deckHasCategory.setDeckId(resultSet.getLong("deck_id"));
			deckHasCategory.setCategoryId(resultSet.getLong("category_id"));
			
		} catch (Exception e) {
			return null;
		}
		
		return deckHasCategory;
	}
	
	/**
	 * 
	 * @param DeckHasCategory deckHasCategory
	 * @return boolean
	 */
	public boolean createDeckHasCategory(Long deck_id, Long category_id) 
	{	
		if (deck_id == null || category_id == null) {
			return false;
		}
		
		try {

			String sql = "INSERT INTO deck_has_category (deck_id, category_id) VALUES "
					+ "('" + deck_id + "',"
					+ "'" + category_id + "')";
			
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
