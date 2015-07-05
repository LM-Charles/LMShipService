package com.longmendelivery.service.security;

/**
 * Created by  rabiddesireon 04/06/15.
 */
public class TokenSecurity {
    private static TokenSecurity instance;

    private TokenSecurity() {

    }

    public static TokenSecurity getInstance() {
        if (instance == null) {
            instance = new TokenSecurity();
        }
        return instance;
    }


    public void authorize(String token, SecurityPower requestedPower) {

    }

    public void authorize(String token, SecurityPower requestedPower, Integer userId) throws NotAuthorizedException {
        if (token == null || userId == null) {
            throw new NotAuthorizedException("Must have a token and user to validate this request");
        }

    }

    public String issueToken(String username, String password) {
        return "master";
    }

    public void invalidateToken(Integer userId) {
    }
}
