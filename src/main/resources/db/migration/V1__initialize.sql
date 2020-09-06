create table users (id bigserial, login varchar(30) not null, password varchar(80) not null, email varchar(50) unique,
  primary key (id)
);

create table roles (id serial, name varchar(50) not null,
  primary key (id)
);

CREATE TABLE users_roles (user_id bigint not null, role_id int not null,
  primary key (user_id, role_id),
  foreign key (user_id) references users (id),
  foreign key (role_id) references roles (id)
);

insert into roles (name) values
('ROLE_USER'), ('ROLE_ADMIN'), ('DELETE_USERS_PERMISSION');

insert into users (login, password, email) values
--         admin
('admin', '$2y$12$B4a6J3eGOjxcRvBxq5Pme.Dbla0zRqsw27rBMvn4Fe8ZMeoSGfgrm', 'admin@email.com');

insert into users_roles (user_id, role_id) values (1, 2);