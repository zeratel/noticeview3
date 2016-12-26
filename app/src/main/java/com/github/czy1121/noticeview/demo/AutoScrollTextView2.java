package com.github.czy1121.noticeview.demo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * com.github.czy1121.noticeview.demo
 * Created by zeratel3000
 * on 2016 11 2016/11/25 11 40
 * description
 */

public class AutoScrollTextView2 extends TextView implements View.OnTouchListener {
    public final static String TAG = AutoScrollTextView2.class.getSimpleName();

    public boolean isStarting = false;//是否开始滚动
    private int viewWidth = 0;
    private int y = 0;//文字的纵坐标
    private ArrayList mStringList;
    //显示第几个
    private int p;
    private boolean flag = true;
    private TextViewItem mItem1;
    private TextViewItem mItem2;

    public AutoScrollTextView2(Context context) {
        super(context);
        initView();
    }

    public AutoScrollTextView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public AutoScrollTextView2(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        setOnTouchListener(this);
    }

    /**
     * 文字list
     */
    public void setStrings(ArrayList strings, Activity activity) {
        mStringList = strings;

        init(activity.getWindowManager());
    }

    /**
     * 文本初始化，每次更改文本内容或者文本效果等之后都需要重新初始化一下
     */
    public void init(WindowManager windowManager) {
        mItem1 = new TextViewItem();
        mItem2 = new TextViewItem();
        //初始化画笔
        mItem1.paint = getPaint();
        mItem2.paint = getPaint();

        if (mStringList != null && mStringList.size() > 0) {
            p = 0;
            mItem1.text = mStringList.get(p).toString();
        } else {
            if (!TextUtils.isEmpty(getText().toString())) {
                mItem1.text = getText().toString();
            } else {
                this.setVisibility(View.GONE);
                return;
            }
        }
        mItem1.textLength = (int) mItem1.paint.measureText(mItem1.text);

        //获取宽度
        getWidth(windowManager);

        mItem1.step = mItem1.textLength;
        mItem1.temp_view_plus_text_length = viewWidth + mItem1.textLength;
        mItem1.temp_view_plus_two_text_length = viewWidth + mItem1.textLength * 2;
        mItem1.temp_view_plus_two_text_length_half = viewWidth / 2 + mItem1.textLength * 2;

        //获取高度
        y = (int) (getTextSize() + getPaddingTop());
    }

    private void getWidth(WindowManager windowManager) {
        viewWidth = getWidth();
        if (viewWidth == 0) {
            if (windowManager != null) {
                Display display = windowManager.getDefaultDisplay();
                viewWidth = display.getWidth();
            }
        }
    }

//    @Override
//    public Parcelable onSaveInstanceState()
//    {
//        Parcelable superState = super.onSaveInstanceState();
//        SavedState ss = new SavedState(superState);
//
//        ss.step = step;
//        ss.isStarting = isStarting;
//
//        return ss;
//
//    }

//    @Override
//    public void onRestoreInstanceState(Parcelable state)
//    {
//        if (!(state instanceof SavedState)) {
//            super.onRestoreInstanceState(state);
//            return;
//        }
//        SavedState ss = (SavedState)state;
//        super.onRestoreInstanceState(ss.getSuperState());
//
//        step = ss.step;
//        isStarting = ss.isStarting;
//
//    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //基本不用测量y的值

                if (onItemClick != null) {
                    onItemClick.onClick(itemClick(event.getX()));
                }

                break;

            default:
                break;
        }
        return true;
    }

    private int itemClick(float x) {
        //具体点击的是哪一个
        if (x > 0 && x < viewWidth) {
            if (mItem1 != null && clickItem(mItem1 ,(int) x)) {
                return mItem1.itemP;
            }
            if (mItem2 != null && clickItem(mItem2 ,(int) x)) {
                return mItem2.itemP;
            }
            return -1;
        } else {
            return -1;
        }
    }

    private boolean clickItem(TextViewItem mItem ,int x) {
        int tempStartX = mItem.temp_view_plus_text_length - mItem.step;//文字开始的横坐标
        int tempEndX = mItem.textLength + tempStartX;//文字结束的横坐标
        return x > tempStartX && x < tempEndX;
    }


    public interface OnItemClick {
        void onClick(int p);
    }

    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

