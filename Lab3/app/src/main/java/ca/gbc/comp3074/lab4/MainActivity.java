package ca.gbc.comp3074.lab4;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

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
        Button btn = findViewById(R.id.button);
        btn.setOnClickListener(v -> openDialog());
    }
    private static final String tag = "DIALOG";
    private boolean selected[] = {false, false, false};
    private void openDialog(){
        //1
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyDialog );
        //2
        builder.setTitle("Alert!");
        //builder.setMessage("This is a dialog");

        boolean tmp[] = {false, false, false};
        for (int i=0; i<selected.length; i++){
            tmp[i] = selected[i];
        }
        builder.setMultiChoiceItems(R.array.colors,null,  (dialog, which, isChecked) ->{
            if(isChecked){
                Log.d(tag, "Item selected "+ which);
            }
            else {
                Log.d(tag, "Item removed "+ which);
            }
            Log.d(tag, "Item selected " +which);
        });

        builder.setNeutralButton("Neutral", (dialog, which)-> {
            Log.d(tag, "Button neutral");
        });
        builder.setPositiveButton("OK", (dialog, which) ->{
            Log.d(tag, "Button positive");
            for (int i=0; i<selected.length; i++){
                selected[i] = tmp[i];
            }
        } );
        builder.setNegativeButton("Cancel", (dialog, which) ->{
            Log.d(tag, "Button negative");
        });


        //3
        AlertDialog d =builder.create();
        d.setCanceledOnTouchOutside(false);
        d.show();
    }
}