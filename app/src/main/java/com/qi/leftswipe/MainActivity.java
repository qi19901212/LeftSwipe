package com.qi.leftswipe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.qi.SwipeLeftView;
import com.qi.SwipeListener;
import com.qi.leftswipe.adapter.GalleryAdapter;
import com.qi.leftswipe.adapter.ListItemAdapter;
import com.qi.leftswipe.model.ItemModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SwipeListener {

    private RecyclerView singleRecyclerView;
    private RecyclerView recyclerView;
    private GalleryAdapter gAdapter;
    private ArrayList<String> datas;
    private SwipeLeftView swipeLeftView;
    private ListItemAdapter listAdapter;
    private ArrayList<ItemModel> datasItemModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        singleRecyclerView= (RecyclerView) findViewById(R.id.single_horizontal_recyclerView);
        swipeLeftView= (SwipeLeftView) findViewById(R.id.refreshable_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        singleRecyclerView.setLayoutManager(layoutManager);

        recyclerView=(RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(MainActivity.this);
        layoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager1);

        gAdapter = new GalleryAdapter(MainActivity.this);
        singleRecyclerView.setAdapter(gAdapter);
        datas = new ArrayList<>();
        datas.add("http://r.yhres.com/invitation/icon127.jpg");
        datas.add("http://r.yhres.com/invitation/icon128.jpg");
        datas.add("http://r.yhres.com/invitation/icon129.jpg");
        datas.add("http://r.yhres.com/invitation/icon130.jpg");
        datas.add("http://r.yhres.com/invitation/icon127.jpg");
        datas.add("http://r.yhres.com/invitation/icon128.jpg");
        datas.add("http://r.yhres.com/invitation/icon129.jpg");
        datas.add("http://r.yhres.com/invitation/icon130.jpg");
        gAdapter.addData(datas);
        swipeLeftView.setOnSwipeListener(this);

        datasItemModel=new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            ItemModel itemModel = new ItemModel();
            itemModel.url=datas;
            datasItemModel.add(itemModel);
        }
        listAdapter = new ListItemAdapter(MainActivity.this);
        recyclerView.setAdapter(listAdapter);
        listAdapter.addData(datasItemModel);
    }

    @Override
    public void onSwipeEnd() {
//         要跳转还是干啥...............
    }
}
