insert into tag (name) VALUES ( 'tag1' );
insert into tag (name) VALUES ( 'tag2' );
insert into tag (name) VALUES ( 'tag3' );
insert into tag (name) VALUES ( 'tag4' );

insert into gift_certificate (name,description,price,duration,create_date,last_update_date)
VALUES ( 'name1', 'description1', 10.0, 100, '2022-04-01T15:30:00', '2022-04-01T15:30:00' );

insert into gift_certificate (name,description,price,duration,create_date,last_update_date)
VALUES ( 'name2', 'description2', 20.0, 101, '2022-04-02T15:30:00', '2022-04-02T15:30:00' );

insert into gift_certificate (name,description,price,duration,create_date,last_update_date)
VALUES ( 'name3', 'description3', 30.0, 102, '2022-04-03T15:30:00', '2022-04-03T15:30:00' );


insert into cert_tag VALUES ( 1, 1 );
insert into cert_tag VALUES ( 1, 2 );

insert into cert_tag VALUES ( 1, 2 );
insert into cert_tag VALUES ( 1, 3 );

insert into cert_tag VALUES ( 1, 1 );
