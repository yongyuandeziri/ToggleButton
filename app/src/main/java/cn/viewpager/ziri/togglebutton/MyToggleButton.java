package cn.viewpager.ziri.togglebutton;

import android.content.Context;
import android.content.QuickViewConstants;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * Created by ward on 2017/8/19.
 */

public class MyToggleButton extends View {

    private Bitmap backgroundbitmap;
    private Bitmap slidebuttonbitmap;
    private Paint paint;
    private boolean mcurrentstate=false;
    private float marginleft=0;
    private float mlastX;
    private float mlastY;
    private float mcurrentX;
    private float mcurrentY;
    private float distanceX;
    private float maxdistance;

    public MyToggleButton(Context context) {
        this(context,null);
    }

    public MyToggleButton(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public MyToggleButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initview();
    }



    private void initview() {
        backgroundbitmap= BitmapFactory.decodeResource(getResources(),R.drawable.switch_background);
        slidebuttonbitmap= BitmapFactory.decodeResource(getResources(),R.drawable.slide_button);
        maxdistance=backgroundbitmap.getWidth()-slidebuttonbitmap.getWidth();
        //初始化画笔
        paint=new Paint();
        paint.setAntiAlias(true);
/*
 *onclick 与ontouch并不冲突，可以通过flag加以区分
 */
//        this.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mcurrentstate=!mcurrentstate;
//                changestate();
//            }
//        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("ward_du", "onTouchEvent: mcurrentx:"+mcurrentX +" mcurrenty:"+mcurrentY);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mcurrentX=mlastX=event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                mcurrentX=event.getX();
                distanceX=mcurrentX-mlastX;
                if(mcurrentstate==true){
                    marginleft=maxdistance+distanceX;
                }else{
                    marginleft=distanceX;
                }
                marginleft=(marginleft>=0)?marginleft:0;
                marginleft=(marginleft>maxdistance)?maxdistance:marginleft;
                Log.d("ward_du" , "onTouchEvent: distanceX"+distanceX+" marginleft:"+marginleft +" maxdistance:"+maxdistance );
                invalidate();//通知数据变化，更新view 会调用onDraw()方法
                break;
            case MotionEvent.ACTION_UP:
                Log.d("ward_du", "onTouchEvent: ACTION_UP");
                if (marginleft > maxdistance/2) {
                    mcurrentstate=true;
                }else{
                    mcurrentstate=false;
                }
                changestate();
                break;
            default:
                break;
        }
        return true;
    }


    private void changestate() {
        if(mcurrentstate){
            marginleft=backgroundbitmap.getWidth()-slidebuttonbitmap.getWidth();
        }else{
            marginleft=0;
        }
        invalidate();//通知数据变化，更新view 会调用onDraw()方法
    }

    /**
     * 自定义view的时候，作用不大
     * @param changed
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        /***
         * 绘制背景
         */
        canvas.drawBitmap(backgroundbitmap,0,0,paint);
        canvas.drawBitmap(slidebuttonbitmap,marginleft,0,paint);
    }

    /**
     * 测量尺寸时候的回调方法，设置当前view的大小
     * @param widthMeasureSpec 单位为像素
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(backgroundbitmap.getWidth(),backgroundbitmap.getHeight());
    }

}
