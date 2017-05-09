CREATE TABLE J_ATTACHMENTS (
        ATTACHMENT_ID          VARCHAR(80) NOT NULL,
        DOCUMENT_REFERENCE_ID          VARCHAR(80),
        SERIALIZED_KEY          TEXT NOT NULL,
        ORIGINAL_FILE_NAME          VARCHAR(255),
        ATTACHMENT_TYPE          VARCHAR(1),
        CONTENT_TYPE          VARCHAR(100),
        DESCRIPTION          VARCHAR(100),
        REMARKS          VARCHAR(255),
        SUPERCEDED_BY          VARCHAR(80),
        CREATED_ON          DATETIME,
        CREATED_BY          VARCHAR(20),
        LAST_CHANGED_ON          DATETIME,
        LAST_CHANGED_BY          VARCHAR(20),
        DATA          LONGBLOB, 
        VERSION_NUMBER          BIGINT
) ENGINE=InnoDB
