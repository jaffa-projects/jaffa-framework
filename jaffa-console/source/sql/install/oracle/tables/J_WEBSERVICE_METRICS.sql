CREATE TABLE "J_WEBSERVICE_METRICS"(
        "ID"          VARCHAR2(80) NOT NULL,
        "ENDPOINT_NAME"          VARCHAR2(300),
        "ENDPOINT_ADDRESS"          VARCHAR2(300),
        "IP_ADDRESS"          VARCHAR2(20),
        "PORT"          VARCHAR2(10),
        "START_TIME"          DATE,
        "STOP_TIME"          DATE,
        "REQUEST_COUNT"          NUMBER,
        "RESPONSE_COUNT"          NUMBER,
        "FAULT_COUNT"          NUMBER,
        "MIN_PROCESSING_TIME"          VARCHAR2(20),
        "MAX_PROCESSING_TIME"          VARCHAR2(20),
        "AVG_PROCESSING_TIME"          VARCHAR2(20),
        "CREATED_ON"          DATE,
    CONSTRAINT "J_WEBSERVICE_METRICSP1" PRIMARY KEY("ID")
)
/
