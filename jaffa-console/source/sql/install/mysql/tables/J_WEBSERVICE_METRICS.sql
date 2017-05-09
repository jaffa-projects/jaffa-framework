CREATE TABLE J_WEBSERVICE_METRICS (
        ID          VARCHAR(80) NOT NULL,
        ENDPOINT_NAME          TEXT,
        ENDPOINT_ADDRESS          TEXT,
        IP_ADDRESS          VARCHAR(20),
        PORT          VARCHAR(10),
        START_TIME          DATETIME,
        STOP_TIME          DATETIME,
<!-- WARNING: #if: Error evaluating condition: org.webmacro.PropertyException: Objects not comparable --><!-- WARNING: #if: Error evaluating condition: org.webmacro.PropertyException: Objects not comparable --><!-- WARNING: #if: Error evaluating condition: org.webmacro.PropertyException: Objects not comparable --><!-- WARNING: #if: Error evaluating condition: org.webmacro.PropertyException: Objects not comparable -->        REQUEST_COUNT          BIGINT,
<!-- WARNING: #if: Error evaluating condition: org.webmacro.PropertyException: Objects not comparable --><!-- WARNING: #if: Error evaluating condition: org.webmacro.PropertyException: Objects not comparable --><!-- WARNING: #if: Error evaluating condition: org.webmacro.PropertyException: Objects not comparable --><!-- WARNING: #if: Error evaluating condition: org.webmacro.PropertyException: Objects not comparable -->        RESPONSE_COUNT          BIGINT,
<!-- WARNING: #if: Error evaluating condition: org.webmacro.PropertyException: Objects not comparable --><!-- WARNING: #if: Error evaluating condition: org.webmacro.PropertyException: Objects not comparable --><!-- WARNING: #if: Error evaluating condition: org.webmacro.PropertyException: Objects not comparable --><!-- WARNING: #if: Error evaluating condition: org.webmacro.PropertyException: Objects not comparable -->        FAULT_COUNT          BIGINT,
        MIN_PROCESSING_TIME          VARCHAR(20),
        MAX_PROCESSING_TIME          VARCHAR(20),
        AVG_PROCESSING_TIME          VARCHAR(20),
        CREATED_ON          DATETIME,
    CONSTRAINT J_WEBSERVICE_METRICSP1 PRIMARY KEY(ID)
) TYPE=InnoDB
