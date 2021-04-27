package co.edu.eci.appsecure.model;

import java.io.Serializable;

public class Token implements Serializable {

    private String accessToken;

    public Token() {
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public String toString() {
        return "Token{" +
                "accessToken='" + accessToken + '\'' +
                '}';
    }
}
