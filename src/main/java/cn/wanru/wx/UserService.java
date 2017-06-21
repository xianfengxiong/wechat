package cn.wanru.wx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xxf
 * @since 1/23/17
 */
@Service
public class UserService {

  @Autowired
  private UserRepo userRepo;

  public User findByOpenID(String openid) {
    return userRepo.findByOpenid(openid);
  }

  public User save(User user) {
    return userRepo.save(user);
  }

}
