create database demo_crud_2class;

use demo_crud_2class;

create table category(
id int primary key auto_increment,
name varchar(50) not null
);

create table product(
id int primary key auto_increment,
name varchar(50) not null,
price double not null,
quantity int not null,
description longtext,
category_id int not null,
foreign key (category_id) references category(id)
);