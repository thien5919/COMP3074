package ca.gbc.comp3074.lab4;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView name;
    EditText input;
    Button button;
    ListView list;

    SharedPreferences preferences;
    private final static String KEY_NAME= "name";
    String[] data= {"one", "two", "three"};
    ArrayList<String> list_data = new ArrayList<>();

    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        for (int i = 0; i < data.length; i++){
            list_data.add(data[i]);
        }

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) ->{
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        name = findViewById(R.id.name);
        input = findViewById(R.id.input);
        button =findViewById(R.id.button);
        list = findViewById(R.id.list);

        dbHandler= new DBHandler(this);

        list_data= dbHandler.getAllItems();

        preferences= getPreferences(Context.MODE_PRIVATE);
        String value= preferences.getString(KEY_NAME, "--Not Set---");
        name.setText(value);

        SharedPreferences.Editor editor =preferences.edit();
        editor.putString(KEY_NAME, "Thien Tran");
        editor.apply();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list_data);
        list.setAdapter(adapter);

        list.setOnItemClickListener((parent, view, position, id) -> {
            String item = list_data.get(position);
            view.animate().setDuration(2000).alpha(0).withEndAction(new Runnable() {
                @Override
                public void run() {
                    list_data.remove(item);
                    dbHandler.deleteItem(item);
                    adapter.notifyDataSetChanged();
                    view.setAlpha(1);
                }
            });
        });
        button.setOnClickListener(v ->{
            String item = input.getText().toString();
            if (item != null && item.length() > 0 ){
                list_data.add(item);
                adapter.notifyDataSetChanged();
                input.setText("");

                dbHandler.addItem(item);
            }
        });
    }
    /*button.setOnClickListenner(v ->{
        String item = input.getText().toString();
        if (item !=null && item.length() > 8){
            list_data.add(item);
            adapter.notifyDataSetChanged();
        }
    })*/
}