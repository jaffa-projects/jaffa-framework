CREATE OR REPLACE PACKAGE BODY ZZ_JUT_ITEM_SP AS
    PROCEDURE GetItems(p_in_itemId IN STRING, 
        p_out_items OUT CURSORTYPE) IS
    BEGIN
        open p_out_items for select * from zz_jut_item where (item_id like p_in_itemId || '%') order by item_id;
    END GetItems;
END ZZ_JUT_ITEM_SP;