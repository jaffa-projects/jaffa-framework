SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE "J_FORM_DEFINITIONS"(
        "FORM_ID"          NUMERIC(20) IDENTITY (1, 1) NOT NULL,
        "FORM_NAME"          VARCHAR(20) NOT NULL,
        "FORM_ALTERNATE"          VARCHAR(20),
        "FORM_VARIATION"          VARCHAR(20),
        "OUTPUT_TYPE"          VARCHAR(20),
        "FORM_TEMPLATE"          VARCHAR(255),
        "FIELD_LAYOUT"          VARCHAR(255),
        "DESCRIPTION"          VARCHAR(100),
        "REMARKS"          VARCHAR(255),
        "DOM_FACTORY"          VARCHAR(255),
        "DOM_CLASS"          VARCHAR(255),
        "DOM_KEY1"          VARCHAR(50),
        "DOM_KEY2"          VARCHAR(50),
        "DOM_KEY3"          VARCHAR(50),
        "DOM_KEY4"          VARCHAR(50),
        "DOM_KEY5"          VARCHAR(50),
        "DOM_KEY6"          VARCHAR(50),
        "ADDITIONAL_DATA_COMPONENT"          VARCHAR(255),
        "FOLLOW_ON_FORM_NAME"          VARCHAR(20),
        "FOLLOW_ON_FORM_ALTERNATE"          VARCHAR(20),
        "CREATED_ON"          DATETIME,
        "CREATED_BY"          VARCHAR(20),
        "LAST_CHANGED_ON"          DATETIME,
        "LAST_CHANGED_BY"          VARCHAR(20),
    CONSTRAINT "J_FORM_DEFINITIONSP1" PRIMARY KEY("FORM_ID")
)
GO
