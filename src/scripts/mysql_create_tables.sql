\. mysql_drop_tables.sql

create table Role(
	role_id int primary key auto_increment,
	role_name varchar(30) not null,
	is_valid varchar(1) default 'Y'
);

insert into Role( role_name, is_valid) values ( 'Administrator', 'Y');
insert into Role( role_name, is_valid) values ( 'Normal User', 'Y');

create table Users(
	users_id int primary key auto_increment,
	username varchar(30) not null,
	password varchar(30) not null,
	email varchar(100),
	role_id int references Role(role_id),
	is_valid varchar(1) default 'Y'
);

create table Acl(
	acl_id int primary key auto_increment,
	acl_page varchar(30) not null,
	is_valid varchar(1) default 'Y',
	role_id int references Role(role_id),
	users_id int references Users(users_id),
	permission int
);

create table Menu(
	menu_id int primary key auto_increment,
	menu_name varchar(50) not null,
	url varchar(200),
	menu_order int not null,
	parent_menu_id int references Menu(menu_id),
	role_id int references Role(role_id)
);
insert into Menu values (-1, 'Root Menu', NULL, 0, NULL, 1);

create table Event(
	event_id int primary key auto_increment,
	event_name varchar(50) not null,
	event_start_date datetime not null,
	event_end_date datetime not null,
	event_description varchar(1000),
	event_registation_close_date datetime not null,
	event_changes_close_date datetime not null
);

create table Event_Type(
	event_type_id int primary key auto_increment,
	event_type_name varchar(50) not null,
	event int references Event(Event_Id),
	event_type_description varchar(750),
	event_type_start_date datetime not null,
	event_type_end_date datetime not null,
	event_type_venue varchar(50) not null,
	online_registration_only varchar(1)
);

create table Gender(
	gender_id int primary key auto_increment,
	gender_name varchar(10) not null
);

create table Age_Category(
	age_category_id int primary key auto_increment,
	age_category varchar(50)
);

create table T_Shirt_Size(
	t_shirt_size_id int primary key auto_increment,
	t_shirt_size_name varchar(50),
	t_shirt_gender int references Gender(Gender_Id)
);

create table Blood_Group(
	blood_group_id int primary key auto_increment,
	blood_group_name varchar(5)
);

create table Payment_Type(
	payment_type_id int primary key auto_increment,
	payment_type_name varchar(50)
);

create table Payment_Status(
	payment_status_id int primary key auto_increment,
	payment_status_name varchar(50)
);

create table Medal(
	medal_id int primary key auto_increment,
	medal_name varchar(10),
	medal_rank int
);

create table Registration_Type(
	registration_type_id int primary key auto_increment,
	registration_type_name varchar(50) not null
);

create table Registration_Source(
	registration_source_id int primary key auto_increment,
	registration_source_name varchar(50) not null
);

create table Registration_Class(
	registration_class_id int primary key auto_increment,
	registration_class_name varchar(50) not null,
	registration_type int references RegistrationType(Registration_Type_Id),
	registration_event int references Event(Event_Id),
	registration_class_value decimal(30,2) not null,
	registration_free_tickets int not null
);

create table Beneficiary(
	beneficiary_id int primary key auto_increment,
	beneficiary_name varchar(50),
	beneficiary_event int references Event(Event_Id)
);

create table Registrant(
	registrant_id int primary key auto_increment,
	registrant_name varchar(50) not null,
	registrant_middle_name varchar(50),
	registrant_last_name varchar(50),
	registrant_email varchar(75) not null,
	registrant_additional_email varchar(500),
	registrant_phone varchar(20) not null,
	registrant_address varchar(200) not null,
	registrant_city varchar(50) not null,
	registrant_state varchar(50) not null,
	registrant_pincode varchar(10) not null,
	registrant_pan varchar(20)
);

create table Registrant_Event(
	registrant_event_id int primary key auto_increment,
	registrant_id int references Registrant(Registrant_Id),
	registrant_event int references Event(Event_Id),
	registrant_type int references RegistrationType(Registration_Type_Id),
	registrant_source int references Registration_Source(Registration_Source_Id),
	registrant_class int references Registration_Class(Registration_Class_Id),
	registrant_beneficiary int references Beneficiary(Beneficiary_Id),
	registrant_emergency_contact varchar(50) not null,
	registrant_emergency_phone varchar(20) not null
);

create table Registrant_Payment(
	registrant_payment_id int primary key auto_increment,
	registrant_event int references Event(Event_Id),
	registrant int references Registrant(Registrant_Id),
	payment_type int references Payment_Type(Payment_Type_Id),
	payment_status int references Payment_Status(Payment_Status_Id),
	payment_amount decimal(30,2) not null,
	payment_additional_amount decimal(30,2) not null,
	payment_date datetime not null,
	receipt_date datetime not null,
	payment_details varchar(200) not null,
	payment_towards varchar(100) not null,
	payment_reference_id varchar(100) not null,
	payment_tax decimal(30,2),
	payment_fee decimal(30,2)
);

create table Participant(
	participant_id int primary key auto_increment,
	participant_first_name varchar(50) not null,
	participant_middle_name varchar(50),
	participant_last_name varchar(50),
	participant_gender int references Gender(Gender_Id),
	participant_date_of_birth datetime,
	participant_age_category int references Age_Category(Age_Category_Id),
	participant_t_shirt_size int references T_Shirt_Size(T_Shirt_Size_Id),
	participant_blood_group int references Blood_Group(Blood_Group_Id),
	participant_cell_phone varchar(20) not null,
	participant_email varchar(75) not null
);

create table Participant_Event(
	participant_event_id int primary key auto_increment,
	participant_id int references Participant(Participant_Id),
	participant_event int references Event(Event_Id),
	participant_type int references RegistrationType(Registration_Type_Id),
	participant_event_type int references EventType(Event_Type_Id),
	participant_bib_no varchar(20),
	participant_group int references Registrant_Event(Registrant_Event_Id)
);

create table Result(
	result_id int primary key auto_increment,
	result_event int references Event(Event_Id),
	result_event_type int references EventType(Event_Type_Id),
	result_medal int references Medal(Medal_Id),
	result_winner int references Participant(Participant_Id),
	result_winner_registrant int references Registrant(Registrant_Id),
	result_score varchar(20),
	result_timing datetime
);

commit;
quit
