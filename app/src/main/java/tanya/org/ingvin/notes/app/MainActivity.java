package tanya.org.ingvin.notes.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    static final private int ADD_NOTE = 1;
    ListView listView;
    NotesAdapter adapter;
    ArrayList<Notes> notes = new ArrayList<Notes>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.listView);

    }

    public void onClick(View view){
        Intent intent = new Intent(MainActivity.this,AddNote.class);
        startActivityForResult(intent,ADD_NOTE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==ADD_NOTE){
            if (resultCode==RESULT_OK){
                String content = data.getStringExtra(AddNote.CONTENT);
                String desc = data.getStringExtra(AddNote.DESC);
                String dataOfNote = data.getStringExtra(AddNote.DATA);

                notes.add(new Notes(content, desc, dataOfNote));

                adapter = new NotesAdapter(this, notes);

                listView.setAdapter(adapter);
            }
        }
    }

}
