INSERT INTO tb_state(name) VALUES('pernambuco');

INSERT INTO tb_city(name, state_id) VALUES('recife', 1);
INSERT INTO tb_city(name, state_id) VALUES('jaboatão dos Guararapes', 1);
INSERT INTO tb_city(name, state_id) VALUES('olinda', 1);

INSERT INTO tb_user(name, email, password, phone, birth_day, url_photo, gender, bio, state_id, city_id) VALUES ('NaveGuard', 'navguard@navguard.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', '81987654321', '1998-05-15', 'https://example.com/photos/ana.jpg', 'FEMALE', 'Apaixonada por tecnologia e viagens.', 1, 1);
INSERT INTO tb_user(name, email, password, phone, birth_day, url_photo, gender, bio, state_id, city_id) VALUES ('Carlos Eduardo', 'carlos.edu@example.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', '81999887766', '1995-11-20', 'https://example.com/photos/carlos.jpg', 'MALE', 'Engenheiro de software com interesse em IA e startups.', 1, 1);
INSERT INTO tb_user(name, email, password, phone, birth_day, url_photo, gender, bio, state_id, city_id) VALUES ('Mariana Souza', 'mariana.souza@example.com', '$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG', '81991234567', '2000-07-08', 'https://example.com/photos/mariana.jpg', 'FEMALE', 'Designer apaixonada por criar experiências visuais incríveis.', 1, 1);

INSERT INTO tb_role(authority) VALUES ('ROLE_CLIENT');
INSERT INTO tb_role(authority) VALUES ('ROLE_ADMIN');

insert into tb_user_role(user_id, role_id) VALUES(1,1);
insert into tb_user_role(user_id, role_id) VALUES(1,2);
insert into tb_user_role(user_id, role_id) VALUES(2,1);
insert into tb_user_role(user_id, role_id) VALUES(3,1);

INSERT INTO tb_article(title, content, url_photo, author_id) VALUES ('Segurança crianças', 'Crianças não devem usar internet sem um adulto', 'htto', 1);
INSERT INTO tb_article(title, content, url_photo, author_id) VALUES ('Segurança adolescente', 'Adolescentes não devem usar internet sem um adulto', 'htto', 2);

INSERT INTO tb_tutorial(title, content, author_id) VALUES('Jogos digitais', 'Jogos podem ser prejudicias as crianças', 1);
INSERT INTO tb_tutorial(title, content, author_id) VALUES('Cassinos digitais', 'Cassinos podem ser prejudicias as crianças', 2);

INSERT INTO tb_media(url) VALUES('https');
INSERT INTO tb_media(url) VALUES('http');

INSERT INTO tb_tutorial_media(tutorial_id, media_id) VALUES (1, 1);
INSERT INTO tb_tutorial_media(tutorial_id, media_id) VALUES (2, 2);