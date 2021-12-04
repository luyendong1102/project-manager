package edu.mngprj.mgprj.ultis;

import edu.mngprj.mgprj.entities.UserModded;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class JwtUltis {

    private final long expire_time = 9999999999999999L;
    @Value("${jwt.secret.key}")
    private String secretkey;

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretkey).parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }

    public String genToken(UserModded user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expire_time))
                .signWith(SignatureAlgorithm.HS256, secretkey)
                .compact();
    }

    public String getUserNameFromToken(String token) {
        return Jwts.parser().setSigningKey(secretkey)
                .parseClaimsJws(token)
                .getBody().getSubject();
    }

}
