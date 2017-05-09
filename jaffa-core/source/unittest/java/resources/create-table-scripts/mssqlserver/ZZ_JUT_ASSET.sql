CREATE TABLE "ZZ_JUT_ASSET"(
        "ASSET_TK"          NUMERIC(20) IDENTITY (1, 1) NOT NULL,
        "ASSET_ID"          VARCHAR(50),
        "PART"          VARCHAR(50),
        "CUSTODIAN"          VARCHAR(50),
        "CONDITION"          VARCHAR(20),
        "CREATED_DATETIME"          DATETIME,
        "QTY"          NUMERIC(8),
        "KEY_REF"          VARCHAR(50),
    CONSTRAINT "ZZ_JUT_ASSETP1" PRIMARY KEY("ASSET_TK")
)
