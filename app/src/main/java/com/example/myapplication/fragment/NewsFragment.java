package com.example.myapplication.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.adapter.NewAdapter;
import com.example.myapplication.adapter.WebActivity;
import com.example.myapplication.bean.NewData;
import com.example.myapplication.bean.OneData;
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
public class NewsFragment extends Fragment {
    private ListView mListView;
    private List<NewData> mList = new ArrayList<>();
    private List<String> mListTitle = new ArrayList<>();
    private List<String> mListUrl = new ArrayList<>();

    public static NewsFragment newInstance(String name){
        Bundle args = new Bundle();
        args.putString("name", name);
        NewsFragment fragment = new NewsFragment();
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
/**
 * 名称	热点	社会	娱乐	体育	美文	科技	财经	时尚
 * type	0	 1	 2	 3	 4	 5	 6	 7
 */
        String url = "http://106.14.6.33:3001/api/daily_info/20171206";
        RxVolley.get(url, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                //Toast.makeText(getActivity(),t, Toast.LENGTH_SHORT).show();
                parsingJson(t);
                L.i("json:"+t);
            }
        });
        //点击
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                L.i("position:"+position);
                Intent intent = new Intent(getActivity(), WebActivity.class);
                //2 way chuan zhi BUNdle
                intent.putExtra("title",mListTitle.get(position));
                intent.putExtra("url",mListUrl.get(position));
                startActivity(intent);
            }
        });
    }

    private void parsingJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONObject jsonObject1 = jsonObject.getJSONObject("data");
            JSONArray jsonList = jsonObject1.getJSONArray("links");

            for (int i = 0; i<jsonList.length();i++){
                JSONObject json = (JSONObject) jsonList.get(i);

                NewData data = new NewData();

                String url = json.getString("url");
                String title = json.getString("title");

                data.setTitle(json.getString("title"));
                data.setDesc(json.getString("description"));

                mList.add(data);
                mListTitle.add(title);
                mListUrl.add(url);
            }
            NewAdapter adapter = new NewAdapter(getActivity(),mList);
            mListView.setAdapter(adapter);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

}
