DROP TABLE IF EXISTS public.alura_user CASCADE;
DROP TABLE IF EXISTS public.alura_roles;

CREATE TABLE public.alura_roles (
	id varchar(255) NOT NULL,
	"name" varchar(255) NULL,
	CONSTRAINT alura_roles_pkey PRIMARY KEY (id)
);

CREATE TABLE public.alura_user (
	created_at timestamp NULL,
	username varchar(20) NULL,
	email text NULL,
	id varchar(255) NOT NULL,
	"name" text NOT NULL,
	"password" text NOT NULL,
	role_id varchar(255) NULL,
	CONSTRAINT alura_user_email_key UNIQUE (email),
	CONSTRAINT alura_user_pkey PRIMARY KEY (id),
	CONSTRAINT alura_user_username_key UNIQUE (username),
	CONSTRAINT fk1ieqgonsff3ebxav276xwv6k2 FOREIGN KEY (role_id) REFERENCES public.alura_roles(id)
);