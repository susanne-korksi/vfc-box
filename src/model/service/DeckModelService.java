package model.service;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

import model.Deck;

public class DeckModelService extends AbstractModelService {

	public DeckModelService(String dbPath) {
		super(dbPath);

	}

	/**
	 * @param int id
	 * @return Deck|null
	 */
	public Deck getDeckById(Long id) 
	{
		if (id == null) {
			return null;
		}
		
		Deck deck = new Deck();
		
		try {
			Statement statement = this.getStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM deck WHERE id = '" + id + "'");
			
			if (!resultSet.next()) {
				return null;
			}
			
			deck.setId(resultSet.getLong("id"));
			deck.setName(resultSet.getString("name"));
			deck.setDescription(resultSet.getString("description"));
			deck.setCreated(DeckModelService.sdf.parse(resultSet.getString("created"))); 
			deck.setModified(DeckModelService.sdf.parse(resultSet.getString("modified")));
			deck.setUserId(resultSet.getLong("user_id"));
			
		} catch (Exception e) {
			return null;
		}
		
		return deck;
	}
	
	/**
	 * @param User user
	 * @return Deck[]|null
	 */
	public Deck getAllDecksByUser(Long user_id) 
	{
		if (user_id == null) {
			return null;
		}
		
		Deck deck = new Deck();
		
		try {
			Statement statement = this.getStatement();
			ResultSet resultSet = statement.executeQuery(""
					+ "SELECT * "
					+ "FROM deck "
					+ "WHERE user_id = '" + user_id + "'");
			
			if (!resultSet.next()) {
				return null;
			}
			
		} catch (Exception e) {
			return null;
		}
		
		return deck;
	}
	
	/**
	 * @param Deck deck
	 * @return boolean
	 */
	public boolean updateDeck(Deck deck) 
	{
		if (deck == null) {
			return false;
		}
		
		try {
			String modified = DeckModelService.sdf.format(deck.getModified());
			
			String sql = "UPDATE deck SET "
					+ "name = '" + deck.getName() + "',"
					+ "description = '" + deck.getDescription() + "',"
					+ "modified = '" + modified + "'"
					+ "WHERE id = " + deck.getId();
		
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
	 * @param Deck deck
	 * @return boolean
	 */
	public boolean createDeck(Deck deck) 
	{	
		if (deck == null) {
			return false;
		}
		
		try {
						
			Date now = new Date();  	
			String created = DeckModelService.sdf.format(now);
			Long longTime = new Long(now.getTime()/1000);
			
			String sql = "INSERT INTO deck (id, name, created, modified, user_id) VALUES "
					+ "('" + longTime + "',"
					+ "'" + deck.getName() + "'," 
					+ "'" + created + "'," 
					+ "'" + created + "'," 
					+ "'" + deck.getUserId() + "')"; 
		
			deck.setId(longTime);
			deck.setCreated(now);
			deck.setModified(now);
			
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
	 * @param Deck deck
	 * @return boolean
	 */
	public boolean deleteDeck(Long deck_id) 
	{
		if (deck_id == null) {
			return false;
		}
		
		try {
			String sql = "DELETE FROM deck WHERE id = " + deck_id;
		
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
