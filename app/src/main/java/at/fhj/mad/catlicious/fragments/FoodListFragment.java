package at.fhj.mad.catlicious.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import at.fhj.mad.catlicious.R;
import at.fhj.mad.catlicious.data.entity.Food;
import at.fhj.mad.catlicious.service.FoodDAOService;
import at.fhj.mad.catlicious.service.FoodDAOServiceImpl;

/**
 * Created by Simone on 22.04.2017.
 */

public class FoodListFragment extends Fragment {

    private ArrayAdapter<Food> foodArrayAdapter;
    private ListView listView;
    private FoodDAOService foodDaoService;
    private Context context;

    public FoodListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        foodDaoService = new FoodDAOServiceImpl();
        context = container.getContext();

        View view = inflater.inflate(R.layout.fragment_foodlist, container, false);

        initListener(view);

        showAllFood();

        return view;
    }

    public void initListener(View view) {
        listView = (ListView) view.findViewById(R.id.allFood);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Food food = (Food) parent.getAdapter().getItem(position);
                Fragment fragment = new EditFoodFragment();

                Bundle bundle = new Bundle();
                bundle.putSerializable("food", food);
                fragment.setArguments(bundle);

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.mainContent, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        Button btAddFood = (Button) view.findViewById(R.id.create_food);
        btAddFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callAddFoodFragment();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, int position, long id) {

                final Food food = (Food) parent.getAdapter().getItem(position);

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setTitle("Delete Food");
                alertDialog.setMessage("Do you want to delete record " + food.getBrand() + " " +food.getSort() + "?");
                alertDialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        foodDaoService.deleteFood(food, context);
                        showAllFood();
                    }
                });
                alertDialog.show();
                return true;
            }
        });
    }

    public void showAllFood() {
        List<Food> foodList = foodDaoService.getAllFood(context);
        foodArrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, foodList);
        listView.setAdapter(foodArrayAdapter);
    }

    public void callAddFoodFragment() {
        Fragment fragment = new AddFoodFragment();

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.mainContent, fragment)
                .addToBackStack(null)
                .commit();
    }
}
