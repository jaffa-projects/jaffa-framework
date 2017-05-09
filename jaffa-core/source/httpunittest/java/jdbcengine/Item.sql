CREATE TABLE "ITEM"(
        "ITEM_ID"          VARCHAR2(20) NOT NULL,
        "SC"          VARCHAR2(20),
        "PART"          VARCHAR2(50),
        "SERIAL"          VARCHAR2(20),
        "QTY"          NUMBER(15,5),
    CONSTRAINT "ITEMP1" PRIMARY KEY("ITEM_ID")
)
/
