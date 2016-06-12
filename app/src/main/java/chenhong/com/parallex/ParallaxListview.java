package chenhong.com.parallex;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewTreeObserver;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.ListView;

/**
 * Created by Administrator on 2016/6/11.
 */
public class ParallaxListview extends ListView{
    private int drawableHeight;
    private ImageView imageView;
    private int originHeight;
    private int maxHeight;

    public ParallaxListview(Context context) {
        super(context);
    }

    public ParallaxListview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ParallaxListview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public  void setParallaxImageview(final ImageView imageview1){
        this.imageView=imageview1;
        imageView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                imageView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                originHeight = imageView.getHeight();//在这个树是layout之后再获取height
                drawableHeight=imageView.getDrawable().getIntrinsicHeight();//得到图片的高度
                maxHeight = originHeight >drawableHeight? originHeight *2:drawableHeight;
            }
        });

    }

    /**
     * listview滑动到头执行，可以继续获取滑动的距离和方向
     * @param deltaX  继续滑动X方向的距离
     * @param deltaY  继续滑动Y方向的距离   负：顶部到头 正：底部到头
     * @param scrollX
     * @param scrollY
     * @param scrollRangeX
     * @param scrollRangeY
     * @param maxOverScrollX  X方向最大可以滚动的距离
     * @param maxOverScrollY  Y方向最大可以滚动的距离
     * @param isTouchEvent    true 是手指拖动滑动 false惯性滑动 fliding
     * @return
     */
    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
       if(imageView!=null) {
           if (deltaY < 0 && isTouchEvent == true) {
               //不断增加imageview的高度
               int newHeight = imageView.getHeight() - deltaY/3;
               if(newHeight>maxHeight){
                   newHeight=maxHeight;
               }
               imageView.getLayoutParams().height = newHeight;
               imageView.requestLayout();//使imageview的布局参数生效
           }
       }
           return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);

    }

    //松开返回原状态
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_UP:
                final ValueAnimator animator=ValueAnimator.ofInt(imageView.getHeight(),originHeight);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int value= (int) animator.getAnimatedValue();//获取动画的值
                        imageView.getLayoutParams().height = value;
                        imageView.requestLayout();//使imageview的布局参数生效
                    }
                });
                animator.setInterpolator(new OvershootInterpolator());//动画插值器 弹性
                animator.setDuration(500);
                animator.start();
            break;
        }
        return super.onTouchEvent(ev);
    }
}
