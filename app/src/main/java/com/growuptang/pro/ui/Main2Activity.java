package com.growuptang.pro.ui;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.growuptang.pro.TaoBaoDetails.GradationScrollView;
import com.growuptang.pro.TaoBaoDetails.MyImageLoader;
import com.growuptang.pro.TaoBaoDetails.NoScrollListView;
import com.growuptang.pro.TaoBaoDetails.ScrollViewContainer;
import com.growuptang.pro.TaoBaoDetails.StatusBarUtil;
import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;
import com.growuptang.pro.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Main2Activity extends AppCompatActivity implements GradationScrollView.ScrollViewListener{

    @BindView(R.id.iv_good_detai_back)
    ImageView iv_good_detai_back;
    @BindView(R.id.scrollview)
    GradationScrollView scrollView;
    @BindView(R.id.iv_good_detai_img)
    ImageView iv;
    @BindView(R.id.ll_good_detail)
    RelativeLayout llTitle;
    @BindView(R.id.ll_offset)
    LinearLayout llOffset;
    @BindView(R.id.iv_good_detai_collect_select)
    ImageView ivCollectSelect;//收藏选中
    @BindView(R.id.iv_good_detai_collect_unselect)
    ImageView ivCollectUnSelect;//收藏未选中
    @BindView(R.id.sv_container)
    ScrollViewContainer container;
    @BindView(R.id.tv_good_detail_title_good)
    TextView tvGoodTitle;
    @BindView(R.id.nlv_good_detial_imgs)
    NoScrollListView nlvImgs;//图片详情
    private QuickAdapter<String> imgAdapter;
    private List<String> imgsUrl;

    private int height;
    private int width;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main2);
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);
        //透明状态栏
        StatusBarUtil.setTranslucentForImageView(this,llOffset);
        LinearLayout.LayoutParams params1 = (LinearLayout.LayoutParams) llOffset.getLayoutParams();
        params1.setMargins(0,-StatusBarUtil.getStatusBarHeight(this)/4,0,0);
        llOffset.setLayoutParams(params1);

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv.getLayoutParams();
        params.height = getScreenHeight(this)*2/3;
        iv.setLayoutParams(params);

        container = new ScrollViewContainer(getApplicationContext());

        iv_good_detai_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initImgDatas();

        initListeners();
    }



    public  int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    // TODO: 16/8/21 模拟图片假数据
    private void initImgDatas(){
        width = getScreenWidth(getApplicationContext());
        imgsUrl = new ArrayList<>();
        imgsUrl.add("https://img.alicdn.com/imgextra/i4/714288429/TB2dLhGaVXXXXbNXXXXXXXXXXXX-714288429.jpg");
        imgsUrl.add("https://img.alicdn.com/imgextra/i3/726966853/TB2vhJ6lXXXXXbJXXXXXXXXXXXX_!!726966853.jpg");
        imgsUrl.add("https://img.alicdn.com/imgextra/i4/2081314055/TB2FoTQbVXXXXbuXpXXXXXXXXXX-2081314055.png");
        imgAdapter = new QuickAdapter<String>(this,R.layout.adapter_good_detail_imgs) {
            @Override
            protected void convert(BaseAdapterHelper helper, String item) {
                ImageView iv = helper.getView(R.id.iv_adapter_good_detail_img);
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv.getLayoutParams();
                params.width = width;
                params.height = width/2;
                iv.setLayoutParams(params);
                MyImageLoader.getInstance().displayImageCen(getApplicationContext(),item,iv,width,width/2);
            }
        };
        imgAdapter.addAll(imgsUrl);
        nlvImgs.setAdapter(imgAdapter);
    }

    private void initListeners() {

        ViewTreeObserver vto = iv.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                llTitle.getViewTreeObserver().removeGlobalOnLayoutListener(
                        this);
                height = iv.getHeight();

                scrollView.setScrollViewListener(Main2Activity.this);
            }
        });
    }

    /**
     * 滑动监听
     * @param scrollView
     * @param x
     * @param y
     * @param oldx
     * @param oldy
     */
    @Override
    public void onScrollChanged(GradationScrollView scrollView, int x, int y,
                                int oldx, int oldy) {
        // TODO Auto-generated method stub
        if (y <= 0) {   //设置标题的背景颜色
            llTitle.setBackgroundColor(Color.argb((int) 0, 255,255,255));
        } else if (y > 0 && y <= height) { //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
            float scale = (float) y / height;
            float alpha = (255 * scale);
            tvGoodTitle.setTextColor(Color.argb((int) alpha, 1,24,28));
            llTitle.setBackgroundColor(Color.argb((int) alpha, 255,255,255));
        } else {    //滑动到banner下面设置普通颜色
            llTitle.setBackgroundColor(Color.argb((int) 255, 255,255,255));
        }
    }
}

