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

-- curl "https://api.mockaroo.com/api/4215e320?count=10&key=afe37040" > "USER.sql"

insert into USERTABLE (first_name, last_name, email)
values ('Ulrick', 'Duckering', 'uduckering0@guardian.co.uk');
insert into USERTABLE (first_name, last_name, email)
values ('Evvie', 'Birrell', 'ebirrell1@cam.ac.uk');
insert into USERTABLE (first_name, last_name, email)
values ('Brianna', 'Yushkov', 'byushkov2@elegantthemes.com');
insert into USERTABLE (first_name, last_name, email)
values ('Harman', 'Pepi', 'hpepi3@bbb.org');
insert into USERTABLE (first_name, last_name, email)
values ('Halli', 'Subhan', 'hsubhan4@imgur.com');
insert into USERTABLE (first_name, last_name, email)
values ('Lucia', 'Leahey', 'lleahey5@mozilla.org');
insert into USERTABLE (first_name, last_name, email)
values ('Yehudit', 'Durrant', 'ydurrant6@pbs.org');
insert into USERTABLE (first_name, last_name, email)
values ('Wainwright', 'Rylstone', 'wrylstone7@sina.com.cn');
insert into USERTABLE (first_name, last_name, email)
values ('Sydelle', 'Mellsop', 'smellsop8@yellowbook.com');
insert into USERTABLE (first_name, last_name, email)
values ('Anette', 'Wren', 'awren9@sciencedirect.com');


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