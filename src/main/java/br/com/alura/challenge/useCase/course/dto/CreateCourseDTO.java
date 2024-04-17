package br.com.alura.challenge.useCase.course.dto;

public record CreateCourseDTO(
		String name,
		String description,
		String code,
		String instructorId ) {
}
