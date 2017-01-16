package com.zhh.adapter.tools;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * 作者： zzhh
 * 
 * 时间：@2016年12月22日 下午5:00:02
 * 
 */
public abstract class CommonAdapter<T> extends BaseAdapter {
    Context mContext; 
    List<T> mList;
    int resource=-1; 
    LayoutInflater mLayoutInflater;
    /**
     * 
     * @param mContext 
     * @param mList 数据源，请填写
     * @param resource 对应的layout的xml，如R.layout.XXXX
     */
    public CommonAdapter(Context mContext, List<T> mList, int resource) {
        super();
        this.mContext = mContext;
        this.mList = mList;
        this.resource = resource;
        mLayoutInflater=(LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mList.size();
    }

    @Override
    public T getItem(int position) {
        // TODO Auto-generated method stub
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    /**
     * getView中间子View的内容设置的代码提出来，向外提供一个抽象发放，自定义实现。
     * @param commonViewHolder 当前的ViewHolder
     * @param currentView    当前的View
     * @param item    对应View的数据
     * @param position 这个大家都懂了吧
     */
    public abstract void setViewData(CommonViewHolder commonViewHolder,View currentView,T item, int position);
    
    /**
     * 方法完全抽离只剩下获取当前View和ViewHolder，然后返回View
     */
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        // TODO Auto-generated method stub
        if(view==null){
            view=mLayoutInflater.inflate(resource, null);
        }
        CommonViewHolder cvh=CommonViewHolder.get(view);
        setViewData(cvh,view,getItem(position), position);
        return view;
    }

}
