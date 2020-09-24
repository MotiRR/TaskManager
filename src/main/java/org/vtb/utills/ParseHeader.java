package org.vtb.utills;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.vtb.config.jwt.JwtProvider;

import java.util.Map;

@Data
@Component
@AllArgsConstructor
public class ParseHeader {

    private JwtProvider jwtProvider;

    private Map<String, String> headers;


    public String getLogin() {
        return jwtProvider.getLoginFromToken(headers
                .get("authorization")
                .replaceFirst("Bearer ", ""));
    }
}
