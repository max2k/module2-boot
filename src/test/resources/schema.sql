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
    FOREIGN KEY (tag_id) REFERENCES tag (id) ON DELETE CASCADE
);