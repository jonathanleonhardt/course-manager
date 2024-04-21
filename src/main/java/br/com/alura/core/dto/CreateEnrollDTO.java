package br.com.alura.core.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateEnrollDTO(
		@NotBlank
		String studentId,
		@NotBlank
		String courseId ) {
	
}
