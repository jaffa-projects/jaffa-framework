CREATE TABLE J_TRANSACTIONS (
        ID          VARCHAR(80) NOT NULL,
        DIRECTION          VARCHAR(20),
        TYPE          VARCHAR(100),
        SUB_TYPE          VARCHAR(100),
        STATUS          VARCHAR(20),
        ERROR_MESSAGE          TEXT,
        CREATED_ON          DATETIME,
        CREATED_BY          VARCHAR(20),
        LAST_CHANGED_ON          DATETIME,
        LAST_CHANGED_BY          VARCHAR(20),
    CONSTRAINT J_TRANSACTIONSP1 PRIMARY KEY(ID)
) TYPE=InnoDB
