package cn.wanru;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author xxf
 * @since 1/24/17
 */
@SpringBootApplication
public class Application extends WebMvcConfigurerAdapter {

  public static void main(String[] args) {
    SpringApplication.run(Application.class,args);
  }

  @Bean
  public FilterRegistrationBean filter() {
    FilterRegistrationBean filter = new FilterRegistrationBean();
    AccessFilter accessFilter = new AccessFilter();
    filter.setFilter(accessFilter);
    filter.addUrlPatterns("/*");
    return filter;
  }

  @Override
  public void configureViewResolvers(ViewResolverRegistry registry) {
    registry.jsp("/WEB-INF/jsp/",".jsp");
  }

}
