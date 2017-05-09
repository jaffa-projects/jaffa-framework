-- DMR BUG 23489 New view for checking transactions where all of their dependencies are not open - J_TRANS_SWEEPER$VIEW
CREATE OR REPLACE VIEW j_trans_sweeper$view (
  id,
  created_on,
  created_by
) AS
select
 id,
 created_on,
 created_by
from j_transactions
where status = 'H'
and id not in (select transaction_id from j_transaction_dependencies where status='O')
/
