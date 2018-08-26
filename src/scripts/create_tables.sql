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

create sequence event_seq start with 1 increment by 1;
create table Event(
	event_id number primary key,
	event_name varchar2(50) not null,
	event_start_date timestamp not null,
	event_end_date timestamp not null,
	event_description varchar2(1000),
	event_registation_close_date timestamp not null,
	event_changes_close_date timestamp not null
);

create sequence event_type_seq start with 1 increment by 1;
create table Event_Type(
	event_type_id number primary key,
	event_type_name varchar2(50) not null,
	event number references Event(Event_Id),
	event_type_description varchar2(750),
	event_type_start_date timestamp not null,
	event_type_end_date timestamp not null,
	event_type_venue varchar2(50) not null,
	online_registration_only varchar2(1)
);

create sequence gender_seq start with 1 increment by 1;
create table Gender(
	gender_id number primary key,
	gender_name varchar2(10) not null
);

create sequence age_category_seq start with 1 increment by 1;
create table Age_Category(
	age_category_id number primary key,
	age_category varchar2(50)
);

create sequence t_shirt_size_seq start with 1 increment by 1;
create table T_Shirt_Size(
	t_shirt_size_id number primary key,
	t_shirt_size_name varchar2(50),
	t_shirt_gender number references Gender(Gender_Id)
);

create sequence blood_group_seq start with 1 increment by 1;
create table Blood_Group(
	blood_group_id number primary key,
	blood_group_name varchar2(5)
);

create sequence payment_type_seq start with 1 increment by 1;
create table Payment_Type(
	payment_type_id number primary key,
	payment_type_name varchar2(50)
);

create sequence payment_status_seq start with 1 increment by 1;
create table Payment_Status(
	payment_status_id number primary key,
	payment_status_name varchar2(50)
);

create sequence medal_seq start with 1 increment by 1;
create table Medal(
	medal_id number primary key,
	medal_name varchar2(10),
	medal_rank number
);

create sequence registration_type_seq start with 1 increment by 1;
create table Registration_Type(
	registration_type_id number primary key,
	registration_type_name varchar2(50) not null
);

create sequence registration_source_seq start with 1 increment by 1;
create table Registration_Source(
	registration_source_id number primary key,
	registration_source_name varchar2(50) not null
);

create sequence registration_class_seq start with 1 increment by 1;
create table Registration_Class(
	registration_class_id number primary key,
	registration_class_name varchar2(50) not null,
	registration_type number references RegistrationType(Registration_Type_Id),
	registration_event number references Event(Event_Id),
	registration_class_value number(30,2) not null,
	registration_free_tickets number not null
);

create sequence beneficiary_seq start with 1 increment by 1;
create table Beneficiary(
	beneficiary_id number primary key,
	beneficiary_name varchar2(50),
	beneficiary_event number references Event(Event_Id)
);

create sequence registrant_seq start with 1 increment by 1;
create table Registrant(
	registrant_id number primary key,
	registrant_name varchar2(50) not null,
	registrant_middle_name varchar2(50),
	registrant_last_name varchar2(50),
	registrant_email varchar2(75) not null,
	registrant_additional_email varchar2(500),
	registrant_phone varchar2(20) not null,
	registrant_address varchar2(200) not null,
	registrant_city varchar2(50) not null,
	registrant_state varchar2(50) not null,
	registrant_pincode varchar2(10) not null,
	registrant_pan varchar2(20)
);

create sequence registrant_event_seq start with 1 increment by 1;
create table Registrant_Event(
	registrant_event_id number primary key,
	registrant_id number references Registrant(Registrant_Id),
	registrant_event number references Event(Event_Id),
	registrant_type number references RegistrationType(Registration_Type_Id),
	registrant_source number references Registration_Source(Registration_Source_Id),
	registrant_class number references Registration_Class(Registration_Class_Id),
	registrant_beneficiary number references Beneficiary(Beneficiary_Id),
	registrant_emergency_contact varchar2(50) not null,
	registrant_emergency_phone varchar2(20) not null
);

create sequence registrant_payment_seq start with 1 increment by 1;
create table Registrant_Payment(
	registrant_payment_id number primary key,
	registrant_event number references Event(Event_Id),
	registrant number references Registrant(Registrant_Id),
	payment_type number references Payment_Type(Payment_Type_Id),
	payment_status number references Payment_Status(Payment_Status_Id),
	payment_amount number(30,2) not null,
	payment_additional_amount number(30,2) not null,
	payment_date timestamp not null,
	receipt_date timestamp not null,
	payment_details varchar2(200) not null,
	payment_towards varchar2(100) not null,
	payment_reference_id varchar2(100) not null,
	payment_tax number(30,2),
	payment_fee number(30,2)
);

create sequence participant_seq start with 1 increment by 1;
create table Participant(
	participant_id number primary key,
	participant_first_name varchar2(50) not null,
	participant_middle_name varchar2(50),
	participant_last_name varchar2(50),
	participant_gender number references Gender(Gender_Id),
	participant_date_of_birth timestamp,
	participant_age_category number references Age_Category(Age_Category_Id),
	participant_t_shirt_size number references T_Shirt_Size(T_Shirt_Size_Id),
	participant_blood_group number references Blood_Group(Blood_Group_Id),
	participant_cell_phone varchar2(20) not null,
	participant_email varchar2(75) not null
);

create sequence participant_event_seq start with 1 increment by 1;
create table Participant_Event(
	participant_event_id number primary key,
	participant_id number references Participant(Participant_Id),
	participant_event number references Event(Event_Id),
	participant_type number references RegistrationType(Registration_Type_Id),
	participant_event_type number references EventType(Event_Type_Id),
	participant_bib_no varchar2(20),
	participant_group number references Registrant_Event(Registrant_Event_Id)
);

create sequence result_seq start with 1 increment by 1;
create table Result(
	result_id number primary key,
	result_event number references Event(Event_Id),
	result_event_type number references EventType(Event_Type_Id),
	result_medal number references Medal(Medal_Id),
	result_winner number references Participant(Participant_Id),
	result_winner_registrant number references Registrant(Registrant_Id),
	result_score varchar2(20),
	result_timing timestamp
);

commit;
exit;
