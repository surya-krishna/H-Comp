
create database hope;

use hope;

create table organization(
organization_id int not null auto_increment primary key,
organization_name varchar(200) not null,
addressline_1 varchar(100) not null,
addressline_2 varchar(100),
pincode varchar(20) not null,
lat decimal,
lon decimal,
created_by varchar(50) default 'system',
created_time timestamp default current_timestamp,
updated_by varchar(50) default 'system',
updated_time timestamp default current_timestamp,
active_status int
);


create table vendor_config(
vendor_config_id int not null auto_increment primary key,
vendor_id int references user(user_id),
accepting_orders int(2) not null,
orders_accepted_from timestamp,
orders_accepted_till timestamp,
minimum_order_value decimal default '0',
offer_desc_1 varchar(200),
offer_desc_2 varchar(200),
out_for_delivery int,
delivery_time timestamp,
additional_charges_dec varchar(200),
created_by varchar(50) default 'system',
created_time timestamp default current_timestamp,
updated_by varchar(50) default 'system',
updated_time timestamp default current_timestamp,
active_status int
);

create table item_group(
item_group_id int not null auto_increment primary key,
item_group_name varchar(100) not null,
vendor_id int not null references users(user_id),
created_by varchar(50) default 'system',
created_time timestamp default current_timestamp,
updated_by varchar(50) default 'system',
updated_time timestamp default current_timestamp,
active_status int
);
create table menu_items(
item_id int not null auto_increment primary key,
item_name varchar(100) not null,
item_price decimal not null,
item_max_quantity int not null,
item_description varchar(200),
customizable int(2),
item_group_id int references item_group(item_group_id),
vendor_id int not null references users(user_id),
created_by varchar(50) default 'system',
created_time timestamp default current_timestamp,
updated_by varchar(50) default 'system',
updated_time timestamp default current_timestamp,
active_status int
);
create table item_customization_group(
item_customization_group_id int not null auto_increment primary key,
item_customization_group_name varchar(100) not null,
item_id int not null references menu_item(item_id),
allow_multiple_select int(2) default 0,
max_selections_allowed int,
created_by varchar(50) default 'system',
created_time timestamp default current_timestamp,
updated_by varchar(50) default 'system',
updated_time timestamp default current_timestamp,
active_status int
);
create table item_customization(
item_customization_id int not null auto_increment primary key,
item_id int not null references menu_item(item_id),
group_id int not null references item_customization_group(item_customization_group_id), 
customization_name varchar(100) not null,
item_price decimal not null default 0,
additional_price decimal default 0,
created_by varchar(50) default 'system',
created_time timestamp default current_timestamp,
updated_by varchar(50) default 'system',
updated_time timestamp default current_timestamp,
active_status int
);

create table order_details(
order_id int not null auto_increment primary key,
items_count int not null default 0,
total_price decimal,
user_id int references user(user_id),
address_id int references user_address(address_id),
vendor_id int not null references users(user_id),
order_acceptance varchar(20) default 'Pending',
place_order varchar(20) default 'Pending',
created_by varchar(50) default 'system',
created_time timestamp default current_timestamp,
updated_by varchar(50) default 'system',
updated_time timestamp default current_timestamp,
active_status int
);
create table order_items(
order_item_id int not null auto_increment primary key,
order_id int not null references order_details(order_id),
menu_item_id int not null references menu_item(item_id),
item_name int not null,
item_price decimal not null,
item_total_price decimal not null,
item_quantity int not null,
order_status varchar(20) not null default 'Pending',
created_by varchar(50) default 'system',
created_time timestamp default current_timestamp,
updated_by varchar(50) default 'system',
updated_time timestamp default current_timestamp,
active_status int
);
Create table order_customization(
order_customization_id int not null auto_increment primary key,
item_customization_id int references item_customization(item_customization_id),
order_item_id int not null references order_items(order_item_id),
customization_name varchar(100) not null,
customization_quantity int,
item_price decimal not null default 0,
additional_price decimal default 0,
created_by varchar(50) default 'system',
created_time timestamp default current_timestamp,
updated_by varchar(50) default 'system',
updated_time timestamp default current_timestamp,
active_status int
);

create table users(
user_id int not null auto_increment primary key,
profile_name varchar(200) not null, 
user_name varchar(50) not null,
mobile_number varchar(20) not null,
password varchar(200) not null,
gstin_number varchar(50) not null,
registration_number varchar(100) not null,
mobile_verification varchar(1) not null default 'N',
role_id int references roles_master(role_id)
created_by varchar(50) default 'system',
created_time timestamp default current_timestamp,
updated_by varchar(50) default 'system',
updated_time timestamp default current_timestamp,
active_status int
);

create table roles_master(
role_id int not null auto_increment primary key,
role_name varchar(20) not null,
created_by varchar(50) default 'system',
created_time timestamp default current_timestamp,
updated_by varchar(50) default 'system',
updated_time timestamp default current_timestamp,
active_status int
);

create table user_address(
address_id int not null auto_increment primary key,
addressline_1 varchar(100),
addressline_2 varchar(100),
pincode varchar(20),
lat decimal,
lon decimal,
user_id int references user(user_id),
role varchar(20) not null,
organization_id int references organization(organization_id),
created_by varchar(50) default 'system',
created_time timestamp default current_timestamp,
updated_by varchar(50) default 'system',
updated_time timestamp default current_timestamp,
active_status int
);

/*vendor serving to*/
create table vendor_organization(
vendor_organization_id int not null auto_increment primary key,
user_id int references user(user_id),
organization_id int references organization(organization_id),
created_by varchar(50) default 'system',
created_time timestamp default current_timestamp,
updated_by varchar(50) default 'system',
updated_time timestamp default current_timestamp,
active_status int
);

create table user_otp(
user_otp_id int not null auto_increment primary key,
otp int(6) not null,
user_id int references user(user_id),
otp_expiry_time timestamp, 
created_by varchar(50) default 'system',
created_time timestamp default current_timestamp,
updated_by varchar(50) default 'system',
updated_time timestamp default current_timestamp,
active_status int
);
