create table car_park_availability (
    car_park_no varchar(7) not null,
    available_lots integer default 0,
    total_lots integer default 0,
    primary key (car_park_no)
);

create table car_park_information (
    car_park_no varchar(7) not null,
    address varchar(255),
    latitude double precision,
    longitude double precision,
    primary key (car_park_no)
);

alter table car_park_availability
add constraint fk_car_park
foreign key (car_park_no)
references car_park_information (car_park_no);