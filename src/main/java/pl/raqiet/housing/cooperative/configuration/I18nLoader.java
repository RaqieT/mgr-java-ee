package pl.raqiet.housing.cooperative.configuration;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import java.util.Locale;

@Configuration
public class I18nLoader {
    @Bean
    public MessageSource messageSource() {
        var messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("/static/i18n/messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    // Configure LocaleResolver with default locale as 'en'
    @Bean
    public LocaleResolver localeResolver() {
        var resolver = new CookieLocaleResolver();
        resolver.setDefaultLocale(Locale.ENGLISH);
        resolver.setCookieName("housing-cooperative");
        resolver.setCookieMaxAge(4800);
        return resolver;
    }
}
