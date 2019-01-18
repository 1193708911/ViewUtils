package supersports.com.ssports;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import supersports.com.lib_annot.BindView;
import supersports.com.lib_annot.Click;
import supersports.com.lib_annot.ViewInject;
import supersports.com.lib_compile.ViewHelper;
import supersports.com.net.R;

@ViewInject(layout_id = R.layout.activity_main)
public class MainActivity extends AppCompatActivity {
    @BindView(value = R.id.button)
    private Button mButton;
    @BindView(value = R.id.img)
    private ImageView mImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewHelper.bindLayoutId(this);

    }


    @Click(values = {R.id.img, R.id.button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                Toast.makeText(MainActivity.this, "点击了button", Toast.LENGTH_LONG).show();
                break;
            case R.id.img:
                Toast.makeText(MainActivity.this, "点击了图片", Toast.LENGTH_LONG).show();
                break;
        }

    }


}
