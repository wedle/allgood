package com.yiqihao.loan.ui.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;


public abstract class AppBaseAdapter<T> extends BaseAdapter {
    public List<T> list;
    public Context context;
    public LayoutInflater inflater;

    public AppBaseAdapter(List<T> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list != null && list.size() > 0 ? list.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getViewHolder(position, convertView, parent).getConvertView();
    }


    public abstract ViewHolder getViewHolder(int position, View convertView, ViewGroup parent);

    /**
     * 当做是hashMap
     */
    static class ViewHolder {
        private SparseArray<View> views = new SparseArray<View>();
        private View convertView;

        private ViewHolder(View convertView) {
            this.convertView = convertView;
            convertView.setTag(this);
        }

        public static ViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
                return new ViewHolder(convertView);
            } else {
                return (ViewHolder) convertView.getTag();
            }
        }

        public View getConvertView() {
            return convertView;
        }

        public void setConvertView(View convertView) {
            this.convertView = convertView;
        }

        public View findViewById(int viewId) {

            View view = views.get(viewId);
            if (view == null) {
                view = convertView.findViewById(viewId);
                views.put(viewId, view);
            }
            return view;
        }

    }

}
