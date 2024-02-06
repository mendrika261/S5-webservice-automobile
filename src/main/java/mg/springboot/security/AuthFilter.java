package mg.springboot.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mg.springboot.repository.PageRepository;
import mg.springboot.service.TokenService;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

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
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = ((HttpServletResponse) servletResponse);
        Response<?> response = null;

        // Cors Parameters
        httpResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS");
        httpResponse.setHeader("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Headers, Authorization, Origin, Access-Control-Request-Method, ngrok-skip-browser-warning");
        httpResponse.setHeader("Feature-Policy", "accelerometer *");

        if(httpRequest.getMethod().equals("OPTIONS")) { // allow preflight request
            httpResponse.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        String uri = httpRequest.getRequestURI();
        List<Page> pages = pageRepository.findAllByUrlStartsWith(uri.toLowerCase());
        if(!pages.isEmpty()) {
            int level = 0;
            Token token = tokenService.getToken(httpRequest);
            if (token != null) {
                if(token.isValid()) {
                    level = token.getUtilisateur().getLevel();
                    tokenService.updateLastActivityByValue(LocalDateTime.now(), token);
                    for (Page page : pages) {
                        if (page.isValid(level)) {
                            filterChain.doFilter(servletRequest, servletResponse);
                            return;
                        } else {
                            httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            response = Response.denied("Vous n'avez pas le droit d'accéder à cette page");
                        }
                    }
                } else {
                    tokenService.delete(token);
                    httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response = Response.denied("Votre session a expiré");
                }
            } else {
                for (Page page : pages) {
                    if (page.getLevel() == 0) {
                        filterChain.doFilter(servletRequest, servletResponse);
                        return;
                    } else {
                        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        response = Response.denied("Vous devez vous connecter pour accéder à cette page");
                    }
                }
            }
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
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
