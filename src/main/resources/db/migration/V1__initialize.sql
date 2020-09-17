DROP TABLE IF EXISTS users CASCADE;
CREATE TABLE users (id bigserial, login varchar(30) not null, password varchar(100) not null, last_name varchar(50),
name varchar(50), second_name varchar(50), phone varchar(50),
email varchar(50) unique,
  primary key (id),
  created_at timestamp default current_timestamp,
  updated_at timestamp default current_timestamp
);


DROP TABLE IF EXISTS roles CASCADE;
CREATE TABLE roles (id serial, name varchar(25) not null,
  primary key (id)
);

DROP TABLE IF EXISTS users_roles CASCADE;
CREATE TABLE users_roles (user_id bigint not null, role_id int not null,
  primary key (user_id, role_id),
  foreign key (user_id) references users (id),
  foreign key (role_id) references roles (id)
);

INSERT INTO roles (name) values
('ROLE_USER'), ('ROLE_ADMIN'), ('DELETE_USERS_PERMISSION');

INSERT INTO users (login, password, email) values
--         admin
('admin', '$2y$12$B4a6J3eGOjxcRvBxq5Pme.Dbla0zRqsw27rBMvn4Fe8ZMeoSGfgrm', 'admin@email.com'),
('alex', '$2y$12$B4a6J3eGOjxcRvBxq5Pme.Dbla0zRqsw27rBMvn4Fe8ZMeoSGfgrm', 'admin1@email.com'),
('bob', '$2y$12$B4a6J3eGOjxcRvBxq5Pme.Dbla0zRqsw27rBMvn4Fe8ZMeoSGfgrm', 'admin2@email.com'),
('james', '$2y$12$B4a6J3eGOjxcRvBxq5Pme.Dbla0zRqsw27rBMvn4Fe8ZMeoSGfgrm', 'admin3@email.com');

INSERT INTO users_roles (user_id, role_id) values
(2, 1),
(3, 1),
(1, 2);

