package br.com.alura.core.event;

public record NotifyFeedbackEvent(
		Long rating,
		String description,
		String courseCode,
		String studentId,
		String studentName,
		String instructorId,
		String instructorName,
		String instructorEmail ) {

}
