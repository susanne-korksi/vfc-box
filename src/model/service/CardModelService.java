package model.service;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

import model.Card;

public class CardModelService extends AbstractModelService {

	public CardModelService(String dbPath) {
		super(dbPath);

	}

	/**
	 * @param int id
	 * @return Card|null
	 */
	public Card getCardById(Long id) 
	{
		if (id == null) {
			return null;
		}
		
		Card card = new Card();
		
		try {
			Statement statement = this.getStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM card WHERE id = '" + id + "'");
			
			if (!resultSet.next()) {
				return null;
			}
			
			card.setId(resultSet.getLong("id"));
			card.setSide_a(resultSet.getString("side_a"));
			card.setSide_b(resultSet.getString("side_b"));
			card.setCreated(CardModelService.sdf.parse(resultSet.getString("created"))); 
			card.setModified(CardModelService.sdf.parse(resultSet.getString("modified")));
			card.setDeckId(resultSet.getLong("deck_id"));
			
		} catch (Exception e) {
			return null;
		}
		
		return card;
	}
	
	/**
	 * @param User user
	 * @return Cards[]|null
	 */
	public Card getAllCardsByDeck(Long deck_id) 
	{
		if (deck_id == null) {
			return null;
		}
		
		Card card = new Card();
		
		try {
			Statement statement = this.getStatement();
			ResultSet resultSet = statement.executeQuery(""
					+ "SELECT * "
					+ "FROM card "
					+ "WHERE deck_id = '" + deck_id + "'");
			
			if (!resultSet.next()) {
				return null;
			}
			
		} catch (Exception e) {
			return null;
		}
		
		return card;
	}
	
	/**
	 * @param Card card
	 * @return boolean
	 */
	public boolean updateCard(Card card) 
	{
		if (card == null) {
			return false;
		}
		
		try {
			String modified = CardModelService.sdf.format(card.getModified());
			
			String sql = "UPDATE card SET "
					+ "name = '" + card.getSide_a() + "',"
					+ "description = '" + card.getSide_b() + "',"
					+ "modified = '" + modified + "'"
					+ "WHERE id = " + card.getId();
		
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
	 * @param Card card
	 * @return boolean
	 */
	public boolean createCard(Card card) 
	{	
		if (card == null) {
			return false;
		}
		
		try {
			
			Date now = new Date();  	
			String created = CardModelService.sdf.format(now);
			Long longTime = new Long(now.getTime()/1000);
			
			String sql = "INSERT INTO card (id, side_a, side_b, created, modified, deck_id) VALUES "
					+ "('" + longTime + "',"
					+ "'" + card.getSide_a() + "',"
					+ "'" + card.getSide_b() + "',"
					+ "'" + created + "',"
					+ "'" + created + "',"
					+ "'" + card.getDeckId() + "')"; 
			
			card.setId(longTime);
			card.setCreated(now);
			card.setModified(now);
			
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
	 * @param Card card
	 * @return boolean
	 */
	public boolean deleteCard(Long card_id) 
	{

		if (card_id == null) {
			return false;
		}
		
		try {
			String sql = "DELETE FROM card WHERE id = " + card_id;
		
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
