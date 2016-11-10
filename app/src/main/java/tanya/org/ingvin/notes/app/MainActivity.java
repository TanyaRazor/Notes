package tanya.org.ingvin.notes.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    static final private int ADD_NOTE = 1;
    List<String> notes = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view){
        Intent intent = new Intent(MainActivity.this,AddNote.class);
        startActivityForResult(intent,ADD_NOTE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ListView listView = (ListView)findViewById(R.id.listView);


        if (requestCode==ADD_NOTE){
            if (resultCode==RESULT_OK){
                String content = data.getStringExtra(AddNote.CONTENT);
                String desc = data.getStringExtra(AddNote.DESC);
            }
        }
    }
}
