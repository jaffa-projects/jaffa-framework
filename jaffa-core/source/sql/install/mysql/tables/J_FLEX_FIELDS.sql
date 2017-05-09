CREATE TABLE J_FLEX_FIELDS (
        FLEX_FIELD_ID          VARCHAR(80) NOT NULL,
        OBJECT_NAME          VARCHAR(80) NOT NULL,
        KEY1          VARCHAR(80) NOT NULL,
        KEY2          VARCHAR(80),
        KEY3          VARCHAR(80),
        FIELD_NAME          VARCHAR(80) NOT NULL,
        VALUE          TEXT,
        VERSION_NUMBER          BIGINT
) ENGINE=InnoDB
