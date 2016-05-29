package model;

import java.util.Date;

public class Exercise {

	private Long id;
	private Date date;
	private Long user_id;

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
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the user_id
	 */
	public Long getUserId() {
		return user_id;
	}

	/**
	 * @param user_id the user_id to set
	 */
	public void setUserId(Long user_id) {
		this.user_id = user_id;
	}
	
	
	
	
}
