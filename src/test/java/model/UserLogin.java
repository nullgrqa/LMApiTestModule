package model;

import com.google.gson.annotations.SerializedName;

public class UserLogin {

    @SerializedName("access-token")
    String accessToken;
    @SerializedName("refresh-token")
    String refreshToken;
    @SerializedName("expires-in")
    String expiresIn;
    User user;

    public String getAccessToken() {
        return accessToken;
    }

    public UserLogin withAccessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public UserLogin withRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        return this;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public UserLogin withExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
        return this;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserLogin{" +
                "accessToken='" + accessToken + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", expiresIn='" + expiresIn + '\'' +
                ", user=" + user +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserLogin)) return false;

        UserLogin userLogin = (UserLogin) o;

        if (getAccessToken() != null ? !getAccessToken().equals(userLogin.getAccessToken()) : userLogin.getAccessToken() != null)
            return false;
        if (getRefreshToken() != null ? !getRefreshToken().equals(userLogin.getRefreshToken()) : userLogin.getRefreshToken() != null)
            return false;
        if (getExpiresIn() != null ? !getExpiresIn().equals(userLogin.getExpiresIn()) : userLogin.getExpiresIn() != null)
            return false;
        return getUser() != null ? getUser().equals(userLogin.getUser()) : userLogin.getUser() == null;
    }

    @Override
    public int hashCode() {
        int result = getAccessToken() != null ? getAccessToken().hashCode() : 0;
        result = 31 * result + (getRefreshToken() != null ? getRefreshToken().hashCode() : 0);
        result = 31 * result + (getExpiresIn() != null ? getExpiresIn().hashCode() : 0);
        result = 31 * result + (getUser() != null ? getUser().hashCode() : 0);
        return result;
    }
}
