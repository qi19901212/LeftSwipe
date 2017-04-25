# LeftSwipe
### 左滑加载详情！这是一个利用RecyclerView滑动到最后跳转到详情的控件
![相关的gif 图片](https://github.com/qi19901212/LeftSwipe/blob/master/app/swipe.gif)

## 使用方法
1. 可以直接引用LeftSwipeLayout包即可
2. 在gradle 中直接添加 即可
     
    compile 'com.qi.leftswipe:LeftSwipeLayout:0.1.0'  


 #### xml 设置 。位置不可以改变
 
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
    
 #### java 调用如下：
     
     singleRecyclerView= (RecyclerView) findViewById(R.id.single_horizontal_recyclerView);
     swipeLeftView= (SwipeLeftView) findViewById(R.id.refreshable_view);
     LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
     layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
     singleRecyclerView.setLayoutManager(layoutManager);
        
     gAdapter = new GalleryAdapter(MainActivity.this);
     singleRecyclerView.setAdapter(gAdapter);
     datas = new ArrayList<>();
     // 添加数据
      gAdapter.addData(datas);
        
  #### 如果你想在滑动结束跳转到详情请实现接口SwipeListener
        在onSwipeEnd 方法中跳转即可。
  
