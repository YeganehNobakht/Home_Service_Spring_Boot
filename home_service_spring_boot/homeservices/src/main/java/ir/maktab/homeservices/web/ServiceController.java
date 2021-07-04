package ir.maktab.homeservices.web;

import ir.maktab.homeservices.dto.ServiceCategoryDto;
import ir.maktab.homeservices.service.maktabMassageSource.MaktabMessageSource;
import ir.maktab.homeservices.service.serviceCategory.ServiceCategoryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/service")
public class ServiceController {
    private final Logger logger = LogManager.getLogger(ServiceController.class);
    private final ServiceCategoryService serviceCategoryService;
    private final MaktabMessageSource maktabMessageSource;

    public ServiceController(ServiceCategoryService serviceCategoryService, MaktabMessageSource maktabMessageSource) {
        this.serviceCategoryService = serviceCategoryService;
        this.maktabMessageSource = maktabMessageSource;
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("subServiceDto") ServiceCategoryDto serviceCategoryDto, Model model) throws Exception {
        logger.info("...add service to service list...");
        serviceCategoryService.addServiceCategory(serviceCategoryDto);
        model.addAttribute("message", maktabMessageSource.getEnglish("service.added", new Object[]{serviceCategoryDto.getName()}));
        model.addAttribute("serviceDto", new ServiceCategoryDto());
        return "managerAddService";
    }
}
