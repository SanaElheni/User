package com.example.agritrack.Utils;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class GeminiService {
    private static final String API_KEY = "AIzaSyCucCFQ2Azt411kOhwXo2VO4KQY-gOWGT0";
    // ✅ URL CORRECTE Gemini (testée 2025)
    private static final String ENDPOINT = "https://generativelanguage.googleapis.com/v1/models/gemini-1.5-flash:generateContent?key=" + API_KEY;
    public interface GeminiCallback {
        void onResponse(String response);
        void onError(String error);
    }

    public void askGemini(String prompt, GeminiCallback callback) {
        new GeminiTask(callback).execute(prompt);
    }

    private static class GeminiTask extends AsyncTask<String, Void, String> {
        private final GeminiCallback callback;

        GeminiTask(GeminiCallback callback) {
            this.callback = callback;
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                String prompt = params[0];
                Log.d("Gemini", "🔄 PROMPT: " + prompt);

                // JSON simple
                String json = "{\"contents\":[{\"parts\":[{\"text\":\"" + prompt + "\"}]}]}";
                Log.d("Gemini", "📤 REQUEST: " + json);

                URL url = new URL(ENDPOINT);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);
                conn.setConnectTimeout(10000);
                conn.setReadTimeout(10000);

                // Écriture
                try (OutputStream os = conn.getOutputStream()) {
                    byte[] input = json.getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }

                int code = conn.getResponseCode();
                Log.d("Gemini", "📥 CODE: " + code);

                // Lecture réponse
                StringBuilder response = new StringBuilder();
                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(code >= 400 ? conn.getErrorStream() : conn.getInputStream()))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        response.append(line);
                    }
                }

                Log.d("Gemini", "📥 BODY: " + response);
                conn.disconnect();

                if (code == 200) {
                    // Réponse simple (première ligne text)
                    String[] parts = response.toString().split("\"text\":\"");
                    if (parts.length > 1) {
                        String answer = parts[1].split("\"")[0];
                        Log.d("Gemini", "✅ ANSWER: " + answer);
                        return answer;
                    }
                }
                return "❌ HTTP " + code + ": " + response;

            } catch (Exception e) {
                Log.e("Gemini", "💥 ERROR: " + e.getMessage());
                return "❌ " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (result.startsWith("✅")) {
                callback.onResponse(result.substring(2));
            } else {
                callback.onError(result);
            }
        }
    }
}
