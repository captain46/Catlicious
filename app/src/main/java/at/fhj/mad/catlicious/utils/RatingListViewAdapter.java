package at.fhj.mad.catlicious.utils;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

import at.fhj.mad.catlicious.R;
import at.fhj.mad.catlicious.data.entity.FoodRating;
import at.fhj.mad.catlicious.data.entity.Profile;
import at.fhj.mad.catlicious.service.AnimalDAOService;
import at.fhj.mad.catlicious.service.AnimalDAOServiceImpl;

/**
 * Created by Simone on 06.05.2017.
 */

public class RatingListViewAdapter extends ArrayAdapter<FoodRating> {

    private List<FoodRating> foodRatings;
    private Context context;
    private AnimalDAOService animalDAOService;

    public RatingListViewAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<FoodRating> objects) {
        super(context, resource, objects);
        this.foodRatings = objects;
        this.context = context;
        animalDAOService = new AnimalDAOServiceImpl();
    }

    @Override
    public FoodRating getItem(int position) {
        return foodRatings.get(position);
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.rating_listview, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.ratingBar.setOnRatingBarChangeListener(onRatingChangedListener(holder, position));

        holder.ratingBar.setTag(position);
        holder.ratingBar.setRating(getItem(position).getRating());
        holder.foodName.setText(getItem(position).getFood().toString());

        return convertView;
    }

    private RatingBar.OnRatingBarChangeListener onRatingChangedListener(final ViewHolder holder, final int position) {
        return new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                FoodRating item = getItem(position);
                item.setRating(v);
                Profile profile = new Profile();
                profile.setAnimal(item.getAnimal());
                profile.setFood(item.getFood());
                profile.setRating(item.getRating());
                animalDAOService.updateProfile(profile, context);
            }
        };
    }

    private static class ViewHolder {
        private RatingBar ratingBar;
        private TextView foodName;

        public ViewHolder(View view) {
            ratingBar = (RatingBar) view.findViewById(R.id.rated_stars);
            foodName = (TextView) view.findViewById(R.id.rated_food);
        }
    }
}
