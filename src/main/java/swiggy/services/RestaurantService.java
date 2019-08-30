package swiggy.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swiggy.domain.Offer;
import swiggy.domain.Restaurant;
import swiggy.repository.OfferRepository;
import swiggy.repository.RestaurantRepository;

import java.sql.Timestamp;
import java.util.List;

@Service
public class RestaurantService {


    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    OfferRepository offerRepository;

    EncodingDecoding encodingDecoding=new EncodingDecoding();

    public RestaurantService(RestaurantRepository restaurantRepository){
        this.restaurantRepository=restaurantRepository;
    }

    public String createRestaurant(Restaurant restaurant) {

        if(restaurantRepository.countByName(restaurant.getRestaurantName())>0) {
            return "{\"message\" : \"existing\"}";
        }

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        restaurant.setCreatedTime(timestamp);
        restaurant.setUpdatedTime(timestamp);
        restaurant.setDeleteFlag(false);
        restaurant.setAvailable(true);

        try {
            Restaurant createdRestaurant = restaurantRepository.save(restaurant);
          //  System.out.println(createdRestaurant.getRestaurantIdentifier());

            System.out.println(toJson(createdRestaurant));
            return toJson(createdRestaurant);


        }
        catch(Exception e) {

            System.out.println(4);
            System.out.print(e);
            return "{\"message\": \"not done\"}";
        }
    }

    public Restaurant createRes(Restaurant restaurant){


        Restaurant createdRestaurant = restaurantRepository.save(restaurant);


        return createdRestaurant;

    }

    public Restaurant findRestaurant(Restaurant restaurant) {


        Restaurant foundRestaurant;
        restaurant=decode(restaurant);
        if (restaurant.getRestaurantIdentifier() != null) {

            foundRestaurant = restaurantRepository.findOne(restaurant.getRestaurantIdentifier());

        } else if (restaurantRepository.countByName(restaurant.getRestaurantName()) == 1) {
            foundRestaurant = restaurantRepository.findByRestaurantName(restaurant.getRestaurantName());

        } else {
            return null;
        }
        if (foundRestaurant.getDeleteFlag() == true)
            return null;

        return foundRestaurant;

    }


    public String updateRestaurant(Restaurant restaurant) {

        try {
            Restaurant toUpdateRestaurant=findRestaurant(restaurant);

                if (toUpdateRestaurant != null)  {

                    toUpdateRestaurant.setUpdatedTime(new Timestamp(System.currentTimeMillis()));


                    if (restaurant.getOpenTime() != null) {
                        toUpdateRestaurant.setOpenTime(restaurant.getOpenTime());
                    }
                    if (restaurant.getCloseTime() != null) {
                        toUpdateRestaurant.setCloseTime(restaurant.getCloseTime());
                    }
                    if (restaurant.getRestaurantCharges() != null ) {
                        toUpdateRestaurant.setRestaurantCharges(restaurant.getRestaurantCharges());
                    }

                    return toJson(restaurantRepository.save(toUpdateRestaurant));
                }

                else
                {
                    return "{\"message\" : \"no such user exists\"}";
                }



        } catch (Exception e) {

            System.out.print(e);
            return e.toString();
        }

    }


    public String deleteRestaurant(Restaurant restaurant) {

        Restaurant toDeleteRestaurant =findRestaurant(restaurant);

        if(toDeleteRestaurant==null) {

            return "{\"message\" : \"not found\"}";
        }
        toDeleteRestaurant.setDeleteFlag(true);
        restaurantRepository.save(restaurant);
        return "{\"message\" : \"deleted\"}";

        //return restaurantRepository.save(toDeleteRestaurant).toString();

    }

    public String displayTypes(){

        List<String> restaurantTypes=restaurantRepository.findRestaurantTypes();

        int i=0;
        String json="\"restaurantType\" : [";
        for(i=0;i<restaurantTypes.size()-1;i++){

            json+="{\"Type\" : \""+restaurantTypes.get(i)+"\"},";
        }
        json+="{\"Type\" : \""+restaurantTypes.get(i)+"\"}";
        json+="]}";

        return json;

    }

    public String displayRestaurantByType(String restaurantType){

        List<Restaurant> restaurants =restaurantRepository.findByRestaurantType(restaurantType);

        return display(restaurants);


    }


