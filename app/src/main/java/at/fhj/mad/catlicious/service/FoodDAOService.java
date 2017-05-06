package at.fhj.mad.catlicious.service;

import android.content.Context;

import java.util.List;

import at.fhj.mad.catlicious.data.entity.Food;

/**
 * Created by Simone on 22.04.2017.
 */

public interface FoodDAOService {
    /**
     * adds a new food entity to the database and updates all profiles
     * the added food will be added to each profile
     * @param food
     * @param context
     */
    void addFood(Food food, Context context);

    /**
     * update a selected food entity
     * @param food
     * @param context
     */
    void updateFood(Food food, Context context);

    /**
     * returns a list of all food entities
     * @param context
     * @return
     */
    List<Food> getAllFood(Context context);

    void deleteFood(Food food, Context context);
}
