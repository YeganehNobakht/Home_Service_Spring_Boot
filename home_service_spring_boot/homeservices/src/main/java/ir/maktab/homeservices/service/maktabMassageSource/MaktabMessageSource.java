package ir.maktab.homeservices.service.maktabMassageSource;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;


@Component
public class MaktabMessageSource {

    private final static Locale LOCALE = new Locale("en_US");
    private final static Locale LOCALEFA = new Locale("fa_IR");

    private final MessageSource messageSource;

    public MaktabMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getEnglish(String key){
        return messageSource.getMessage(key, null,LOCALE);
    }

    public String getEnglish(String key,Object[] objects){
        return messageSource.getMessage(key,objects,LOCALE);
    }

    public String getPersian(String key){
        return messageSource.getMessage(key, null,LOCALEFA);
    }
    }

