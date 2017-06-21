package cn.wanru.wx;

/**
 * @author xxf
 * @since 2017/6/21
 */
public class AccessToken {

  private String access_token;

  private String openid;

  private int expires_in;

  private String refresh_token;

  private String scope;

  public String getAccess_token() {
    return access_token;
  }

  public void setAccess_token(String access_token) {
    this.access_token = access_token;
  }

  public String getOpenid() {
    return openid;
  }

  public void setOpenid(String openid) {
    this.openid = openid;
  }

  public int getExpires_in() {
    return expires_in;
  }

  public void setExpires_in(int expires_in) {
    this.expires_in = expires_in;
  }

  public String getRefresh_token() {
    return refresh_token;
  }

  public void setRefresh_token(String refresh_token) {
    this.refresh_token = refresh_token;
  }

  public String getScope() {
    return scope;
  }

  public void setScope(String scope) {
    this.scope = scope;
  }

  @Override
  public String toString() {
    return "AccessToken{" +
        "access_token='" + access_token + '\'' +
        ", openid='" + openid + '\'' +
        ", expires_in=" + expires_in +
        ", refresh_token='" + refresh_token + '\'' +
        ", scope='" + scope + '\'' +
        '}';
  }
}
