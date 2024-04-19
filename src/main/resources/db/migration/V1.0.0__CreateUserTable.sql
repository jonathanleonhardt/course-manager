DROP TABLE IF EXISTS public.alura_user CASCADE;

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
	CONSTRAINT alura_user_username_key UNIQUE (username)
);