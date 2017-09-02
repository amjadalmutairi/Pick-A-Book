package me.amjadalmutairi.pickabook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity {
    ImageButton search;
    EditText searchKeys;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        search = (ImageButton) findViewById(R.id.search_button);
        searchKeys = (EditText) findViewById(R.id.search_key);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!searchKeys.getText().toString().trim().equals("")) {
                    Intent intent = new Intent(MainActivity.this, BookActivity.class);
                    intent.putExtra("keys", searchKeys.getText().toString());
                    searchKeys.setText("");
                    startActivity(intent);
                } else {
                    Toasty.error(MainActivity.this, getString(R.string.topic_error), Toast.LENGTH_SHORT, true).show();
                }
            }
        });
    }
}
