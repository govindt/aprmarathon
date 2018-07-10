@drop_tables.sql;
create sequence role_seq start with 1 increment by 1;
create sequence users_seq start with 1 increment by 1;
create sequence acl_seq start with 1 increment by 1;
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

create table Menu(
	menu_id number primary key,
	menu_name varchar2(50) not null,
	url varchar2(200),
	menu_order number not null,
	parent_menu_id number references Menu(menu_id),
	role_id number references Role(role_id)
);
insert into Menu values (-1, 'Root Menu', NULL, 0, NULL, 1);

create sequence registrationsheet_seq start with 1 increment by 1;
create table RegistrationSheet(
	registrant_name varchar2(50) not null,
	registrant_middle_name varchar2(50),
	registrant_last_name varchar2(50),
	registrant_email varchar2(75) not null,
	registrant_additional_email varchar2(500),
	registrant_phone_number varchar2(20) not null,
	registrant_address varchar2(200) not null,
	registrant_city varchar2(50) not null,
	registrant_state varchar2(50) not null,
	registrant_pincode varchar2(10) not null,
	registrant_pan varchar2(20),
	registrant_event name,
	registrant_type_name varchar2(50),
	registrant_source_name varchar2(50),
	registrant_class_name varchar2(50),
	registrant_beneficiary_name varchar2(50),
	registrant_emergency_contact varchar2(50) not null,
	registrant_emergency_phone varchar2(20) not null,
	registrant_payment_type_name varchar2(50),
	registrant_payment_status_name varchar2(50),
	registrant_payment_amount number(30,2) not null,
	registrant_additional_amount number(30,2) not null,
	registrant_payment_date timestamp not null,
	registrant_receipt_date timestamp not null,
	registrant_payment_details varchar2(200) not null,
	registrant_payment_towards varchar2(100) not null,
	registrant_payment_reference_id varchar2(100) not null,
	regsitrant_payment_tax number(30,2),
	registrant_payment_fee number(30,2)
);

commit;
exit;
