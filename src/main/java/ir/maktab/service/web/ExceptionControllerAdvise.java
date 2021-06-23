package ir.maktab.service.web;

import ir.maktab.service.configuration.LastViewInterceptor;
import ir.maktab.service.dto.CustomerDto;
import ir.maktab.service.dto.ManagerDto;
import ir.maktab.service.dto.SpecialistDto;
import ir.maktab.service.exceptions.checkes.*;
import ir.maktab.service.service.maktabMassageSource.MaktabMessageSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@ControllerAdvice
public class ExceptionControllerAdvise {
    private final static Logger logger=LogManager.getLogger(ExceptionControllerAdvise.class);
    private final MaktabMessageSource maktabMessageSource;

    public ExceptionControllerAdvise(MaktabMessageSource maktabMessageSource) {
        this.maktabMessageSource = maktabMessageSource;
    }

    @ExceptionHandler(value = BindException.class)
    public ModelAndView bindExceptionHandler(BindException ex, HttpServletRequest request) {
        Map<String, Object> model = ex.getBindingResult().getModel();
        String lastView = (String) request.getSession().getAttribute(LastViewInterceptor.LAST_VIEW_ATTRIBUTE);
        ex.getFieldErrors().forEach(
                error -> {
                    Object message = maktabMessageSource.getEnglish(Objects.requireNonNull(error.getDefaultMessage()));
                    model.put(error.getField(),message);
                    logger.error(message);
                });
        return new ModelAndView(lastView,model);
    }
    @ExceptionHandler({PasswordNotFoundException.class,UserNameNotFoundException.class,TooLowSubServicePriceException.class,
            OrderNotFoundException.class,OrderException.class,SubServiceNotFoundException.class,ServiceNotFoundException.class,OrderNoSuggestionException.class,
            SuggestionNotFoundException.class,CustomerNotFoundException.class,
            SpecialistNotFoundException.class,DuplicateEmailException.class})
    public ModelAndView passwordException(Exception e, HttpServletRequest request){
        Map<String, Object> model = new HashMap<>();
        logger.error(e.getMessage());
        model.put("error", e.getMessage());
        model.put("customerDto", new CustomerDto());
        model.put("specialistDto",new SpecialistDto());
        model.put("managerDto", new ManagerDto());
        String lastView = (String) request.getSession().getAttribute(LastViewInterceptor.LAST_VIEW_ATTRIBUTE);
        return new ModelAndView(lastView, model);
    }






}
