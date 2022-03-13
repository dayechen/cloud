package run.cfloat.cloud.service;

import java.security.Key;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import run.cfloat.cloud.common.MD5Utils;
import run.cfloat.cloud.dto.RequestDto;
import run.cfloat.cloud.entity.User;
import run.cfloat.cloud.entity.UserConfig;
import run.cfloat.cloud.mapper.UserMapper;

@Service
public class UserService {

    @Autowired
    UserMapper mapper;

    public Key key = Keys.hmacShaKeyFor(
            "zIIUzrMb3Qb5tzdyMgamtwO6ZFI6fFNW8e6uTJY6SC0O8tmTkRqlBAW0r7b4taAmqjVqFovaWBUKk5FNSE48aGziSjQbKC7AsatdByp9vCrtZvZFyOU6VkD3H3SIYiymqoI9csRGcIXYQ635v5FBCAQMZevYYYTm8x44WIeLQsMVennTokXCAPZkjlur0MW57Znp1T5taNxTOPTjnpvgSW3CzIWCFJSVFquu2tSJdHISiXbpwLwNf4OBDz6DQe2f"
                    .getBytes());

    public void create(RequestDto.Regiser p) {
        final var saltPass = MD5Utils.getSaltMD5(p.getPassword());
        final var createTime = new Timestamp(new Date().getTime());
        final var user = User.builder()
                .name(p.getName())
                .password(saltPass)
                .createTime(createTime).build();
        mapper.createUser(user);
    }

    public User getUserByName(String name) {
        return mapper.getUserByName(name);
    }

    public User getUserById(String uid) {
        return mapper.getUserById(uid);
    }

    public boolean isAdmin(String id) {
        final var user = mapper.getUserById(id, "identity");
        return user.getIdentity().equals("1");
    }

    // 更新用户配置文件
    public void updateConfig(UserConfig config) {
        mapper.updateConfig(config);
    }

    // 判断用户是否存在
    public boolean thereIs(String name) {
        return mapper.getUserByName(name) != null;
    }

    // 验证密码
    public boolean checkPassword(String targetPass, String dataPass) {
        return MD5Utils.getSaltverifyMD5(targetPass, dataPass);
    }

    // 生成token
    public String generateToken(Integer id) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 7);
        return Jwts.builder()
                .setIssuer("陈大爷")
                .setSubject(id.toString())
                .setExpiration(calendar.getTime())
                .signWith(key).compact();
    }

    public String parserToken(String token) {
        try {
            Jws<Claims> jws = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return jws.getBody().getSubject();
        } catch (Exception e) {
            return "";
        }
    }
}
