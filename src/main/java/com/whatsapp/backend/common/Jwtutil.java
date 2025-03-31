package com.whatsapp.backend.common;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
@Service
public class Jwtutil {
    @Value("${app.jwt.secret}")
    private String key;
    @Value("${app.jwt.expiration}")
    private Long expiration;
    private static final Logger logger = LoggerFactory.getLogger(Jwtutil.class);
    public String generateToken(String username) {

        Date currentDate = new Date();
        Date expirationDate = new Date(currentDate.getTime() + expiration * 1000);
        String token = Jwts.builder().setSubject(username)
                .setIssuedAt(currentDate)
                .setExpiration(expirationDate)
                .signWith(key())
                .compact();
        return token;

    }


    //decode key
    public Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(key));
    }


    //extract username
    public String getUsernameFromToken(String key) {
        try
        {
            return    Jwts.parser()
                    .verifyWith((SecretKey) key())
                    .build().parseClaimsJws(key)
                    .getPayload().getSubject();
        }catch (JwtException e)
        {
            logger.error("JWt parsing error {}", e.getMessage());
            throw new IllegalArgumentException("Illegal JWT token");
        }

    }
    public boolean validateToken(String token, UserDetails userDetails){
        try{
            Jwts.parser().verifyWith((SecretKey) key()).build().parseClaimsJws(token);
            return true;
        }catch(MalformedJwtException e){
            logger.error("Invalid JWT"+e.getMessage());
        }catch(ExpiredJwtException e){
            logger.warn("Expired JWT"+e.getMessage());
        }
        catch (UnsupportedJwtException e){
            logger.error("Unsupported JWT"+e.getMessage());
        }
        catch (IllegalArgumentException e){
            logger.error("Illegal JWT"+e.getMessage());
        }
        return false;
    }

    public String getJwtFromHeader(HttpServletRequest request){
        String header = request.getHeader("Authorization");
        logger.debug("header:"+header);
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }

}
