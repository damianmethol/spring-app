INSERT INTO spring_aplicacion.usuario (email, nombre, apellido, password, username) VALUES ('admin@gmail.com', 'admin', 'admin', '$2a$04$/B24F7V8ThCW7k09ub1QBuIcBoEukIEfzpd8gwclls22jpFpJQsQy', 'admin');
INSERT INTO spring_aplicacion.usuario (email, nombre, apellido, password, username) VALUES ('user1@gmail.com', 'user1', 'user1', '$2a$04$/B24F7V8ThCW7k09ub1QBuIcBoEukIEfzpd8gwclls22jpFpJQsQy', 'user1');
INSERT INTO spring_aplicacion.usuario (email, nombre, apellido, password, username) VALUES ('user2@gmail.com', 'user2', 'user2', '$2a$04$/B24F7V8ThCW7k09ub1QBuIcBoEukIEfzpd8gwclls22jpFpJQsQy', 'user2');

INSERT INTO spring_aplicacion.role (description, name) VALUES ('ROLE_ADMIN', 'ADMIN');
INSERT INTO spring_aplicacion.role (description, name) VALUES ('ROLE_USER', 'USER');
INSERT INTO spring_aplicacion.role (description, name) VALUES ('ROLE_SUPERVISOR', 'SUPERVISOR');


INSERT INTO spring_aplicacion.user_roles (user_id, role_id) VALUES ('1', '1');
INSERT INTO spring_aplicacion.user_roles (user_id, role_id) VALUES ('2', '2');
INSERT INTO spring_aplicacion.user_roles (user_id, role_id) VALUES ('3', '2');