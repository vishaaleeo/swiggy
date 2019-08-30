package swiggy.services;


import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import swiggy.domain.*;
import swiggy.repository.*;

import java.sql.Timestamp;
import java.util.List;

@Service
public class OrderService {


    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OfferRepository offerRepository;

    @Autowired
    FoodRepository foodRepository;

    @Autowired
    CustomizationRepository customizationRepository;

    @Autowired
    RestaurantRepository restaurantRepository;

    EncodingDecoding encodingDecoding=new EncodingDecoding();



    public String createOrder(Order order) {

        Offer offer=new Offer();

        order=decode(order);

            AuthUser principal=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            order.setUserIdentifier(principal.getUserIdentifier());


        order.setCreatedTime(new Timestamp(System.currentTimeMillis()));
        order.setUpdatedTme(new Timestamp(System.currentTimeMillis()));
        order=setFoodDetails(order);
        order=setcustomizationCost(order);
        order.setDeleteFlag(false);
        if(order.getOrderStatus()==null || order.getOrderStatus()=="") {

            order.setOrderStatus("oncart");
        }


        List<Order> onCartOrders = orderRepository.findOnCart(order.getUserIdentifier());


        //System.out.println(onCartOrders.toString());

        if (onCartOrders.isEmpty()) {
            Integer identifier = orderRepository.findMaxGroupOrderIdentifierByUser();

            if(identifier==null) {
                identifier=0;
            }

            order.setOrderGroupIdentifier(identifier+1);


            return toJSON(orderRepository.save(order));
        }

        else if (onCartOrders.get(0).getRestaurantIdentifier() == order.getRestaurantIdentifier()) {

            for (Order onCartOrder : onCartOrders) {

                if (onCartOrder.getFoodIdentifier() == order.getFoodIdentifier() ) {

                        if (onCartOrder.getCustomizationIdentifier()!=null && order.getCustomizationIdentifier()!=null && onCartOrder.getCustomizationIdentifier().equals(order.getCustomizationIdentifier())) {

                            if (order.getFoodCount() != onCartOrder.getFoodCount()) {

                                onCartOrder.setFoodCount(order.getFoodCount());
                                onCartOrder.setOrderCost(order.getOrderCost());
                                onCartOrder.setUpdatedTme(new Timestamp(System.currentTimeMillis()));
                                orderRepository.save(onCartOrder);
                                if(onCartOrder.getOfferIdentifier()!=null)
                                offer.setOfferIdentifier(onCartOrder.getOfferIdentifier());
                                return applyOffer(offer,onCartOrder);

                            }
                        }
                        else if(order.getCustomizationIdentifier()==null && onCartOrder.getCustomizationIdentifier()==null) {
                            if (order.getFoodCount() != onCartOrder.getFoodCount()) {

                                onCartOrder.setFoodCount(order.getFoodCount());
                                onCartOrder.setOrderCost(order.getOrderCost());
                                onCartOrder.setUpdatedTme(new Timestamp(System.currentTimeMillis()));
                                orderRepository.save(onCartOrder);
                                if(onCartOrder.getOfferIdentifier()!=null)
                                offer.setOfferIdentifier(onCartOrder.getOfferIdentifier());
                                return applyOffer(offer,onCartOrder);

                            }
                        }
                        else
                        {
                            order.setOrderGroupIdentifier(onCartOrders.get(0).getOrderGroupIdentifier());
                            //order.setOfferIdentifier(onCartOrders.get(0).getOfferIdentifier());
                            orderRepository.save(order);
                            if(onCartOrders.get(0).getOfferIdentifier()!=null)
                            offer.setOfferIdentifier(onCartOrders.get(0).getOfferIdentifier());
                           return applyOffer(offer,order);
                        }




                    }

                }

            order.setOrderGroupIdentifier(onCartOrders.get(0).getOrderGroupIdentifier());
            orderRepository.save(order);
            if(onCartOrders.get(0).getOfferIdentifier()!=null)
            offer.setOfferIdentifier(onCartOrders.get(0).getOfferIdentifier());
            return applyOffer(offer,order);

        }


        else {
            return "{\"message\":\"Already existing on cart\"}";
        }
    }





