package com.emptywidgetapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.widget.RemoteViews;

import com.emptywidgetapp.R;

import java.util.Random;

public class WidgetProvider extends AppWidgetProvider {

    static final String ALL_ITEMS_ACTION = "android.appwidget.action.APPWIDGET_DISPLAY_ALL_ITEMS";
    static final String FIRST_LIST_ITEMS_ACTION = "android.appwidget.action.APPWIDGET_DISPLAY_FIRST_LIST";
    static final String SECOND_LIST_ITEMS_ACTION = "android.appwidget.action.APPWIDGET_DISPLAY_SECOND_LIST";
    static final String THIRD_LIST_ITEMS_ACTION = "android.appwidget.action.APPWIDGET_DISPLAY_THIRD_LIST";

    private ListProvider.Display display = ListProvider.Display.DISPLAY_ALL;

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        boolean update = false;
        switch (intent.getAction()) {
            case ALL_ITEMS_ACTION:
                display = ListProvider.Display.DISPLAY_ALL;
                update = true;
                break;
            case FIRST_LIST_ITEMS_ACTION:
                display = ListProvider.Display.FIRST_LIST;
                update = true;
                break;
            case SECOND_LIST_ITEMS_ACTION:
                display = ListProvider.Display.SECOND_LIST;
                update = true;
                break;
            case THIRD_LIST_ITEMS_ACTION:
                display = ListProvider.Display.THIRD_LIST;
                update = true;
                break;
        }

        if (update) {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            ComponentName widget = new ComponentName(context.getPackageName(), getClass().getName());
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(widget);

            onUpdate(context, appWidgetManager, appWidgetIds);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            RemoteViews widgetView = updateWidgetListView(context, appWidgetId);
            appWidgetManager.updateAppWidget(appWidgetId, widgetView);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    private RemoteViews updateWidgetListView(Context context, int appWidgetId) {
        RemoteViews widgetView = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        Intent svcIntent = new Intent(context, WidgetService.class);
        svcIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                appWidgetId + new Random().nextInt());
        svcIntent.putExtra(ListProvider.TO_DISPLAY, display.ordinal());
        svcIntent.setData(Uri.parse(svcIntent.toUri(Intent.URI_INTENT_SCHEME)));

        //First btn
        Intent intent1 = new Intent(ALL_ITEMS_ACTION);
        PendingIntent pendingIntent1 = PendingIntent.getBroadcast(context, 0, intent1,
                PendingIntent.FLAG_UPDATE_CURRENT);
        widgetView.setOnClickPendingIntent(R.id.display_all_items, pendingIntent1);

        //Second btn
        Intent intent2 = new Intent(FIRST_LIST_ITEMS_ACTION);
        PendingIntent pendingIntent2 = PendingIntent.getBroadcast(context, 0, intent2,
                PendingIntent.FLAG_UPDATE_CURRENT);
        widgetView.setOnClickPendingIntent(R.id.display_first_list, pendingIntent2);

        //First btn
        Intent intent3 = new Intent(SECOND_LIST_ITEMS_ACTION);
        PendingIntent pendingIntent3 = PendingIntent.getBroadcast(context, 0, intent3,
                PendingIntent.FLAG_UPDATE_CURRENT);
        widgetView.setOnClickPendingIntent(R.id.display_second_list, pendingIntent3);

        //First btn
        Intent intent4 = new Intent(THIRD_LIST_ITEMS_ACTION);
        PendingIntent pendingIntent4 = PendingIntent.getBroadcast(context, 0, intent4,
                PendingIntent.FLAG_UPDATE_CURRENT);
        widgetView.setOnClickPendingIntent(R.id.display_third_list, pendingIntent4);

        //Adapter
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH)
            widgetView.setRemoteAdapter(R.id.list_view, svcIntent);
        else
            widgetView.setRemoteAdapter(appWidgetId, R.id.list_view, svcIntent);
        return widgetView;
    }


}
