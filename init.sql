CREATE DATABASE outbox;

\c outbox;

CREATE TABLE "public"."accounts"
(
    "id"           uuid NOT NULL,
    "username"     character varying(255),
    "mail"         character varying(255),
    "password"     character varying(255),
    "mailStatus"   character varying(255),
    "created_date" timestamp,
    CONSTRAINT "accounts_pkey" PRIMARY KEY ("id")
) WITH (oids = false);

CREATE TABLE "public"."outboxes"
(
    "id"                     uuid NOT NULL,
    "aggregatetype"          character varying(255),
    "aggregateid"            character varying(255),
    "type"                   character varying(255),
    "payload"                character varying(2000),
    CONSTRAINT "outboxes_pkey" PRIMARY KEY ("id")
) WITH (oids = false);