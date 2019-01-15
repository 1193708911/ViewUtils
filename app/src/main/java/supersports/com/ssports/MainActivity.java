package supersports.com.ssports;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import supersports.com.net.R;

public class MainActivity extends AppCompatActivity {

    private AutoViewPager mViewPager;
    List<String> imgUrls = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        bindViewPager();

    }

    private void bindViewPager() {

        imgUrls.add("http://www.pptok.com/wp-content/uploads/2012/08/xunguang-4.jpg");

        imgUrls.add("http://www.pptbz.com/pptpic/UploadFiles_6909/201203/2012031220134655.jpg");

        imgUrls.add("http://pic17.nipic.com/20111021/8633866_210108284151_2.jpg");

        mViewPager.setUpWithImages(imgUrls);
    }


    private void initView() {
        mViewPager = findViewById(R.id.view_pager);
    }
}
