package swiggy.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import swiggy.domain.Customization;
import swiggy.services.CustomizationService;

@Controller
@RequestMapping("/customization")
public class FoodCustomizationManagement {

    @Autowired
    CustomizationService customizationService;

    @PostMapping(value="/create",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createCustomization(@RequestBody Customization customization) {

        return new ResponseEntity(customizationService.createCustomization(customization), HttpStatus.OK);
    }

    @PostMapping(value="/update",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateCustomization(@RequestBody Customization customization) {

        return new ResponseEntity(customizationService.updateCustomization(customization),HttpStatus.OK);
    }

    @PostMapping(value="/delete",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteCustomization(@RequestBody Customization customization) {

        return new ResponseEntity(customizationService.deleteCustomization(customization),HttpStatus.OK);
    }

    @PostMapping(value="/display",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity displayCustomization(@RequestBody Customization customization) {

        return new ResponseEntity(customizationService.displayCustomization(customization),HttpStatus.OK);
    }
}
