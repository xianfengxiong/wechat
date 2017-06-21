package cn.wanru;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/**
 * @author xxf
 * @since 1/23/17
 */
public class AccessFilter implements Filter {

  private final Logger log = LoggerFactory.getLogger(getClass());
  private final Gson gson = new Gson();

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {

  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    HttpServletRequest httpServletRequest = (HttpServletRequest) request;
    String uri = httpServletRequest.getRequestURI();
    Map<String,String[]> paramMap = request.getParameterMap();
    String json = gson.toJson(paramMap);
    log.info("{} {}",uri,json);

    chain.doFilter(request,response);
  }

  @Override
  public void destroy() {

  }
}
