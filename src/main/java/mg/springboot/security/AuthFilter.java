package mg.springboot.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import mg.springboot.repository.PageRepository;
import mg.springboot.service.TokenService;
import org.springframework.stereotype.Component;

@Component
public class AuthFilter implements Filter {
  private final TokenService tokenService;
  private final PageRepository pageRepository;

  public AuthFilter(TokenService tokenService, PageRepository pageRepository) {
    this.tokenService = tokenService;
    this.pageRepository = pageRepository;
  }

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    Filter.super.init(filterConfig);
  }

  @Override
  public void doFilter(ServletRequest servletRequest,
                       ServletResponse servletResponse, FilterChain filterChain)
      throws IOException, ServletException {
    HttpServletRequest httpRequest = (HttpServletRequest)servletRequest;
    HttpServletResponse httpResponse = ((HttpServletResponse)servletResponse);
    Response<?> response;

    // Cors Parameters
    httpResponse.setHeader("Access-Control-Allow-Origin", "*");
    httpResponse.setHeader("Access-Control-Allow-Methods",
                           "POST, GET, PUT, DELETE");
    httpResponse.setHeader(
        "Access-Control-Allow-Headers",
        "Content-Type, Access-Control-Allow-Headers, Authorization");

    String uri = httpRequest.getRequestURI();
    List<Page> pages =
        pageRepository.findAllByUrlStartingWith(uri.toLowerCase());
    if (pages.isEmpty()) {
      /* httpResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
      response = Response.notFound("Page not found (Mety mbola tsy ao anaty base
      de données?)"); */
      // By pass for dev
      filterChain.doFilter(servletRequest, servletResponse);
      return;
    } else {
      int level = 0;
      Token token = tokenService.getToken(httpRequest);
      if (token != null) {
        if (token.isValid()) {
          level = token.getUtilisateur().getLevel();
          tokenService.updateLastActivityByValue(LocalDateTime.now(), token);
        } else if (!token.isDeleted())
          tokenService.delete(token);
      }

      for (Page page : pages) {
        if (page.isValid(level)) {
          filterChain.doFilter(servletRequest, servletResponse);
          return;
        }
      }
      httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
      response = Response.denied("Vous n'avez pas accès à cette page");
    }

    httpResponse.setContentType("application/json");
    ObjectMapper objectMapper = new ObjectMapper();
    httpResponse.getWriter().write(objectMapper.writeValueAsString(response));
  }

  @Override
  public void destroy() {
    Filter.super.destroy();
  }
}
