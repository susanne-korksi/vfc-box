package model;

import java.util.Date;

public class Card {

	private Long id;
	private String side_a;
	private String side_b;
	private Date created;
	private Date modified;
	private Long deck_id;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the side_a
	 */
	public String getSide_a() {
		return side_a;
	}

	/**
	 * @param side_a the side_a to set
	 */
	public void setSide_a(String side_a) {
		this.side_a = side_a;
	}

	/**
	 * @return the side_b
	 */
	public String getSide_b() {
		return side_b;
	}

	/**
	 * @param side_b the side_b to set
	 */
	public void setSide_b(String side_b) {
		this.side_b = side_b;
	}

	/**
	 * @return the created
	 */
	public Date getCreated() {
		return created;
	}

	/**
	 * @param created the created to set
	 */
	public void setCreated(Date created) {
		this.created = created;
	}

	/**
	 * @return the modified
	 */
	public Date getModified() {
		return modified;
	}

	/**
	 * @param modified the modified to set
	 */
	public void setModified(Date modified) {
		this.modified = modified;
	}

	/**
	 * @return the deck
	 */
	public Long getDeckId() {
		return deck_id;
	}

	/**
	 * @param deck the deck to set
	 */
	public void setDeckId(Long deck_id) {
		this.deck_id = deck_id;
	}
	
	
	
}
