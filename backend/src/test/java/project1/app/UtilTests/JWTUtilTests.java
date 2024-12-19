package project1.app.UtilTests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import project1.app.DTO.AuthUserInfoDTO;
import project1.app.Enums.UserRole;
import project1.app.Exceptions.Status401.InvalidOrMissingJWTExeptions;
import project1.app.Utils.JWTUtil;

public class JWTUtilTests {
  // Header
  // "alg": "HS512"
  // Payload
  // "sub": userId
  // "userRole": userRole
  // SecretKey
  // 48b51d8ae1e4db6476cf7ea6aa3973445f6732bb219c201a88c41cfbc573f232
  // Put that info on https://jwt.io/ and DO NOT check base64 encoded

  // Hardcoded values checked with instructions above and used for all tests
  private String userId1RoleManagerJWT = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwidXNlclJvbGUiOiJNQU5BR0VSIn0.OgO6tSVj2LVuu5nCvbMjnWUkz-ct2k1LPmyWQoX8IZFJ_V3KHS8Gsse6PaGQq329QP324b9EO1i6a4eIueiTrw";
  private String userId2222RoleEmployeeJWT = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIyMjIyIiwidXNlclJvbGUiOiJFTVBMT1lFRSJ9.lJk5mQUauHatlzrYGjSvnc_pbMEDF2Rdq31pDmIF8_T-M1djWs_5hdypzNt3fDT6NHcWYo0ddHsoihROY4c6ow";
  private String userId3324324RoleManagerJWT = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIzMzI0MzI0IiwidXNlclJvbGUiOiJNQU5BR0VSIn0.JSk719TWBALUGZDSv0Pg8duwZSdDLYOTvDocJ6FELIG9kYrSfOUhsPHkAOetyA-xEqRvYRCSN_xZ4bwCjfJLdA";

  @Test
  public void testGeneratedTokens() {
    // Test that tokens are generated properly and match with https://jwt.io/ outputs hardcoded above
    assertEquals(userId1RoleManagerJWT, JWTUtil.generateToken(1L, UserRole.MANAGER));
    assertEquals(userId2222RoleEmployeeJWT, JWTUtil.generateToken(2222L, UserRole.EMPLOYEE));
    assertEquals(userId3324324RoleManagerJWT, JWTUtil.generateToken(3324324L, UserRole.MANAGER));
  }

  @Test
  public void testExtractFromValidJWT() {
    // Test that data is properly extracted from the JWT payload into an AuthUserInfoDTO object

    // userId 1, UserRole.MANAGER
    AuthUserInfoDTO extractedUserInfo = JWTUtil.extractUserInfoFromJWT(userId1RoleManagerJWT);
    assertEquals(1L, extractedUserInfo.getUserId());
    assertEquals(UserRole.MANAGER, extractedUserInfo.getUserRole());

    // userId 2222, UserRole.EMPLOYEE
    extractedUserInfo = JWTUtil.extractUserInfoFromJWT(userId2222RoleEmployeeJWT);
    assertEquals(2222L, extractedUserInfo.getUserId());
    assertEquals(UserRole.EMPLOYEE, extractedUserInfo.getUserRole());

    // userId 3324324, UserRole.MANAGER
    extractedUserInfo = JWTUtil.extractUserInfoFromJWT(userId3324324RoleManagerJWT);
    assertEquals(3324324L, extractedUserInfo.getUserId());
    assertEquals(UserRole.MANAGER, extractedUserInfo.getUserRole());
  }

  @Test
  public void testExtractFromInvalidJWT() {
    // Test that appropriate exception is thrown if the JWT has been tempered with/is invalid
    String modifiedHeader = userId1RoleManagerJWT.substring(1);
    assertThrows(InvalidOrMissingJWTExeptions.class, () -> JWTUtil.extractUserInfoFromJWT(modifiedHeader));

    int payloadStartIdx = userId1RoleManagerJWT.indexOf(".");
    String modifiedPayload = userId1RoleManagerJWT.substring(payloadStartIdx + 1) + userId1RoleManagerJWT.substring(payloadStartIdx + 3);
    assertThrows(InvalidOrMissingJWTExeptions.class, () -> JWTUtil.extractUserInfoFromJWT(modifiedPayload));

    String modifierSignature = userId1RoleManagerJWT.substring(0, userId1RoleManagerJWT.length() - 3);
    assertThrows(InvalidOrMissingJWTExeptions.class, () -> JWTUtil.extractUserInfoFromJWT(modifierSignature));
  }

  @Test
  public void testExtractFromValidJWTInvalidPayload() {
    // Test valid JWT with invalid payload. This will cause Status500Exception to be thrown since it can't be parsed into an AuthUserInfoDTO

    // Same as the rest of the hardcoded JWTs, but this one's payload has "userId": "1" instead of "sub": "1", as well as "userRole": "MANAGER", but that won't make a difference
    String userIdJWT = "eyJhbGciOiJIUzUxMiJ9.eyJ1c2VySWQiOiIxIiwidXNlclJvbGUiOiJNQU5BR0VSIn0.TCV3XNfItvnRV0_9SCJBYrU3HKhqWppUH5Jf0Wr7X9vyqE_WzKoSln7i74ogv0GK8IcszP2YwULH6cEWKmpsfA";
    assertThrows(InvalidOrMissingJWTExeptions.class, () -> JWTUtil.extractUserInfoFromJWT(userIdJWT));

    // // Empty payload, just { }
    String emptyPayloadJWT = "eyJhbGciOiJIUzUxMiJ9.e30.wSPZhcKmfgycswN02WZcWO8YKTU7xDqcQCc9TTkYoX27rrsjZ78xtMTGPQFGRwctiIwbOzYcPIF4-c5QzcdDbw";
    assertThrows(InvalidOrMissingJWTExeptions.class, () -> JWTUtil.extractUserInfoFromJWT(emptyPayloadJWT));
  }
}
