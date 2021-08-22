package com.example.fragment_practice2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.LayoutInflater;

public class MainActivity extends AppCompatActivity implements ImageSelectionCallback {

    ListFragment listFragment;
    ImageFragment imageFragment;

    int images[] = {R.drawable.aduino,R.drawable.android,R.drawable.unity };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

   /*     FragmentManager fragmentManager = getSupportFragmentManager();
        imageFragment = (ImageFragment) fragmentManager.findFragmentById(R.id.imageFragment);
        listFragment = (ListFragment) fragmentManager.findFragmentById(R.id.listFragment);*/
        imageFragment = new ImageFragment();
        listFragment = new ListFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout1,listFragment).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout2,imageFragment).commit();

    }

    @Override
    public void onImageSelected(int position) {
        imageFragment.setImage(images[position]);
    }
}