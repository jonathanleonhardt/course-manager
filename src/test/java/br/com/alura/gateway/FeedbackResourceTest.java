package br.com.alura.gateway;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.alura.AluraModulith;
import br.com.alura.core.dto.CreateCourseDTO;
import br.com.alura.core.dto.CreateFeedbackDTO;
import br.com.alura.core.dto.CreateUserDTO;
import br.com.alura.core.enums.UserRolesEnum;
import br.com.alura.course.ICourseManagement;
import br.com.alura.persistence.course.repository.CourseRepository;
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
class FeedbackResourceTest {

	@Autowired
	MockMvc mvc;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	CourseRepository courseRepository;

	@Autowired
	FeedbackRepository feedbackRepository;

	@Autowired
	ICourseManagement courseManagement;

	@Autowired
	UserManagement userManagement;

	private String courseId;
	private String studentId;

	@BeforeAll
	void configure() {
		this.createRoles();
		CreateUserDTO instructorDTO = new CreateUserDTO(
				"instructor",
				"instructor",
				"instructor@mail.com",
				"pass",
				UserRolesEnum.INSTRUTOR );
		userManagement.createUser( instructorDTO );
		String instructorId = userRepository.findByUsername( instructorDTO.username() ).get().getId();

		CreateCourseDTO courseDTO = new CreateCourseDTO(
				"code with me",
				"code using spring",
				"code-with-me",
				instructorId );
		courseManagement.createCourse( courseDTO );
		courseId = courseRepository.findByCode( courseDTO.code() ).get().getId();

		CreateUserDTO studentDTO = new CreateUserDTO(
				"name",
				"username",
				"email@mail.com",
				"pass",
				UserRolesEnum.ESTUDANTE );
		userManagement.createUser( studentDTO );
		studentId = userRepository.findByUsername( studentDTO.username() ).get().getId();
	}

	@Test
	@Order( 1 )
	@WithMockUser( username = "username", password = "pass", roles = "ESTUDANTE" )
	void giveStatus200_whenCreateNewFeedback() throws Exception {
		CreateFeedbackDTO feedback = new CreateFeedbackDTO( studentId, courseId, 8, "Good" );
		mvc.perform( post( "/api/feedback/create" )
					.contentType( MediaType.APPLICATION_JSON )
					.content( new ObjectMapper().writeValueAsString( feedback ) ) )
				.andExpect( status().isOk() );
	}

	@AfterAll
	void clean() {
		this.feedbackRepository.deleteAll();
		this.courseRepository.deleteAll();
		this.userRepository.deleteAll();
		this.roleRepository.deleteAll();
	}

	private void createRoles() {
		Role adminRole = Role.builder().withName( UserRolesEnum.ADMIN.name() ).build();
		Role studentRole = Role.builder().withName( UserRolesEnum.ESTUDANTE.name() ).build();
		Role instructorRole = Role.builder().withName( UserRolesEnum.INSTRUTOR.name() ).build();
		roleRepository.save( adminRole );
		roleRepository.save( studentRole );
		roleRepository.save( instructorRole );
	}

}
