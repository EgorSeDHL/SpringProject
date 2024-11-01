-- This script was generated by the ERD tool in pgAdmin 4.
-- Please log an issue at https://redmine.postgresql.org/projects/pgadmin4/issues/new if you find any bugs, including reproduction steps.
BEGIN;


CREATE TABLE IF NOT EXISTS public.airport
(
    id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    city character varying(255) COLLATE pg_catalog."default" NOT NULL,
    code character varying(3) COLLATE pg_catalog."default" NOT NULL,
    country character varying(255) COLLATE pg_catalog."default" NOT NULL,
    name character varying(100) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT airport_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.booking
(
    id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    booking_date timestamp(6) without time zone,
    status character varying(255) COLLATE pg_catalog."default" NOT NULL,
    flight_id bigint NOT NULL,
    passenger_id bigint NOT NULL,
    CONSTRAINT booking_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.flight
(
    id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    arrival_time timestamp(6) without time zone,
    available_seats integer NOT NULL,
    departure_time timestamp(6) without time zone,
    flight_number character varying(10) COLLATE pg_catalog."default" NOT NULL,
    arrival_airport_id bigint NOT NULL,
    departure_airport_id bigint NOT NULL,
    CONSTRAINT flight_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.luggage
(
    id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    description character varying(255) COLLATE pg_catalog."default",
    weight double precision NOT NULL,
    ticket_id bigint NOT NULL,
    CONSTRAINT luggage_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.passenger
(
    id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    email character varying(255) COLLATE pg_catalog."default",
    first_name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    last_name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    phone_number character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT passenger_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.payment
(
    id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    amount double precision NOT NULL,
    payment_date timestamp(6) without time zone,
    payment_method character varying(255) COLLATE pg_catalog."default" NOT NULL,
    booking_id bigint NOT NULL,
    CONSTRAINT payment_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.seat
(
    id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    is_available boolean NOT NULL,
    seat_class character varying(255) COLLATE pg_catalog."default" NOT NULL,
    seat_number character varying(255) COLLATE pg_catalog."default" NOT NULL,
    flight_id bigint NOT NULL,
    CONSTRAINT seat_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.ticket
(
    id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    price double precision NOT NULL,
    seat_number character varying(255) COLLATE pg_catalog."default" NOT NULL,
    booking_id bigint NOT NULL,
    CONSTRAINT ticket_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.users
(
    id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    name character varying(255) COLLATE pg_catalog."default",
    password character varying(255) COLLATE pg_catalog."default",
    role character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT users_pkey PRIMARY KEY (id)
);

ALTER TABLE IF EXISTS public.booking
    ADD CONSTRAINT fk546eybei9q7dsna94vryofrbr FOREIGN KEY (flight_id)
    REFERENCES public.flight (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE CASCADE;


ALTER TABLE IF EXISTS public.booking
    ADD CONSTRAINT fkabxd6qpdfkp11yan46jj1td90 FOREIGN KEY (passenger_id)
    REFERENCES public.passenger (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE CASCADE;


ALTER TABLE IF EXISTS public.flight
    ADD CONSTRAINT fkillsy04237nltbk2yryrbderb FOREIGN KEY (departure_airport_id)
    REFERENCES public.airport (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE CASCADE;


ALTER TABLE IF EXISTS public.flight
    ADD CONSTRAINT fkor550l1m73innd911e6nm8lj0 FOREIGN KEY (arrival_airport_id)
    REFERENCES public.airport (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE CASCADE;


ALTER TABLE IF EXISTS public.luggage
    ADD CONSTRAINT fk1d1qimeuk2ylq19sccwa73nib FOREIGN KEY (ticket_id)
    REFERENCES public.ticket (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE CASCADE;


ALTER TABLE IF EXISTS public.payment
    ADD CONSTRAINT fkqewrl4xrv9eiad6eab3aoja65 FOREIGN KEY (booking_id)
    REFERENCES public.booking (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE CASCADE;


ALTER TABLE IF EXISTS public.seat
    ADD CONSTRAINT fkeda0njvaxhowgf6120eh6hxpq FOREIGN KEY (flight_id)
    REFERENCES public.flight (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE CASCADE;


ALTER TABLE IF EXISTS public.ticket
    ADD CONSTRAINT fkrg7x158t96nucwslhq2bad6qm FOREIGN KEY (booking_id)
    REFERENCES public.booking (id) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE CASCADE;

END;