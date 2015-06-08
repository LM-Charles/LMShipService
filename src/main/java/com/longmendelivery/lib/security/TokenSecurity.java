package com.longmendelivery.lib.security;

/**
 * Created by desmond on 04/06/15.
 */
public class TokenSecurity {
    private static TokenSecurity instance;
    private TokenSecurity(){

    }

    public static TokenSecurity getInstance() {
        if(instance == null){
            instance = new TokenSecurity();
        }
        return instance;
    }


    public void authorize(String token, SecurityPower requestedPower) {

    }

    public void authorize(String token, SecurityPower requestedPower, Integer userId) {

    }

    public String issueToken(String username, String password) {
        return null;
    }

    public void invalidateToken(Integer userId) {
    }
}
