update QRTZ_FIRED_TRIGGERS a set a.PRIORITY =
   (select b.PRIORITY FROM ZZ_QRTZ_FIRED_TRIGGERS b
     where b.JOB_GROUP       = a.JOB_GROUP)
/

update QRTZ_TRIGGERS a set a.PRIORITY =
   (select b.PRIORITY FROM ZZ_QRTZ_TRIGGERS b
     where b.TRIGGER_NAME       = a.TRIGGER_NAME
       and b.TRIGGER_GROUP      = a.TRIGGER_GROUP)
/
