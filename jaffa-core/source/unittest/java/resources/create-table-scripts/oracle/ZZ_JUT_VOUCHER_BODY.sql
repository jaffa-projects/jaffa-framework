CREATE OR REPLACE PACKAGE BODY ZZ_JUT_VOUCHER AS
    PROCEDURE GetVoucher(p_in_prefix IN STRING, 
        p_in_size IN NUMBER, 
        p_out_voucher OUT STRING) IS
    BEGIN
        p_out_voucher := p_in_prefix || '0000';
    END GetVoucher;
    PROCEDURE GetVoucher(p_in_prefix IN STRING, 
        p_out_voucher OUT STRING) IS
    BEGIN
        p_out_voucher := p_in_prefix || '0000';
    END GetVoucher;
END ZZ_JUT_VOUCHER;