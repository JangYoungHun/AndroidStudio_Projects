package com.example.optionmenu_practice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        actionBar = getSupportActionBar();

      // actionBar.setLogo(R.drawable.ic_launcher_foreground);
        actionBar.setTitle("Title");
      //  actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME| ActionBar.DISPLAY_USE_LOGO);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        View v = (View) menu.findItem(R.id.menu3).getActionView();
        if(v != null) {
            Button button = v.findViewById(R.id.button);
            EditText editText = v.findViewById(R.id.editText);


            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (editText != null)
                        showToast(editText.getText().toString());
                }
            });


            editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        if (editText != null)
                            showToast(editText.getText().toString());
                    }
                    return false;
                }
            });
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int currentId = item.getItemId();

        switch(currentId){

            case R.id.menu1 :
                showToast("refresh");
                break;
            case R.id.menu2 :
                showToast("search");
                break;
            case R.id.menu3 :
                showToast("setting");
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);

    }


    public void showToast(String data){
        Toast.makeText(this, data, Toast.LENGTH_SHORT).show();
    }

}