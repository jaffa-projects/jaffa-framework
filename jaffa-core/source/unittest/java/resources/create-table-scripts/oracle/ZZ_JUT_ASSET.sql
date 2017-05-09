CREATE TABLE "ZZ_JUT_ASSET"(
        "ASSET_TK"          NUMBER(20) NOT NULL,
        "ASSET_ID"          VARCHAR2(50),
        "PART"          VARCHAR2(50),
        "CUSTODIAN"          VARCHAR2(50),
        "CONDITION"          VARCHAR2(20),
        "CREATED_DATETIME"          DATE,
        "QTY"          NUMBER(8),
        "KEY_REF"          VARCHAR2(50),
    CONSTRAINT "ZZ_JUT_ASSETP1" PRIMARY KEY("ASSET_TK")
)
