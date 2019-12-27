CREATE TABLE notification
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    notifier BIGINT NOT NULL,
    receiver BIGINT NOT NULL,
    outerId BIGINT NOT NULL,
    type INT NOT NULL,
    gmtCreate BIGINT NOT NULL,
    status INT DEFAULT 0 NOT NULL
);