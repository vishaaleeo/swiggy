package swiggy.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import swiggy.domain.Payment;
import swiggy.repository.PaymentRepository;

@Service
public class PaymentService {


    @Autowired
    PaymentRepository paymentRepository;

    EncodingDecoding encodingDecoding=new EncodingDecoding();


    public String createPayment(Payment payment) {

        return toJson(paymentRepository.save(payment));
    }

    public String updatePayment(Payment payment) {

        Payment toUpdatePayment;

        toUpdatePayment=paymentRepository.findOne(payment.getPayementIdentifier());

        if(toUpdatePayment==null) {

            return "{\"message\" : \"invalid\"}";

        }
        else {


            if (payment.getPaymentMode() != null) {

                toUpdatePayment.setPaymentMode(payment.getPaymentMode());

            }
            if(payment.getPaymentStatus()!=null) {

                toUpdatePayment.setPaymentStatus(payment.getPaymentStatus());
            }

            return toJson(paymentRepository.save(toUpdatePayment));
        }
    }

    public String toJson(Payment payment) {

        String json = "{";

        if (payment.getPayementIdentifier() != null)
            json += "\"paymentId\" : \"" + encodingDecoding.encode(payment.getPayementIdentifier()) + "\",";

        if (payment.getOrderIdentifier() != null)
            json += "\"orderId\" : \"" + encodingDecoding.encode(payment.getOrderIdentifier()) + "\",";

        if (payment.getPaymentAmount() != null)
            json += "\"paymentAmount\" : \"" + (payment.getPaymentAmount()) + "\",";

        json += "}";

        return json;
    }




 }






