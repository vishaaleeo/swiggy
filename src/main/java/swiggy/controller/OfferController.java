package swiggy.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import swiggy.domain.Offer;
import swiggy.services.OfferService;

@Controller
@RequestMapping("/offer")
public class OfferController {


    @Autowired
    OfferService offerService;


    @PostMapping(value="/create",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createOffer(@RequestBody Offer offer) {

        return new ResponseEntity(offerService.createOffer(offer), HttpStatus.OK);

    }

    @PostMapping(value="/update",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateOffer(@RequestBody Offer offer) {

        return new ResponseEntity(offerService.updateOffer(offer),HttpStatus.OK);
    }

    @PostMapping(value="/delete",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteOffer(@RequestBody Offer offer) {

        return new ResponseEntity(offerService.deleteOffer(offer),HttpStatus.OK);
    }

    @GetMapping(value="/display/{restaurantIdentifier}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity displayOffer(@PathVariable("restaurantIdentifier") String restaurantIdentifier) {

        return new ResponseEntity(offerService.displayOffers(restaurantIdentifier),HttpStatus.OK);
    }
}
