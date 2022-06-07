INSERT INTO spring_aplicacion.usuario (email, nombre, apellido, password, username) VALUES ('admin@admin@gmail.com', 'admin', 'admin', '1234', 'admin');

INSERT INTO spring_aplicacion.role (description, name) VALUES ('ROLE_ADMIN', 'ADMIN');
INSERT INTO spring_aplicacion.role (description, name) VALUES ('ROLE_USER', 'USER');
INSERT INTO spring_aplicacion.role (description, name) VALUES ('ROLE_SUPERVISOR', 'SUPERVISOR');

INSERT INTO spring_aplicacion.user_roles (user_id, role_id) VALUES ('1', '1');