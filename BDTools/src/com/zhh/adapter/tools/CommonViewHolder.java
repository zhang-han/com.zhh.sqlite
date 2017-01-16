package com.zhh.adapter.tools;

import android.util.SparseArray;
import android.view.View;



/**
 * 作者： zzhh
 * 
 * 时间：@2016年12月22日 下午5:00:09
 * 
 */
public class CommonViewHolder extends SparseArray<View>{
    /**
     * 
     * @param view Adapter中传过来的View
     * @return 返回一个ViewHolder
     */
    public static CommonViewHolder get(View view){
        CommonViewHolder viewHolder= (CommonViewHolder) view.getTag();
        if(viewHolder==null){
            viewHolder=new CommonViewHolder();
            view.setTag(viewHolder);
        }
        return viewHolder;
    }

    /**
     * 此方法在CommonAdapter的子类中有使用，返回一个View,可以来设置View的内容
     * @param commonViewHolder 当前View对应的ViewHolder
     * @param currentView    当前的View
     * @param key    View中的一个控件View存放的Key
     * @return
     */
    public View get(CommonViewHolder commonViewHolder,View currentView,int key) {
        // TODO Auto-generated method stub
        View view = get(key);
        if(view==null){
            view=currentView.findViewById(key);
            commonViewHolder.put(key, view);
        }
        return view;
    }
    
}
