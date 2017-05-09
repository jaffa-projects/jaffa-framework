PROMPT CREATE OR REPLACE TRIGGER t_soa_events
CREATE OR REPLACE TRIGGER t_soa_events
BEFORE  INSERT
ON j_soa_events
FOR EACH ROW

DECLARE
    v_username VARCHAR2(30);
    v_prefix  VARCHAR2(20);
    v_localId VARCHAR2(80);
    v_pos     NUMBER;
    v_userid VARCHAR2(80);

BEGIN

    --
    -- Get the prefix
    --

    SELECT sys_context('USERENV', 'SESSION_USER') into v_username FROM dual;

    v_pos := instr(v_username , '_');

    v_prefix := substr(v_username, 1, v_pos-1);

    --
    -- Get the localId context
    --

    SELECT SYS_CONTEXT(v_prefix||'_sec_ctx','localid') INTO v_localId FROM DUAL;
    
    --
    -- populate localId context variable in current J_SOA_EVENTS record
    --

    :NEW.LOCAL_ID:=v_localId;

END;
/