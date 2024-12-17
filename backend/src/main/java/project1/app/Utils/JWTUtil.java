package project1.app.Utils;

import java.nio.charset.StandardCharsets;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import project1.app.DTO.AuthUserInfoDTO;
import project1.app.Enums.UserRole;
import project1.app.Exceptions.Status500.InvalidJWTException;
import project1.app.Exceptions.Status500.InvalidJWTPayloadException;
import project1.app.Exceptions.Status500.Status500Exception;

// https://github.com/jwtk/jjwt?tab=readme-ov-file#jwt-example
// Code examples from github above helped as there's no official docs that I could find
// Documentation on github was more than enough for this
public class JWTUtil {
  // A secret key should be stored as an environment variable, but for the purpose of this project it will just be a string here
  // Key generated as 32 length from https://jwtsecret.com/generate
  private static final String SecretKeyString = "48b51d8ae1e4db6476cf7ea6aa3973445f6732bb219c201a88c41cfbc573f232";
  private static final SecretKey secretKey = Keys.hmacShaKeyFor(SecretKeyString.getBytes(StandardCharsets.UTF_8));

  public static String generateToken(Long userId, UserRole userRole) {
    // Subject is a unique identified used to figure out who's sending the request - userId makes sense to use here
    // Claims are essentially key value pairs stored in the JWT for any additional information - UserRole in this case is all we need for now as far as additional information goes
    String JWT = Jwts.builder()
      .subject(String.valueOf(userId))
      .claim("userRole", userRole)
      .signWith(secretKey)
      .compact();
    
    return JWT;
  }

  public static AuthUserInfoDTO extractUserInfoFromJWT(String JWT) {
    Claims jwtPayload;
    try {
      jwtPayload = Jwts.parser()
        .verifyWith(secretKey)
        .build()
        .parseSignedClaims(JWT)
        .getPayload();
    } catch (JwtException e) {
      // Invalid token, been tempered with
      throw new InvalidJWTException("Invalid JWT. Cleared auth cookies. Try logging in again.");
    }

    AuthUserInfoDTO userInfo;
    try {
      userInfo = new AuthUserInfoDTO(Long.valueOf((String) jwtPayload.get("sub")), UserRole.fromString((String) jwtPayload.get("userRole")));
    } catch (Exception e) {
      // Valid token, invalid payload data
      throw new InvalidJWTPayloadException("Invalid JWT payload. Cleared auth cookies. Try logging in again.");
    }

    return userInfo;
  }
}
