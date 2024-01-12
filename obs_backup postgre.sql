--
-- PostgreSQL database dump
--

-- Dumped from database version 15.4 (Ubuntu 15.4-2.pgdg22.04+1)
-- Dumped by pg_dump version 16.0 (Ubuntu 16.0-1.pgdg22.04+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: public; Type: SCHEMA; Schema: -; Owner: pg_database_owner
--

CREATE SCHEMA public;


ALTER SCHEMA public OWNER TO pg_database_owner;

--
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: pg_database_owner
--

COMMENT ON SCHEMA public IS 'standard public schema';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: DERS; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."DERS" (
    "ID" bigint NOT NULL,
    "OGRETMEN_ID" bigint NOT NULL,
    "KONU_ID" bigint NOT NULL
);


ALTER TABLE public."DERS" OWNER TO postgres;

--
-- Name: DERS_ID_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."DERS_ID_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public."DERS_ID_seq" OWNER TO postgres;

--
-- Name: DERS_ID_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."DERS_ID_seq" OWNED BY public."DERS"."ID";


--
-- Name: KONU; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."KONU" (
    "ID" bigint NOT NULL,
    "NAME" character varying(100) NOT NULL
);


ALTER TABLE public."KONU" OWNER TO postgres;

--
-- Name: OGRETMEN; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."OGRETMEN" (
    "ID" bigint NOT NULL,
    "NAME" character varying(50) NOT NULL,
    "IS_GICIK" boolean DEFAULT false NOT NULL
);


ALTER TABLE public."OGRETMEN" OWNER TO postgres;

--
-- Name: DERS_KONU_OGRETMEN_VIEW; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public."DERS_KONU_OGRETMEN_VIEW" AS
 SELECT drs."ID",
    ogr."NAME" AS ogretmen_adi,
    kn."NAME" AS ders_konusu
   FROM ((public."DERS" drs
     JOIN public."OGRETMEN" ogr ON ((ogr."ID" = drs."OGRETMEN_ID")))
     JOIN public."KONU" kn ON ((kn."ID" = drs."KONU_ID")));


ALTER VIEW public."DERS_KONU_OGRETMEN_VIEW" OWNER TO postgres;

--
-- Name: DERS_OGRENCI; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."DERS_OGRENCI" (
    "ID" bigint NOT NULL,
    "DERS_ID" bigint NOT NULL,
    "OGRENCI_ID" bigint NOT NULL,
    "DEVAMSIZLIK" integer DEFAULT 0 NOT NULL,
    "NOTE" integer,
    CONSTRAINT "CHECK_NOTE" CHECK (("NOTE" < 101))
);


ALTER TABLE public."DERS_OGRENCI" OWNER TO postgres;

--
-- Name: DERS_OGRENCI_ID_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."DERS_OGRENCI_ID_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public."DERS_OGRENCI_ID_seq" OWNER TO postgres;

--
-- Name: DERS_OGRENCI_ID_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."DERS_OGRENCI_ID_seq" OWNED BY public."DERS_OGRENCI"."ID";


--
-- Name: KONU_ID_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."KONU_ID_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public."KONU_ID_seq" OWNER TO postgres;

--
-- Name: KONU_ID_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."KONU_ID_seq" OWNED BY public."KONU"."ID";


--
-- Name: OGRENCI; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public."OGRENCI" (
    "ID" bigint NOT NULL,
    "NAME" character varying(50) NOT NULL,
    "OGR_NUMBER" bigint NOT NULL,
    "YEAR" bigint DEFAULT 1 NOT NULL
);


ALTER TABLE public."OGRENCI" OWNER TO postgres;

--
-- Name: OGRENCI_ID_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."OGRENCI_ID_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public."OGRENCI_ID_seq" OWNER TO postgres;

--
-- Name: OGRENCI_ID_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."OGRENCI_ID_seq" OWNED BY public."OGRENCI"."ID";


--
-- Name: OGRETMEN_ID_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public."OGRETMEN_ID_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public."OGRETMEN_ID_seq" OWNER TO postgres;

--
-- Name: OGRETMEN_ID_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public."OGRETMEN_ID_seq" OWNED BY public."OGRETMEN"."ID";


--
-- Name: authorities; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.authorities (
    username character varying(50) NOT NULL,
    authority character varying(50) NOT NULL
);


ALTER TABLE public.authorities OWNER TO postgres;

--
-- Name: roles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.roles (
    rolename character varying(50) NOT NULL
);


ALTER TABLE public.roles OWNER TO postgres;

--
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    username character varying(50) NOT NULL,
    password character varying(500) NOT NULL,
    enabled boolean NOT NULL
);


