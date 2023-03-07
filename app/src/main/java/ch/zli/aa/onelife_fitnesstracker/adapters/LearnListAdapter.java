package ch.zli.aa.onelife_fitnesstracker.adapters;
/**
 * Hilfsmittel: https://www.youtube.com/watch?v=W4hTJybfU7s
 *
 * **/
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ch.zli.aa.onelife_fitnesstracker.R;
import ch.zli.aa.onelife_fitnesstracker.fragments.rvLearnFragmentNotFrag;


public class LearnListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<String> categoryList;

    public LearnListAdapter(Context context, List<String> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    public static class Holder extends RecyclerView.ViewHolder{

        ImageView imageView, ivPosterOverlay;
        TextView tvTitle, tvDescription;
        CardView cardView;
        RelativeLayout rvContainer;

        public Holder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.cardViewTextBox);
            cardView = itemView.findViewById(R.id.card_learn_item);
        }

        public void setData(String category) {
            String getTitle = category;

            tvTitle.setText(getTitle);


            tvTitle.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), rvLearnFragmentNotFrag.class);
                    if(category == "Before Workout Stretches"){
                        intent.putExtra("category", "StretchingBefore_query");
                        itemView.getContext().startActivity(intent);
                    } else if (category == "Yoga") {
                        intent.putExtra("category", "Yoga_query");
                        itemView.getContext().startActivity(intent);
                    } else if (category == "Pilates") {
                        intent.putExtra("category", "Pilates_query");
                        itemView.getContext().startActivity(intent);
                    } else {
                        Log.i("LearnFragment", "onItemClick: intent if check failed");
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.learn_item_layout, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        String category = categoryList.get(position);
        Holder hold = (Holder) holder;
        hold.setData(category);
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }
}
