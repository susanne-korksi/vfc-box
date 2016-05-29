package model;

public class ExerciseHasDeck {

	private Long exercise_id;
	private Long deck_id;
	
	public Long getDeckId() {
		return deck_id;
	}
	
	public void setDeckId(Long deck_id) {
		this.deck_id = deck_id;
	}

	public Long getExerciseId() {
		return exercise_id;
	}

	public void setExerciseId(Long exercise_id) {
		this.exercise_id = exercise_id;
	}

	
}
