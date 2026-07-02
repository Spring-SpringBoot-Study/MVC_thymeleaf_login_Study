package hello.login;

import hello.login.web.filter.LogFilter;
import hello.login.web.filter.LoginCheckFilter;
import hello.login.web.interceptor.LogInterceptor;
import hello.login.web.interceptor.LoginCheckInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.servlet.Filter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // @Bean
    public FilterRegistrationBean logFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();

        filterRegistrationBean.setFilter(new LogFilter()); // 등록할 필터 지정
        filterRegistrationBean.setOrder(1); // 필터의 순서(1번 필터)
        filterRegistrationBean.addUrlPatterns("/*"); // 모든 URL에 적용

        return filterRegistrationBean;
    }

    // @Bean
    public FilterRegistrationBean loginCheckFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();

        filterRegistrationBean.setFilter(new LoginCheckFilter()); // 등록할 필터 지정
        filterRegistrationBean.setOrder(2); // 필터의 순서(2번 필터)
        filterRegistrationBean.addUrlPatterns("/*"); // 모든 URL에 적용

        return filterRegistrationBean;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor()) // 스프링 인터셉터 지정
                .order(1)
                .addPathPatterns("/**") // 하위 경로는 전부 포함
                .excludePathPatterns("/css/**", "/*.ico", "/error"); // 이 항목들은 인터셉터에서 제외!

        registry.addInterceptor(new LoginCheckInterceptor()) // 스프링 인터셉터 지정
                .order(2)
                .addPathPatterns("/**") // 하위 경로는 전부 포함
                .excludePathPatterns(
                        "/", "/members/add", "/login", "/logout",
                        "/css/**", "/*.ico", "/error"
                ); // LoginCheckInterceptor에서 whiteList를 만들지 않고, 인터셉터를 등록할 때, 위와 같이 제외할 패턴을 지정함
    }

}
