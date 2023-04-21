CREATE TABLE tag
(
    id   INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE gift_certificate
(
    id               INT AUTO_INCREMENT PRIMARY KEY,
    name             VARCHAR(200)   NOT NULL,
    description      VARCHAR(1200),
    price            DECIMAL(10, 2) NOT NULL,
    duration         INT            NOT NULL,
    create_date      TIMESTAMP      NOT NULL,
    last_update_date TIMESTAMP      NOT NULL
);

CREATE TABLE cert_tag
(
    cert_id INT NOT NULL,
    tag_id  INT NOT NULL,
    FOREIGN KEY (cert_id) REFERENCES gift_certificate (id) ON DELETE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES tag (id) ON DELETE CASCADE
);


CREATE TABLE `USER` (
      id   INT AUTO_INCREMENT PRIMARY KEY,
      first_name VARCHAR(50) NOT NULL,
      last_name VARCHAR(50) NOT NULL,
      email VARCHAR(50) NOT NULL
);

CREATE TABLE `ORDER` (
     id   INT AUTO_INCREMENT PRIMARY KEY,
     user_id INT NOT NULL,
     cert_id INT NOT NULL,
     order_price  DECIMAL(10, 2) NOT NULL,
     FOREIGN KEY (user_id) REFERENCES `USER` (id) ON DELETE CASCADE,
     FOREIGN KEY (cert_id) REFERENCES `gift_certificate` (id) ON DELETE CASCADE
);

