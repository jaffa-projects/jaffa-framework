CREATE TABLE "ZZ_JUT_ITEM"(
        "ITEM_ID"          VARCHAR2(20) NOT NULL,
        "RECEIVED_ITEM_ID"          VARCHAR2(20),
        "PART"          VARCHAR2(50),
        "PRIME"          VARCHAR2(50),
        "SC"          VARCHAR2(20),
        "CONDITION"          VARCHAR2(20),
        "CREATED_DATETIME"          DATE,
        "QTY"          NUMBER(8),
        "KEY_REF"          VARCHAR2(50),
        "STATUS_1"          VARCHAR2(1),
        "STATUS_2"          VARCHAR2(1),
        "STATUS_3"          VARCHAR2(1),
        "PRICE"          NUMBER(15,5),
    CONSTRAINT "ZZ_JUT_ITEMP1" PRIMARY KEY("ITEM_ID")
)
