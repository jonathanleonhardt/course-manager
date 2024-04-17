package br.com.alura.challenge.useCase.enroll.dto;

import java.util.Calendar;

public record GetEnrollDTO(
		String id,
		String studentId,
		String courseId,
		Calendar createdAt ) {

}
