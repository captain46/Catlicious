package at.fhj.mad.catlicious.data.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Simone on 06.05.2017.
 */

public class AnimalFoodProfile implements Serializable {

    private List<Profile> profiles;

    public List<Profile> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<Profile> profiles) {
        this.profiles = profiles;
    }
}
