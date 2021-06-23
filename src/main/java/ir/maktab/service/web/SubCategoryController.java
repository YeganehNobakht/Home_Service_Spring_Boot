package ir.maktab.service.web;


import ir.maktab.service.dto.OrderDto;
import ir.maktab.service.dto.ServiceCategoryDto;
import ir.maktab.service.dto.SubCategoryDto;
import ir.maktab.service.service.serviceCategory.ServiceCategoryService;
import ir.maktab.service.service.subCategoryService.SubCategoryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Yeganeh Nobakht
 **/
@Controller
@RequestMapping("/sub")
public class SubCategoryController {
    private final Logger logger = LogManager.getLogger(SubCategoryController.class);
    private final SubCategoryService subCategoryService;
    private final ServiceCategoryService serviceCategoryService;

    public SubCategoryController(SubCategoryService subCategoryService, ServiceCategoryService serviceCategoryService) {
        this.subCategoryService = subCategoryService;
        this.serviceCategoryService = serviceCategoryService;
    }

    @GetMapping("/subCategory")
    public String getSubServices(@RequestParam("service") String service, Model model,
                                 @SessionAttribute("serviceList") List<ServiceCategoryDto> serviceList
            , @SessionAttribute("newOrder") OrderDto orderDto) {
        logger.info("...showing all sub-services...");
        List<String> subCategory = subCategoryService.getByServiceName(service);
        model.addAttribute("newOrder", orderDto);
        model.addAttribute("subServiceList", subCategory);
        model.addAttribute("serviceList", serviceList);
        model.addAttribute("selectedService", service);
        return "showOrder";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("subServiceDto")SubCategoryDto subCategoryDto, Model model) throws Exception {
        logger.info("...adding a new sub-service to a service...");
        ServiceCategoryDto service = serviceCategoryService.getByName(subCategoryDto.getServiceCategory().getName());
        subCategoryDto.setServiceCategory(service);
        subCategoryService.sava(subCategoryDto);
        model.addAttribute("success","Sub-service successfully added.");
        return "managerSuccessPage";
    }
}
