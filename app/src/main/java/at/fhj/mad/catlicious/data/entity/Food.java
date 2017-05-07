package at.fhj.mad.catlicious.data.entity;

import com.orm.SugarRecord;

import java.io.Serializable;

/**
 * Created by Simone on 22.04.2017.
 */

public class Food extends SugarRecord implements Serializable {

    private Long id;
    private String brand;
    private String sort;
    private byte[] image;

    public Food() {
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return brand + " - " + sort;
    }
}
