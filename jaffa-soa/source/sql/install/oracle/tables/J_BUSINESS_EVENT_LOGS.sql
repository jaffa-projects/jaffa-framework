CREATE TABLE "J_BUSINESS_EVENT_LOGS"(
        "LOG_ID"          VARCHAR2(80) NOT NULL,
        "CORRELATION_TYPE"          VARCHAR2(80),
        "CORRELATION_KEY1"          VARCHAR2(100),
        "CORRELATION_KEY2"          VARCHAR2(100),
        "CORRELATION_KEY3"          VARCHAR2(100),
        "SCHEDULED_TASK_ID"          VARCHAR2(80),
        "MESSAGE_ID"          VARCHAR2(80),
        "LOGGED_ON"          TIMESTAMP,
        "LOGGED_BY"          VARCHAR2(20),
        "PROCESS_NAME"          VARCHAR2(80),
        "SUB_PROCESS_NAME"          VARCHAR2(80),
        "MESSAGE_TYPE"          VARCHAR2(20),
        "MESSAGE_TEXT"          VARCHAR2(4000),
        "SOURCE_CLASS"          VARCHAR2(255),
        "SOURCE_METHOD"          VARCHAR2(100),
        "SOURCE_LINE"          NUMBER(8),
        "STACK_TRACE"          VARCHAR2(4000),
    CONSTRAINT "J_BUSINESS_EVENT_LOGSP1" PRIMARY KEY("LOG_ID")
)
/
