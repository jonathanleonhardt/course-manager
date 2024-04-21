DROP TABLE IF EXISTS public.course CASCADE;

CREATE TABLE public.course (
	active bool NULL,
	created_at timestamp NULL,
	inactivated_at timestamp NULL,
	code varchar(20) NOT NULL,
	description varchar(20) NOT NULL,
	id varchar(255) NOT NULL,
	instructor_id varchar(255) NOT NULL,
	"name" text NOT NULL,
	CONSTRAINT course_pkey PRIMARY KEY (id),
	CONSTRAINT fk2wm4tlyupif5o4galwhxs6y53 FOREIGN KEY (instructor_id) REFERENCES public.alura_user(id)
);