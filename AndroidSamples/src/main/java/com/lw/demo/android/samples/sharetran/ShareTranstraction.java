package com.lw.demo.android.samples.sharetran;

import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.TransitionSet;
import android.view.View;
import android.widget.TextView;

import com.lw.demo.android.samples.R;

public class ShareTranstraction extends AppCompatActivity {

    private View mTagView,mTitle;
    private ActivityOptionsCompat mOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_transtraction);
        mTagView = findViewById(R.id.edt_title);
        mTitle = findViewById(R.id.title_line);

        TransitionSet mtransitionset=new TransitionSet();//制定过度动画set
        mtransitionset.addTransition(new ChangeBounds());//改变表框大小
//        mtransitionset.addTransition(new ChangeImageTransform());//图片移动，还可以是其他的，要什么效果自己添加
        mtransitionset.setDuration(1000);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            getWindow().setEnterTransition(mtransitionset);//注意，下面是必须的
            getWindow().setExitTransition(mtransitionset);
            getWindow().setSharedElementEnterTransition(mtransitionset);
            getWindow().setSharedElementExitTransition(mtransitionset);
        }
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.edt_title:
                Pair titleEdt = new Pair(mTagView,"edt_title");
                Pair title = new Pair(mTitle,"title");
                mOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(this,title,titleEdt);
                ActivityCompat.startActivity(this,new Intent(this,ShareTranstractionTargetActivity.class),mOptions.toBundle());
                break;
        }
    }
}
