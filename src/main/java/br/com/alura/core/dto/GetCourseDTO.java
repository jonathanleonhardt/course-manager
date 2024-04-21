package br.com.alura.core.dto;

import java.util.Calendar;

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
