SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE "J_BUSINESS_EVENT_LOGS"(
        "LOG_ID"          VARCHAR(80) NOT NULL,
        "CORRELATION_TYPE"          VARCHAR(80),
        "CORRELATION_KEY1"          VARCHAR(100),
        "CORRELATION_KEY2"          VARCHAR(100),
        "CORRELATION_KEY3"          VARCHAR(100),
        "SCHEDULED_TASK_ID"          VARCHAR(80),
        "MESSAGE_ID"          VARCHAR(80),
        "LOGGED_ON"          DATETIME,
        "LOGGED_BY"          VARCHAR(20),
        "PROCESS_NAME"          VARCHAR(80),
        "SUB_PROCESS_NAME"          VARCHAR(80),
        "MESSAGE_TYPE"          VARCHAR(20),
        "MESSAGE_TEXT"          VARCHAR(4000),
        "SOURCE_CLASS"          VARCHAR(255),
        "SOURCE_METHOD"          VARCHAR(100),
        "SOURCE_LINE"          NUMERIC(8),
        "STACK_TRACE"          VARCHAR(4000),
    CONSTRAINT "J_BUSINESS_EVENT_LOGSP1" PRIMARY KEY("LOG_ID")
)
GO