//    public static class SavedState extends BaseSavedState {
//        public boolean isStarting = false;
//        public float step = 0.0f;
//        SavedState(Parcelable superState) {
//            super(superState);
//        }
//
//        @Override
//        public void writeToParcel(Parcel out, int flags) {
//            super.writeToParcel(out, flags);
//            out.writeBooleanArray(new boolean[]{isStarting});
//            out.writeFloat(step);
//        }
//
//
//        public static final Creator<SavedState> CREATOR
//                = new Creator<SavedState>() {
//
//            public SavedState[] newArray(int size) {
//                return new SavedState[size];
//            }
//
//            @Override
//            public SavedState createFromParcel(Parcel in) {
//                return new SavedState(in);
//            }
//        };
//
//        private SavedState(Parcel in) {
//            super(in);
//            boolean[] b = null;
//            in.readBooleanArray(b);
//            if(b != null && b.length > 0)
//                isStarting = b[0];
//            step = in.readFloat();
//        }
//    }

    /**
     * 开始滚动
     */
    public void startScroll() {
        isStarting = true;
        invalidate();
    }

    /**
     * 停止滚动
     */
    public void stopScroll() {
        isStarting = false;
        invalidate();
    }


    @Override
    public void onDraw(Canvas canvas) {

        if (!isStarting) {
            return;
        }

        if (mItem1.textLength == 0 && mItem2.textLength == 0) {
            return;
        }

        if (mItem1.textLength != 0) {
            //绘制第一个
            canvas.drawText(mItem1.text,
                    mItem1.temp_view_plus_text_length - mItem1.step,
                    y, mItem1.paint);
            mItem1.step += 5;
        }

        if (mItem2.textLength != 0) {
            //绘制第二个
            canvas.drawText(mItem2.text,
                    mItem2.temp_view_plus_text_length - mItem2.step,
                    y, mItem2.paint);
            mItem2.step += 5;
        }

        //当第一个展示结束
        if (mItem1.step > mItem1.temp_view_plus_two_text_length) {
//            step = textLength;
            mItem1.clearText();
        }
        //当第二个展示结束
        if (mItem2.step > mItem2.temp_view_plus_two_text_length) {
//            step = textLength;
            mItem2.clearText();
        }

        //当第一个展示到中间需要装载下一个
        if (mItem1.step > mItem1.temp_view_plus_two_text_length_half && flag) {
            click();
            mItem2.setText(mStringList.get(p).toString(), p);
            flag = false;
        }

        //当第二个展示到中间需要装载下一个
        if (mItem2.step > mItem2.temp_view_plus_two_text_length_half && !flag) {
            click();
            mItem1.setText(mStringList.get(p).toString(), p);
            flag = true;
        }

        invalidate();

    }

    private void click() {
        ++p;
        if (p >= mStringList.size()) {
            p = 0;
        }
    }

//    @Override
//    public void onClick(View v) {
//        if(isStarting)
//            stopScroll();
//        else
//            startScroll();
//
//    }

    class TextViewItem {
        public int textLength = 0;//文本长度
        public int step = 0;//文字的横坐标
        public int temp_view_plus_text_length = 0;//屏幕加文字长度
        public int temp_view_plus_two_text_length = 0;//最终结束的时候
        public int temp_view_plus_two_text_length_half = 0;//第二个文字载入的长度
        public Paint paint = null;//绘图样式
        public String text = "";//文本内容
        public int itemP;//是第几个

        public void clearText() {
            text = "";
            textLength = 0;
            step = 0;
            temp_view_plus_text_length = 0;
            temp_view_plus_two_text_length = 0;
            temp_view_plus_two_text_length_half = 0;
            itemP = 0;
        }

        private void setText(String s, int temp) {
            text = s;
            textLength = (int) paint.measureText(text);
            step = textLength;
            temp_view_plus_text_length = viewWidth + textLength;
            temp_view_plus_two_text_length = viewWidth + textLength * 2;
            temp_view_plus_two_text_length_half = viewWidth / 2 + textLength * 2;
            itemP = temp;
        }

    }

}
