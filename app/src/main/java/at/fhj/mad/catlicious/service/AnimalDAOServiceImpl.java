package at.fhj.mad.catlicious.service;

import android.content.Context;
import at.fhj.mad.catlicious.data.entity.Animal;
import at.fhj.mad.catlicious.data.entity.Food;
import at.fhj.mad.catlicious.data.entity.Profile;
import at.fhj.mad.catlicious.utils.DAOUtils;
import at.fhj.mad.catlicious.utils.SUID;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.List;

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
        DAOUtils.terminateContext();
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

        DAOUtils.terminateContext();
        return profile;
    }

    @Override
    public List<Animal> getAllAnimals(Context context) {
        DAOUtils.createContext(context);
        List<Animal> animals = Animal.listAll(Animal.class);
        DAOUtils.terminateContext();
        return animals;
    }

    @Override
    public void updateAnimal(Animal animal, Context context) {
        DAOUtils.createContext(context);
        Animal a = Animal.findById(Animal.class, animal.getId());
        a.setName(animal.getName());
        a.save();
        DAOUtils.terminateContext();
    }
}