    public String updateOrder(Order order) {

        //Offer offer;
        order=decode(order);
        Order toUpdateOrder =orderRepository.findOne(order.getOrderIdentifier());

        if(order.getFoodCount()!=null && order.getFoodCount()!=toUpdateOrder.getFoodCount()) {

            if(order.getFoodCount()==0) {
                deleteOrder(toUpdateOrder);
            }
            toUpdateOrder.setFoodCount(order.getFoodCount());
            toUpdateOrder=setFoodDetails(toUpdateOrder);

        }

        if(toUpdateOrder.getCustomizationIdentifier()!=null  && order.getCustomizationIdentifier()==null) {

            if(toUpdateOrder.getFoodCount()!=null && toUpdateOrder.getFoodCount()!=0) {
                toUpdateOrder.setCustomizationIdentifier(order.getCustomizationIdentifier());
                //  toUpdateOrder=setcustomizationCost(to as UpdateOrder);
                toUpdateOrder = setFoodDetails(toUpdateOrder);
                toUpdateOrder = setcustomizationCost(toUpdateOrder);
            }
        }

        if(order.getOrderStatus()!=null && order.getOrderStatus()!="") {

            // toUpdateOrder.setOrderStatus(order.getOrderStatus());
            updateOrderStatus(order);
        }
        toUpdateOrder.setUpdatedTme(new Timestamp(System.currentTimeMillis()));

        //orderRepository.save(toUpdateOrder);
        orderRepository.save(toUpdateOrder);

        Offer offer=new Offer();
        if(toUpdateOrder.getOfferIdentifier()!=null)
        offer.setOfferIdentifier(toUpdateOrder.getOfferIdentifier());
        return applyOffer(offer,toUpdateOrder);

    }



    public void updateOrderStatus(Order order){

        order=decode(order);

        List<Order> orders=orderRepository.findByOrderGroupIdentifier(order.getOrderGroupIdentifier());

        for(Order order1 :orders) {

            order1.setOrderStatus(order.getOrderStatus());
            orderRepository.save(order1);
        }

    }



    public void deleteOrder(Order toDeleteOrder) {

        toDeleteOrder=decode(toDeleteOrder);

        toDeleteOrder.setDeleteFlag(true);
        orderRepository.save(toDeleteOrder);


    }



    public String checkOffer(Order order) {

        order=decode(order);

       List<Order> toApplyorder = orderRepository.findByOrderGroupIdentifier(order.getOrderGroupIdentifier());

       if(toApplyorder!=null) {

           order.setRestaurantIdentifier(toApplyorder.get(0).getRestaurantIdentifier());
           Offer offer = null;

           if (order.getOfferIdentifier() == null) {

               offer = offerRepository.findByRestaurantIdentifierAndOfferCode(order.getRestaurantIdentifier(), order.getOfferCode());
           } else {

               offer = offerRepository.findOne(order.getOfferIdentifier());
           }

           if (offer == null) {

               return "{\"message\" : \"not found\"}";
           }


           return applyOffer(offer, order);
       }
       return "{\"message\":\"empty cart\"}";

    }




