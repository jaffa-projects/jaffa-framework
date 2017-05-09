CREATE TABLE "J_TRANSACTIONS"(
        "ID"          VARCHAR2(80) NOT NULL,
        "DIRECTION"          VARCHAR2(20),
        "TYPE"          VARCHAR2(100),
        "SUB_TYPE"          VARCHAR2(100),
        "STATUS"          VARCHAR2(20),
        "ERROR_MESSAGE"          VARCHAR2(2000),
        "CREATED_ON"          TIMESTAMP,
        "CREATED_BY"          VARCHAR2(20),
        "LAST_CHANGED_ON"          TIMESTAMP,
        "LAST_CHANGED_BY"          VARCHAR2(20),
    CONSTRAINT "J_TRANSACTIONSP1" PRIMARY KEY("ID")
)
/