DROP TABLE IF EXISTS projects CASCADE;
CREATE TABLE projects
(
    id         bigserial,
    title      varchar(127) not null,
    leader_id  bigint      not null,
    primary key (id),
    foreign key (leader_id) references users (id),
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

DROP TABLE IF EXISTS users_projects CASCADE;
CREATE TABLE users_projects
(
    user_id    bigint not null,
    project_id bigint not null,
    primary key (user_id, project_id),
    foreign key (user_id) references users (id),
    foreign key (project_id) references projects (id)
);

DROP TABLE IF EXISTS tasks CASCADE;
create table tasks
(
    id          bigserial,
    title       varchar(25) not null,
    description varchar(3000),
    status      varchar(25),
    priority    varchar(25),
    leader_id   bigint      not null,
    project_id  bigint      not null,
    deadline    date        not null,
    primary key (id),
    foreign key (leader_id) references users (id),
    foreign key (project_id) references projects (id),
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp,
    is_archived BOOLEAN default 'f',
    is_visible BOOLEAN default 'f'

);

DROP TABLE IF EXISTS users_tasks CASCADE;
CREATE TABLE users_tasks
(
    user_id bigint not null,
    task_id bigint not null,
    primary key (user_id, task_id),
    foreign key (user_id) references users (id),
    foreign key (task_id) references tasks (id)
);

DROP TABLE IF EXISTS comments CASCADE;
CREATE TABLE comments
(
    id         bigserial,
    text       varchar(255) not null,
    user_id    bigint       not null,
    task_id    bigint       not null,
    primary key (id),
    foreign key (user_id) references users (id),
    foreign key (task_id) references tasks (id),
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

INSERT INTO projects(title, leader_id)
VALUES ('Project 1', 1),
       ('Project 2', 2),
       ('Project 2', 2),
       ('Project 2', 2),
       ('Project 2', 2),
       ('Project 2', 2),
       ('Project 2', 2),
       ('Project 2', 2),
       ('Project 2', 2),
       ('Project 2', 2),
       ('Project 2', 2);

INSERT INTO tasks (title, description, status, priority, leader_id, project_id, deadline, is_archived, is_visible)
VALUES ('Task 1', 'Description', 'CREATED', 'PLANNING', 1, 1, '2020-09-07', true, true),
--       ('Task 2', 'Description', 'CREATED', 'PLANNING', 1, 1, '2020-09-07'),
--       ('Task 3', 'Description', 'CREATED', 'PLANNING', 1, 1, '2020-09-07'),
--       ('Task 4', 'Description', 'CREATED', 'PLANNING', 1, 1, '2020-09-07'),
--       ('Task 5', 'Description', 'CREATED', 'PLANNING', 2, 2, '2020-09-07'),
--       ('Task 6', 'Description', 'CREATED', 'PLANNING', 2, 2, '2020-09-07'),
--       ('Task 7', 'Description', 'CREATED', 'PLANNING', 2, 2, '2020-09-07'),
--          ('Task 2', 'Description', 'CREATED', 'PLANNING', 1, 1, '2020-09-07'),
--       ('Task 3', 'Description', 'CREATED', 'PLANNING', 1, 1, '2020-09-07'),
--       ('Task 4', 'Description', 'CREATED', 'PLANNING', 1, 1, '2020-09-07'),
--       ('Task 5', 'Description', 'CREATED', 'PLANNING', 2, 2, '2020-09-07'),
--       ('Task 6', 'Description', 'CREATED', 'PLANNING', 2, 2, '2020-09-07'),
--       ('Task 7', 'Description', 'CREATED', 'PLANNING', 2, 2, '2020-09-07'),
--            ('Task 2', 'Description', 'CREATED', 'PLANNING', 1, 1, '2020-09-07'),
--       ('Task 3', 'Description', 'CREATED', 'PLANNING', 1, 1, '2020-09-07'),
--       ('Task 4', 'Description', 'CREATED', 'PLANNING', 1, 1, '2020-09-07'),
--       ('Task 5', 'Description', 'CREATED', 'PLANNING', 2, 2, '2020-09-07'),
--       ('Task 6', 'Description', 'CREATED', 'PLANNING', 2, 2, '2020-09-07'),
--       ('Task 7', 'Description', 'CREATED', 'PLANNING', 2, 2, '2020-09-07'),
       ('Task 3', 'Description', 'CREATED', 'PLANNING', 2, 2, '2020-09-07', true, true),
       ('Task 5', 'Description', 'CREATED', 'PLANNING', 2, 2, '2020-09-07', true, true),
       ('Task 6', 'Description', 'CREATED', 'PLANNING', 2, 2, '2020-09-07', true, true),
       ('Task 7', 'Description', 'CREATED', 'PLANNING', 2, 2, '2020-09-07', true, true),
       ('Task 8', 'Description', 'CREATED', 'PLANNING', 2, 2, '2020-09-07', true, true);

       INSERT INTO tasks (title, description, status, priority, leader_id, project_id, deadline, is_archived, is_visible)
       VALUES ('task', 'werfewc', 'CREATED', 'PLANNING', 1, 1, '2020-09-07', false, true);

DROP TABLE IF EXISTS files CASCADE;
CREATE TABLE files (id varchar(255), name varchar(255) not null, task_id bigint, type varchar(255), data bigint,
  primary key (id),
  foreign key (task_id) references tasks (id)
);

DROP TABLE IF EXISTS subscribe CASCADE;
CREATE TABLE subscribe (id bigserial, user_id bigint, task_id bigint, is_subscribe BOOLEAN default 'f',
  primary key (id),
  foreign key (task_id) references tasks (id),
  foreign key (user_id) references users (id)
);

INSERT INTO subscribe (user_id, task_id, is_subscribe)
VALUES (1, 1, true),
 (1, 1, true),
 (1, 2, true);

DROP TABLE IF EXISTS notification CASCADE;
CREATE TABLE notification (id bigserial,
    from_user bigint,
    to_user bigint,
    task_id bigint,
    message varchar(255),
    is_notification BOOLEAN default 'f',
    primary key (id),
    foreign key (from_user) references users (id),
    foreign key (to_user) references users (id),
    foreign key (task_id) references tasks (id)
);

