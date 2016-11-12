package tanya.org.ingvin.notes.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by tanya on 11.11.16.
 */
public class AllNotes extends Activity {
    static final private int ADD_NOTE = 1;

    ListView listView;
    NotesAdapter adapter;
    ArrayList<Notes> notes = new ArrayList<Notes>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab1_list_note);

        listView = (ListView)findViewById(R.id.listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(AllNotes.this,ShowNote.class);

                intent.putExtra("SHOW_CONTENT", notes.get(i).content.toString());
                intent.putExtra("SHOW_DESC", notes.get(i).desc.toString());
                intent.putExtra("SHOW_DATA", notes.get(i).createData.toString());
                startActivity(intent);
            }
        });
    }

    public void onClick(View view){
        Intent intent = new Intent(AllNotes.this,AddNote.class);
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