    public String readRestaurants() {

        List<Restaurant> restaurants= (List<Restaurant>) restaurantRepository.findAll();

        return display(restaurants);


    }

    public String display(List<Restaurant> restaurants){

        String json="{ \"restaurants\":[";

        if(!(restaurants.isEmpty())) {
            int iterator = 0;

            for (iterator = 0; iterator < restaurants.size() - 1; iterator++) {

                List<Offer> offers = offerRepository.findByRestaurantIdentifier(restaurants.get(iterator).getRestaurantIdentifier());
                if(!(offers.isEmpty()) && offers!=null)
                    restaurants.get(iterator).setOffers(offers);
                json += toJson(restaurants.get(iterator)) + ",";

            }

            List<Offer> offers = offerRepository.findByRestaurantIdentifier(restaurants.get(iterator).getRestaurantIdentifier());
            json += toJson(restaurants.get(iterator));

            json += "]}";

            return json;
        }
        return "{ \"restaurants\" : [] }";

    }


    public String toJson(Restaurant restaurant) {

        try {

            String json = "{";
            if (restaurant.getRestaurantIdentifier() != null)
                json += "\"restaurantId\" : \"" + encodingDecoding.encode(restaurant.getRestaurantIdentifier()) + "\",";

            if (restaurant.getRestaurantName() != null)
                json += "\"restaurantName\" : \"" + (restaurant.getRestaurantName()) + "\",";

            if (restaurant.getRestaurantCharges() != null)
                json += "\"restaurantCharges\" : \"" + (restaurant.getRestaurantCharges()) + "\",";

            if (restaurant.getOpenTime() != null)
                json += "\"openTime\" : \"" + (restaurant.getOpenTime()) + "\",";

            if (restaurant.getCloseTime() != null)
                json += "\"closeTime\" : \"" + (restaurant.getCloseTime()) + "\",";

            if (restaurant.getAvailable() != null)
                json += "\"isAvailable\" : \"" + (restaurant.getAvailable()) + "\",";

            if (restaurant.getRating() != null)
                json += "\"rating\" : \"" + (restaurant.getRating()) + "\",";

            if (restaurant.getAverageCost() != null)
                json += "\"averageCost\" : \"" + (restaurant.getAverageCost()) + "\",";

            if (restaurant.getAverageTime() != null)
                json += "\"averageTime\" : \"" + (restaurant.getAverageTime()) + "\",";

            if (restaurant.getRestaurantType() != null)
                json += "\"restaurantType\" : \"" + (restaurant.getRestaurantType()) + "\"";

            if(restaurant.getOffers()!=null && !restaurant.getOffers().isEmpty() ){

                json+=",\"offers\" : [";

                int iterator=0;
                for(iterator=0;iterator<restaurant.getOffers().size()-1;iterator++){

                    Offer offer= restaurant.getOffers().get(iterator);
                    json=json+toJsonOffer(offer)+",";
                }

                Offer offer= restaurant.getOffers().get(iterator);


                json+=toJsonOffer(offer);

                json+="]";
            }

            json += "}";
            return json;
        }
        catch (Exception e){
            return e.toString();
        }

    }

    public String toJsonOffer(Offer offer){

        String json="";

        json+="{";
        if(offer.getOfferCode()!=null)
            json+="\"offerCode\" : \"" + offer.getOfferCode()+"\",";
        if(offer.getRateOfDiscount()!=null)
            json+="\"rateOfDiscount\" : \""+ offer.getRateOfDiscount()+"\",";
        if(offer.getMaximumDiscountAmount()!=null)
            json+="\"maximumDiscountAmount\" : \""+offer.getMaximumDiscountAmount()+"\",";
        if(offer.getMinimumOrderValue()!=null)
            json+="\"minimumOrderValue\" : \""+offer.getMinimumOrderValue()+"\",";
        if(offer.getOfferIdentifier()!=null)
            json+="\"offerId\" : \""+encodingDecoding.encode(offer.getOfferIdentifier())+"\"";


        json+="}";

        return json;

    }

    public Restaurant decode(Restaurant restaurant) {

        if(restaurant.getRestaurantId()!=null) {

            restaurant.setRestaurantIdentifier(encodingDecoding.decode(restaurant.getRestaurantId()));
        }
        return restaurant;
    }


}
