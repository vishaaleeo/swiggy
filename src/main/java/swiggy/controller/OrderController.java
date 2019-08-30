package swiggy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import swiggy.domain.Order;
import swiggy.repository.OrderRepository;
import swiggy.services.OrderService;

@Controller
@RequestMapping("/order")
public class OrderController {


    @Autowired
    OrderService orderService;

    @PostMapping(value="/create",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createOrder(@RequestBody Order order) {

        String createadOrder=orderService.createOrder(order);
        if(createadOrder==null) {
            return new ResponseEntity("{\"message\" : \"different on cart order exists\"}", HttpStatus.OK);
        }
        else {
            return new ResponseEntity(createadOrder,HttpStatus.OK);
        }

    }

    @PostMapping(value="/update",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateOrder(@RequestBody Order order) {

        return new ResponseEntity(orderService.updateOrder(order),HttpStatus.OK);
    }


    @PostMapping(value="/updateStatus",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateOrderStatus(@RequestBody Order order) {

        orderService.updateOrderStatus(order);
        return new ResponseEntity("{\"message\" : \"done\"}",HttpStatus.OK);
    }

    @PostMapping(value="/applyOffer",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity applyOffer(@RequestBody Order order) {

        return new ResponseEntity(orderService.checkOffer(order),HttpStatus.OK);
    }

    @GetMapping(value="/displayOnCart", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity displayOrder() {


        return new ResponseEntity(orderService.onCartDisplay(),HttpStatus.OK);
    }

    @GetMapping(value="/display",produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity displayOrders() {

        return new ResponseEntity(orderService.displayOrder(),HttpStatus.OK);
    }

    @GetMapping(value="/displayById/{orderGroupId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity displayById(@PathVariable("orderGroupId") String orderGroupId){

        return new ResponseEntity(orderService.displayById(orderGroupId),HttpStatus.OK);
    }
}
