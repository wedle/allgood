package com.yiqihao.loan.widget.listview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * Created by 冯浩 on 2015/12/1.
 */
public class PagingListView extends ListView {

    private boolean isLoading;
    private boolean hasMoreItems;
    private Pagingable pagingableListener;
    private LoadingView loadingView;
    private OnScrollListener onScrollListener;

    public PagingListView(Context context) {
        super(context);
        init();
    }

    public PagingListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PagingListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public boolean isLoading() {
        return this.isLoading;
    }

    public void setIsLoading(boolean isLoading) {
        this.isLoading = isLoading;
    }

    public void setPagingableListener(Pagingable pagingableListener) {
        this.pagingableListener = pagingableListener;
    }

    public void setHasMoreItems(boolean hasMoreItems) {
        this.hasMoreItems = hasMoreItems;
        loadingView.setShowLoadView(hasMoreItems);
    }

    public boolean hasMoreItems() {
        return this.hasMoreItems;
    }

    public void onFinishLoading(boolean hasMoreItems) {
        setHasMoreItems(hasMoreItems);
        setIsLoading(false);
    }

    private void init() {
        setFooterDividersEnabled(false);
        isLoading = false;
        loadingView = new LoadingView(getContext());
        addFooterView(loadingView);
        loadingView.setShowLoadView(isLoading);
        super.setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

                //Dispatch to child OnScrollListener
                if (onScrollListener != null) {
                    onScrollListener.onScrollStateChanged(view, scrollState);
                }

                if (isLoading && hasMoreItems && scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
                    if (pagingableListener != null) {
                        isLoading = false;
                        pagingableListener.onLoadMoreItems();
                    }
                }

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                //Dispatch to child OnScrollListener
                if (onScrollListener != null) {
                    onScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
                }

                if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount > 0) {
                    isLoading = true;
                }

            }
        });
    }

    @Override
    public void setOnScrollListener(OnScrollListener listener) {
        onScrollListener = listener;
    }

    public interface Pagingable {
        void onLoadMoreItems();
    }
}
