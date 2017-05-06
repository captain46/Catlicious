package at.fhj.mad.catlicious.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import at.fhj.mad.catlicious.R;
import at.fhj.mad.catlicious.data.entity.Animal;
import at.fhj.mad.catlicious.service.AnimalDAOService;
import at.fhj.mad.catlicious.service.AnimalDAOServiceImpl;

/**
 * Created by Simone on 25.04.2017.
 */

public class AnimalListFragment extends Fragment {

    private ListView listView;
    private AnimalDAOService animalDAOService;
    private Context context;

    public AnimalListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        animalDAOService = new AnimalDAOServiceImpl();
        context = container.getContext();

        View view = inflater.inflate(R.layout.fragment_animallist, container, false);

        initListener(view);

        showAllAnimals();

        return view;
    }

    public void initListener(View view) {
        listView = (ListView) view.findViewById(R.id.allAnimals);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Animal animal = (Animal) parent.getAdapter().getItem(position);
                Fragment fragment = new EditAnimalFragment();

                Bundle bundle = new Bundle();
                bundle.putSerializable("animal", animal);
                fragment.setArguments(bundle);

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.mainContent, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        Button btAddAnimal = (Button) view.findViewById(R.id.create_animal);
        btAddAnimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callAddAnimalFragment();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                final Animal animal = (Animal) parent.getAdapter().getItem(position);

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setTitle("Delete Animal");
                alertDialog.setMessage("Do you want to delete record " + animal.getName() + "?");
                alertDialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        animalDAOService.deleteAnimal(animal, context);
                        showAllAnimals();
                    }
                });
                alertDialog.show();
                return true;
            }
        });
    }

    public void showAllAnimals() {
        List<Animal> animalList = animalDAOService.getAllAnimals(context);
        ArrayAdapter<Animal> animalArrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, animalList);
        listView.setAdapter(animalArrayAdapter);
    }

    public void callAddAnimalFragment() {
        Fragment fragment = new AddAnimalFragment();

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.mainContent, fragment)
                .addToBackStack(null)
                .commit();
    }
}
