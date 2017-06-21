package cn.wanru.wx;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xxf
 * @since 1/23/17
 */
@Controller
@RequestMapping("/wx")
public class WxController {

  Map<String,User> cache = new ConcurrentHashMap<>();

  @Autowired
  private UserService userService;

  private final Logger log = LoggerFactory.getLogger(getClass());

  private static final String APPID = "wx01dad7f2fc0d2f00";
  private static final String APP_SECRET = "ec86384fae0717ad9f178d98298cf083";

  @ResponseBody
  @RequestMapping("/verify")
  public String verify(String signature,String timestamp,String nonce,String echostr) {
//    String token = "andy429006";
//    List<String> list = Arrays.asList(token,timestamp,nonce);
//    Collections.sort(list);
//    String s = String.join("",list);
//     DigestUtils.sha1Hex(s);
    return echostr;
  }


  @RequestMapping("/login")
  public void login(HttpServletResponse response) throws IOException {
    String redirectUrl = "http://www.wangruxiong.cn/wx/callback";
    String encodedUrl = URLEncoder.encode(redirectUrl, "UTF-8");

    String url = "https://open.weixin.qq.com/connect/oauth2/authorize?" +
        "appid=" + APPID +
        "&redirect_uri=" + encodedUrl +
        "&response_type=code" +
        "&scope=snsapi_userinfo" +
        "&state=STATE#wechat_redirect";

    log.info("prepare go to wechat auth page.");

    response.sendRedirect(url);

    log.info("redirect to wechat success.");
  }

  @RequestMapping("/callback")
  public ModelAndView callBack(HttpServletRequest request)
      throws IOException, SQLException, ServletException {

    log.info("from wechat's callback request={}",request);

    String code = request.getParameter("code");
    String url = "https://api.weixin.qq.com/sns/oauth2/access_token?" +
        "appid=" + APPID +
        "&secret=" + APP_SECRET +
        "&code=" + code +
        "&grant_type=authorization_code";

    CloseableHttpClient client = HttpClientBuilder.create().build();
    HttpGet httpGet = new HttpGet(url);

    HttpResponse resp = client.execute(httpGet);
    HttpEntity entity = resp.getEntity();
    String entityString = EntityUtils.toString(entity);
    client.close();
    Map<String, String> result = new Gson().fromJson(entityString, new TypeToken<Map>() {}.getType());

    log.info("get access_token from wechat ,entity string {}",entityString);

    String accessToken = result.get("access_token");
    String openid = result.get("openid");

    return getUserInfo(accessToken,openid);
  }

  private ModelAndView getUserInfo(String accessToken,String openid)
      throws IOException {

    String url = "https://api.weixin.qq.com/sns/userinfo?" +
        "access_token=" + accessToken +
        "&openid=" + openid +
        "&lang=zh_CN";

    CloseableHttpClient client = HttpClientBuilder.create().build();
    HttpGet httpGet = new HttpGet(url);
    HttpResponse response = client.execute(httpGet);
    HttpEntity entity = response.getEntity();
    String entityString = EntityUtils.toString(entity);
    client.close();

    log.info("get user info entity string {}",entityString);

    User user = new Gson().fromJson(entityString,new TypeToken<User>(){}.getType());
    User dbUser = userService.findByOpenID(user.getOpenid());

    if (dbUser == null){
      cache.put(openid,user);
      log.info("openid {} not bind yet,cache",openid);

      ModelAndView bindView = new ModelAndView("wx_bind");
      bindView.addObject("openid",user.getOpenid());
      return bindView;
    }
    else{
      ModelAndView indexView = new ModelAndView("index");
      indexView.addObject("user",dbUser);
      return indexView;
    }
  }

  @RequestMapping("/bind")
  public ModelAndView bindAccount(String openid,String account,String password)
      throws SQLException, ServletException, IOException {

    User user = cache.remove(openid);
    user.setAccount(account);
    user.setPassword(password);
    user = userService.saveUserInfo(user);

    ModelAndView indexView = new ModelAndView("index");
    indexView.addObject("user",user);
    return indexView;
  }




}
