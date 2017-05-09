CREATE TABLE J_AUDIT_TRANSACTION_FIELDS (
        FIELD_ID          VARCHAR(80) NOT NULL,
        OBJECT_ID          VARCHAR(80) NOT NULL,
        FIELD_NAME          VARCHAR(80) NOT NULL,
        FROM_VALUE          TEXT,
        TO_VALUE          TEXT,
        CHANGED          CHAR(1),
        FLEX          CHAR(1),
        OVERFLOW_FLAG          CHAR(1),
        MULTILINE_HTML_FLAG          VARCHAR(1)
) ENGINE=InnoDB
