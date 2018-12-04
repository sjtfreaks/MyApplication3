package com.example.myapplication.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.adapter.HotAdapter;
import com.example.myapplication.bean.HotData;
import com.example.myapplication.util.L;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HotFragment extends Fragment {
    private ListView mListView;
    private List<HotData> mList = new ArrayList<>();
    private List<String> mListTitle = new ArrayList<>();
    private List<String> mListUrl = new ArrayList<>();

    public static HotFragment newInstance(String name){
        Bundle args = new Bundle();
        args.putString("name", name);
        HotFragment fragment = new HotFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one,null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mListView = (ListView) view.findViewById(R.id.mListView);
        mList.clear();
        String url = "http://106.14.6.33:3001/api/joke_img";
        RxVolley.get(url, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                //Toast.makeText(getActivity(),t, Toast.LENGTH_SHORT).show();
                parsingJson(t);
                L.i("json:"+t);
            }
        });

    }

    private void parsingJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONArray jsonList = jsonObject.getJSONArray("data");

            for (int i = 0; i<jsonList.length();i++){
                JSONObject json = (JSONObject) jsonList.get(i);

                HotData data = new HotData();
                String title = json.getString("title");

                data.setTitle(json.getString("title"));
                data.setImage(json.getString("sourceurl"));

                mList.add(data);
                mListTitle.add(title);
            }
            HotAdapter adapter = new HotAdapter(getActivity(),mList);
            mListView.setAdapter(adapter);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

}
