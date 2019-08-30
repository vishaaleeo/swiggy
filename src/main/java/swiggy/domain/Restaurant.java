package swiggy.domain;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "restaurant")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer restaurantIdentifier;

    @Column(name = "restaurant_name")
    private String restaurantName;

    @Column(name="created_time")
    private Timestamp createdTime;

    @Column(name="updated_time")
    private Timestamp updatedTime;

    @Column(name="delete_flag")
    private Boolean deleteFlag;

    @Column(name="is_available")
    private Boolean isAvailable;

    @Column(name="open_time")
    private Time openTime;

    @Column(name="close_time")
    private Time closeTime;

    @Column(name="restaurant_charges")
    private Integer restaurantCharges;

    @Column(name="rating")
    private Float rating;

    @Column(name="restaurantType")
    private String restaurantType;

    @Column(name="average_cost")
    private Integer averageCost;

    @Column(name="average_time")
    private Time averageTime;

    @Transient
    private String restaurantId;


    @Transient
    List<Offer> offers;

    public List<Offer> getOffers() {
        return offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }

    public Integer getRestaurantIdentifier() {
        return restaurantIdentifier;
    }

    public void setRestaurantIdentifier(Integer restaurantIdentifier) {
        this.restaurantIdentifier = restaurantIdentifier;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    public Timestamp getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Timestamp updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Boolean getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }

    public Time getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Time openTime) {
        this.openTime = openTime;
    }

    public Time getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Time closeTime) {
        this.closeTime = closeTime;
    }

    public Integer getRestaurantCharges() {
        return restaurantCharges;
    }

    public void setRestaurantCharges(Integer restaurantCharges) {
        this.restaurantCharges = restaurantCharges;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getRestaurantType() {
        return restaurantType;
    }

    public void setRestaurantType(String restaurantType) {
        this.restaurantType = restaurantType;
    }

    public Integer getAverageCost() {
        return averageCost;
    }

    public void setAverageCost(Integer averageCost) {
        this.averageCost = averageCost;
    }

    public Time getAverageTime() {
        return averageTime;
    }

    public void setAverageTime(Time averageTime) {
        this.averageTime = averageTime;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "restaurantIdentifier=" + restaurantIdentifier +
                ", restaurantName='" + restaurantName + '\'' +
                ", createdTime=" + createdTime +
                ", updatedTime=" + updatedTime +
                ", deleteFlag=" + deleteFlag +
                ", isAvailable=" + isAvailable +
                ", openTime=" + openTime +
                ", closeTime=" + closeTime +
                ", restaurantCharges=" + restaurantCharges +
                '}';
    }
}
