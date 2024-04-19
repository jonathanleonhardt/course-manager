DROP TABLE IF EXISTS public.alura_roles;

CREATE TABLE public.alura_roles (
	id varchar(255) NOT NULL,
	"name" varchar(255) NULL,
	CONSTRAINT alura_roles_pkey PRIMARY KEY (id)
);