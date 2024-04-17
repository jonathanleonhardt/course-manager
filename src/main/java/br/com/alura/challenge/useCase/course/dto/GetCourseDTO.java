package br.com.alura.challenge.useCase.course.dto;

import java.util.Calendar;

import br.com.alura.challenge.useCase.user.dto.GetUserDTO;

public record GetCourseDTO(
		String id,
		String name,
		String description,
		String code,
		GetUserDTO instructor,
		Boolean active,
		Calendar createdAt,
		Calendar inactivatedAt ) {
}
