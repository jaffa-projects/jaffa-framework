declare
   v_count number;
begin
   v_count := 0;
   select count(*) into v_count from qrtz_locks where lock_name = 'TRIGGER_ACCESS';
   if( v_count = 0 ) then
      dbms_output.put_line('INSERT INTO QRTZ_LOCKS VALUES (''TRIGGER_ACCESS'')');
      execute immediate 'INSERT INTO qrtz_locks (sched_name, lock_name) VALUES (''Scheduler'',''TRIGGER_ACCESS'') ';
   end if;

   select count(*) into v_count from qrtz_locks where lock_name = 'JOB_ACCESS';
   if( v_count = 0 ) then
      dbms_output.put_line('INSERT INTO QRTZ_LOCKS VALUES (''JOB_ACCESS'')');
      execute immediate 'INSERT INTO qrtz_locks (sched_name, lock_name) VALUES (''Scheduler'',''JOB_ACCESS'') ';
   end if;

   select count(*) into v_count from qrtz_locks where lock_name = 'CALENDAR_ACCESS';
   if( v_count = 0 ) then
      dbms_output.put_line('INSERT INTO QRTZ_LOCKS VALUES (''CALENDAR_ACCESS'')');
      execute immediate 'INSERT INTO qrtz_locks (sched_name, lock_name) VALUES (''Scheduler'',''CALENDAR_ACCESS'') ';
   end if;

   select count(*) into v_count from qrtz_locks where lock_name = 'STATE_ACCESS';
   if( v_count = 0 ) then
      dbms_output.put_line('INSERT INTO QRTZ_LOCKS VALUES (''STATE_ACCESS'')');
      execute immediate 'INSERT INTO qrtz_locks (sched_name, lock_name) VALUES (''Scheduler'',''STATE_ACCESS'') ';
   end if;

   select count(*) into v_count from qrtz_locks where lock_name = 'MISFIRE_ACCESS';
   if( v_count = 0 ) then
      dbms_output.put_line('INSERT INTO QRTZ_LOCKS VALUES (''MISFIRE_ACCESS'')');
      execute immediate 'INSERT INTO qrtz_locks (sched_name, lock_name) VALUES (''Scheduler'',''MISFIRE_ACCESS'') ';
   end if;
end;
/
