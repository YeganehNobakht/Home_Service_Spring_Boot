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
        ServiceCategoryDto service = serviceCategoryService.getByName(serviceCategoryDto.getName());
        if (service!=null) {
            serviceCategoryService.sava(serviceCategoryDto);
            model.addAttribute("success", maktabMessageSource.getEnglish("service.add"));
        }
        else
            logger.warn("...Service already exist...");
            model.addAttribute("success", maktabMessageSource.getEnglish("service.exist"));
        return "managerSuccessPage";
    }
}
