package com.example.alpha;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.blongho.country_data.World;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static com.example.alpha.string_functions.isStarted;
import static com.example.alpha.string_functions.split_Score;

public class CricketFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    ProgressBar progressBar;
    ArrayList<Cricket_live_scores> cls = new ArrayList<>();
    SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_page, container, false);
        World.init(getContext());
        progressBar = view.findViewById(R.id.progress);
        return view;
    }

    RecyclerView recyclerView;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        swipeRefreshLayout=view.findViewById(R.id.refreshCricket);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.black);

        LinearLayoutManager RecyclerViewLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(RecyclerViewLayoutManager);
        LinearLayoutManager HorizontalLayout = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(HorizontalLayout);
        SnapHelper linearSnapHelper = new PagerSnapHelper();
        linearSnapHelper.attachToRecyclerView(recyclerView);
        onLoadingSwipeRefresh();

    }


    private void data_fetcher(final String url, final View view) {

        class data_fetch extends AsyncTask<Void, Void, String> {
            String s;

            protected void onPreExecute() {
                super.onPreExecute();
                view.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setRefreshing(true);
            }

            @Override
            protected String doInBackground(Void... voids) {
                CricBuzzParser cbp = new CricBuzzParser("http://mapps.cricbuzz.com/cbzios/match/livematches");
                cbp.RetrieveURL();
                cbp.Parse();
				cls.clear();

                ArrayList<Match> alm = cbp.getMatches();
                System.out.println("Matches count = "+ alm.size());
                for (Match m :
                        alm) {
                    String t1 = "";
                    String str = m.getScoreCard();
                    int i = 0;
                    for (i = 0; i < str.length(); i++) {
                        if (str.charAt(i) == '-')
                            break;
                        t1 = t1 + str.charAt(i);
                    }
                    if (i == str.length())
                        continue;
                    i++;
                    String t2 = str.substring(i);
//                    System.out.println(t1);
                    //                  System.out.println(t2);
                    cls.add(new Cricket_live_scores(t1, t2, m.getStatus(), m));
                }

                return null;
            }


            @Override
            protected void onPostExecute(String s) {
                Custom_adapter myadapter = new Custom_adapter(cls);
                recyclerView.setAdapter(myadapter);
                view.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
            }
        }
        data_fetch data_fetch = new data_fetch();
        data_fetch.execute();
    }

    @Override
    public void onRefresh() {
        data_fetcher("https://mapps/cricbuzz.com/match/livematches", progressBar);
    }

    private void onLoadingSwipeRefresh() {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                data_fetcher("https://mapps/cricbuzz.com/match/livematches", progressBar);
            }
        });
    }
}