ALTER TABLE public.users OWNER TO postgres;

--
-- Name: DERS ID; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."DERS" ALTER COLUMN "ID" SET DEFAULT nextval('public."DERS_ID_seq"'::regclass);


--
-- Name: DERS_OGRENCI ID; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."DERS_OGRENCI" ALTER COLUMN "ID" SET DEFAULT nextval('public."DERS_OGRENCI_ID_seq"'::regclass);


--
-- Name: KONU ID; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."KONU" ALTER COLUMN "ID" SET DEFAULT nextval('public."KONU_ID_seq"'::regclass);


--
-- Name: OGRENCI ID; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."OGRENCI" ALTER COLUMN "ID" SET DEFAULT nextval('public."OGRENCI_ID_seq"'::regclass);


--
-- Name: OGRETMEN ID; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."OGRETMEN" ALTER COLUMN "ID" SET DEFAULT nextval('public."OGRETMEN_ID_seq"'::regclass);


--
-- Data for Name: DERS; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public."DERS" ("ID", "OGRETMEN_ID", "KONU_ID") VALUES (3, 2, 2);
INSERT INTO public."DERS" ("ID", "OGRETMEN_ID", "KONU_ID") VALUES (11, 2, 3);
INSERT INTO public."DERS" ("ID", "OGRETMEN_ID", "KONU_ID") VALUES (15, 2, 4);
INSERT INTO public."DERS" ("ID", "OGRETMEN_ID", "KONU_ID") VALUES (20, 2, 3);


--
-- Data for Name: DERS_OGRENCI; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public."DERS_OGRENCI" ("ID", "DERS_ID", "OGRENCI_ID", "DEVAMSIZLIK", "NOTE") VALUES (10, 11, 3, 4, 90);
INSERT INTO public."DERS_OGRENCI" ("ID", "DERS_ID", "OGRENCI_ID", "DEVAMSIZLIK", "NOTE") VALUES (15, 15, 3, 23, 55);
INSERT INTO public."DERS_OGRENCI" ("ID", "DERS_ID", "OGRENCI_ID", "DEVAMSIZLIK", "NOTE") VALUES (7, 3, 3, 99, 85);
INSERT INTO public."DERS_OGRENCI" ("ID", "DERS_ID", "OGRENCI_ID", "DEVAMSIZLIK", "NOTE") VALUES (11, 11, 4, 9, 40);
INSERT INTO public."DERS_OGRENCI" ("ID", "DERS_ID", "OGRENCI_ID", "DEVAMSIZLIK", "NOTE") VALUES (13, 3, 2, 10, 65);
INSERT INTO public."DERS_OGRENCI" ("ID", "DERS_ID", "OGRENCI_ID", "DEVAMSIZLIK", "NOTE") VALUES (18, 3, 5, 20, 90);


--
-- Data for Name: KONU; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public."KONU" ("ID", "NAME") VALUES (1, 'Java');
INSERT INTO public."KONU" ("ID", "NAME") VALUES (2, 'Spring');
INSERT INTO public."KONU" ("ID", "NAME") VALUES (3, 'Docker');
INSERT INTO public."KONU" ("ID", "NAME") VALUES (4, 'React');


--
-- Data for Name: OGRENCI; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public."OGRENCI" ("ID", "NAME", "OGR_NUMBER", "YEAR") VALUES (1, 'zehra', 123, 1);
INSERT INTO public."OGRENCI" ("ID", "NAME", "OGR_NUMBER", "YEAR") VALUES (2, 'gül', 234, 2);
INSERT INTO public."OGRENCI" ("ID", "NAME", "OGR_NUMBER", "YEAR") VALUES (3, 'ibrahim', 345, 2);
INSERT INTO public."OGRENCI" ("ID", "NAME", "OGR_NUMBER", "YEAR") VALUES (4, 'şevval', 777, 4);
INSERT INTO public."OGRENCI" ("ID", "NAME", "OGR_NUMBER", "YEAR") VALUES (5, 'furkan', 765, 2);


