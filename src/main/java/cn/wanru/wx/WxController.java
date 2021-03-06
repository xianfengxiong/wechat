package cn.wanru.wx;

import cn.wanru.util.JsonUtil;
import cn.wanru.util.WxUtil;
import com.google.gson.reflect.TypeToken;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xxf
 * @since 1/23/17
 */
@RestController
@RequestMapping("/wx")
public class WxController {

  private final Logger log = LoggerFactory.getLogger(getClass());

  Map<String,WxUserInfo> cache = new ConcurrentHashMap<>();

  @Autowired private UserService userService;

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
    response.sendRedirect(WxUtil.getRedirectUrl("/wx/callback"));
    log.info("redirect to wechat auth page");
  }

  @RequestMapping("/callback")
  public ModelAndView callBack(HttpServletRequest request)
      throws IOException, SQLException, ServletException {

    String code = request.getParameter("code");

    log.info("prepare to get access token, code={}",code);

    String url = WxUtil.getAccessTokenUrl(code);
    String entity = Jsoup.connect(url).maxBodySize(0).execute().body();
    AccessToken accessToken = JsonUtil.fromJson(entity,new TypeToken<AccessToken>() {});
    User user = userService.findByOpenID(accessToken.getOpenid());
    if (user == null) {
      return getUserInfo(accessToken);
    }else{
      return skipBind(user);
    }
  }

  private ModelAndView skipBind(User user) {
    ModelAndView mav = new ModelAndView("index");
    mav.addObject("user",user);
    return mav;
  }

  private ModelAndView getUserInfo(AccessToken accessToken) throws IOException {
    String url = WxUtil.getUserInfoUrl(accessToken);
    String entity = Jsoup.connect(url).maxBodySize(0).execute().body();

    log.info("get user info entity string {}",entity);

    WxUserInfo wxUserInfo = JsonUtil.fromJson(entity,new TypeToken<WxUserInfo>(){});
    return bindPage(wxUserInfo);
  }

  private ModelAndView bindPage(WxUserInfo wxUserInfo) {
    ModelAndView mav = new ModelAndView("wx_bind");
    cache.put(wxUserInfo.getOpenid(),wxUserInfo);
    mav.addObject("openid",wxUserInfo.getOpenid());
    return mav;
  }


  @RequestMapping("/bind")
  public ModelAndView bindAccount(String openid,String account,String password)
      throws SQLException, ServletException, IOException {

    WxUserInfo wxUserInfo = cache.remove(openid);
    User user = new User();
    user.setAccount(account);
    user.setPassword(password);
    user.setOpenid(wxUserInfo.getOpenid());
    user.setUnionid(wxUserInfo.getUnionid());
    user.setCountry(wxUserInfo.getCountry());
    user.setProvince(wxUserInfo.getProvince());
    user.setCity(wxUserInfo.getCity());
    user.setHeadimgurl(wxUserInfo.getHeadimgurl());
    user.setUnionid(wxUserInfo.getUnionid());
    user.setNickname(wxUserInfo.getNickname());
    user.setSex(wxUserInfo.getSex());
    user = userService.save(user);

    return skipBind(user);
  }

  @RequestMapping("/loginform")
  public ModelAndView loginForm() {
    return new ModelAndView("wxlogin");
  }



}
