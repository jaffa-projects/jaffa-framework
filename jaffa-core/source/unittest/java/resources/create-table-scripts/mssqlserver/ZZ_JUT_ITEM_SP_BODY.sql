CREATE PROCEDURE ZZ_JUT_ITEM_SP_GetItems
   @p_in_itemId VARCHAR,
   @p_out_items CURSOR VARYING OUTPUT
AS
BEGIN
   SET @p_out_items = CURSOR
   FORWARD_ONLY STATIC FOR
   select * from zz_jut_item where (item_id like @p_in_itemId + '%') order by item_id
   OPEN @p_out_items
END