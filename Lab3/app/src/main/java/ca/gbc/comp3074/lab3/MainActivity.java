package ca.gbc.comp3074.lab3;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private Button btn_activity, btn_alarm, btn_map, btn_browser, btn_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btn_activity = findViewById(R.id.btn_activity);
        btn_activity.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openActivity();
            }
        });
        btn_alarm= findViewById(R.id.btn_alarm);
        btn_alarm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                setAlarm();
            }
        });
        btn_browser = findViewById(R.id.btn_browser);
        btn_browser.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openBrowser();
            }
        });
        btn_map = findViewById(R.id.btn_map);
        btn_map.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                openMap();
            }
        });
        btn_image = findViewById(R.id.btn_image);
        btn_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureImage();
            }
        });
    }

    private void openActivity(){
        Intent i = new Intent(this, MyActivity2.class);
        startActivity(i);
    }
    private void setAlarm(){
        Intent i = new Intent(AlarmClock.ACTION_SET_ALARM);
        /*i.putExtra(AlarmClock.EXTRA_HOUR, 11);*/
        i.putExtra(AlarmClock.EXTRA_MINUTES, 1);
        i.putExtra(AlarmClock.EXTRA_MESSAGE, "TimeIsUp");
        i.putExtra(AlarmClock.EXTRA_SKIP_UI,false);
        startActivity(i);
    }
    private void openBrowser(){
        Uri page= Uri.parse("http://georgebrown.ca");
        Intent i = new Intent(Intent.ACTION_VIEW, page);
        startActivity(i);
    }
    private void openMap(){
        Uri page= Uri.parse("geo:0,0?q=160+kendal+ave+toronto");
        Intent i = new Intent(Intent.ACTION_VIEW, page);
        startActivity(i);
    }
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void captureImage(){
        Intent i =  new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(i, REQUEST_IMAGE_CAPTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            ImageView img = findViewById(R.id.imageView);
            Bitmap bitmap = data.getParcelableExtra("data");
            img.setImageBitmap(bitmap);
        }else {
            super.onActivityResult(requestCode, resultCode, data);
        }

    }
}