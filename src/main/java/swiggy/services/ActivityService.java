package swiggy.services;

import org.springframework.beans.factory.annotation.Autowired;
import swiggy.domain.Activity;
import swiggy.repository.ActivityRepository;


public class ActivityService {


    Activity activity;

    @Autowired
    ActivityRepository activityRepository;


    public void recordUserActivity(Integer identifier,String activityType){

        activity=new Activity();

        activity.setUserIdentifier(identifier);
        activity.setActivityType(activityType);

        activityRepository.save(activity);




    }

    public void recordRestaurantActivity(Integer identifier,String acitivityType){

        activity=new Activity();

        activity.setRestaurantIdentifier(identifier);
        activity.setActivityType(acitivityType);

        activityRepository.save(activity);
    }

    public void recordFoodActivity(Integer identifier,String activityType) {

        activity=new Activity();

        activity.setFoodIdentifier(identifier);
        activity.setActivityType(activityType);

        activityRepository.save(activity);
    }

    public void recordCustomizationActivity(Integer identifier,String activityType) {

        activity=new Activity();

        activity.setCustomizationIdentifier(identifier);
        activity.setActivityType(activityType);

        activityRepository.save(activity);
    }

    public void recordOfferActivity(Integer identifier,String activityType){

        activity=new Activity();

        activity.setOfferIdentifier(identifier);
        activity.setActivityType(activityType);

        activityRepository.save(activity);
    }

}
