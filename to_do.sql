--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.1
-- Dumped by pg_dump version 9.5.1

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: categories; Type: TABLE; Schema: public; Owner: Abigail
--

CREATE TABLE categories (
    id integer NOT NULL,
    name character varying
);


ALTER TABLE categories OWNER TO "Abigail";

--
-- Name: categories_id_seq; Type: SEQUENCE; Schema: public; Owner: Abigail
--

CREATE SEQUENCE categories_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE categories_id_seq OWNER TO "Abigail";

--
-- Name: categories_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Abigail
--

ALTER SEQUENCE categories_id_seq OWNED BY categories.id;


--
-- Name: categories_tasks; Type: TABLE; Schema: public; Owner: Abigail
--

CREATE TABLE categories_tasks (
    id integer NOT NULL,
    category_id integer,
    task_id integer
);


ALTER TABLE categories_tasks OWNER TO "Abigail";

--
-- Name: categories_tasks_id_seq; Type: SEQUENCE; Schema: public; Owner: Abigail
--

CREATE SEQUENCE categories_tasks_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE categories_tasks_id_seq OWNER TO "Abigail";

--
-- Name: categories_tasks_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Abigail
--

ALTER SEQUENCE categories_tasks_id_seq OWNED BY categories_tasks.id;


--
-- Name: tasks; Type: TABLE; Schema: public; Owner: Abigail
--

CREATE TABLE tasks (
    id integer NOT NULL,
    description character varying
);


ALTER TABLE tasks OWNER TO "Abigail";

--
-- Name: tasks_id_seq; Type: SEQUENCE; Schema: public; Owner: Abigail
--

CREATE SEQUENCE tasks_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE tasks_id_seq OWNER TO "Abigail";

--
-- Name: tasks_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Abigail
--

ALTER SEQUENCE tasks_id_seq OWNED BY tasks.id;


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Abigail
--

ALTER TABLE ONLY categories ALTER COLUMN id SET DEFAULT nextval('categories_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Abigail
--

ALTER TABLE ONLY categories_tasks ALTER COLUMN id SET DEFAULT nextval('categories_tasks_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: Abigail
--

ALTER TABLE ONLY tasks ALTER COLUMN id SET DEFAULT nextval('tasks_id_seq'::regclass);


--
-- Data for Name: categories; Type: TABLE DATA; Schema: public; Owner: Abigail
--

COPY categories (id, name) FROM stdin;
1	Yardwork
2	Kitchen
3	Homework
\.


--
-- Name: categories_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Abigail
--

SELECT pg_catalog.setval('categories_id_seq', 3, true);


--
-- Data for Name: categories_tasks; Type: TABLE DATA; Schema: public; Owner: Abigail
--

COPY categories_tasks (id, category_id, task_id) FROM stdin;
1	2	1
2	2	5
3	2	4
4	1	2
5	1	3
6	3	2
7	3	4
\.


--
-- Name: categories_tasks_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Abigail
--

SELECT pg_catalog.setval('categories_tasks_id_seq', 7, true);


--
-- Data for Name: tasks; Type: TABLE DATA; Schema: public; Owner: Abigail
--

COPY tasks (id, description) FROM stdin;
1	Empty dishwasher
2	Weed
3	Plant flowers
4	Sweep floors
5	Empty trash
\.


--
-- Name: tasks_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Abigail
--

SELECT pg_catalog.setval('tasks_id_seq', 5, true);


--
-- Name: categories_pkey; Type: CONSTRAINT; Schema: public; Owner: Abigail
--

ALTER TABLE ONLY categories
    ADD CONSTRAINT categories_pkey PRIMARY KEY (id);


--
-- Name: categories_tasks_pkey; Type: CONSTRAINT; Schema: public; Owner: Abigail
--

ALTER TABLE ONLY categories_tasks
    ADD CONSTRAINT categories_tasks_pkey PRIMARY KEY (id);


--
-- Name: tasks_pkey; Type: CONSTRAINT; Schema: public; Owner: Abigail
--

ALTER TABLE ONLY tasks
    ADD CONSTRAINT tasks_pkey PRIMARY KEY (id);


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

