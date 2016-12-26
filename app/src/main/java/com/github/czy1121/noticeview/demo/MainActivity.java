package com.github.czy1121.noticeview.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.github.czy1121.view.NoticeView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static String[] notices = new String[]{
            "伪装者:胡歌演绎'痞子特工'",
            "无心法师:生死离别!月牙遭虐杀",
            "花千骨:尊上沦为花千骨",
            "综艺饭:胖轩偷看夏天洗澡掀波澜",
            "碟中谍4:阿汤哥高塔命悬一线,超越不可能,碟中谍4:阿汤哥高塔命悬一线,超越不可能,碟中谍4:阿汤哥高塔命悬一线,超越不可能",
    };

    NoticeView vNotice;
    NoticeView vNotice2;
    private AutoScrollTextView autoScrollTextView;
    private AutoScrollTextView2 autoScrollTextView2;
    private ArrayList mArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        vNotice = (NoticeView) findViewById(R.id.notice);
//        vNotice.start(Arrays.asList(notices));
//        vNotice.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, notices[vNotice.getIndex()], Toast.LENGTH_SHORT).show();
//            }
//        });
//
//
//        vNotice2 = (NoticeView) findViewById(R.id.notice2);
//        vNotice2.start(Arrays.asList(notices));
//        vNotice2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity.this, notices[vNotice.getIndex()], Toast.LENGTH_SHORT).show();
//            }
//        });


        //启动公告滚动条
//        autoScrollTextView = (AutoScrollTextView)findViewById(R.id.tvn);
//        autoScrollTextView.init(getWindowManager());
//        autoScrollTextView.startScroll();

        mArrayList = new ArrayList();
        mArrayList.add("伪装者:胡歌演绎'痞子特工喵哈哈，伪装者:胡歌演绎'痞子特工喵哈哈，伪装者:胡歌演绎'痞子特工喵哈哈");
        mArrayList.add("无心法师:生死离别!月牙遭虐杀，无心法师:生死离别!月牙遭虐杀，无心法师:生死离别!月牙遭虐杀");
        mArrayList.add("花千骨:尊上沦为花千骨喵哈哈，花千骨:尊上沦为花千骨喵哈哈，花千骨:尊上沦为花千骨喵哈哈");
        mArrayList.add("综艺饭:胖轩偷看夏天洗澡掀波澜喵哈哈，综艺饭:胖轩偷看夏天洗澡掀波澜喵哈哈，综艺饭:胖轩偷看夏天洗澡掀波澜喵哈哈");
        mArrayList.add("碟中谍4:阿汤哥高塔命悬一线,超越不可能喵哈哈，碟中谍4:阿汤哥高塔命悬一线,超越不可能喵哈哈，碟中谍4:阿汤哥高塔命悬一线,超越不可能喵哈哈");
//        mArrayList.add("伪");
//        mArrayList.add("无");
//        mArrayList.add("花");
//        mArrayList.add("综");
//        mArrayList.add("碟");

        //启动公告滚动条
        autoScrollTextView2 = (AutoScrollTextView2) findViewById(R.id.tvn2);
        autoScrollTextView2.setStrings(mArrayList, this);
        autoScrollTextView2.setOnItemClick(new AutoScrollTextView2.OnItemClick() {
            @Override
            public void onClick(int p) {
                if (p != -1) {
                    Toast.makeText(MainActivity.this, (CharSequence) mArrayList.get(p),Toast.LENGTH_SHORT).show();
                }
            }
        });
        autoScrollTextView2.startScroll();


    }


    @Override
    public void onClick(View v) {
    }
}
