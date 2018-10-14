@drop_tables.sql;
create sequence role_seq start with 1 increment by 1;
create sequence users_seq start with 1 increment by 1;
create sequence acl_seq start with 1 increment by 1;
create sequence site_seq start with 1 increment by 1;
create sequence menu_seq start with 1 increment by 1;

create table Role(
	role_id number primary key,
	role_name varchar2(30) not null,
	is_valid varchar2(1) default 'Y'
);

insert into Role(role_id, role_name, is_valid) values (role_seq.nextval, 'Administrator', 'Y');
insert into Role(role_id, role_name, is_valid) values (role_seq.nextval, 'Normal User', 'Y');

create table Users(
	users_id number primary key,
	username varchar2(30) not null,
	password varchar2(30) not null,
	email varchar2(100),
	role_id number references Role(role_id),
	is_valid varchar2(1) default 'Y'
);

create table Acl(
	acl_id number primary key,
	acl_page varchar2(30) not null,
	is_valid varchar2(1) default 'Y',
	role_id number references Role(role_id),
	users_id number references Users(users_id),
	permission number
);

create table Site(
	site_id number primary key,
	site_name varchar2(50) not null,
	site_url varchar2(200)
);

insert into Site(site_id, site_name, site_url) values (site_seq.nextval, 'Local Host', 'http://localhost:9002');

create table Menu(
	menu_id number primary key,
	menu_name varchar2(50) not null,
	site_id number references Site(site_id),
	url varchar2(200),
	menu_order number not null,
	parent_menu_id number references Menu(menu_id),
	role_id number references Role(role_id)
);
insert into Menu values (-1, 'Root Menu', 1, NULL, 0, NULL, 1);

create sequence participant_sheet_seq start with 1 increment by 1;
create table Participant_Sheet(
	participant_id number primary key,
	participant_first_name varchar2(),
	participant_middle_name varchar2(),
	participant_last_name varchar2(),
	participant_gender varchar2(),
	participant_date_of_birth timestamp,
	participant_age_category varchar2(),
	participant_t_shirt_size varchar2(),
	participant_blood_group varchar2(),
	participant_cell_phone varchar2(),
	participant_email varchar2(),
	participant_group varchar2(),
	participant_event_id number,
	participant_event varchar2(),
	participant_type varchar2(),
	participant_event_type varchar2(),
	participant_bib_no varchar2(),
	participant_db_operation varchar2()
);

commit;
exit;
