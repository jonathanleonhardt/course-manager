package br.com.alura.core.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record CreateFeedbackDTO(
			@NotBlank
			String studentId,
			@NotBlank
			String courseId,
			@Min(0)
			@Max(10)
			Integer rating,
			String description
) {
}
