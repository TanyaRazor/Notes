package tanya.org.ingvin.notes.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
    SharedPreferences sPref;
    final String SIZE_NOTES = "size";
    final String SAVED_CONTENT = "content";
    final String SAVED_DESC = "desc";;
    final String SAVED_DATA = "data";

    ListView listView;
    NotesAdapter adapter;
    ArrayList<Notes> notes = new ArrayList<Notes>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab1_list_note);


        listView = (ListView) findViewById(R.id.listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(AllNotes.this, ShowNote.class);

                intent.putExtra("SHOW_CONTENT", notes.get(i).content.toString());
                intent.putExtra("SHOW_DESC", notes.get(i).desc.toString());
                intent.putExtra("SHOW_DATA", notes.get(i).createData.toString());
                startActivity(intent);
            }

        });

        loadNotes(this);
    }

    public void onClick(View view) {
        Intent intent = new Intent(AllNotes.this, AddNote.class);
        startActivityForResult(intent, ADD_NOTE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_NOTE) {
            if (resultCode == RESULT_OK) {
                String content = data.getStringExtra(AddNote.CONTENT);
                String desc = data.getStringExtra(AddNote.DESC);
                String dataOfNote = data.getStringExtra(AddNote.DATA);

                notes.add(new Notes(content, desc, dataOfNote));

                adapter = new NotesAdapter(this, notes);

                listView.setAdapter(adapter);
            }
        }
    }


    private void loadNotes(Context ctx) {
        sPref = getPreferences(MODE_PRIVATE);
        int size = sPref.getInt(SIZE_NOTES, 0);

        for (int i = 0; i < size; i++) {
            String savedContent = sPref.getString(SAVED_CONTENT + i, "");
            String savedDesc = sPref.getString(SAVED_DESC + i, "");
            String savedData = sPref.getString(SAVED_DATA + i, "");

            notes.add(new Notes(savedContent,savedDesc,savedData));
        }

        adapter = new NotesAdapter(ctx,notes);
        listView.setAdapter(adapter);
    }

    protected void saveNotes() {
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();

        ed.putInt(SIZE_NOTES, notes.size());

        for (int i = 0; i < notes.size(); i++) {
            ed.putString(SAVED_CONTENT + i,notes.get(i).content.toString());
            ed.putString(SAVED_DESC + i,notes.get(i).desc.toString());
            ed.putString(SAVED_DATA + i,notes.get(i).createData.toString());
        }

        ed.commit();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveNotes();
    }
}
