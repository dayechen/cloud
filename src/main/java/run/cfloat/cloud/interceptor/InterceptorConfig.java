package run.cfloat.cloud.interceptor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {
    // @Override
    // public void addInterceptors(InterceptorRegistry registry) {
    // registry.addInterceptor(authenticationInterceptor())
    // .addPathPatterns("/**");
    // }
    private List<String> getPathList() {
        final var result = new ArrayList<String>();
        result.add("/home/cddy/store/no1/movie/yellow");
        result.add("/home/cddy/store/no2/movie/yellow");
        return result;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/image/**")
                .addResourceLocations("file:/home/cddy/store/image/yellow/");
        getPathList().forEach(x -> {
            registry.addResourceHandler("/video/**")
                    .addResourceLocations("file:" + x + "/");
        });
    }

    // @Bean
    // public AuthenticationInterceptor authenticationInterceptor() {
    // return new AuthenticationInterceptor();
    // }
}
