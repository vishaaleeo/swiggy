package swiggy.domain;

import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;

@Entity
@Table(name="category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer categoryIdentifier;

    @Column(name="restaurant_identifier")
    private Integer restaurantIdentifier;

    @Column(name="category_name")
    private String categoryName;

    @Column(name="food_count")
    private Integer foodCount;

    @Column(name="created_time")
    private Timestamp createdTime;

    @Column(name="updated_time")
    private Timestamp updatedTime;

    @Column(name="delete_flag")
    private Boolean deleteFlag;

    public Category(Integer categoryIdentifier, Integer restaurantIdentifier, String categoryName) {
        this.categoryIdentifier = categoryIdentifier;
        this.restaurantIdentifier = restaurantIdentifier;
        this.categoryName = categoryName;
    }

    public Category(){
        super();
    }

    public Category(String categoryName,Integer restaurantIdentifier){

        this.categoryName=categoryName;
        this.restaurantIdentifier=restaurantIdentifier;
    }

    public Integer getCategoryIdentifier() {
        return categoryIdentifier;
    }

    public void setCategoryIdentifier(Integer categoryIdentifier) {
        this.categoryIdentifier = categoryIdentifier;
    }

    public Integer getRestaurantIdentifier() {
        return restaurantIdentifier;
    }

    public void setRestaurantIdentifier(Integer restaurantIdentifier) {
        this.restaurantIdentifier = restaurantIdentifier;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getFoodCount() {
        return foodCount;
    }

    public void setFoodCount(Integer foodCount) {
        this.foodCount = foodCount;
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
}
