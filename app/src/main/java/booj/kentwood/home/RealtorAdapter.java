package booj.kentwood.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import booj.kentwood.R;
import booj.kentwood.model.Realtor;

/**
 * Adapter for a list of realtors
 */

class RealtorAdapter extends RecyclerView.Adapter<RealtorAdapter.RealtorViewHolder> {
    private static final String WIDTH_50_PX = "width/50";
    private final RealtorViewHolder.itemTouchListener itemTouchListener;
    private List<Realtor> items;
    private Context context;

    RealtorAdapter(Context context, RealtorViewHolder.itemTouchListener itemTouchListener, List<Realtor> realtors) {
        this.context = context;
        this.itemTouchListener = itemTouchListener;
        this.items = realtors;
    }

    @Override
    public RealtorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.list_item_realtor, parent, false);

        return new RealtorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RealtorViewHolder holder, final int position) {
        Realtor realtor = items.get(position);

        if (realtor.photo == null) {
            // TODO: Load placeholder image?
        } else {
            // Load a small version of their photo
            String thumbnailUrl = String.format("%s%s", realtor.photo, WIDTH_50_PX);
            Picasso.with(context).load(thumbnailUrl)
                    .into(holder.photoImageView);
        }

        holder.nameText.setText(realtor.getFullNameString());

        if (realtor.phone_number == null) {
            // I think they should get a phone
            // TODO: log this?
            holder.phoneText.setText(R.string.no_phone);
        } else {
            holder.phoneText.setText(realtor.phone_number);
        }

        holder.realtorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemTouchListener.onRowTap(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<Realtor> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public Realtor getItem(int position) {
        return items.get(position);
    }

    public static class RealtorViewHolder extends RecyclerView.ViewHolder {
        View realtorView;
        TextView nameText;
        TextView phoneText;
        ImageView photoImageView;

        RealtorViewHolder(View view) {
            super(view);
            realtorView = view.findViewById(R.id.realtor_view);
            nameText = (TextView) view.findViewById(R.id.realtor_name);
            phoneText = (TextView) view.findViewById(R.id.realtor_phone_number);
            photoImageView = (ImageView) view.findViewById(R.id.realtor_photo);
        }

        public interface itemTouchListener {
            void onRowTap(int position);
        }
    }
}
