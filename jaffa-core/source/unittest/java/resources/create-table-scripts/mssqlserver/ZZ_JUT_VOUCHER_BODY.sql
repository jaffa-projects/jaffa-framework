CREATE PROCEDURE ZZ_JUT_VOUCHER_GetVoucher
   @p_in_prefix VARCHAR,
   @p_in_size INT,
   @p_out_voucher VARCHAR OUTPUT
AS
BEGIN
   SET @p_out_voucher = @p_in_prefix + '0000'
END