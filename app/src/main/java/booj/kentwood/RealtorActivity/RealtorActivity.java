package booj.kentwood.RealtorActivity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import booj.kentwood.R;
import booj.kentwood.model.Realtor;

/**
 * Activity to view details of a realtor
 */

public class RealtorActivity extends AppCompatActivity {
    public static final String REALTOR = "REALTOR";
    private static final Object WIDTH_200_PX = "width/200";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realtor);

        Bundle extras = getIntent().getExtras();
        Realtor realtor = (Realtor) extras.getSerializable(REALTOR);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(realtor.first_name);

        ImageView photoView = (ImageView) findViewById(R.id.realtor_detail_photo);
        // Load a medium version of their photo
        String thumbnailUrl = String.format("%s%s", realtor.photo, WIDTH_200_PX);
        Picasso.with(this)
                .load(thumbnailUrl)
                .fit()
                .into(photoView);

        TextView nameView = (TextView) findViewById(R.id.realtor_detail_fullname);
        nameView.setText(realtor.getFullNameString());
        TextView phoneView = (TextView) findViewById(R.id.realtor_detail_phone);
        if (realtor.phone_number != null) {
            phoneView.setText(realtor.phone_number);
        } else {
            phoneView.setText(getString(R.string.no_phone));
        }
    }
}