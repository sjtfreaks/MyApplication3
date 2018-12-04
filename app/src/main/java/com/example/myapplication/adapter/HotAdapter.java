package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.bean.HotData;
import com.example.myapplication.util.PicassoUtils;


import java.util.ArrayList;
import java.util.List;

public class HotAdapter extends BaseAdapter {
    private Context mContext;
    private List<HotData> mList;
    private HotData data;
    private LayoutInflater inflater;

    private WindowManager windowManager;
    private int width;

    public HotAdapter (Context mContext, List<HotData> mList){
        this.mContext = mContext;
        this.mList = mList;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //get系统服务
        windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        //获取屏幕宽度
        width = windowManager.getDefaultDisplay().getWidth();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null){
            viewHolder = new ViewHolder();
            view = inflater.inflate(R.layout.item_image,null);
            viewHolder.tv_title = view.findViewById(R.id.tv_title);
            viewHolder.iv_joke = view.findViewById(R.id.iv_joke);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        data = mList.get(i);
        String iv_joke = data.getImage();
        viewHolder.tv_title.setText(data.getTitle());


        //指定大小
        PicassoUtils.loadImageViewSize(mContext,iv_joke,viewHolder.iv_joke,width,width);
        return view;
    }

    class ViewHolder{
        private TextView tv_title;//标题
        private ImageView iv_joke;
    }
}
