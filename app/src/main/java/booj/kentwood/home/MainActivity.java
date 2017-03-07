package booj.kentwood.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import booj.kentwood.R;
import booj.kentwood.RealtorActivity.RealtorActivity;
import booj.kentwood.model.Realtor;
import booj.kentwood.network.KentwoodHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements Callback<List<Realtor>>,
        RealtorAdapter.RealtorViewHolder.itemTouchListener {

    private RealtorAdapter realtorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set up recyclerview with empty list
        ArrayList<Realtor> realtorList = new ArrayList<>();
        realtorAdapter = new RealtorAdapter(this, this, realtorList);
        RecyclerView realtorRecycler = (RecyclerView) findViewById(R.id.realtor_list);
        RecyclerView.ItemDecoration dividerItemDecoration =
                new RealtorItemDecoration(this, R.drawable.divider);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        realtorRecycler.setLayoutManager(linearLayoutManager);
        realtorRecycler.addItemDecoration(dividerItemDecoration);
        realtorRecycler.setAdapter(realtorAdapter);

        // Make network request async with this activity as callback
        KentwoodHttpClient.KentwoodUnauthenticatedService httpClient =
                KentwoodHttpClient.INSTANCE.getUnauthenticatedService(this);
        Call<List<Realtor>> realtorsCall = httpClient.getRealtors();
        realtorsCall.enqueue(this);
    }

    @Override
    public void onResponse(Response<List<Realtor>> response) {
        if (response.isSuccess()) {
            //Snackbar.make(findViewById(R.id.main_view), "Success", Snackbar.LENGTH_SHORT);
            List<Realtor> newItems = response.body();
            if (newItems == null) {
                Snackbar.make(findViewById(R.id.main_view), "Null response from server", Snackbar.LENGTH_SHORT);
            } else {
                realtorAdapter.setItems(newItems);
            }
        } else {
            Snackbar.make(findViewById(R.id.main_view), "Error reading response from server", Snackbar.LENGTH_SHORT);
        }
    }

    @Override
    public void onFailure(Throwable t) {
        Snackbar.make(findViewById(R.id.main_view), "Error contacting server", Snackbar.LENGTH_SHORT);
    }

    @Override
    public void onRowTap(int position) {
        // A realtor was tapped, start a RealtorActivity
        Intent intent = new Intent(this, RealtorActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(RealtorActivity.REALTOR, realtorAdapter.getItem(position));
        intent.putExtras(bundle);
        startActivity(intent);
    }
}