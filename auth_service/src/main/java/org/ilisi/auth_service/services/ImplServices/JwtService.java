package org.ilisi.auth_service.services.ImplServices;

import org.ilisi.auth_service.services.IJwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService implements IJwtService {
    private static final String SECRET_KEY = "d00b8a46351437f781e8529d5523f4cf7b863920461e97222d8f23a65c75a9faa44d8eb4fd9eaeb9ffa610d0cc70d4995417599ddcd408c6b25e05a7af79cccb6a68aedd9821a60bd8ec770709fcd8456854ab4f15b17b734706a8200d367729dada7749851c00678bc1d1582576bebac10ee3f8d35ecfdd3d5918372a18edeb28e7f5da5f9d890e78a393b17994317dcf001c9b37eceb85a8836a231e3abe685ee252baae7ee46da912a80124baa24a327020cc6b7f9ec10a72ddb3bbe3ae9ae1af2eb1f52fd261fccd9c4ac41c6abc21b71c32494c94374cd6e2b317ce1598a0beed8a9fec300e21e1e46fb3189e2196be772c26d4b5af851383e9c88221b6";

    @Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    @Override
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    @Override
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    @Override
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
