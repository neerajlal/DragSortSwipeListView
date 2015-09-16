package com.android.lvdrag;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.ListView;
import android.widget.Toast;

import com.android.lvdrag.swipe.SwipeMenu;
import com.android.lvdrag.swipe.SwipeMenuCreator;
import com.android.lvdrag.swipe.SwipeMenuItem;
import com.example.android.listviewdragginganimation.R;

import java.util.ArrayList;

public class ListViewDragActivity extends Activity {

    ArrayList<String> items;
    StableArrayAdapter adapter;
    DynamicListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        items = new ArrayList<String>();
        for (int i = 0; i < 10; ++i) {
            items.add("Item " + i);
        }

        adapter = new StableArrayAdapter(this, R.layout.text_view, items);
        listView = (DynamicListView) findViewById(R.id.listview);
        listView.setAdapter(adapter);

        listView.setCheeseList(items);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        // Swipe to delete
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xf2,
                        0x6e, 0x71)));
                // set item width
                deleteItem.setWidth(dp2px(50));
                // set a icon
                deleteItem.setIcon(android.R.drawable.ic_menu_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        // set creator
        listView.setMenuCreator(creator);

        listView.setOnMenuItemClickListener(new DynamicListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final int position, SwipeMenu menu,
                                           int index) {
                switch (index) {
                    case 0:
                        Toast.makeText(getApplicationContext(),
                                "Delete " + items.get(position) + "?",
                                Toast.LENGTH_LONG).show();
                        break;
                }
                // false : close the menu; true : not close the menu
                return true;
            }
        });

        ArrayList<Boolean> enabled = new ArrayList<Boolean>();
        for (String item : items) {
            enabled.add(true);
        }
        listView.setAdapter(adapter, enabled);
//		listView.setAdapter(adapter);

    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getApplicationContext().getResources().getDisplayMetrics());
    }
}
