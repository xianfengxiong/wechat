package cn.wanru.wx;

import cn.wanru.jdbc.JdbcUtils;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author xxf
 * @since 1/23/17
 */
@Service
public class UserService {

  public User findByOpenID(String openid) {
    Connection conn = null;
    try {
      conn = JdbcUtils.getConnection();
    } catch (SQLException e) {
      return null;
    }

    String sql = "select id,account,password,openid,nickname," +
        "sex,province,city,country,headimgurl,unionid" +
        " from user where openid=?";

    ResultSetHandler<User> handler = new ResultSetHandler<User>() {
      @Override
      public User handle(ResultSet rs) throws SQLException {
        User user = new User();
        int id = rs.getInt("id");
        String account = rs.getString("account");
        String password = rs.getString("account");
        String openid = rs.getString("account");
        String nickname = rs.getString("account");
        String sex = rs.getString("account");
        String province = rs.getString("account");
        String city = rs.getString("account");
        String country = rs.getString("account");
        String headimgurl = rs.getString("account");
        String unionid = rs.getString("account");
        user.setId(id);
        user.setAccount(account);
        user.setPassword(password);
        user.setOpenid(openid);
        user.setNickname(nickname);
        user.setSex(sex);
        user.setProvince(province);
        user.setCity(city);
        user.setCountry(country);
        user.setHeadimgurl(headimgurl);
        user.setUnionid(unionid);
        return user;
      }
    };

    QueryRunner queryRunner = new QueryRunner();
    try {
      User result = queryRunner.query(conn, sql, handler, openid);
      DbUtils.closeQuietly(conn);
      return result;
    }catch (SQLException e){
      return null;
    }
  }

  public User saveUserInfo(User user) throws SQLException {
    String sql = "update user set " +
        "openid = ?,nickname = ?," +
        "sex = ?,province = ?," +
        "city = ?,country = ?," +
        "headimgurl=?,unionid=? " +
        "where account=? and password=?";

    Connection conn = JdbcUtils.getConnection();
    QueryRunner queryRunner = new QueryRunner();
    int result = queryRunner.update(conn,sql,
        user.getOpenid(),
        user.getNickname(),
        user.getSex(),
        user.getProvince(),
        user.getCity(),
        user.getCountry(),
        user.getHeadimgurl(),
        user.getUnionid(),
        user.getAccount(),
        user.getPassword());
    if (result > 0) {
      return findByOpenID(user.getOpenid());
    }
    else{
      return null;
    }
  }

}
