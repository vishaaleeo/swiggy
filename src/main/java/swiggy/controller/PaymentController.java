package swiggy.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import swiggy.domain.Payment;
import swiggy.repository.PaymentRepository;
import swiggy.services.PaymentService;

@Controller
@RequestMapping("/payment")
public class PaymentController {


    @Autowired
    PaymentService paymentService;

    @PostMapping(value="/checkOut",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createPayment(@RequestBody Payment payment){

        return new ResponseEntity(paymentService.createPayment(payment), HttpStatus.OK);


    }

    @PostMapping(value="/update",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updatePayment(@RequestBody Payment payment) {

        return new ResponseEntity(paymentService.updatePayment(payment),HttpStatus.OK);
    }


}