--
-- Data for Name: OGRETMEN; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public."OGRETMEN" ("ID", "NAME", "IS_GICIK") VALUES (6, 'oğuzhan', false);
INSERT INTO public."OGRETMEN" ("ID", "NAME", "IS_GICIK") VALUES (2, 'hasan', true);
INSERT INTO public."OGRETMEN" ("ID", "NAME", "IS_GICIK") VALUES (24, 'berke', true);
INSERT INTO public."OGRETMEN" ("ID", "NAME", "IS_GICIK") VALUES (25, 'berfin', true);
INSERT INTO public."OGRETMEN" ("ID", "NAME", "IS_GICIK") VALUES (33, 'deneme', true);


--
-- Data for Name: authorities; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.authorities (username, authority) VALUES ('user', 'ROLE_USER');
INSERT INTO public.authorities (username, authority) VALUES ('admin', 'ROLE_ADMIN');


--
-- Data for Name: roles; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.roles (rolename) VALUES ('ROLE_ADMIN');
INSERT INTO public.roles (rolename) VALUES ('ROLE_USER');


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.users (username, password, enabled) VALUES ('admin', '$2a$10$V2UuPZLY7Pzu6ihGbA0Yc.lCHmZ7KCr0Ahdm5IPcYkES/HO0bD1NO', true);
INSERT INTO public.users (username, password, enabled) VALUES ('user', '$2a$10$WDtUVEjZuuQ7YNOfweEOu.5BxWq/yRH/LIyU9jv0g.TYjP8DfiMfC', true);


--
-- Name: DERS_ID_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."DERS_ID_seq"', 27, true);


--
-- Name: DERS_OGRENCI_ID_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."DERS_OGRENCI_ID_seq"', 18, true);


--
-- Name: KONU_ID_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."KONU_ID_seq"', 13, true);


--
-- Name: OGRENCI_ID_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."OGRENCI_ID_seq"', 5, true);


--
-- Name: OGRETMEN_ID_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public."OGRETMEN_ID_seq"', 33, true);


--
-- Name: DERS PK_DERS; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."DERS"
    ADD CONSTRAINT "PK_DERS" PRIMARY KEY ("ID");


--
-- Name: DERS_OGRENCI PK_DERS_OGRENCI; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."DERS_OGRENCI"
    ADD CONSTRAINT "PK_DERS_OGRENCI" PRIMARY KEY ("ID");


--
-- Name: KONU PK_KONU; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."KONU"
    ADD CONSTRAINT "PK_KONU" PRIMARY KEY ("ID");


--
-- Name: OGRENCI PK_OGRENCI; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."OGRENCI"
    ADD CONSTRAINT "PK_OGRENCI" PRIMARY KEY ("ID");


--
-- Name: OGRETMEN PK_OGRETMEN; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."OGRETMEN"
    ADD CONSTRAINT "PK_OGRETMEN" PRIMARY KEY ("ID");


--
-- Name: DERS_OGRENCI UNIQUE_DERS_OGRENCI; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."DERS_OGRENCI"
    ADD CONSTRAINT "UNIQUE_DERS_OGRENCI" UNIQUE ("DERS_ID", "OGRENCI_ID");


--
-- Name: OGRENCI UNIQUE_NUMBER; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."OGRENCI"
    ADD CONSTRAINT "UNIQUE_NUMBER" UNIQUE ("OGR_NUMBER");


--
-- Name: roles roles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (rolename);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (username);


--
-- Name: DERS_OGRENCI FK_DERS; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."DERS_OGRENCI"
    ADD CONSTRAINT "FK_DERS" FOREIGN KEY ("DERS_ID") REFERENCES public."DERS"("ID") ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: DERS FK_KONU; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."DERS"
    ADD CONSTRAINT "FK_KONU" FOREIGN KEY ("KONU_ID") REFERENCES public."KONU"("ID") ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: DERS_OGRENCI FK_OGRENCI; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."DERS_OGRENCI"
    ADD CONSTRAINT "FK_OGRENCI" FOREIGN KEY ("OGRENCI_ID") REFERENCES public."OGRENCI"("ID") ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: DERS FK_OGRETMEN; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public."DERS"
    ADD CONSTRAINT "FK_OGRETMEN" FOREIGN KEY ("OGRETMEN_ID") REFERENCES public."OGRETMEN"("ID") ON UPDATE CASCADE ON DELETE CASCADE;


--
-- Name: authorities fk_authorities_authority; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.authorities
    ADD CONSTRAINT fk_authorities_authority FOREIGN KEY (authority) REFERENCES public.roles(rolename);


--
-- Name: authorities fk_authorities_users; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.authorities
    ADD CONSTRAINT fk_authorities_users FOREIGN KEY (username) REFERENCES public.users(username);


--
-- PostgreSQL database dump complete
--

