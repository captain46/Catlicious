package at.fhj.mad.catlicious.data.entity;

import com.orm.SugarRecord;

import java.util.List;

/**
 * Created by Simone on 22.04.2017.
 */

public class Profile extends SugarRecord {

    private Animal animal;
    private List<Food> foodList;

    public Profile() {
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public List<Food> getFoodList() {
        return foodList;
    }

    public void setFoodList(List<Food> foodList) {
        this.foodList = foodList;
    }
}
