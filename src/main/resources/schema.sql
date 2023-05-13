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
    cert_id INT,
    tag_id  INT,
    FOREIGN KEY (cert_id) REFERENCES gift_certificate (id) ON DELETE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES tag (id) ON DELETE CASCADE,
    unique (cert_id, tag_id)
);


CREATE TABLE USERTABLE
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50)  NOT NULL,
    last_name  VARCHAR(50)  NOT NULL,
    email      VARCHAR(100) NOT NULL UNIQUE,
    `passwd`   VARCHAR(250) NOT NULL,
    `status`   VARCHAR(50)  NOT NULL DEFAULT 'ACTIVE'
);
CREATE INDEX idx_email ON USERTABLE (email);

CREATE TABLE ORDERTABLE
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    user_id     INT            NOT NULL,
    cert_id     INT            NOT NULL,
    order_price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES USERTABLE (id) ON DELETE CASCADE,
    FOREIGN KEY (cert_id) REFERENCES `gift_certificate` (id) ON DELETE CASCADE,
    UNIQUE (user_id, cert_id)
);

CREATE TABLE ROLES
(
    id   INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE USER_ROLE
(
    user_id INT NOT NULL,
    role_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES USERTABLE (id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES ROLES (id) ON DELETE CASCADE,
    unique (user_id, role_id)
);

