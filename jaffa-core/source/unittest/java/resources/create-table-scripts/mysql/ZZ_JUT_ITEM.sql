CREATE TABLE ZZ_JUT_ITEM (
        ITEM_ID          VARCHAR(20) NOT NULL,
        RECEIVED_ITEM_ID          VARCHAR(20),
        PART          VARCHAR(50),
        PRIME          VARCHAR(50),
        SC          VARCHAR(20),
        CONDITION          VARCHAR(20),
        CREATED_DATETIME          DATETIME,
        QTY          INT,
        KEY_REF          VARCHAR(50),
        STATUS_1          VARCHAR(1),
        STATUS_2          VARCHAR(1),
        STATUS_3          VARCHAR(1),
        PRICE          DOUBLE(15,5),
    CONSTRAINT ZZ_JUT_ITEMP1 PRIMARY KEY(ITEM_ID)
) TYPE=InnoDB
