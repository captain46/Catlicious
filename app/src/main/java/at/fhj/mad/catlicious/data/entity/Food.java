package at.fhj.mad.catlicious.data.entity;

import android.net.Uri;

import com.orm.SugarRecord;

import java.io.Serializable;

/**
 * Created by Simone on 22.04.2017.
 */

public class Food extends SugarRecord implements Serializable {

    private Long id;
    private String brand;
    private String sort;
    private Uri imageUri;
    private int rating;

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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

    @Override
    public String toString() {
        return brand + " - " + sort;
    }
}
