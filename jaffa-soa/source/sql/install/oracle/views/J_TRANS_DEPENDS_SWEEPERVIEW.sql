-- DMR BUG 23489 New view to look for out dated transaction dependenciesfor - J_TRANS_DEPENDS_SWEEPER$VIEW

CREATE OR REPLACE VIEW j_trans_depends_sweeper$view (
  transaction_id,
  depends_on_id,
  status
) AS
SELECT
transaction_id,
depends_on_id,
status
FROM j_transaction_dependencies b
WHERE b.status = 'O'
and ((not exists (select 1 from j_transactions a where a.id = b.depends_on_id))
or (exists (select 1 from j_transactions a where a.id = b.depends_on_id and a.status = 'S'))
)
/
