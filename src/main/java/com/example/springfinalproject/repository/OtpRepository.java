package com.example.springfinalproject.repository;

import com.example.springfinalproject.model.Entity.Otp;
import com.example.springfinalproject.model.request.OtpRequest;
import org.apache.ibatis.annotations.*;
@Mapper
public interface   OtpRepository {
    @Select("""
SELECT  * FROM otps_tb WHERE user_id =#{userId} ORDER BY issued_at DESC LIMIT 1
""")
    @Results(id="otpMapper", value = {
            @Result(property = "otpId", column = "opt_id" ),
            @Result(property = "otpCode", column = "otp_code"),
            @Result(property = "issuedAt", column = "issued_at"),
            @Result(property = "verify", column = "verify"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "expiration", column = "expiration")
    })
    Otp findOptByUserId(Integer userId);

    @Insert("""
    INSERT INTO otps_tb(otp_code, issued_at,expiration,verify,user_id) 
    VALUES (#{otp.otpCode},#{otp.issueAt},#{otp.expiration},#{otp.verify},#{otp.userId})
""")
    void saveOtp(@Param("otp")OtpRequest otpRequest);

    @Select("""
SELECT * FROM otps_tb WHERE otp_code = #{otpCode} ORDER BY issued_at DESC LIMIT 1
""")
    @ResultMap("otpMapper")
    Otp findOtpByOtpCode(String otpCode);

    @Select("""
    UPDATE otps_tb SET verify= true WHERE otp_code = #{otpCode}
""")
    void verify(String otpCode);

    @Select("""
    UPDATE otps_tb
    SET otp_code = #{otp.otpCode}, issued_at = #{otp.issueAt},verify= #{otp.verify},user_id = #{otp.userId},expiration = #{otp.expiration}
    WHERE user_id = #{otp.userId}
""")
    void updateOtp(@Param("otp")OtpRequest otpRequest);

}

