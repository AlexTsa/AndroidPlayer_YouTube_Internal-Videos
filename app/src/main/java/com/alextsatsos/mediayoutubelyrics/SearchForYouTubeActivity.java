package com.alextsatsos.mediayoutubelyrics;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.alextsatsos.mediayoutubelyrics.Entities.YouTubeEntity;
import com.alextsatsos.mediayoutubelyrics.InterfacesApi.SuggestYouTubeApi;
import com.alextsatsos.mediayoutubelyrics.MyAdapters.DisplayYouTubeEntityAdapter;
import com.alextsatsos.mediayoutubelyrics.MyViewModel.YouTubeEntityViewModel;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SearchForYouTubeActivity extends AppCompatActivity {
    private Button searchYtBtn;
    private AutoCompleteTextView searchAutoCompleteTextView;
    private SuggestYouTubeApi suggestYouTubeApi;
    private String arrayString[];
    private ArrayAdapter<String> adapter;
    private YouTubeEntityViewModel youTubeEntityViewModel;
    private RecyclerView recyclerViewFavouriteYouTube;
    public static int Update_YouTube_Activity_Code = 1;
    private Spinner spinnerYouTube;
    private ArrayAdapter<CharSequence> arrayAdapter;
    private  DisplayYouTubeEntityAdapter displayYouTubeEntityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_for_you_tube);
        searchYtBtn = findViewById(R.id.searchYtBtn);
        recyclerViewFavouriteYouTube = findViewById(R.id.recyclerViewFavouriteYouTube);
        spinnerYouTube = findViewById(R.id.spinnerYouTubeQueries);
        arrayAdapter = ArrayAdapter.createFromResource(this,R.array.string_array_queries,R.layout.support_simple_spinner_dropdown_item);
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerYouTube.setAdapter(arrayAdapter);

        displayYouTubeEntityAdapter = new DisplayYouTubeEntityAdapter();
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        recyclerViewFavouriteYouTube.setAdapter(displayYouTubeEntityAdapter);
        recyclerViewFavouriteYouTube.setLayoutManager(layoutManager);
        youTubeEntityViewModel = new ViewModelProvider(this).get(YouTubeEntityViewModel.class);
        spinnerYouTube.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                disY(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        displayYouTubeEntityAdapter.setListener(new DisplayYouTubeEntityAdapter.Listener() {
            @Override
            public void onClick(final int position) {
                final String[] favid = new String[2];
                //ebala handler 19/6
                Handler handlerfav = new Handler();
                handlerfav.post(new Runnable() {
                    @Override
                    public void run() {
                        favid[0] = displayYouTubeEntityAdapter.getYouTubeEntityAt(position).getId();
                        favid[1]=displayYouTubeEntityAdapter.getYouTubeEntityAt(position).getTitle();
                        Intent intent = new Intent(SearchForYouTubeActivity.this, YouTubeVideoLyricsActivity.class);
                        intent.putExtra(YouTubeVideoLyricsActivity.SPECIFIC_YouTubeId, favid[0]);
                        intent.putExtra("lyricsMusixMatch",favid[1]);
                        startActivity(intent);

                    }
                });

            }
        });
        displayYouTubeEntityAdapter.setOnLongListener(new DisplayYouTubeEntityAdapter.OnLongListener() {
            @Override
            public void onLongClick(int position) {
                Toast.makeText(SearchForYouTubeActivity.this, "On Long Click", Toast.LENGTH_SHORT).show();
                String ytid, category, genre, title, did;
                ytid = displayYouTubeEntityAdapter.getYouTubeEntityAt(position).getId();
                category = displayYouTubeEntityAdapter.getYouTubeEntityAt(position).getCategory();
                genre = displayYouTubeEntityAdapter.getYouTubeEntityAt(position).getGenre();
                title = displayYouTubeEntityAdapter.getYouTubeEntityAt(position).getTitle();
                did = String.valueOf(displayYouTubeEntityAdapter.getYouTubeEntityAt(position).getYid());
                Intent intent = new Intent(SearchForYouTubeActivity.this, UpdateYouTubeEntityActivity.class);
                intent.putExtra("ytidForUpdate", ytid);
                intent.putExtra("categoryForUpdate", category);
                intent.putExtra("genreForUpdate", genre);
                intent.putExtra("titleForUpdate", title);
                intent.putExtra("titleForUpdate", title);
                intent.putExtra("didForUpdate", did);
                startActivityForResult(intent, Update_YouTube_Activity_Code);
            }
        });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                youTubeEntityViewModel.deleteYoutubeEntity(displayYouTubeEntityAdapter.getYouTubeEntityAt(viewHolder.getAdapterPosition()));
                Toast.makeText(SearchForYouTubeActivity.this, "Your Favourite Video Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerViewFavouriteYouTube);

        searchAutoCompleteTextView = findViewById(R.id.searchAutoCompleteTextView);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://suggestqueries.google.com")
                .build();
        suggestYouTubeApi = retrofit.create(SuggestYouTubeApi.class);
        searchAutoCompleteChanges();
    }

    public void searchAutoCompleteChanges() {
        searchAutoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                getSuggested(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void getSuggested(String query) {
        Call<ResponseBody> call = suggestYouTubeApi.getAutoComplete("youtube", "yt", "firefox", query);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    ResponseBody suggests = response.body();
                    try {
                        String autosuggest = suggests.string();
                        JSONArray jsonArray = new JSONArray(autosuggest).getJSONArray(1);
                        ArrayList<String> list = getAutocomplete(jsonArray);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(SearchForYouTubeActivity.this, "Something went wrong" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private ArrayList<String> getAutocomplete(JSONArray jsonArray) throws JSONException {
        ArrayList<String> list = new ArrayList<>();
        arrayString = new String[jsonArray.length()];

        if (jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                list.add(jsonArray.get(i).toString());
                arrayString[i] = jsonArray.get(i).toString();

            }
            adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, arrayString);
            searchAutoCompleteTextView.setThreshold(1);
            searchAutoCompleteTextView.setAdapter(adapter);
            searchAutoCompleteTextView.setTextColor(Color.GREEN);
        }
        return list;
    }

    public void specificSearchYt(View view) {
        String specificText = searchAutoCompleteTextView.getText().toString();
        Intent intent = new Intent(SearchForYouTubeActivity.this, DisplayThumbnailsFromYoutubeActivity.class);
        intent.putExtra(DisplayThumbnailsFromYoutubeActivity.SPECIFIC_TEXT, specificText);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Update_YouTube_Activity_Code && resultCode == RESULT_OK) {
            Toast.makeText(this, "You updated the video", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "You did not update the video", Toast.LENGTH_SHORT).show();
        }
    }
    private  void disY(int position){

                switch (position){
                    case 0:
                        youTubeEntityViewModel.getAllFromYouTubeEntityORDERBYtitleDESC().observe(SearchForYouTubeActivity.this, new Observer<List<YouTubeEntity>>() {
                            @Override
                            public void onChanged(List<YouTubeEntity> youTubeEntities) {
                                displayYouTubeEntityAdapter.setYouTubeEntityList(youTubeEntities);
                            }
                        });
                        break;
                    case 1:
                        youTubeEntityViewModel.getAllFromYouTubeEntityORDERBYtitleASC().observe(SearchForYouTubeActivity.this, new Observer<List<YouTubeEntity>>() {
                            @Override
                            public void onChanged(List<YouTubeEntity> youTubeEntities) {
                                displayYouTubeEntityAdapter.setYouTubeEntityList(youTubeEntities);
                            }
                        });
                        break;
                    default:
                        youTubeEntityViewModel.getAllFromYouTubeEntity().observe(SearchForYouTubeActivity.this, new Observer<List<YouTubeEntity>>() {
                            @Override
                            public void onChanged(List<YouTubeEntity> youTubeEntities) {
                                displayYouTubeEntityAdapter.setYouTubeEntityList(youTubeEntities);
                            }
                        });
                }
            }




}
