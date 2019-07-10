package com.xohealth.club.bean;

/**
 * Desc :
 * Created by xulc on 2018/12/9.
 */
public class Jwt {
    /**
     * access_token : eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2luZm8iOnsidXNlcl9pZCI6NDcxMDY4NzU2NzM2Nzc4MjR9LCJ1c2VyX25hbWUiOiIxODY2ODAzNzcyNSIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSJdLCJleHAiOjE1NDQyOTExNTQsImF1dGhvcml0aWVzIjpbIi9hcGkvdjEvY3VyVXNlcjpwdXQiLCIvYXBpL3YxL3Nob3dzLyoqOkFMTCIsIi9hcGkvdjEvY3VyVXNlci93ZWNoYXQvdW5iaW5kLyoqOmRlbGV0ZSIsIi9hcGkvdjEvdXNlci9hZGRyZXNzLyoqOkFMTCIsIi9hcGkvdjEvcWluaXUvdXBsb2FkVG9rZW46Z2V0IiwiL2FwaS92MS91c2Vycy8qKjpnZXQiLCIvYXBpL3YxL3VzZXIvZ29kZGVzcy9hZXJ0aWZpY2F0aW9uLyoqOnBvc3QiLCIvYXBpL3YxL2N1clVzZXIvKio6Z2V0IiwiL2FwaS92MS9yZXNldC8qKjpwdXQiLCIvYXBpL3YxL3VzZXIvZmFucy8qKjpBTEwiXSwianRpIjoiZWRmOWI5ODQtNTU0My00NDY5LWJhZWEtNTY0MmM5YjY0M2M3IiwiY2xpZW50X2lkIjoiYzBhODAxNDUtNWNjOS0xMjlmLTgxNWMtY2E3YmY1NTUwMDAzIn0.rLtyJU-kqgAQbzWtrmjFMR7jBsg7CP78kpia3Kcplhg
     * token_type : bearer
     * refresh_token : eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2luZm8iOnsidXNlcl9pZCI6NDcxMDY4NzU2NzM2Nzc4MjR9LCJ1c2VyX25hbWUiOiIxODY2ODAzNzcyNSIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSJdLCJhdGkiOiJlZGY5Yjk4NC01NTQzLTQ0NjktYmFlYS01NjQyYzliNjQzYzciLCJleHAiOjE1NDQ4ODg3NTQsImF1dGhvcml0aWVzIjpbIi9hcGkvdjEvY3VyVXNlcjpwdXQiLCIvYXBpL3YxL3Nob3dzLyoqOkFMTCIsIi9hcGkvdjEvY3VyVXNlci93ZWNoYXQvdW5iaW5kLyoqOmRlbGV0ZSIsIi9hcGkvdjEvdXNlci9hZGRyZXNzLyoqOkFMTCIsIi9hcGkvdjEvcWluaXUvdXBsb2FkVG9rZW46Z2V0IiwiL2FwaS92MS91c2Vycy8qKjpnZXQiLCIvYXBpL3YxL3VzZXIvZ29kZGVzcy9hZXJ0aWZpY2F0aW9uLyoqOnBvc3QiLCIvYXBpL3YxL2N1clVzZXIvKio6Z2V0IiwiL2FwaS92MS9yZXNldC8qKjpwdXQiLCIvYXBpL3YxL3VzZXIvZmFucy8qKjpBTEwiXSwianRpIjoiZjMxNmU3NjMtNDY4OC00ZDZiLTkyNTktOTAxOTBiY2VkMzU5IiwiY2xpZW50X2lkIjoiYzBhODAxNDUtNWNjOS0xMjlmLTgxNWMtY2E3YmY1NTUwMDAzIn0.PsP96DLbXoiBgBTyNbOLubz60Q7cuRN9oaymXpXZKfk
     * expires_in : 7199
     * scope : read write
     * jti : edf9b984-5543-4469-baea-5642c9b643c7
     */

    private String access_token;
    private String token_type;
    private String refresh_token;
    private int expires_in;
    private String scope;
    private String jti;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getJti() {
        return jti;
    }

    public void setJti(String jti) {
        this.jti = jti;
    }
}
