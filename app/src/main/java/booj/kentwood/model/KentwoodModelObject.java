package booj.kentwood.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Serializable;

/**
 * Parent for all gson models in the app.
 */

public class KentwoodModelObject implements Serializable {
    private static Gson sGson;

    public static Gson getGson() {
        if (sGson == null) {
            sGson = new GsonBuilder().create();
        }
        return sGson;
    }
}
