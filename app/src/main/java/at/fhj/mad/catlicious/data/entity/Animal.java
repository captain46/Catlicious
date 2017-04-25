package at.fhj.mad.catlicious.data.entity;

import com.orm.SugarRecord;

import java.io.Serializable;

/**
 * @author bnjm@harmless.ninja - 4/20/17.
 */
public class Animal extends SugarRecord implements Serializable {

    private String name;

    public Animal() {}

    public Animal(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}

