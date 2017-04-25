package at.fhj.mad.catlicious.service;

import android.support.test.runner.AndroidJUnit4;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import at.fhj.mad.catlicious.data.entity.Animal;
import at.fhj.mad.catlicious.data.entity.Food;
import at.fhj.mad.catlicious.fixture.MockApplication;
import at.fhj.mad.catlicious.utils.SUID;

import static org.junit.Assert.*;

/**
 * Created by Simone on 26.04.2017.
 */
@RunWith(AndroidJUnit4.class)
public class FoodDAOServiceImplTest extends MockApplication {

    private Food food;
    private FoodDAOService foodDAOService;

    @Before
    public void setup() {
        foodDAOService = new FoodDAOServiceImpl();
        food = new Food();
    }

    @Test
    public void addFood() throws Exception {
        food.setId(SUID.id());
        food.setBrand("Whiskas");
        food.setBrand("Beef");
        foodDAOService.addFood(food, getBaseContext());
        Food expected = Food.findById(Food.class, food.getId());
        Assert.assertEquals(food, expected);
    }

    @Test
    public void updateFood() throws Exception {

    }

    @Test
    public void getAllFood() throws Exception {

    }

    @After
    public void teardown() {
        Food.deleteAll(Food.class);
    }
}