package br.com.alura.core.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateCourseDTO(
		@NotBlank
		String name,
		@NotBlank
		String description,
		@NotBlank
		String code,
		@NotBlank
		String instructorId ) {
}
