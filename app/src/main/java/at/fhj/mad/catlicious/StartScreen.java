package at.fhj.mad.catlicious;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import at.fhj.mad.catlicious.fragments.AddAnimalFragment;
import at.fhj.mad.catlicious.fragments.AddFoodFragment;
import at.fhj.mad.catlicious.fragments.FoodlocatorFragment;
import at.fhj.mad.catlicious.fragments.ProfilesFragment;
import at.fhj.mad.catlicious.utils.DrawListAdapter;
import at.fhj.mad.catlicious.utils.NavItem;

public class StartScreen extends AppCompatActivity {

    private static String TAG = StartScreen.class.getSimpleName();

    private ListView mDrawerList;
    private RelativeLayout mDrawerPane;
    private DrawerLayout mDrawerLayout;

    private List<NavItem> navItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);

        navItems = new ArrayList<>();

        navItems.add(new NavItem("Profiles", R.drawable.profiles));
        navItems.add(new NavItem("Add Animal",  R.drawable.add_animal));
        navItems.add(new NavItem("Add Food", R.drawable.can));
        navItems.add(new NavItem("Food Locator", R.drawable.map));

        // DrawerLayout
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        // Populate the Navigtion Drawer with options
        mDrawerPane = (RelativeLayout) findViewById(R.id.drawerPane);
        mDrawerList = (ListView) findViewById(R.id.navList);
        DrawListAdapter drawListAdapter = new DrawListAdapter(this, navItems);
        mDrawerList.setAdapter(drawListAdapter);

        // Drawer Item click listeners
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItemFromDrawer(position);
            }
        });
    }

    /*
    * Called when a particular item from the navigation drawer
    * is selected.
    * */
    private void selectItemFromDrawer(int position) {

        Fragment fragment = null;

        switch (position) {
            //profiles
            case 0:
                fragment = new ProfilesFragment();
                break;
            //add animal
            case 1:
                fragment = new AddAnimalFragment();
                break;
            //add food
            case 2:
                fragment = new AddFoodFragment();
                break;
            //food locator
            case 3:
                fragment = new FoodlocatorFragment();
                break;
            // show profiles as default
            default:
                fragment = new ProfilesFragment();
                break;
        }

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.mainContent, fragment)
                .commit();

        mDrawerList.setItemChecked(position, true);
        setTitle(navItems.get(position).getTitle());

        // Close the drawer
        mDrawerLayout.closeDrawer(mDrawerPane);
    }
}