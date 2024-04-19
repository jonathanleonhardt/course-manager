package br.com.alura.enroll.useCase.dto;

import java.util.Calendar;

public record GetEnrollDTO(
		String id,
		String studentId,
		String courseId,
		Calendar createdAt ) {

}
