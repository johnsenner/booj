package booj.kentwood.network;

import android.content.Context;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import booj.kentwood.model.KentwoodModelObject;
import booj.kentwood.model.Realtor;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.http.GET;

/**
 * This class is responsible for creating and resolving all HttpRequests in the app.
 * These HttpRequests will all be asynchronous, so don't call them from the main/UI thread.
 **/
public enum KentwoodHttpClient {

    INSTANCE;

    private static final String KENTWOOD_BASE_URL =
            "http://www.denverrealestate.com/rest.php/mobile/realtor/";
    private final GsonConverterFactory converterFactory;
    private final HttpLoggingInterceptor loggingInterceptor;

    KentwoodHttpClient() {
        Gson gson = KentwoodModelObject.getGson();
        converterFactory = GsonConverterFactory.create(gson);

        loggingInterceptor = new HttpLoggingInterceptor();

        // log everything in this coding exercise
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    public KentwoodUnauthenticatedService getUnauthenticatedService(final Context context) {

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        Request request = original.newBuilder()
                                .method(original.method(), original.body())
                                .build();

                        return chain.proceed(request);
                    }
                })
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(KENTWOOD_BASE_URL)
                .client(httpClient)
                .addConverterFactory(converterFactory)
                .build();

        return retrofit.create(KentwoodUnauthenticatedService.class);
    }

    public interface KentwoodUnauthenticatedService {
        // USER APIS
        @GET("list?app_key=f7177163c833dff4b38fc8d2872f1ec6")
        Call<List<Realtor>> getRealtors();
    }
}