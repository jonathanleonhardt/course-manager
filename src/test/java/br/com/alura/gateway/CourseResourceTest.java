package br.com.alura.gateway;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.alura.AluraModulith;
import br.com.alura.core.dto.CreateCourseDTO;
import br.com.alura.core.dto.CreateUserDTO;
import br.com.alura.core.enums.UserRolesEnum;
import br.com.alura.persistence.course.repository.CourseRepository;
import br.com.alura.persistence.user.domain.Role;
import br.com.alura.persistence.user.repository.RoleRepository;
import br.com.alura.persistence.user.repository.UserRepository;
import br.com.alura.user.UserManagement;

@SpringBootTest( webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = AluraModulith.class )
@AutoConfigureMockMvc
@TestPropertySource( locations = "classpath:application-test.properties" )
@TestMethodOrder( MethodOrderer.OrderAnnotation.class )
@TestInstance( Lifecycle.PER_CLASS )
class CourseResourceTest {

	@Autowired
	MockMvc mvc;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	CourseRepository courseRepository;

	@Autowired
	UserManagement userManagement;

	private CreateUserDTO userDTO;
	private CreateCourseDTO course1DTO;
	private CreateCourseDTO course2DTO;

	@BeforeAll
	void configure() {
		this.createRoles();
		userDTO = new CreateUserDTO(
				"name",
				"username",
				"email@mail.com",
				"pass",
				UserRolesEnum.ADMIN );
		userManagement.createUser( userDTO );
		CreateUserDTO instructorDTO = new CreateUserDTO(
					"instructor",
					"instructor",
					"instructor@mail.com",
					"pass",
					UserRolesEnum.INSTRUTOR );
		userManagement.createUser( instructorDTO );
		String instructorId = userRepository.findByUsername( instructorDTO.username() ).get().getId();
		course1DTO = new CreateCourseDTO(
				"code with me",
				"code using spring",
				"code-with-me",
				instructorId );
		course2DTO = new CreateCourseDTO(
				"code with you",
				"code using react",
				"code-with-you",
				instructorId );
	}

	@Test
	@Order( 1 )
	@WithMockUser( username = "name", password = "pass", roles = "ADMIN" )
	void giveStatus200_whenCreateNewCourse() throws Exception {
		mvc.perform( post( "/api/course/create" )
					.contentType( MediaType.APPLICATION_JSON )
					.content( new ObjectMapper().writeValueAsString( course1DTO ) ) )
				.andExpect( status().isOk() )
				.andExpect( content().contentTypeCompatibleWith( MediaType.APPLICATION_JSON ) );
		mvc.perform( post( "/api/course/create" )
					.contentType( MediaType.APPLICATION_JSON )
					.content( new ObjectMapper().writeValueAsString( course2DTO ) ) )
				.andExpect( status().isOk() )
				.andExpect( content().contentTypeCompatibleWith( MediaType.APPLICATION_JSON ) );
	}

	@Test
	@Order( 2 )
	@WithMockUser( username = "name", password = "pass", roles = "ADMIN" )
	void giveStatus200AndBody_whenListCourse_withStatusAndPage() throws Exception {
		MvcResult result = mvc.perform( get( "/api/course/list?active=true" )
					.contentType( MediaType.APPLICATION_JSON ) )
				.andExpect( status().isOk() )
				.andExpect( content().contentTypeCompatibleWith( MediaType.APPLICATION_JSON ) )
				.andReturn();
		String contentAsString = result.getResponse().getContentAsString();
		boolean hasCourse1 = contentAsString.contains( course1DTO.code() );
		boolean hasCourse2 = contentAsString.contains( course2DTO.code() );

		assertTrue(hasCourse1);
		assertTrue(hasCourse2);
	}

	@Test
	@Order( 3 )
	@WithMockUser( username = "name", password = "pass", roles = "ADMIN" )
	void giveStatus200_whenInactivateCourse_withCode() throws Exception {
		mvc.perform( put( "/api/course/inactive/" + course1DTO.code() )
					.contentType( MediaType.APPLICATION_JSON ) )
				.andExpect( status().isOk() );
	}

	@AfterAll
	void clean() {
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
