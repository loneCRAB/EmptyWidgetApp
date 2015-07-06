package com.emptywidgetapp.widget;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.emptywidgetapp.R;
import com.emptywidgetapp.model.Category;
import com.emptywidgetapp.model.ListItem;

import java.util.ArrayList;

public class ListProvider implements RemoteViewsService.RemoteViewsFactory {

    static final String TO_DISPLAY = "com.emptywidgetapp.DISPLAY";

    private ArrayList<ListItem> itemsList;
    private Display display;
    private Context context;

    public ListProvider(Context context, Intent intent) {
        this.context = context;
        display = Display.values()[intent.getIntExtra(TO_DISPLAY, Display.DISPLAY_ALL.ordinal())];
        populateListItem();
    }

    private void populateListItem() {
        switch (display) {
            case DISPLAY_ALL:
                itemsList = (ArrayList<ListItem>) Category.getAllItems();
                break;
            case FIRST_LIST:
                itemsList = (ArrayList<ListItem>) Category.getAllItems(1);
                break;
            case SECOND_LIST:
                itemsList = (ArrayList<ListItem>) Category.getAllItems(2);
                break;
            case THIRD_LIST:
                itemsList = (ArrayList<ListItem>) Category.getAllItems(3);
                break;
        }
    }

    @Override
    public RemoteViews getViewAt(int position) {
        final RemoteViews remoteView = new RemoteViews(
                context.getPackageName(), R.layout.lv_item_layout
        );
        ListItem listItem = itemsList.get(position);
        remoteView.setTextViewText(R.id.title, listItem.title);
        remoteView.setTextViewText(R.id.message, listItem.message);
        remoteView.setInt(R.id.lv_item_root_view, "setBackgroundColor", listItem.color);

        return remoteView;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        Log.d("TAGTAG", "OnDataSetCHANGED");
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return itemsList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    static enum Display {DISPLAY_ALL, FIRST_LIST, SECOND_LIST, THIRD_LIST}

}
