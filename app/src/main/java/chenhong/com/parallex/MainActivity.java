package chenhong.com.parallex;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

/**
 * 先显示一部分
 */
public class MainActivity extends AppCompatActivity {

    private ParallaxListview parallaxListview;
    private String[] indexArr = { "A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
            "V", "W", "X", "Y", "Z" };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        parallaxListview = (ParallaxListview) findViewById(R.id.pl);
        parallaxListview.setOverScrollMode(View.OVER_SCROLL_NEVER);//永远不显示头顶的蓝色阴影
        View headerview= View.inflate(this,R.layout.layout_header,null);
        ImageView imageView= (ImageView) headerview.findViewById(R.id.imageView);
        parallaxListview.addHeaderView(headerview);
        parallaxListview.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, indexArr));
        parallaxListview.setParallaxImageview(imageView);
    }
}
