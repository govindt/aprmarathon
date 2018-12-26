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

create sequence participant_seq start with 1 increment by 1;
create table Participant(
	participant_id number primary key,
	participant_first_name varchar2(50) not null,
	participant_middle_name varchar2(50),
	participant_last_name varchar2(50),
	participant_gender number references Gender(Gender_Id),
	participant_date_of_birth timestamp,
	participant_t_shirt_size number references T_Shirt_Size(T_Shirt_Size_Id),
	participant_blood_group number references Blood_Group(Blood_Group_Id),
	participant_cell_phone varchar2(20) not null,
	participant_email varchar2(75) not null,
	participant_group number references Registrant_Event(Registrant_Event_Id)
);

commit;
exit;
