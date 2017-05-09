CREATE TABLE "J_FORM_GROUPS"(
        "FORM_NAME"          VARCHAR2(20) NOT NULL,
        "DESCRIPTION"          VARCHAR2(100),
        "FORM_TYPE"          VARCHAR2(20),
        "CREATED_ON"          DATE,
        "CREATED_BY"          VARCHAR2(20),
        "LAST_CHANGED_ON"          DATE,
        "LAST_CHANGED_BY"          VARCHAR2(20),
    CONSTRAINT "J_FORM_GROUPSP1" PRIMARY KEY("FORM_NAME")
)
/
