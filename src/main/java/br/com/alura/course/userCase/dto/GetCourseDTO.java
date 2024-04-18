package br.com.alura.course.userCase.dto;

import java.util.Calendar;

import br.com.alura.user.useCase.dto.GetUserDTO;

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
