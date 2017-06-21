package cn.wanru.util;

import cn.wanru.wx.AccessToken;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author xxf
 * @since 2017/6/21
 */
public class WxUtil {

  public static final String HOST = "http://www.wanruxiong.cn";

  public static final String APPID = "wx01dad7f2fc0d2f00";

  public static final String APP_SECRET = "ec86384fae0717ad9f178d98298cf083";

  public static String getRedirectUrl(String uri) {
    String prefix = "";
    if (!uri.startsWith("/")){
      prefix = "/";
    }
    String redirectUrl = HOST + prefix + uri;
    String encodedUrl = null;
    try{
      encodedUrl = URLEncoder.encode(redirectUrl, "UTF-8");
    }catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
    return "https://open.weixin.qq.com/connect/oauth2/authorize?" +
        "appid=" + APPID +
        "&redirect_uri=" + encodedUrl +
        "&response_type=code" +
        "&scope=snsapi_userinfo" +
        "&state=STATE#wechat_redirect";
  }

  public static String getAccessTokenUrl(String code){
    return "https://api.weixin.qq.com/sns/oauth2/access_token?" +
        "appid=" + APPID +
        "&secret=" + APP_SECRET +
        "&code=" + code +
        "&grant_type=authorization_code";
  }

  public static String getUserInfoUrl(AccessToken accessToken) {
    return "https://api.weixin.qq.com/sns/userinfo?" +
        "access_token=" + accessToken.getAccess_token() +
        "&openid=" + accessToken.getOpenid() +
        "&lang=zh_CN";
  }
}