create TABLE `todo` (
    id         BIGINT       NOT NULL AUTO_INCREMENT,
    name       VARCHAR(255) NOT NULL,
    completed  BOOLEAN      NOT NULL,
    created_at TIMESTAMP    NOT NULL,
    PRIMARY KEY (id)
)
