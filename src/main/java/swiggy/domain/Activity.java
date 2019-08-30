package swiggy.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Activity {


    @Id
    @GeneratedValue
    @Column(name="activity_identifier")
    private Integer activityIdentifier;

    @Column(name="user_identifier")
    private Integer userIdentifier;

    @Column(name="restaurant_identifier")
    private Integer restaurantIdentifier;

    @Column(name="food_identifier")
    private Integer foodIdentifier;

    @Column(name="order_identifier")
    private Integer orderIdentifier;

    @Column(name="customization_identifier")
    private Integer customizationIdentifier;

    @Column(name="offer_identifier")
    private Integer offerIdentifier;

    @Column(name="payment_identifier")
    private Integer paymentIdentifier;

    @Column(name="activity_type")
    private String activityType;


    public Integer getActivityIdentifier() {
        return activityIdentifier;
    }

    public void setActivityIdentifier(Integer activityIdentifier) {
        this.activityIdentifier = activityIdentifier;
    }

    public Integer getUserIdentifier() {
        return userIdentifier;
    }

    public void setUserIdentifier(Integer userIdentifier) {
        this.userIdentifier = userIdentifier;
    }

    public Integer getRestaurantIdentifier() {
        return restaurantIdentifier;
    }

    public void setRestaurantIdentifier(Integer restaurantIdentifier) {
        this.restaurantIdentifier = restaurantIdentifier;
    }

    public Integer getFoodIdentifier() {
        return foodIdentifier;
    }

    public void setFoodIdentifier(Integer foodIdentifier) {
        this.foodIdentifier = foodIdentifier;
    }

    public Integer getOrderIdentifier() {
        return orderIdentifier;
    }

    public void setOrderIdentifier(Integer orderIdentifier) {
        this.orderIdentifier = orderIdentifier;
    }

    public Integer getCustomizationIdentifier() {
        return customizationIdentifier;
    }

    public void setCustomizationIdentifier(Integer customizationIdentifier) {
        this.customizationIdentifier = customizationIdentifier;
    }

    public Integer getOfferIdentifier() {
        return offerIdentifier;
    }

    public void setOfferIdentifier(Integer offerIdentifier) {
        this.offerIdentifier = offerIdentifier;
    }

    public Integer getPaymentIdentifier() {
        return paymentIdentifier;
    }

    public void setPaymentIdentifier(Integer paymentIdentifier) {
        this.paymentIdentifier = paymentIdentifier;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }
}
