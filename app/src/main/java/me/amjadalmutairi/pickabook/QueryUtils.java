package me.amjadalmutairi.pickabook;

/**
 * Created by amjadalmutairi on 7/26/17.
 */

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public final class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private QueryUtils() {
    }

    public static List<Book> fetchBookData(String requestUrl) {
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }
        List<Book> books = extractFeatureFromJson(jsonResponse);

        return books;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();


            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the Book JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static List<Book> extractFeatureFromJson(String bookJSON) {

        if (TextUtils.isEmpty(bookJSON)) {
            return null;
        }

        List<Book> books = new ArrayList<>();
        try {

            JSONObject baseJsonResponse = new JSONObject(bookJSON);
          if (!baseJsonResponse.isNull("items")) {
            JSONArray booksArray = baseJsonResponse.getJSONArray("items");
            for (int i = 0; i < booksArray.length(); i++) {

                JSONObject currentBook = booksArray.getJSONObject(i);
                JSONObject info = currentBook.getJSONObject("volumeInfo");

                String title = "";
                if (!info.isNull("title")) {

                    title = info.getString("title");
                }

                ArrayList<String> authors = new ArrayList<>();
                if (!info.isNull("authors")) {
                    JSONArray authorsJSONArray = info.getJSONArray("authors");

                    for (int j = 0; j < authorsJSONArray.length(); j++) {

                        authors.add(authorsJSONArray.getString(j));
                    }
                }

                String publishedDate = "";
                if (!info.isNull("publishedDate")) {
                    publishedDate = info.getString("publishedDate");
                }

                String description = "";
                if (!info.isNull("description")) {
                    description = info.getString("description");
                }

                int pageCount = 0;
                if (!info.isNull("pageCount")) {
                    pageCount = info.getInt("pageCount");
                }

                Book book = new Book(title, authors, publishedDate, description, pageCount);
                books.add(book);
            }
            }
        } catch (JSONException e) {

            Log.e("QueryUtils", "Problem parsing the book JSON results", e);
        }
        return books;
    }
}
