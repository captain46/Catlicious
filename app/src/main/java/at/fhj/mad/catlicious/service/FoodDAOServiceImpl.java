package at.fhj.mad.catlicious.service;

import android.content.Context;
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
        food.setRating(0);
        food.save();

        List<Profile> profiles = Profile.listAll(Profile.class);

        for(Profile profile : profiles) {
            List<Food> foodList = profile.getFoodList();
            foodList.add(food);
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
        f.setImageUri(food.getImageUri());
        f.save();
        DAOUtils.terminateContext();
    }

    @Override
    public List<Food> getAllFood(Context context) {
        DAOUtils.createContext(context);
        List<Food> foodList = Food.listAll(Food.class);
        DAOUtils.terminateContext();
        return foodList;
    }
}
