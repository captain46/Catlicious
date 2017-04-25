package at.fhj.mad.catlicious.service;

import android.content.Context;

import java.util.List;

import at.fhj.mad.catlicious.data.entity.Animal;
import at.fhj.mad.catlicious.data.entity.Profile;

/**
 * Created by Simone on 25.04.2017.
 */

public interface AnimalDAOService {

    void addAnimal(Animal animal, Context context);

    Profile getProfile(Animal animal, Context context);

    List<Animal> getAllAnimals(Context context);
}
