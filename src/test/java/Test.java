import cn.wanru.wx.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * @author xxf
 * @since 1/23/17
 */
public class Test {

  @org.junit.Test
  public void test() {
    String str = "{\"openid\":\"oyOKxt6c2SQDbKEz8DT2vRS-VH10\",\"nickname\":\"???????????????\",\"sex\":1,\"language\":\"zh_CN\",\"city\":\"??????\",\"province\":\"??????\",\"country\":\"??????\",\"headimgurl\":\"http:\\/\\/wx.qlogo.cn\\/mmopen\\/G3k9TImAGTCDoCjIXXRKOdiarMjEZHPIlZ3BgUOwYsVAZIS9SaCWP7C0ghBZSL5RVNedKOWrk9uC0MPloxwdQxHZia353jkoAl\\/0\",\"privilege\":[]}";
    User user = new Gson().fromJson(str,new TypeToken<User>(){}.getType());
    System.out.println(user.getOpenid());
  }


}
