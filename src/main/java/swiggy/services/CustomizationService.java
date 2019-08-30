package swiggy.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swiggy.domain.Customization;
import swiggy.repository.CustomizationRepository;

import java.security.spec.EncodedKeySpec;
import java.sql.Timestamp;
import java.util.List;

@Service
public class CustomizationService {

    @Autowired
    CustomizationRepository customizationRepository;

    EncodingDecoding encodingDecoding=new EncodingDecoding();

    public String createCustomization(Customization customization) {
        customization=decode(customization);

        customization.setCreatedTime(new Timestamp(System.currentTimeMillis()));
        customization.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
        customization.setDeleteFlag(false);

        if(customizationRepository.findByCustomizationName(customization.getCustomizationName())==null) {

            return customizationRepository.save(customization).toString();
        }
        else {

            return "existing";
        }


    }

    public String updateCustomization(Customization customization) {

        customization=decode(customization);

        Customization toUpdateCustomization=customizationRepository.findByCustomizationName(customization.getCustomizationName());

        if(toUpdateCustomization==null) {

            return "invalid customization option";

        }
        if(customization.getCustomizationCost()!=null) {

            toUpdateCustomization.setCustomizationCost(customization.getCustomizationCost());
        }

        if(customization.getIsAvailable()!=null) {

            toUpdateCustomization.setIsAvailable(customization.getIsAvailable());
        }

        return customizationRepository.save(toUpdateCustomization).toString();

    }

    public String deleteCustomization(Customization customization) {

        customization=decode(customization);

        Customization toDeleteCustomization=customizationRepository.findByCustomizationName((customization.getCustomizationName()));

        if(toDeleteCustomization==null) {

            return "invalid customization option";
        }

        toDeleteCustomization.setDeleteFlag(true);

        customizationRepository.save(toDeleteCustomization);

        return "deleted";
    }

    public String displayCustomization(Customization customization) {

        customization=decode(customization);

        List<Customization> customizations=customizationRepository.findByFoodIdentifier(customization.getFoodIdentifier());

        if(customizations.isEmpty()) {

            return "{\"customizations\" : \"[]\"}";
        }

        String json="{ \"customizations\" : [";

        int iterator=0;

        for(iterator=0;iterator<customizations.size()-1; iterator++) {

            json+=toJson(customizations.get(iterator))+",";

        }

        json+=toJson(customizations.get(iterator));

        json+="]}";

        return json;

    }

    public String toJson(Customization customization) {

        String json="{";

        if(customization.getCustomizationIdentifier()!=null) {
            json+="\"customizationIdentifier\" : \""+encodingDecoding.encode(customization.getCustomizationIdentifier())+"\",";
        }

        if(customization.getCustomizationName()!=null) {

            json+="\"customizationName\" : \""+(customization.getCustomizationName())+"\",";

        }

        if(customization.getCustomizationCost()!=null) {

            json+="\"customizationCost\" : \""+(customization.getCustomizationCost())+"\"";
        }

        json+="}";

        return json;



    }
    public Customization decode(Customization customization){

        if(customization.getFoodId()!=null)
            customization.setFoodIdentifier(encodingDecoding.decode(customization.getFoodId()));

        if(customization.getCustomizationId()!=null)
            customization.setCustomizationIdentifier(encodingDecoding.decode(customization.getCustomizationId()));

        return customization;
    }

}
