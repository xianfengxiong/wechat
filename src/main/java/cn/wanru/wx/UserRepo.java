package cn.wanru.wx;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author xxf
 * @since 2017/6/21
 */
public interface UserRepo extends JpaRepository<User,Long>{
  User findByOpenid(String openid);
}
