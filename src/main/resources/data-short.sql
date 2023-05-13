insert into tag (name)
VALUES ('tag1');
insert into tag (name)
VALUES ('tag2');
insert into tag (name)
VALUES ('tag3');
insert into tag (name)
VALUES ('tag4');

insert into gift_certificate (name, description, price, duration, create_date, last_update_date)
VALUES ('name1', 'description1', 10.0, 100, '2022-05-01T15:30:00', '2022-04-01T15:30:00');

insert into gift_certificate (name, description, price, duration, create_date, last_update_date)
VALUES ('name2', 'description2', 20.0, 101, '2022-04-02T15:30:00', '2022-04-02T15:30:00');

insert into gift_certificate (name, description, price, duration, create_date, last_update_date)
VALUES ('name3', 'description3', 30.0, 102, '2022-04-03T15:30:00', '2022-04-03T15:30:00');

insert into gift_certificate (name, description, price, duration, create_date, last_update_date)
VALUES ('name4', 'description2', 20.0, 101, '2022-04-02T15:30:00', '2022-04-02T15:30:00');

insert into gift_certificate (name, description, price, duration, create_date, last_update_date)
VALUES ('name5', 'description2', 20.0, 101, '2022-04-02T15:30:00', '2022-04-02T15:30:00');

insert into gift_certificate (name, description, price, duration, create_date, last_update_date)
VALUES ('name6', 'description2', 20.0, 101, '2022-04-02T15:30:00', '2022-04-02T15:30:00');


insert into cert_tag
VALUES (1, 1);
insert into cert_tag
VALUES (1, 2);
insert into cert_tag
VALUES (1, 3);

insert into cert_tag
VALUES (2, 1);
insert into cert_tag
VALUES (2, 2);
insert into cert_tag
VALUES (2, 4);

-- ROLES
insert into ROLES (NAME)
VALUES ('ADMIN'),
       ('USER');

insert into USERTABLE (first_name, last_name, email, passwd)
values ('test user', 'test user', 'user@user.com', '$2a$12$ZDwfZ686VJUeiCWb/N824eGxu43g6qEfh0iyLmycqb3qObse1Tmr6');

insert into USERTABLE (first_name, last_name, email, passwd)
values ('admin first name', 'admin last name', 'admin@admin.com',
        '$2a$12$O9mal./8MGViMVKijBuBSeEFCoq8wViPyNDK3H3lFqV1xjRtC7lCS');

insert into USER_ROLE (user_id, role_ID)
values (1, 2);
insert into USER_ROLE (user_id, role_ID)
values (2, 1);

-- curl "https://api.mockaroo.com/api/4215e320?count=10&key=afe37040" > "USER.sql"

insert into USERTABLE (first_name, last_name, email, passwd)
values ('Ulrick', 'Duckering', 'uduckering0@guardian.co.uk',
        '$2a$12$ZDwfZ686VJUeiCWb/N824eGxu43g6qEfh0iyLmycqb3qObse1Tmr6');
insert into USERTABLE (first_name, last_name, email, passwd)
values ('Evvie', 'Birrell', 'ebirrell1@cam.ac.uk', '$2a$12$ZDwfZ686VJUeiCWb/N824eGxu43g6qEfh0iyLmycqb3qObse1Tmr6');
insert into USERTABLE (first_name, last_name, email, passwd)
values ('Brianna', 'Yushkov', 'byushkov2@elegantthemes.com',
        '$2a$12$ZDwfZ686VJUeiCWb/N824eGxu43g6qEfh0iyLmycqb3qObse1Tmr6');
insert into USERTABLE (first_name, last_name, email, passwd)
values ('Harman', 'Pepi', 'hpepi3@bbb.org', '$2a$12$ZDwfZ686VJUeiCWb/N824eGxu43g6qEfh0iyLmycqb3qObse1Tmr6');
insert into USERTABLE (first_name, last_name, email, passwd)
values ('Halli', 'Subhan', 'hsubhan4@imgur.com', '$2a$12$ZDwfZ686VJUeiCWb/N824eGxu43g6qEfh0iyLmycqb3qObse1Tmr6');
insert into USERTABLE (first_name, last_name, email, passwd)
values ('Lucia', 'Leahey', 'lleahey5@mozilla.org', '$2a$12$ZDwfZ686VJUeiCWb/N824eGxu43g6qEfh0iyLmycqb3qObse1Tmr6');
insert into USERTABLE (first_name, last_name, email, passwd)
values ('Yehudit', 'Durrant', 'ydurrant6@pbs.org', '$2a$12$ZDwfZ686VJUeiCWb/N824eGxu43g6qEfh0iyLmycqb3qObse1Tmr6');
insert into USERTABLE (first_name, last_name, email, passwd)
values ('Wainwright', 'Rylstone', 'wrylstone7@sina.com.cn',
        '$2a$12$ZDwfZ686VJUeiCWb/N824eGxu43g6qEfh0iyLmycqb3qObse1Tmr6');
insert into USERTABLE (first_name, last_name, email, passwd)
values ('Sydelle', 'Mellsop', 'smellsop8@yellowbook.com',
        '$2a$12$ZDwfZ686VJUeiCWb/N824eGxu43g6qEfh0iyLmycqb3qObse1Tmr6');
insert into USERTABLE (first_name, last_name, email, passwd)
values ('Anette', 'Wren', 'awren9@sciencedirect.com', '$2a$12$ZDwfZ686VJUeiCWb/N824eGxu43g6qEfh0iyLmycqb3qObse1Tmr6');


--- curl "https://api.mockaroo.com/api/81873d70?count=10&key=afe37040" > "ORDER.sql"


insert into ORDERTABLE (user_id, cert_id, order_price)
values (6, 5, 136.78);
insert into ORDERTABLE (user_id, cert_id, order_price)
values (7, 1, 87.07);
insert into ORDERTABLE (user_id, cert_id, order_price)
values (10, 2, 114.39);
insert into ORDERTABLE (user_id, cert_id, order_price)
values (4, 6, 50.45);
insert into ORDERTABLE (user_id, cert_id, order_price)
values (1, 2, 68.0);
insert into ORDERTABLE (user_id, cert_id, order_price)
values (2, 4, 138.08);
insert into ORDERTABLE (user_id, cert_id, order_price)
values (4, 4, 71.57);
insert into ORDERTABLE (user_id, cert_id, order_price)
values (1, 1, 62.69);
insert into ORDERTABLE (user_id, cert_id, order_price)
values (6, 1, 24.71);
insert into ORDERTABLE (user_id, cert_id, order_price)
values (4, 3, 80.02);