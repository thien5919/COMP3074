package ca.gbc.comp3074.labex7;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class NetworkUtils {
    private final Executor excutor = Executors.newSingleThreadExecutor();
    Handler handler = new Handler(Looper.getMainLooper());

    public interface Callback<R>{
        void onCompleted(R result);
    }
    public <T> void executeAsync(Callable<T> callable, Callback<T> callback){
        excutor.execute(() -> {
            final T result;
            try {
                result = callable.call();
                handler.post(() -> {
                    callback.onCompleted(result);
                });

            } catch (Exception e){
                Log.e("NetworkUtils", "executeAsync: " + e.getMessage());
            }
        });
    }
    static List<String> fetchData() {
        List<String> data = new ArrayList<>();
        String urlString = "https://jsonplaceholder.typicode.com/todos?userId=1";

        try {
            JSONArray jsonArray = getJsonArray(urlString);
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject post = jsonArray.getJSONObject(i);
                String title = post.getString("title");
                data.add(title);
            }
        }catch (Exception e){
            Log.e("MainActivity,fetchData", "fetchData: "+ e.getMessage());
            data.add("Failed to fetch data");
            throw new RuntimeException(e);
        }
        return data;
    }

    private static @NonNull JSONArray getJsonArray(String urlString) throws IOException, JSONException {
        URL url= new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader((new InputStreamReader(connection.getInputStream())));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null){
            result.append(line);
        }
        reader.close();
        connection.disconnect();

        JSONArray array = new JSONArray(result.toString());
        return array;

    }

}
