package br.com.alura.gateway;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.NotActiveException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import br.com.alura.AluraModulith;
import br.com.alura.core.dto.CreateCourseDTO;
import br.com.alura.core.dto.CreateEnrollDTO;
import br.com.alura.core.dto.CreateFeedbackDTO;
import br.com.alura.core.dto.CreateUserDTO;
import br.com.alura.core.enums.UserRolesEnum;
import br.com.alura.course.ICourseManagement;
import br.com.alura.enroll.IEnrollManagement;
import br.com.alura.feedback.FeedbackManagement;
import br.com.alura.persistence.course.repository.CourseRepository;
import br.com.alura.persistence.enroll.repository.EnrollRepository;
import br.com.alura.persistence.feedback.repository.FeedbackRepository;
import br.com.alura.persistence.user.domain.Role;
import br.com.alura.persistence.user.repository.RoleRepository;
import br.com.alura.persistence.user.repository.UserRepository;
import br.com.alura.user.UserManagement;

@SpringBootTest( webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = AluraModulith.class )
@AutoConfigureMockMvc
@TestPropertySource( locations = "classpath:application-test.properties" )
@TestMethodOrder( MethodOrderer.OrderAnnotation.class )
@TestInstance( Lifecycle.PER_CLASS )
class DashboardResourceTest {

	@Autowired
	MockMvc mvc;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	CourseRepository courseRepository;

	@Autowired
	EnrollRepository enrollRepository;

	@Autowired
	FeedbackRepository feedbackRepository;

	@Autowired
	FeedbackManagement feedbackManagement;

	@Autowired
	IEnrollManagement enrollManagement;

	@Autowired
	ICourseManagement courseManagement;

	@Autowired
	UserManagement userManagement;

	private List< String > coursesId;
	private List< String > studentsId = new ArrayList<>();
	private String notValidCourseId;

	@BeforeAll
	void configure() throws NotActiveException, NoSuchElementException {
		this.createRoles();

		CreateUserDTO instructorDTO = createUser( "instrutor", UserRolesEnum.INSTRUTOR );
		String instructorId = userRepository.findByUsername( instructorDTO.username() ).get().getId();

		for ( int i = 0; i < 40; i++ ) {
			CreateUserDTO studentDTO = createUser( "estudante" + i, UserRolesEnum.ESTUDANTE );
			String studentId = userRepository.findByUsername( studentDTO.username() ).get().getId();
			studentsId.add( studentId );
		}

		CreateCourseDTO course1DTO = createCourse( "code one", instructorId );
		String course1Id = courseRepository.findByCode( course1DTO.code() ).get().getId();

		CreateCourseDTO course2DTO = createCourse( "code two", instructorId );
		String course2Id = courseRepository.findByCode( course2DTO.code() ).get().getId();

		CreateCourseDTO courseNotValidDTO = createCourse( "code invalid", instructorId );
		notValidCourseId = courseRepository.findByCode( courseNotValidDTO.code() ).get().getId();

		coursesId = List.of( course1Id, course2Id );

		this.createEnrolls( coursesId, studentsId );

		this.createFeedbacks( coursesId, studentsId );
	}

	@Test
	@Order( 1 )
	@WithMockUser( username = "username", password = "pass", roles = "ESTUDANTE" )
	void giveStatus200_whenCreateNewFeedback() throws Exception {
		MvcResult result = mvc.perform( get( "/api/dashboard/nps" )
					.contentType( MediaType.APPLICATION_JSON ) )
				.andExpect( status().isOk() )
				.andExpect( content().contentTypeCompatibleWith( MediaType.APPLICATION_JSON ) )
				.andReturn();
		String contentAsString = result.getResponse().getContentAsString();

		for ( String courseId : coursesId ) {
			boolean hasCourse = contentAsString.contains( courseId );
			assertTrue( hasCourse );
		}

		boolean hasNpsJson = contentAsString.contains( "nps" );
		boolean hasInvalidCourseId = contentAsString.contains( notValidCourseId);

		assertTrue( hasNpsJson );
		assertFalse( hasInvalidCourseId );
	}

	private void createFeedbacks( List< String > coursesId, List< String > studentsId ) {
		for ( String studentId : studentsId ) {
			int randomCourse = new Random().nextInt( 1 );
			String courseId = coursesId.get( randomCourse );
			CreateFeedbackDTO feedback = new CreateFeedbackDTO(
					studentId,
					courseId,
					new Random().nextInt( 11 ),
					courseId );
			feedbackManagement.createFeedback( feedback );
		}
	}

	private void createEnrolls( List< String > coursesId, List< String > studentsId ) throws NotActiveException, NoSuchElementException {
		for ( String studentId : studentsId ) {
			int randomCourse = new Random().nextInt( 1 );
			String courseId = coursesId.get( randomCourse );
			System.out.println( "criando enroll - " + studentId + "  --  " + courseId );
			CreateEnrollDTO enroll = new CreateEnrollDTO( studentId, courseId );
			enrollManagement.createEnroll( enroll );
		}
	}

	private void createRoles() {
		Role adminRole = Role.builder().withName( UserRolesEnum.ADMIN.name() ).build();
		Role studentRole = Role.builder().withName( UserRolesEnum.ESTUDANTE.name() ).build();
		Role instructorRole = Role.builder().withName( UserRolesEnum.INSTRUTOR.name() ).build();
		roleRepository.save( adminRole );
		roleRepository.save( studentRole );
		roleRepository.save( instructorRole );
	}

	private CreateCourseDTO createCourse( String title, String instructorId ) {
		CreateCourseDTO course = new CreateCourseDTO(
				title,
				"code using spring",
				title.replace( " ", "-" ).toLowerCase(),
				instructorId );
		courseManagement.createCourse( course );
		return course;
	}

	private CreateUserDTO createUser( String name, UserRolesEnum type ) {
		CreateUserDTO user = new CreateUserDTO(
				name,
				name,
				name + "@mail.com",
				"pass",
				type );
		userManagement.createUser( user );
		return user;
	}

	@AfterAll
	void clean() {
		this.feedbackRepository.deleteAll();
		this.enrollRepository.deleteAll();
		this.courseRepository.deleteAll();
		this.userRepository.deleteAll();
		this.roleRepository.deleteAll();
	}

}
