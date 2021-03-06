package at.fhj.mad.catlicious.data.entity;

import com.orm.SugarRecord;

import java.io.Serializable;

/**
 * Created by Simone on 22.04.2017.
 */

public class Profile extends SugarRecord implements Serializable {

    private Animal animal;
    private Food food;
    private float rating;

    public Profile() {
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
