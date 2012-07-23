# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table image (
  id                        bigint not null,
  s3url                     varchar(255),
  project_id                bigint,
  uploaded                  timestamp,
  constraint pk_image primary key (id))
;

create table linked_account (
  id                        bigint not null,
  user_id                   bigint,
  provider_user_id          varchar(255),
  provider_key              varchar(255),
  constraint pk_linked_account primary key (id))
;

create table particle (
  id                        bigint not null,
  s3url                     varchar(255),
  pipeline_id               bigint,
  classification_id         bigint,
  constraint pk_particle primary key (id))
;

create table particle_class (
  id                        bigint not null,
  pipeline_id               bigint,
  average_id                bigint,
  constraint pk_particle_class primary key (id))
;

create table pipeline (
  id                        bigint not null,
  project_id                bigint,
  status                    bigint,
  created                   timestamp,
  constraint pk_pipeline primary key (id))
;

create table project (
  id                        bigint not null,
  name                      varchar(255),
  description               text,
  owner_id                  bigint,
  created                   timestamp,
  constraint pk_project primary key (id))
;

create table security_role (
  id                        bigint not null,
  role_name                 varchar(255),
  constraint pk_security_role primary key (id))
;

create table token_action (
  id                        bigint not null,
  token                     varchar(255),
  target_user_id            bigint,
  type                      varchar(2),
  created                   timestamp,
  expires                   timestamp,
  constraint ck_token_action_type check (type in ('EV','PR')),
  constraint uq_token_action_token unique (token),
  constraint pk_token_action primary key (id))
;

create table users (
  id                        bigint not null,
  email                     varchar(255),
  name                      varchar(255),
  last_login                timestamp,
  active                    boolean,
  email_validated           boolean,
  constraint pk_users primary key (id))
;

create table user_permission (
  id                        bigint not null,
  value                     varchar(255),
  constraint pk_user_permission primary key (id))
;


create table users_security_role (
  users_id                       bigint not null,
  security_role_id               bigint not null,
  constraint pk_users_security_role primary key (users_id, security_role_id))
;

create table users_user_permission (
  users_id                       bigint not null,
  user_permission_id             bigint not null,
  constraint pk_users_user_permission primary key (users_id, user_permission_id))
;
create sequence image_seq;

create sequence linked_account_seq;

create sequence particle_seq;

create sequence particle_class_seq;

create sequence pipeline_seq;

create sequence project_seq;

create sequence security_role_seq;

create sequence token_action_seq;

create sequence users_seq;

create sequence user_permission_seq;

alter table image add constraint fk_image_project_1 foreign key (project_id) references project (id);
create index ix_image_project_1 on image (project_id);
alter table linked_account add constraint fk_linked_account_user_2 foreign key (user_id) references users (id);
create index ix_linked_account_user_2 on linked_account (user_id);
alter table particle add constraint fk_particle_pipeline_3 foreign key (pipeline_id) references pipeline (id);
create index ix_particle_pipeline_3 on particle (pipeline_id);
alter table particle add constraint fk_particle_classification_4 foreign key (classification_id) references particle_class (id);
create index ix_particle_classification_4 on particle (classification_id);
alter table particle_class add constraint fk_particle_class_pipeline_5 foreign key (pipeline_id) references pipeline (id);
create index ix_particle_class_pipeline_5 on particle_class (pipeline_id);
alter table particle_class add constraint fk_particle_class_average_6 foreign key (average_id) references particle (id);
create index ix_particle_class_average_6 on particle_class (average_id);
alter table pipeline add constraint fk_pipeline_project_7 foreign key (project_id) references project (id);
create index ix_pipeline_project_7 on pipeline (project_id);
alter table project add constraint fk_project_owner_8 foreign key (owner_id) references users (id);
create index ix_project_owner_8 on project (owner_id);
alter table token_action add constraint fk_token_action_targetUser_9 foreign key (target_user_id) references users (id);
create index ix_token_action_targetUser_9 on token_action (target_user_id);



alter table users_security_role add constraint fk_users_security_role_users_01 foreign key (users_id) references users (id);

alter table users_security_role add constraint fk_users_security_role_securi_02 foreign key (security_role_id) references security_role (id);

alter table users_user_permission add constraint fk_users_user_permission_user_01 foreign key (users_id) references users (id);

alter table users_user_permission add constraint fk_users_user_permission_user_02 foreign key (user_permission_id) references user_permission (id);

# --- !Downs

drop table if exists image cascade;

drop table if exists linked_account cascade;

drop table if exists particle cascade;

drop table if exists particle_class cascade;

drop table if exists pipeline cascade;

drop table if exists project cascade;

drop table if exists security_role cascade;

drop table if exists token_action cascade;

drop table if exists users cascade;

drop table if exists users_security_role cascade;

drop table if exists users_user_permission cascade;

drop table if exists user_permission cascade;

drop sequence if exists image_seq;

drop sequence if exists linked_account_seq;

drop sequence if exists particle_seq;

drop sequence if exists particle_class_seq;

drop sequence if exists pipeline_seq;

drop sequence if exists project_seq;

drop sequence if exists security_role_seq;

drop sequence if exists token_action_seq;

drop sequence if exists users_seq;

drop sequence if exists user_permission_seq;

