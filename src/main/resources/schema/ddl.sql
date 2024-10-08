drop table members;
drop table tasks;

create table members (
    id bigserial primary key,
    email varchar(200) not null,
    user_name varchar(50) not null,
    signup_date timestamptz not null
);

create table tasks (
    id bigserial primary key,
    member_id bigint not null references members(id),
    contents varchar(200) not null,
    is_done boolean not null,
    created_date timestamptz not null,
    modified_date timestamptz not null
);