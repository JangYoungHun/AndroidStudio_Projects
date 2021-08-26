package com.example.viewpager;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter extends FragmentStateAdapter {


    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch(position){

            case 0 : return Fragment1.getInstance();
            case 1 : return Fragment2.getInstance();
            case 2 : return Fragment3.getInstance();
            case 3 : return Fragment4.getInstance();
            default:  return null;
        }
    }

    @Override
    public int getItemCount() {
        return 4; // 개수  0~3
    }

}
