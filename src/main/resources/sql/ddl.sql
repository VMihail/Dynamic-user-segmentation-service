create table if not exists employee (
    employee_id serial not null primary key,
    employee_name varchar not null,
    employee_email varchar not null unique
);

create table if not exists segment (
    segment_id serial not null primary key,
    segment_name varchar not null unique
);

create table if not exists employee_segment (
    employee_segment_id serial not null primary key,
    employee_id serial not null, foreign key (employee_id) references employee(employee_id),
    segment_id serial not null, foreign key (segment_id) references segment(segment_id),
    unique (employee_id, segment_id)
);

alter table employee_segment add if not exists date_added date;
alter table employee_segment add if not exists date_removed date
    check (date_removed >= employee_segment.date_added);