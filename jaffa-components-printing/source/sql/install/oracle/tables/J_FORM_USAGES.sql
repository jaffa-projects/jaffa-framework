CREATE TABLE "J_FORM_USAGES"(
        "FORM_NAME"          VARCHAR2(20) NOT NULL,
        "EVENT_NAME"          VARCHAR2(20) NOT NULL,
        "FORM_ALTERNATE"          VARCHAR2(20),
        "COPIES"          NUMBER(8),
        "CREATED_ON"          DATE,
        "CREATED_BY"          VARCHAR2(20),
        "LAST_CHANGED_ON"          DATE,
        "LAST_CHANGED_BY"          VARCHAR2(20),
    CONSTRAINT "J_FORM_USAGESP1" PRIMARY KEY("FORM_NAME","EVENT_NAME")
)
/
