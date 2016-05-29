package model;

public class ExamHasDeck {

	private Long exam_id;
	private Long deck_id;
	
	public Long getDeckId() {
		return deck_id;
	}
	
	public void setDeckId(Long deck_id) {
		this.deck_id = deck_id;
	}

	public Long getExaId() {
		return exam_id;
	}

	public void setExamId(Long exam_id) {
		this.exam_id = exam_id;
	}

	
}
