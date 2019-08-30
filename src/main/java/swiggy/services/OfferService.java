
package swiggy.services;


import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import swiggy.domain.Offer;
import swiggy.repository.OfferRepository;

import java.sql.Timestamp;
import java.util.List;


@Service
public class OfferService {

    @Autowired
    OfferRepository offerRepository;

    EncodingDecoding encodingDecoding=new EncodingDecoding();

    public String createOffer(Offer offer) {

        offer=decode(offer);
        List<Offer> offers= (List<Offer>) offerRepository.findByRestaurantIdentifierAndOfferCode(offer.getRestaurantIdentifier(),offer.getOfferCode());
        if(offers!=null  && !offers.isEmpty()) {

            return "{\"message\" : \"existing\"}";
        }
        offer.setCreatedTime(new Timestamp(System.currentTimeMillis()));
        offer.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
        offer.setDeleteFlag(false);

        return toJsonOffer(offerRepository.save(offer));
    }

    public String updateOffer(Offer offer) {

        Offer toUpdateOffer;

        offer=decode(offer);
        if(offer.getOfferIdentifier()==null) {

            toUpdateOffer = offerRepository.findByRestaurantIdentifierAndOfferCode(offer.getRestaurantIdentifier(), offer.getOfferCode());
        }
        else {

            toUpdateOffer = offerRepository.findOne(offer.getOfferIdentifier());
        }
        if(toUpdateOffer==null) {

            return "Invalid";
        }
        else if(toUpdateOffer.getDeleteFlag()==true) {

            return "Invalid";
        }
        else {

                if(offer.getOfferType()!=null) {
                    toUpdateOffer.setOfferType(offer.getOfferType());
                }

                if(offer.getMaximumDiscountAmount()!=null) {
                    toUpdateOffer.setMaximumDiscountAmount(offer.getMaximumDiscountAmount());
                }

                if(offer.getMinimumOrderValue()!=null) {
                    toUpdateOffer.setMinimumOrderValue(offer.getMinimumOrderValue());
                }

                if(offer.getRateOfDiscount()!=null) {
                    toUpdateOffer.setRateOfDiscount(offer.getRateOfDiscount());
                }
                if(offer.getOfferCode()!=null) {
                    toUpdateOffer.setOfferCode(offer.getOfferCode());
                }

                return offerRepository.save(toUpdateOffer).toString();
            }
        }

    public String deleteOffer(Offer offer) {

        Offer toDeleteOffer;

        offer=decode(offer);
        if(offer.getOfferIdentifier()==null) {

            toDeleteOffer = offerRepository.findByRestaurantIdentifierAndOfferCode(offer.getRestaurantIdentifier(), offer.getOfferCode());
        }
        else {

            toDeleteOffer = offerRepository.findOne(offer.getOfferIdentifier());
        }

        if(toDeleteOffer==null) {
            return "{\"message\" : \"Invalid\"}";
        }
        else {
            toDeleteOffer.setDeleteFlag(true);
            offerRepository.save(toDeleteOffer);
            return "{\"message\" : \"deleted\"}";
        }
    }

    public String displayOffers() {


        List<Offer>  offerList= offerRepository.findOffers();
        Gson offerGson =new Gson();

        String offers =offerGson.toJson(offerList);

        return offers;

    }

    public String displayOffers(String restaurantId) {



        Integer restaurantIdentifier=encodingDecoding.decode(restaurantId);
        List<Offer>  offerList= offerRepository.findByRestaurantIdentifier(restaurantIdentifier);

        String json="{ \"Offers\" : [";
        if(offerList.size()>0) {
            int iterator = 0;
            if (!offerList.isEmpty() && offerList != null) {
                for (iterator = 0; iterator < offerList.size() - 1; iterator++) {

                    json += toJsonOffer(offerList.get(iterator));

                    json += ",";
                }
            }

            json += toJsonOffer(offerList.get(iterator));
        }

        json +="]}";

        return json;

    }

    public Offer decode(Offer offer){

        if(offer.getOfferIdentifier()==null && offer.getOfferId()!=null)
            offer.setOfferIdentifier(encodingDecoding.decode(offer.getOfferId()));

        if(offer.getRestaurantIdentifier()==null && offer.getRestaurantId()!=null)
            offer.setRestaurantIdentifier(encodingDecoding.decode(offer.getRestaurantId()));

        return offer;
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

}
