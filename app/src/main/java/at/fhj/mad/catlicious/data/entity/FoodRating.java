package at.fhj.mad.catlicious.data.entity;

/**
 * Created by Simone on 06.05.2017.
 */

public class FoodRating {

    private Animal animal;
    private float rating;
    private Food food;

    public FoodRating(Animal animal, float rating, Food food) {
        this.animal = animal;
        this.rating = rating;
        this.food = food;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }
}
