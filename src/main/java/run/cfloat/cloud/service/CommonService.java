package run.cfloat.cloud.service;

import java.security.Key;
import java.util.Calendar;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class CommonService {

    static Key key = Keys.hmacShaKeyFor(
            "zIIUzrMb3Qb5tzdyMgamtwO6ZFI6fFNW8e6uTJY6SC0O8tmTkRqlBAW0r7b4taAmqjVqFovaWBUKk5FNSE48aGziSjQbKC7AsatdByp9vCrtZvZFyOU6VkD3H3SIYiymqoI9csRGcIXYQ635v5FBCAQMZevYYYTm8x44WIeLQsMVennTokXCAPZkjlur0MW57Znp1T5taNxTOPTjnpvgSW3CzIWCFJSVFquu2tSJdHISiXbpwLwNf4OBDz6DQe2f"
                    .getBytes());

    public static String generateToken(Integer id) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 7);
        return Jwts.builder()
                .setIssuer("陈大爷")
                .setSubject(id.toString())
                .setExpiration(calendar.getTime())
                .signWith(key).compact();
    }

    public static String parserToken(String token) {
        try {
            Jws<Claims> jws = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return jws.getBody().getSubject();
        } catch (Exception e) {
            return "";
        }
    }
}
