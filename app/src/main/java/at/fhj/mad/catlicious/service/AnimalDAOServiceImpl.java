package at.fhj.mad.catlicious.service;

import android.content.Context;

import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.List;

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

        List<Food> foodList = Food.listAll(Food.class);

        for (Food food : foodList) {
            Profile profile = new Profile();
            profile.setId(SUID.id());
            profile.setAnimal(animal);
            profile.setFood(food);
            profile.save();
        }

        DAOUtils.terminateContext();
    }

    @Override
    public List<Profile> getProfiles(Animal animal, Context context) {
        DAOUtils.createContext(context);

        List<Profile> profiles = Select.from(Profile.class)
                .where(Condition.prop("animal").eq(animal))
                .orderBy("rating DESC").list();

        DAOUtils.terminateContext();
        return profiles;
    }

    @Override
    public void updateProfile(Profile profile, Context context) {
        DAOUtils.createContext(context);

        List<Profile> profiles = Select.from(Profile.class)
                .where(Condition.prop("animal").eq(profile.getAnimal()))
                .and(Condition.prop("food").eq(profile.getFood())).list();

        Profile p = profiles.get(0);
        p.setRating(profile.getRating());
        p.save();

        DAOUtils.terminateContext();
    }

    @Override
    public List<Animal> getAllAnimals(Context context) {
        DAOUtils.createContext(context);
        List<Animal> animals = Animal.listAll(Animal.class, "name");
        DAOUtils.terminateContext();
        return animals;
    }

    @Override
    public void updateAnimal(Animal animal, Context context) {
        DAOUtils.createContext(context);
        Animal a = Animal.findById(Animal.class, animal.getId());
        a.setName(animal.getName());
        a.setImage(animal.getImage());
        a.save();
        DAOUtils.terminateContext();
    }

    @Override
    public void deleteAnimal(Animal animal, Context context) {
        DAOUtils.createContext(context);
        animal.delete();
        DAOUtils.terminateContext();
    }
}
