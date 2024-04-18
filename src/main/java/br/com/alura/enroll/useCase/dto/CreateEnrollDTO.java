package br.com.alura.enroll.useCase.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateEnrollDTO(
		@NotBlank
		String studentId,
		@NotBlank
		String courseId ) {
	
}
