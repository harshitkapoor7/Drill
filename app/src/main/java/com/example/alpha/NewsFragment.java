package com.example.alpha;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alpha.api.ApiClient;
import com.example.alpha.api.ApiInterface;
import com.example.alpha.models.Article;
import com.example.alpha.models.News;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsFragment extends Fragment {
    public static final String API_KEY = "3311bc2d7bea4d908d120a736209c87e";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Article> articles  = new ArrayList<>();
    private Adapter adapter;
    private String TAG=NewsFragment.class.getSimpleName();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_news,container,false);
    }
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=view.findViewById(R.id.recyclerView);
        layoutManager=new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        LoadJson();
    }
    public void LoadJson()
    {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<News> call;
        call=apiInterface.getNews("in","sports",API_KEY);
       System.out.println(call+".................................................");
        call.enqueue(new Callback<News>()
        {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                 if(response.isSuccessful() && response.body().getArticle()!=null){
                 if(!articles.isEmpty()){
                     articles.clear();
                 }
                 articles=response.body().getArticle();
                 adapter=new Adapter(articles,getActivity().getApplicationContext());
                 recyclerView.setAdapter(adapter);
                 adapter.notifyDataSetChanged();
                 }
                 else{
//                     Toast.makeText(getContext().getApplicationContext(),"NO RESULT", Integer.parseInt("SHORT")).show();
                 }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {

            }
        });
    }
}
