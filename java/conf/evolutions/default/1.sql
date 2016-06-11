# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table accident (
  id                            integer not null,
  email                         varchar(255),
  description                   varchar(255),
  lat                           varchar(255),
  lng                           varchar(255),
  time                          varchar(255),
  constraint pk_accident primary key (id)
);
create sequence accident_seq;

create table employees (
  id                            integer not null,
  email                         varchar(255),
  pwd                           varchar(255),
  constraint pk_employees primary key (id)
);
create sequence employees_seq;

create table users (
  id                            integer not null,
  fname                         varchar(255),
  lname                         varchar(255),
  email                         varchar(255),
  pwd                           varchar(255),
  constraint pk_users primary key (id)
);
create sequence users_seq;


# --- !Downs

drop table if exists accident;
drop sequence if exists accident_seq;

drop table if exists employees;
drop sequence if exists employees_seq;

drop table if exists users;
drop sequence if exists users_seq;

