# LeftSwipe
### 左滑加载详情！这是一个利用RecyclerView滑动到最后跳转到详情的控件
![相关的gif 图片](https://github.com/qi19901212/LeftSwipe/blob/master/app/swipe.gif)

## 使用方法
 1.xml 设置 。位置不可以改变
 
    <com.qi.SwipeLeftView
        android:id="@+id/refreshable_view"
        android:layout_width="match_parent"
        android:layout_height="200dp">
        <android.support.v7.widget.RecyclerView
            android:id="@+id/single_horizontal_recyclerView"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:scrollbars="none"/>
        <include
            layout="@layout/left_pull_to_detail"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"/>
    </com.qi.SwipeLeftView>
  2. java 调用如下：
  
        singleRecyclerView= (RecyclerView) findViewById(R.id.single_horizontal_recyclerView);
        swipeLeftView= (SwipeLeftView) findViewById(R.id.refreshable_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        singleRecyclerView.setLayoutManager(layoutManager);
        
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
       
        
        
    3 .如果你想在滑动结束跳转到详情请实现接口SwipeListener
        在onSwipeEnd 方法中跳转即可。
     swipeLeftView.setOnSwipeListener(this);
