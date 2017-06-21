package cn.wanru.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * @author xxf
 * @since 2017/6/21
 */
public class JsonUtil {

  private static final Gson GSON = new Gson();

  public static  <T> T fromJson(String json, TypeToken<T> tTypeToken) {
    return GSON.fromJson(json,tTypeToken.getType());
  }
}
