package at.fhj.mad.catlicious.service;

import android.content.Context;

import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.List;
import java.util.concurrent.locks.AbstractQueuedLongSynchronizer;

import at.fhj.mad.catlicious.data.entity.Animal;
import at.fhj.mad.catlicious.data.entity.Food;
import at.fhj.mad.catlicious.data.entity.Profile;
import at.fhj.mad.catlicious.utils.DAOUtils;
import at.fhj.mad.catlicious.utils.SUID;

/**
 * Created by Simone on 25.04.2017.
 */

public class AnimalDAOServiceImpl implements AnimalDAOService {

    @Override
    public void addAnimal(Animal animal, Context context) {
        DAOUtils.createContext(context);
        animal.setId(SUID.id());
        animal.save();

        Profile profile = new Profile();
        profile.setId(SUID.id());
        profile.setAnimal(animal);

        List<Food> foodList = Food.listAll(Food.class);
        profile.setFoodList(foodList);

        profile.save();
        DAOUtils.terminateContext(context);
    }

    @Override
    public Profile getProfile(Animal animal, Context context) {
        DAOUtils.createContext(context);
        Profile profile;
        List<Profile> profiles = Select.from(Profile.class)
                .where(Condition.prop("animal").eq(animal)).list();

        //the query above should only return one list entry
        //if there are more, just take the 1st one as error handling
        profile = profiles.get(0);

        DAOUtils.terminateContext(context);
        return profile;
    }

    @Override
    public List<Animal> getAllAnimals(Context context) {
        DAOUtils.createContext(context);
        List<Animal> animals = Animal.listAll(Animal.class);
        DAOUtils.terminateContext(context);
        return animals;
    }
}
