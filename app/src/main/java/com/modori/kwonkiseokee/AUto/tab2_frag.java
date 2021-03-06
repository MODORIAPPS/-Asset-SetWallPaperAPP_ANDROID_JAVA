package com.modori.kwonkiseokee.AUto;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.modori.kwonkiseokee.AUto.RA.Tab2_tagListsRA;
import com.modori.kwonkiseokee.AUto.Util.NETWORKS;
import com.modori.kwonkiseokee.AUto.Util.TagTools;
import com.modori.kwonkiseokee.AUto.data.api.ApiClient;
import com.modori.kwonkiseokee.AUto.data.data.PhotoSearch;
import com.modori.kwonkiseokee.AUto.data.data.Results;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class tab2_frag extends Fragment implements View.OnClickListener {

    private List<Results> results = new ArrayList<>();

    public Context context;

    public static final String PREFS_FILE = "PrefsFile";

    //TagLists
    private List<String> tagLists;

    private static String tag1 = null;
    private static String tag2 = null;
    private static String tag3 = null;
    private static String tag4 = null;
    private static String tag5 = null;
    private static String tag6 = null;


    //Widgets
    private RecyclerView viewTagLists;

    //GridViews of tags
    private ImageView tag1Gridview;
    private ImageView tag2Gridview;
    private ImageView tag3Gridview;
    private ImageView tag4Gridview;
    private ImageView tag5Gridview;
    private ImageView tag6Gridview;

    // TextView of GridViews, tags..
    @BindView(R.id.view_tag1Grid)
    TextView view_tag1Grid;

    @BindView(R.id.view_tag2Grid)
    TextView view_tag2Grid;

    @BindView(R.id.view_tag3Grid)
    TextView view_tag3Grid;

    @BindView(R.id.view_tag4Grid)
    TextView view_tag4Grid;

    @BindView(R.id.view_tag5Grid)
    TextView view_tag5Grid;

    @BindView(R.id.view_tag6Grid)
    TextView view_tag6Grid;


    View view;

    Intent intent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab2_frag, container, false);
        context = getActivity();
        ButterKnife.bind(this, view);

        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        String ads_app = getResources().getString(R.string.ads_app);
        MobileAds.initialize(context, ads_app);
        AdView adView = view.findViewById(R.id.adView_frag1);
        adView.loadAd(adRequest);

        tagLists = new ArrayList<>();
        initWork();
        if (NETWORKS.getNetWorkType(context) == 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(getString(R.string.noNetworkErrorTitle));
            builder.setMessage(getString(R.string.noNetworkErrorContent));
            builder.setPositiveButton(R.string.tab2_DialogOk,
                    (dialog, which) -> {

                    });

            builder.show();

        }
        getPhotosAsEachTag(tag1, tag2, tag3, tag4, tag5, tag6);
        return view;
    }

    private void initWork() {

        intent = new Intent(getActivity(), ListsOfPhotos.class);

        View goInfo = view.findViewById(R.id.goInfo);
        View goReset = view.findViewById(R.id.goReset);

        tag1Gridview = view.findViewById(R.id.tag1Gridview);
        tag2Gridview = view.findViewById(R.id.tag2Gridview);
        tag3Gridview = view.findViewById(R.id.tag3Gridview);
        tag4Gridview = view.findViewById(R.id.tag4Gridview);
        tag5Gridview = view.findViewById(R.id.tag5Gridview);
        tag6Gridview = view.findViewById(R.id.tag6Gridview);

        // GridView
        View grid1 = view.findViewById(R.id.grid1);
        View grid2 = view.findViewById(R.id.grid2);
        View grid3 = view.findViewById(R.id.grid3);
        View grid4 = view.findViewById(R.id.grid4);
        View grid5 = view.findViewById(R.id.grid5);
        View grid6 = view.findViewById(R.id.grid6);


        Toolbar toolbar = view.findViewById(R.id.toolbar2);
        viewTagLists = view.findViewById(R.id.viewTagLists);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        setTagListsView(context);


        grid1.setOnClickListener(this);
        grid2.setOnClickListener(this);
        grid3.setOnClickListener(this);
        grid4.setOnClickListener(this);
        grid5.setOnClickListener(this);
        grid6.setOnClickListener(this);
        goInfo.setOnClickListener(this);
        goReset.setOnClickListener(this);

    }

    private void setTagListsView(Context context) {
        getTagLists(context);
        addTagLists();
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        viewTagLists.setLayoutManager(layoutManager);

        Tab2_tagListsRA adapterOfTagLists = new Tab2_tagListsRA(context, tagLists);
        viewTagLists.setAdapter(adapterOfTagLists);
    }

    //TEST

    private void getTagLists(Context context) {
        tagLists = TagTools.getTagLists(context);

        tag1 = tagLists.get(0);
        tag2 = tagLists.get(1);
        tag3 = tagLists.get(2);
        tag4 = tagLists.get(3);
        tag5 = tagLists.get(4);
        tag6 = tagLists.get(5);

        Log.d("태그1 정보", tag1 + "");


    }

    private void addTagLists() {

        view_tag1Grid.setText(tag1);
        view_tag2Grid.setText(tag2);
        view_tag3Grid.setText(tag3);
        view_tag4Grid.setText(tag4);
        view_tag5Grid.setText(tag5);
        view_tag6Grid.setText(tag6);


    }


    private void getPhotosAsEachTag(String tag1, String tag2, String tag3, String tag4, String tag5, final String tag6) {

        Log.d("가져오는 중 ", "태그별 가져오는 중");

        ApiClient.getPhotoByKeyword().getPhotobyKeyward(tag1, 1, 1).enqueue(new Callback<PhotoSearch>() {
            @Override
            public void onResponse(Call<PhotoSearch> call, Response<PhotoSearch> response) {
                if (response.isSuccessful()) {
                    //photoUrl[0] = (Results) response.body().getResults();
                    results = response.body().getResults();
                    setImageView(results.get(0).getUrls().getSmall(), tag1Gridview);

                    //Glide.with(context).load(results.get(0).getUrls().getSmall()).into(tag1Gridview);
                    Log.d("tag1", "잘 가져옴");


                }
            }

            @Override
            public void onFailure(Call<PhotoSearch> call, Throwable t) {
                Log.d("tag1 에서 사진 검색 오류", t.getMessage());

            }
        });

        ApiClient.getPhotoByKeyword().getPhotobyKeyward(tag2, 1, 1).enqueue(new Callback<PhotoSearch>() {
            @Override
            public void onResponse(Call<PhotoSearch> call, Response<PhotoSearch> response) {
                if (response.isSuccessful()) {
                    //photoUrl[0] = (Results) response.body().getResults();
                    results = response.body().getResults();

                    setImageView(results.get(0).getUrls().getSmall(), tag2Gridview);
                    //Glide.with(context).load(results.get(0).getUrls().getSmall()).into(tag2Gridview);
                    Log.d("tag2", "잘 가져옴");

                }
            }

            @Override
            public void onFailure(Call<PhotoSearch> call, Throwable t) {
                Log.d("tag2 에서 사진 검색 오류", t.getMessage());

            }
        });

        ApiClient.getPhotoByKeyword().getPhotobyKeyward(tag3, 1, 1).enqueue(new Callback<PhotoSearch>() {
            @Override
            public void onResponse(Call<PhotoSearch> call, Response<PhotoSearch> response) {
                if (response.isSuccessful()) {
                    //photoUrl[0] = (Results) response.body().getResults();
                    results = response.body().getResults();

                    setImageView(results.get(0).getUrls().getSmall(), tag3Gridview);
                    //Glide.with(context).load(results.get(0).getUrls().getSmall()).into(tag3Gridview);
                    Log.d("tag3", "잘 가져옴");

                }
            }

            @Override
            public void onFailure(Call<PhotoSearch> call, Throwable t) {
                Log.d("tag3 에서 사진 검색 오류", t.getMessage());

            }
        });
        ApiClient.getPhotoByKeyword().getPhotobyKeyward(tag4, 1, 1).enqueue(new Callback<PhotoSearch>() {
            @Override
            public void onResponse(Call<PhotoSearch> call, Response<PhotoSearch> response) {
                if (response.isSuccessful()) {
                    //photoUrl[0] = (Results) response.body().getResults();
                    results = response.body().getResults();

                    setImageView(results.get(0).getUrls().getSmall(), tag4Gridview);
                    //Glide.with(context).load(results.get(0).getUrls().getSmall()).into(tag4Gridview);
                    Log.d("tag4", "잘 가져옴");

                }
            }

            @Override
            public void onFailure(Call<PhotoSearch> call, Throwable t) {
                Log.d("tag4 에서 사진 검색 오류", t.getMessage());

            }
        });
        ApiClient.getPhotoByKeyword().getPhotobyKeyward(tag5, 1, 1).enqueue(new Callback<PhotoSearch>() {
            @Override
            public void onResponse(Call<PhotoSearch> call, Response<PhotoSearch> response) {
                if (response.isSuccessful()) {
                    //photoUrl[0] = (Results) response.body().getResults();
                    results = response.body().getResults();

                    setImageView(results.get(0).getUrls().getSmall(), tag5Gridview);
                    //Glide.with(context).load(results.get(0).getUrls().getSmall()).into(tag5Gridview);
                    Log.d("tag5", "잘 가져옴");

                }
            }

            @Override
            public void onFailure(Call<PhotoSearch> call, Throwable t) {
                Log.d("tag5 에서 사진 검색 오류", t.getMessage());

            }
        });

        ApiClient.getPhotoByKeyword().getPhotobyKeyward(tag6, 1, 1).enqueue(new Callback<PhotoSearch>() {
            @Override
            public void onResponse(Call<PhotoSearch> call, Response<PhotoSearch> response) {
                if (response.isSuccessful()) {
                    //photoUrl[0] = (Results) response.body().getResults();
                    results = response.body().getResults();

                    setImageView(results.get(0).getUrls().getSmall(), tag6Gridview);
                    //Glide.with(context).load(results.get(0).getUrls().getSmall()).into(tag6Gridview);
                    Log.d("tag6", "잘 가져옴");

                }
            }

            @Override
            public void onFailure(Call<PhotoSearch> call, Throwable t) {
                Log.d("tag6 에서 사진 검색 오류", t.getMessage());

            }
        });


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.grid1:
                intent.putExtra("photoID", tag1);
                startActivity(intent);
                break;

            case R.id.grid2:
                intent.putExtra("photoID", tag2);
                startActivity(intent);
                break;

            case R.id.grid3:
                intent.putExtra("photoID", tag3);
                startActivity(intent);
                break;

            case R.id.grid4:
                intent.putExtra("photoID", tag4);
                startActivity(intent);
                break;

            case R.id.grid5:
                intent.putExtra("photoID", tag5);
                startActivity(intent);
                break;

            case R.id.grid6:
                intent.putExtra("photoID", tag6);
                startActivity(intent);
                break;

            case R.id.goInfo:
                //show Dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(R.string.tab2_DialogTitle);
                builder.setMessage(R.string.tab2_DialogMessage);
                builder.setPositiveButton(R.string.tab2_DialogOk,
                        (dialog, which) -> {
                        });

                builder.show();

                break;

            case R.id.goReset:
                AlertDialog.Builder resetDialog = new AlertDialog.Builder(context);
                resetDialog.setTitle(getString(R.string.tab2_resetTitle));
                resetDialog.setMessage(getString(R.string.tab2_resetContent));
                resetDialog.setPositiveButton(R.string.tab2_DialogOk,
                        (dialog, which) -> {
                            TagTools.resetTags(context);
                            setTagListsView(context);
                            getPhotosAsEachTag(tag1, tag2, tag3, tag4, tag5, tag6);
                        }).setNegativeButton(R.string.saveDialogNega,
                        ((dialog, which) -> {

                        }));

                resetDialog.show();


                break;
        }
    }


    private void setImageView(String photoUrl, ImageView target) {

        if (getActivity() != null) {
            if (!getActivity().isFinishing()) {
                Glide.with(context).load(photoUrl).into(target);
            }
        }


    }


}
