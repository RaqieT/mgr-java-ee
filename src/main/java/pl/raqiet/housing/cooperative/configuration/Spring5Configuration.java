package pl.raqiet.housing.cooperative.configuration;

import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import java.util.Locale;

@Configuration
@EnableWebMvc
@ComponentScan("pl.raqiet.housing.cooperative")
public class Spring5Configuration implements WebMvcConfigurer {

    private final ApplicationContext applicationContext;
    private final ConvertersLoader convertersLoader;

    public Spring5Configuration(ApplicationContext applicationContext, ConvertersLoader convertersLoader) {
        this.applicationContext = applicationContext;
        this.convertersLoader = convertersLoader;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("/static/");
    }

    /*
     * STEP 1 - Create SpringResourceTemplateResolver
     * */
    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        var templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setPrefix("/WEB-INF/views/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML5");
        templateResolver.setCharacterEncoding("UTF-8");
        return templateResolver;
    }

    /*
     * STEP 2 - Create SpringTemplateEngine
     * */
    @Bean
    public SpringTemplateEngine templateEngine() {
        var templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.setEnableSpringELCompiler(true);
        templateEngine.addDialect(new SpringSecurityDialect());
        return templateEngine;
    }

    /*
     * STEP 3 - Register ThymeleafViewResolver
     * */
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        var resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine());
        resolver.setCharacterEncoding("UTF-8");
        registry.viewResolver(resolver);
    }

    // Configure interceptor to switch language when 'lang' parameter found in request
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        var interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("lang");
        registry.addInterceptor(interceptor);
    }

    @Override
    public void addFormatters(FormatterRegistry formatterRegistry)
    {
        formatterRegistry.addConverter(convertersLoader.roleConverter());
        formatterRegistry.addConverter(convertersLoader.appUserConverter());
        formatterRegistry.addConverter(convertersLoader.blockConverter());
    }
}

