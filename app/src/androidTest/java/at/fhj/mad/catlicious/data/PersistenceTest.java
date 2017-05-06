package at.fhj.mad.catlicious.data;

import android.support.test.runner.AndroidJUnit4;
import at.fhj.mad.catlicious.data.entity.Animal;
import at.fhj.mad.catlicious.fixture.MockApplication;
import com.orm.SugarContext;
import junit.framework.Assert;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

/**
 * The purpose of this test is to illustrate the capabilities of SugarORM.
 * <p>
 * The following test methods demonstrates the usage:
 *
 * @author bnjm@harmless.ninja - 4/20/17.
 * @see #AshouldPersistWithoutException() - Create
 * @see #BretrieveAll()                   - Read
 * @see #CretrieveWithQuery()             - Read
 * @see #Ddelete()                        - Delete
 * @see #Eupdate()                        - Update
 * <p>
 *
 * findById is not demonstrated for the sake of brevity.
 *
 * Database configuration can be found in {@see AndroidManifest.xml} under {@literal <application><meta-data>}
 */
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PersistenceTest extends MockApplication {

    private Animal animalEntity;

    @Before
    public void setup() {
        animalEntity = new Animal("rocky");
    }

    @After
    public void teardown() {
        Animal.deleteAll(Animal.class);
    }

    @AfterClass
    public void afterClass() {
        SugarContext.terminate();
    }

    /**
     * To persist a entity use save()
     *
     * @throws Exception
     */
    @Test
    public void AshouldPersistWithoutException() throws Exception {
        try {
            animalEntity.save();
        } catch (Exception ex) {
            fail("Entity could not be persisted");
        }
    }

    /**
     * This test demonstrates how to retrieve all objects with listAll(Class clazz)
     *
     * @throws Exception
     */
    @Test
    public void BretrieveAll() throws Exception {
        animalEntity.save();
        Animal second = new Animal("test");
        second.save();
        List<Animal> retrieved = Animal.listAll(Animal.class);

        assertEquals(2, retrieved.size());
    }

    /**
     * Demonstrates how find can be used with custom where clauses.
     *
     * @throws Exception
     */
    @Test
    public void CretrieveWithQuery() throws Exception {
        animalEntity.save();
        List<Animal> retrieved = Animal.find(Animal.class, "name = ?", "rocky");

        assertEquals(1, retrieved.size());
    }

    /**
     * Demonstrates delete()
     *
     * @throws Exception
     */
    @Test
    public void Ddelete() throws Exception {
        animalEntity.save();

        boolean expectation = Animal.delete(animalEntity);

        Assert.assertTrue(expectation);
    }

    /**
     * Demonstrates how to update entities
     *
     * @throws Exception
     */
    @Test
    public void Eupdate() throws Exception {
        animalEntity.save();
        List<Animal> retrieved = Animal.find(Animal.class, "name = ?", "rocky");
        Animal animal = retrieved.get(0);
        animal.setName("rocky2");
        animal.save();

        List<Animal> updated = Animal.find(Animal.class, "name = ?", "rocky2");

        Assert.assertEquals("rocky2", updated.get(0).getName());
    }
}