    public String applyOffer(Offer offer,Order order) {

        Double priceCut;

        AuthUser principal=(AuthUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

      //  List<Order> onCartOrders = orderRepository.findOnCart(principal.getUserIdentifier());

        List<Order> onCartOrders =orderRepository.findByOrderGroupIdentifier(order.getOrderGroupIdentifier());

        Double totalCost=(Double)calculateTotalCost(onCartOrders);

        Gson onCartGson=new Gson();

        String onCartList=onCartGson.toJson(onCartOrders);

        String message="not compatible";

        if(offer.getOfferIdentifier()!=null) {

            if (offer.getMinimumOrderValue() != null && totalCost > offer.getMinimumOrderValue()) {

                if (offer.getRateOfDiscount() == null) {
                    return "{ \"orders\" :" + onCartList + ", \"message\" : \"" + "not applied" + "\", \"totalCost\" : \"" + totalCost + "\"}";
                }

                priceCut = totalCost * (offer.getRateOfDiscount() / 100);

                if (offer.getMaximumDiscountAmount() != null && priceCut <= offer.getMaximumDiscountAmount()) {

                    totalCost = totalCost - priceCut;

                } else {
                    if (offer.getMaximumDiscountAmount() != null)
                        totalCost = totalCost - offer.getMaximumDiscountAmount();
                    else
                        totalCost = totalCost - priceCut;
                }
                updateOffer(onCartOrders, offer.getOfferIdentifier());

                message = "offer applied";

            } else if (offer.getMinimumOrderValue() == null) {

                if (offer.getRateOfDiscount() == null) {
                    return "{ \"orders\" :" + onCartList + ", \"message\" : \"" + "not applied" + "\", \"totalCost\" : \"" + totalCost + "\"}";
                }

                priceCut = totalCost * (offer.getRateOfDiscount() / 100);


                if (offer.getMaximumDiscountAmount() != null && priceCut <= offer.getMaximumDiscountAmount()) {

                    totalCost = totalCost - priceCut;

                } else {
                    if (offer.getMaximumDiscountAmount() != null)
                        totalCost = totalCost - offer.getMaximumDiscountAmount();
                    else
                        totalCost = totalCost - priceCut;
                }
                updateOffer(onCartOrders, offer.getOfferIdentifier());

                message = "offer applied";


            }

            return "{ \"orders\" :" + display(onCartOrders) + ", \"message\" : \"" + message + "\", \"totalCost\" : \"" + totalCost + "\"}";

        }
        else {

            message="no offers";
            return "{ \"orders\" :" + display(onCartOrders) + ", \"message\" : \"" + message + "\", \"totalCost\" : \"" + totalCost + "\"}";
        }
    }

    public String displayById(String orderGroupId){

        Integer orderGroupIdentifier=encodingDecoding.decode(orderGroupId);
        List<Order> orderList = orderRepository.findByOrderGroupIdentifier(orderGroupIdentifier);

        return "{\"orders\" : "+display(orderList)+"}";
    }

    public String display(List<Order> orders){

        String json="[";

        int iterator=0;

        for(iterator=0;iterator<orders.size()-1;iterator++) {


            orders.get(iterator).setRestaurantName(restaurantRepository.findOne(orders.get(iterator).getRestaurantIdentifier()).getRestaurantName());
            orders.get(iterator).setFoodName(foodRepository.findOne(orders.get(iterator).getFoodIdentifier()).getFoodName());
            json+=toJSON(orders.get(iterator));
            json+=",";

//
        }
//
        orders.get(iterator).setRestaurantName(restaurantRepository.findOne(orders.get(iterator).getRestaurantIdentifier()).getRestaurantName());
        orders.get(iterator).setFoodName(foodRepository.findOne(orders.get(iterator).getFoodIdentifier()).getFoodName());
        json+=toJSON(orders.get(iterator));

        json+="]";


        return json;
    }




    public String displayOrder() {


        AuthUser principal=(AuthUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<Order> orders=orderRepository.findByUserIdentifier(principal.getUserIdentifier());
        List<Object[]> ordersTotal = orderRepository.findOrders(principal.getUserIdentifier());

        String json="{ \"orders\":[";

        int iterator=0;

        for(iterator=0;iterator<orders.size()-1;iterator++) {


            orders.get(iterator).setRestaurantName(restaurantRepository.findOne(orders.get(iterator).getRestaurantIdentifier()).getRestaurantName());
            orders.get(iterator).setFoodName(foodRepository.findOne(orders.get(iterator).getFoodIdentifier()).getFoodName());
            json+=toJSON(orders.get(iterator));
            json+=",";

//            json+="{";
//            json+="\"restaurantIdentifier\" : \""+orders.get(iterator).getRestaurantIdentifier()+"\" ,";
//            json+="\"foodIdentifier\" : \""+orders.get(iterator).getFoodIdentifier()+"\",";
//            json+="\"foodCount\" :\""+orders.get(iterator).getFoodCount()+"\",";
//            json+="\"orderCost\" : \""+orders.get(iterator).getOrderCost()+"\"";
//            json+="},";
        }
//        json+="{";
//        json+="\"restaurantIdentifier\" : \""+orders.get(iterator).getRestaurantIdentifier()+"\" ,";
//        json+="\"foodIdentifier\" : \""+orders.get(iterator).getFoodIdentifier()+"\",";
//        json+="\"foodCount\" :\""+orders.get(iterator).getFoodCount()+"\",";
//        json+="\"orderCost\" : \""+orders.get(iterator).getOrderCost()+"\"";
//        json+="}";

        orders.get(iterator).setRestaurantName(restaurantRepository.findOne(orders.get(iterator).getRestaurantIdentifier()).getRestaurantName());
        orders.get(iterator).setFoodName(foodRepository.findOne(orders.get(iterator).getFoodIdentifier()).getFoodName());
        json+=toJSON(orders.get(iterator));

        json+="]}";


        return json;


    }



    public String onCartDisplay(){

        AuthUser principal=(AuthUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<Order> onCartOrders = orderRepository.findOnCart(principal.getUserIdentifier());


        String onCartList="";
        if(onCartOrders!=null && !onCartOrders.isEmpty()) {

            int iterator = 0;

            for (iterator = 0; iterator < onCartOrders.size() - 1; iterator++) {

                onCartOrders.get(iterator).setRestaurantName(restaurantRepository.findOne(onCartOrders.get(iterator).getRestaurantIdentifier()).getRestaurantName());
                onCartOrders.get(iterator).setFoodName(foodRepository.findOne(onCartOrders.get(iterator).getFoodIdentifier()).getFoodName());
                onCartOrders.get(iterator).setRestaurantCharges(getRestaurantCharge(onCartOrders.get(iterator)));
                onCartList += toJSON(onCartOrders.get(iterator)) + ",";

            }


            onCartOrders.get(iterator).setRestaurantName(restaurantRepository.findOne(onCartOrders.get(iterator).getRestaurantIdentifier()).getRestaurantName());
            onCartOrders.get(iterator).setFoodName(foodRepository.findOne(onCartOrders.get(iterator).getFoodIdentifier()).getFoodName());

            onCartList += toJSON(onCartOrders.get(iterator));

            Double totalCost = calculateTotalCost(onCartOrders) + getRestaurantCharge(onCartOrders.get(0));

            // System.out.println("{"+onCartList+", \"totalCost\" : \""+totalCost+"\"}");

            return "{ \"orders\" : [" + onCartList + "], \"totalCost\" : \"" + totalCost + "\"}";

        }
        else
            return "{\"orders\" : []}";

    }

    public Integer getRestaurantCharge(Order order) {

        Restaurant restaurant=null;
        try {
            if (order != null) {

                restaurant = restaurantRepository.findOne(order.getRestaurantIdentifier());

            }
            return restaurant.getRestaurantCharges();
        }
        catch(Exception e){
            return 0;
        }

    }

    public Order setFoodDetails(Order order){

        Food food= foodRepository.findOne(order.getFoodIdentifier());
        order.setFoodName(food.getFoodName());
        order.setOrderCost(order.getFoodCount()*food.getFoodCost());
        return order;

    }



    public Order setcustomizationCost(Order order) {

        if(order.getCustomizationIdentifier()==null || order.getCustomizationIdentifier()==""){
           // order.setOrderCost();
            return order;

        }

        Integer cost=0;
        String[] customizationIdentifiers =order.getCustomizationIdentifier().split(",");

        for(String identifier:customizationIdentifiers) {
            Customization customization = customizationRepository.findOne(Integer.valueOf(identifier));
            cost+=(order.getFoodCount() * customization.getCustomizationCost());
        }
        order.setOrderCost(order.getOrderCost()+cost);
        return order;

    }
    public Integer getFoodCost(Order order){
        return foodRepository.findOne(order.getFoodIdentifier()).getFoodCost();
    }

    public void updateOffer(List<Order> order,Integer offerIdentifier) {

        //List<Order>orders=orderRepository.findByOrderGroupIdentifier(order.getOrderGroupIdentifier());

        for(Order orderitem:order) {

            orderitem.setOfferIdentifier(offerIdentifier);
            orderRepository.save(orderitem);

        }
    }




    public Double calculateTotalCost(List<Order> orders){

        Integer totalCost=0;
        Restaurant restaurant=null;
        for( Order order:orders) {

            totalCost+=order.getOrderCost();
        }



        return Double.valueOf(totalCost);
    }

   public String toJSON(Order order){


        String json="{";
        if(order.getOrderIdentifier()!=null)
        json+="\"orderId\" : \""+encodingDecoding.encode(order.getOrderIdentifier())+"\",";

        if(order.getOrderGroupIdentifier()!=null)
        json+="\"orderGroupId\" : \""+encodingDecoding.encode(order.getOrderGroupIdentifier())+"\",";

        if(order.getRestaurantIdentifier()!=null)
        json+="\"restaurantId\" : \""+encodingDecoding.encode(order.getRestaurantIdentifier())+"\",";

        if(order.getFoodIdentifier()!=null)
        json+="\"foodId\" : \""+encodingDecoding.encode(order.getFoodIdentifier())+"\",";

        if(order.getOfferIdentifier()!=null)
            json+="\"offerIdentifier\" : \""+encodingDecoding.encode(order.getOfferIdentifier())+"\",";

        if(order.getOfferCode()!=null)
            json+="\"offerCode\" : \""+order.getOfferCode()+"\",";

        if(order.getRestaurantName()!=null)
        json+="\"restaurantName\" : \""+order.getRestaurantName()+"\",";


        if(order.getRestaurantCharges()!=null)
            json+="\"restaurantCharges\" : \""+order.getRestaurantCharges()+"\",";

        if(order.getFoodName()!=null)
        json+="\"foodName\" : \""+order.getFoodName()+"\",";

        if(order.getFoodCount()!=null)
            json+="\"foodCount\" : \""+order.getFoodCount()+"\",";

        if(order.getCreatedTime()!=null)
            json+="\"createdTime\" : \""+order.getCreatedTime()+"\"";

        if(order.getCustomizationIdentifier()!=null) {


            String[] identifiers = order.getCustomizationIdentifier().split(",");



            String jsonCust = ",\"customization\" : [";
            int iterator = 0;
            if (identifiers.length > 0) {
                for (iterator = 0; iterator < identifiers.length; iterator++) {

                    Customization customization=customizationRepository.findOne(Integer.parseInt(identifiers[iterator]));
                    jsonCust+=toParseCustomization(customization);
                    if(!(iterator==identifiers.length-1))
                    jsonCust+=",";


                }

            }
            jsonCust+="]";
            json+=jsonCust;

        }
            if (order.getOrderCost() != null)
                json += ",\"orderCost\" : \"" + order.getOrderCost() + "\"";

            json += "}";



        return json;
   }

   public String toParseCustomization(Customization customization){

        String jsonCust="";
        if(customization.getCustomizationIdentifier()!=null)
            jsonCust+="{ \"customizationId\" : \""+encodingDecoding.encode(customization.getCustomizationIdentifier())+"\"";
        if(customization.getCustomizationName()!=null)
        jsonCust+=",\"customizationName\" : \""+customization.getCustomizationName()+"\"";

        jsonCust+="}";
        return  jsonCust;

   }

   public Order decode(Order order) {

        if(order.getOrderId()!=null)
            order.setOrderIdentifier(encodingDecoding.decode(order.getOrderId()));

        if(order.getOrderGroupId()!=null)
            order.setOrderGroupIdentifier(encodingDecoding.decode(order.getOrderGroupId()));

        if(order.getFoodId()!=null)
            order.setFoodIdentifier(encodingDecoding.decode(order.getFoodId()));

        if(order.getRestaurantId()!=null)
            order.setRestaurantIdentifier(encodingDecoding.decode(order.getRestaurantId()));

        if(order.getCustomizationIdentifier()!=null)
        {
            String[] identifiers=order.getCustomizationIdentifier().split(",");

            String id="";
            int iterator;
            for(iterator=0;iterator<identifiers.length-1;iterator++){

                id+=encodingDecoding.decode(identifiers[iterator]);

                id+=",";

            }
            id+=encodingDecoding.decode(identifiers[iterator]);
            order.setCustomizationIdentifier(id);
        }




        return order;
   }


}
