DROP TABLE IF EXISTS public.feedback;

CREATE TABLE public.feedback (
	id varchar(255) NOT NULL,
	created_at timestamp NULL,
	description varchar(255) NULL,
	rating int8 NULL,
	course_id varchar(255) NOT NULL,
	student_id varchar(255) NOT NULL,
	CONSTRAINT feedback_pkey PRIMARY KEY (id),
	CONSTRAINT fk3nrubevow1q2tkcft90v3n8k6 FOREIGN KEY (student_id) REFERENCES public.alura_user(id),
	CONSTRAINT fkko7f08v61t5y67teh5jxxwrea FOREIGN KEY (course_id) REFERENCES public.course(id)
);
