DROP TABLE IF EXISTS public.course CASCADE;

CREATE TABLE public.course (
	active bool NULL,
	created_at timestamp NULL,
	inactivated_at timestamp NULL,
	code varchar(20) NOT NULL,
	description varchar(20) NOT NULL,
	course_id varchar(255) NOT NULL,
	id varchar(255) NOT NULL,
	instructor_id varchar(255) NOT NULL,
	"name" text NOT NULL,
	student_id varchar(255) NOT NULL,
	CONSTRAINT course_course_id_key UNIQUE (course_id),
	CONSTRAINT course_pkey PRIMARY KEY (id),
	CONSTRAINT course_student_id_key UNIQUE (student_id),
	CONSTRAINT fk1mmionn9uhfddo2y9iqr21cmp FOREIGN KEY (student_id) REFERENCES public.alura_user(id),
	CONSTRAINT fk2wm4tlyupif5o4galwhxs6y53 FOREIGN KEY (instructor_id) REFERENCES public.alura_user(id),
	CONSTRAINT fksih9ud7kv7hk71nhv2cksbn0r FOREIGN KEY (course_id) REFERENCES public.course(id)
);