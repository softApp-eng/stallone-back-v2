package far.insp.sirhat.security.jwt;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.*;

@Slf4j
@Component
public class JwtUtils {

  private final String JWT_SECRET;
  private final Long ACCESS_TOKEN_EXPIRATION_MN;
  private final Long REFRECH_TOKEN_EXPIRATION_MN;
  public JwtUtils(
          @Value("${educast.jwt.secret}") String JWT_SECRET,
          @Value("${educast.jwt.token.expiration_access_mn}") Long ACCESS_TOKEN_EXPIRATION_MN,
          @Value("${educast.jwt.token.expiration_refresh_mn}")Long REFRECH_TOKEN_EXPIRATION_MN
  ) {
    this.JWT_SECRET = JWT_SECRET;
    this.ACCESS_TOKEN_EXPIRATION_MN = ACCESS_TOKEN_EXPIRATION_MN;
    this.REFRECH_TOKEN_EXPIRATION_MN = REFRECH_TOKEN_EXPIRATION_MN;
  }

  public String generateAccessToken(String username, List<String> roles,String origine){
    Instant instant = Instant.now();
    return Jwts.builder()
              .setSubject(username)
              .setIssuedAt(Date.from(instant))
              .claim("roles",roles)
              .claim("origine",origine)
              .setIssuer("sirhat-service")
              .setExpiration(Date.from(instant.plus(ACCESS_TOKEN_EXPIRATION_MN, ChronoUnit.MINUTES)))
              .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
              .compact();
  }
  public String generateRefreshToken(String id,String origine){
    Instant instant = Instant.now();
    return Jwts.builder()
              .setId(id)
              .setIssuedAt(Date.from(instant))
            .claim("origine",origine)
              .setIssuer("sirhat-service")
              .setExpiration(Date.from(instant.plus(REFRECH_TOKEN_EXPIRATION_MN, ChronoUnit.MINUTES)))
              .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
              .compact();
  }

  public String getUserNameFromJwtToken(String token) {
    return Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody().getSubject();
  }

  public String getIdFromRefreshToken(String token) {
    return Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(token).getBody().getId();
  }

  public boolean validateJwtToken(String authToken) {
    try {
      Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(authToken);
      return true;
    } catch (SignatureException e) {
      log.error("Invalid JWT signature: {}", e.getMessage());
    } catch (MalformedJwtException e) {
      log.error("Invalid JWT token: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      log.error("JWT token is expired: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      log.error("JWT token is unsupported: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      log.error("JWT claims string is empty: {}", e.getMessage());
    }
    return false;
  }

}
