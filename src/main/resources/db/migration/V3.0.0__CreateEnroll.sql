DROP TABLE IF EXISTS public.enroll;

CREATE TABLE public.enroll (
	id varchar(255) NOT NULL,
	created_at timestamp NULL,
	course_id varchar(255) NOT NULL,
	student_id varchar(255) NOT NULL,
	CONSTRAINT enroll_pkey PRIMARY KEY (id),
	CONSTRAINT fkiv2pkft2ab8mqx9ffc4ex4q7e FOREIGN KEY (course_id) REFERENCES public.course(id),
	CONSTRAINT fkr5q610w53w8qkm6c0o7llx3ct FOREIGN KEY (student_id) REFERENCES public.alura_user(id)
);