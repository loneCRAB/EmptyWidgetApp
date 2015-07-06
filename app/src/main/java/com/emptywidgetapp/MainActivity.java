package com.emptywidgetapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.emptywidgetapp.model.Category;
import com.emptywidgetapp.model.ListItem;

import java.util.Random;

public class MainActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void dbPopulate(View view) {
        Random rand = new Random();

        Category firstList = new Category("FirstList");
        Category secondList = new Category("SecondList");
        Category thirdList = new Category("ThirdList");
        ActiveAndroid.beginTransaction();
        try {
            for (int i = 0; i < 100; i++) {
                Category randCategory = null;
                int randomColor = Color.argb(
                        255,
                        rand.nextInt(256), rand.nextInt(256), rand.nextInt(256) // RGB
                );

                switch (rand.nextInt(3) + 1) {
                    case 1:
                        randCategory = firstList;
                        break;
                    case 2:
                        randCategory = secondList;
                        break;
                    case 3:
                        randCategory = thirdList;
                        break;
                }

                new ListItem(
                        "Title#" + (i + 1),
                        "This is " + (i + 1) + " message",
                        randCategory,
                        randomColor
                ).save();
            }
            ActiveAndroid.setTransactionSuccessful();
        } finally {
            ActiveAndroid.endTransaction();
        }
        Toast.makeText(this, "The database has been filled", Toast.LENGTH_SHORT).show();
    }

    public void dbClear(View view) {
        ActiveAndroid.dispose();
        deleteDatabase("ListItems.db");
        ActiveAndroid.initialize(this);
        Toast.makeText(this, "The database has been cleared", Toast.LENGTH_SHORT).show();
    }
}
