CREATE TABLE IF NOT EXISTS public.registered_user
(
    id            bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    email_address character varying(255) COLLATE pg_catalog."default",
    password      character varying(255) COLLATE pg_catalog."default",
    role          character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT registered_user_pkey PRIMARY KEY (id),
    CONSTRAINT uk_f6onv3wuwewrd39x86xydfglw UNIQUE (email_address)
);

INSERT INTO public.registered_user(email_address, password, role)
VALUES ('mail_ivanov@gmail.com', '$2a$12$krnldhMKg2Y8AA8no6XX6eYwkkaHHDj0OHYFZ6er8u7zkEixOjEOu', 'USER');
INSERT INTO public.registered_user(email_address, password, role)
VALUES ('mail_denisov@gmail.com', '$2a$12$uY75B5swB09fJfF2uce7dOC4eO8Be.ViQ9Lutd8pWBJfwAcwAEMui', 'USER');
INSERT INTO public.registered_user(email_address, password, role)
VALUES ('mail_petrov@gmail.com', '$2a$12$V0W6imo4AWYEtnTMzv0.yOqAxUYPtQukb1t8ncEn4eWS9Jm7AOgIK', 'USER');
INSERT INTO public.registered_user(email_address, password, role)
VALUES ('mail_dmitriy@gmail.com', '$2a$12$iBTpA5OiqEIUoNs6n2TqseRu0C/RVsyL7/aU3FrXtvEQm8R6tQHMi', 'USER');
INSERT INTO public.registered_user(email_address, password, role)
VALUES ('admin_adminov@gmail.com', '$2a$12$VPlfSsQLSp3SiLsmht7H5OCnNdF4eixliKjn6hssAkImLAgFqWoFW', 'ADMIN');