package snn.soluciones.com.config;

import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import snn.soluciones.com.interceptor.RedirectLoopPreventionInterceptor;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

  @Autowired(required = false)
  private RedirectLoopPreventionInterceptor redirectLoopPreventionInterceptor;

  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/error_403").setViewName("error_403");
  }

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public LocaleResolver localeResolver() {
    SessionLocaleResolver localeResolver = new SessionLocaleResolver();
    localeResolver.setDefaultLocale(new Locale("es", "ES"));
    return localeResolver;
  }

  @Bean
  public LocaleChangeInterceptor localeChangeInterceptor() {
    LocaleChangeInterceptor localeInterceptor = new LocaleChangeInterceptor();
    localeInterceptor.setParamName("lang");
    return localeInterceptor;
  }

  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(localeChangeInterceptor());

    // Agregar el interceptor de prevención de bucles si está disponible
    if (redirectLoopPreventionInterceptor != null) {
      registry.addInterceptor(redirectLoopPreventionInterceptor)
          .addPathPatterns("/**")
          .excludePathPatterns(
              "/css/**",
              "/js/**",
              "/images/**",
              "/vendor/**",
              "/fonts/**",
              "/assets/**",  // Corregido: era assets.bootstrap-datapicker
              "/scss/**",
              "/static/**",
              "/error",
              "/error/**"
          );
    }
  }
}