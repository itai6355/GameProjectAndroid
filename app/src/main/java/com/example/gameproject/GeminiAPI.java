package com.example.gameproject;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.Executors;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GeminiAPI {

    private String response = null;

    private static final String API_KEY = "AIzaSyAvRr7uJNTGMp0TyPjyLOtObZW6qN3pcpw";
    private static final String BASE_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent";
    private static final String TAG = "GeminiAPI";

    static {
        Executors.newSingleThreadExecutor();
    }

    private interface ResponseCallback {
        void onResponse(String response);

        void onFailure(String error);
    }

    public String askGemini(String prompt, Context context) {
        askGemini(prompt, new ResponseCallback() {
            @Override
            public void onResponse(String response) {
                new android.os.Handler(android.os.Looper.getMainLooper()).post(() -> {
                    GeminiAPI.this.response = response;

                });
            }

            @Override
            public void onFailure(String error) {
                new android.os.Handler(android.os.Looper.getMainLooper()).post(() -> {
                    GeminiAPI.this.response = error;
                });
            }
        });
        return response;
    }

    private static void askGemini(String prompt, ResponseCallback callback) {
        OkHttpClient client = new OkHttpClient();

        JsonObject requestBody = new JsonObject();
        JsonArray contentsArray = new JsonArray();
        JsonObject messageObject = new JsonObject();
        JsonArray partsArray = new JsonArray();
        JsonObject textObject = new JsonObject();
        textObject.addProperty("text", prompt);

        partsArray.add(textObject);
        messageObject.add("parts", partsArray);
        contentsArray.add(messageObject);
        requestBody.add("contents", contentsArray);

        RequestBody body = RequestBody.create(requestBody.toString(), MediaType.get("application/json; charset=utf-8"));

        HttpUrl url = Objects.requireNonNull(HttpUrl.parse(BASE_URL))
                .newBuilder()
                .addQueryParameter("key", API_KEY)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                callback.onFailure("Network failure: " + e.getMessage());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (!response.isSuccessful()) {
                    callback.onFailure("API Error: " + response.code() + " - " + response.message());
                    return;
                }

                String responseBody = Objects.requireNonNull(response.body()).string();
                JsonObject jsonResponse = JsonParser.parseString(responseBody).getAsJsonObject();
                JsonArray candidates = jsonResponse.getAsJsonArray("candidates");

                if (candidates == null || candidates.isEmpty()) {
                    callback.onFailure("Empty response from API.");
                    return;
                }

                JsonObject firstCandidate = candidates.get(0).getAsJsonObject();
                JsonObject content = firstCandidate.getAsJsonObject("content");

                if (content == null || !content.has("parts")) {
                    callback.onFailure("Invalid response structure.");
                    return;
                }

                JsonArray parts = content.getAsJsonArray("parts");
                if (parts != null && !parts.isEmpty()) {
                    String reply = parts.get(0).getAsJsonObject().get("text").getAsString();
                    callback.onResponse(reply);
                } else {
                    callback.onFailure("No valid content parts in response.");
                }
            }
        });
    }
}
