CREATE TABLE "USERS"(
        "USER_NAME"          VARCHAR2(50) NOT NULL,
        "FIRST_NAME"          VARCHAR2(50) NOT NULL,
        "LAST_NAME"          VARCHAR2(50) NOT NULL,
        "PASSWORD"          VARCHAR2(80) NOT NULL,
        "STATUS"          VARCHAR2(1) NOT NULL,
        "E_MAIL_ADDRESS"          VARCHAR2(75),
        "SECURITY_QUESTION"          NUMBER(20),
        "SECURITY_ANSWER"          VARCHAR2(50),
        "CREATED_DATETIME"          DATE,
        "CREATED_USER"          VARCHAR2(50),
        "LAST_UPDATE_DATETIME"          DATE,
        "LAST_UPDATE_USER"          VARCHAR2(50),
    CONSTRAINT "USERSP1" PRIMARY KEY("USER_NAME")
)
/
