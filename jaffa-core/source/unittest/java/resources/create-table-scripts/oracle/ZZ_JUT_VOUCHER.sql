CREATE OR REPLACE PACKAGE ZZ_JUT_VOUCHER AS 
    PROCEDURE GetVoucher(p_in_prefix IN STRING, 
        p_in_size IN NUMBER, 
        p_out_voucher OUT STRING); 
    PROCEDURE GetVoucher(p_in_prefix IN STRING, 
        p_out_voucher OUT STRING); 
END ZZ_JUT_VOUCHER;