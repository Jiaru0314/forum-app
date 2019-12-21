package com.jit.avtivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jit.R;
import com.jit.fragment.RecommendFragment;
import com.jit.fragment.UserFragment;
import com.jit.fragment.UsersFragment;

import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContainerActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.linear_head)
    LinearLayout linearLayout;
    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    @BindView(R.id.search_view)
    SearchView searchView;
    UsersFragment usersFragment = new UsersFragment();
    RecommendFragment recommendFragment = new RecommendFragment();
    UserFragment userFragment = new UserFragment();

    //默认选择第一个fragment
    private int lastSelectedPosition = 0;
    private Fragment[] fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        ButterKnife.bind(this);
        initFragments();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(ContainerActivity.this, ResultActivity.class);
                intent.putExtra("query", query);
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void initFragments() {
        //监听切换事件
        navigation.setOnNavigationItemSelectedListener(this);
        //平均布局
        setItemType(navigation);

        fragments = new Fragment[]{recommendFragment, usersFragment, userFragment};
        //默认提交第一个
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fl_main, recommendFragment)//添加
                .commit();//提交
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        linearLayout.setVisibility(View.VISIBLE);
        switch (menuItem.getItemId()) {
            case R.id.navigation_recommend:
                if (0 != lastSelectedPosition) {
                    setDefaultFragment(lastSelectedPosition, 0);
                    lastSelectedPosition = 0;
                }
                return true;
            case R.id.navigation_users:
                if (1 != lastSelectedPosition) {
                    setDefaultFragment(lastSelectedPosition, 1);
                    lastSelectedPosition = 1;
                }
                return true;
            case R.id.navigation_me:
                if (2 != lastSelectedPosition) {
                    linearLayout.setVisibility(View.GONE);
                    setDefaultFragment(lastSelectedPosition, 2);
                    lastSelectedPosition = 2;
                }
                return true;
        }
        return false;
    }

    private void setDefaultFragment(int lastIndex, int index) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(fragments[lastIndex]);
        if (!fragments[index].isAdded()) {
            transaction.add(R.id.fl_main, fragments[index]);
        }
        //需要展示fragment下标的位置
        //commit：安排该事务的提交。这一承诺不会立即发生;它将被安排在主线程上，以便在线程准备好的时候完成。
        //commitAllowingStateLoss：与 commit类似，但允许在活动状态保存后执行提交。这是危险的，因为如果Activity需要从其状态恢复，
        // 那么提交就会丢失，因此，只有在用户可以意外地更改UI状态的情况下，才可以使用该提交
        transaction.show(fragments[index]).commit();
    }

    /**
     * 防止超过3个fragment布局不平分
     */
    @SuppressLint("RestrictedApi")
    private void setItemType(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                //noinspection RestrictedApi
                item.setShifting(false);
                // set once again checked value, so view will be updated
                //noinspection RestrictedApi
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("BNVHelper", "Unable to get shift mode field", e);
        } catch (IllegalAccessException e) {
            Log.e("BNVHelper", "Unable to change value of shift mode", e);
        }
    }

    @OnClick(R.id.toNewBlog)
    public void toNewBlog(View view) {
        startActivity(new Intent(this, NewBlogActivity.class));
    }
}
