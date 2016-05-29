package model.service;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

import model.Exam;

public class ExamModelService extends AbstractModelService {

	public ExamModelService(String dbPath) {
		super(dbPath);

	}

	/**
	 * @param Long id
	 * @return Exam|null
	 */
	public Exam getExamById(Long id) 
	{
		if (id == null) {
			return null;
		}
		
		Exam exam = new Exam();
		
		try {
			Statement statement = this.getStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM exam WHERE id = '" + id + "'");
			
			if (!resultSet.next()) {
				return null;
			}
			
			exam.setId(resultSet.getLong("id"));
			exam.setDate(DeckModelService.sdf.parse(resultSet.getString("date")));
			exam.setUserId(resultSet.getLong("user_id"));
			
		} catch (Exception e) {
			return null;
		}
		
		return exam;
	}
	
	/**
	 * 
	 * @param Exam exam
	 * @return boolean
	 */
	public boolean createExam(Exam exam) 
	{	
		if (exam == null) {
			return false;
		}
		
		try {
			
			String date = ExamModelService.sdf.format(new Date());
			
			Date now = new Date();  	
			Long longTime = new Long(now.getTime()/1000);
			
			String sql = "INSERT INTO exam (id, date, user) VALUES "
					+ "('" + longTime + "',"
					+ "'" + date + "',"
					+ "'" + exam.getUserId() + "')";
			
			exam.setId(longTime);
			exam.setDate(now);
		
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
