package pl.raqiet.housing.cooperative.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class I18nService {
    private final MessageSource messageSource;

    public String getMessage(String code) {
        //Attention LocaleContextHolder.getLocale() is thread based,
        //maybe you need some fallback locale
        return messageSource.getMessage(code, new Object[0], LocaleContextHolder.getLocale());
    }
}
