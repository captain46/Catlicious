package at.fhj.mad.catlicious.service;

import android.content.Context;

import at.fhj.mad.catlicious.data.entity.Animal;
import at.fhj.mad.catlicious.data.entity.Food;
import at.fhj.mad.catlicious.data.entity.Profile;
import at.fhj.mad.catlicious.utils.DAOUtils;
import at.fhj.mad.catlicious.utils.SUID;

import java.util.List;

/**
 * Created by Simone on 22.04.2017.
 */

public class FoodDAOServiceImpl implements FoodDAOService {

    @Override
    public void addFood(Food food, Context context) {
        DAOUtils.createContext(context);
        food.setId(SUID.id());
        food.save();

        List<Animal> animals = Animal.listAll(Animal.class);

        for(Animal animal : animals) {
            Profile profile = new Profile();
            profile.setId(SUID.id());
            profile.setAnimal(animal);
            profile.setFood(food);
            profile.save();
        }

        DAOUtils.terminateContext();
    }

    @Override
    public void updateFood(Food food, Context context) {
        DAOUtils.createContext(context);
        Food f = Food.findById(Food.class, food.getId());
        f.setBrand(food.getBrand());
        f.setSort(food.getSort());
        f.setImage(food.getImage());
        f.save();
        DAOUtils.terminateContext();
    }

    @Override
    public List<Food> getAllFood(Context context) {
        DAOUtils.createContext(context);
        List<Food> foodList = Food.listAll(Food.class, "brand, sort");
        DAOUtils.terminateContext();
        return foodList;
    }

    @Override
    public void deleteFood(Food food, Context context) {
        DAOUtils.createContext(context);

        List<Profile> profiles = Profile.listAll(Profile.class);

        for(Profile profile : profiles) {

            if(profile.getFood().getId().equals(food.getId())) {
                profile.delete();
            }
        }

        food.delete();
        DAOUtils.terminateContext();
    }
}
