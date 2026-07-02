package hello.login.web.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.UUID;

@Slf4j
public class LogFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("log filter init");
    }

    @Override
    // 고객의 요청이 올 때마다, 호출할 메서드 함수
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request; // 다운 캐스팅 필요
        String requestURI = httpRequest.getRequestURI();

        String uuid = UUID.randomUUID().toString(); // 각 요청을 구분하기 위해서(그냥 로그에서 각 요청을 구분하기 위함)

        try {
            log.info("REQUEST  [{}][{}]", uuid, requestURI);
            chain.doFilter(request, response); // 다음 필터를 호출하도록 함(다음 필터가 있으면, 재귀로 계속 호출, 없으면 서블릿 단계로 넘어감)
        } catch (Exception e) {
            throw e;
        }finally {
            log.info("RESPONSE [{}][{}]", uuid, requestURI);
        }
    }

    @Override
    public void destroy() {
        log.info("log filter destroy");
    }
}
