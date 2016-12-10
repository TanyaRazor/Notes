package tanya.org.ingvin.notes.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
public class ExecNotes extends Activity {

    SharedPreferences sPref;
    final String SIZE_NOTES = "size";
    final String SAVED_CONTENT = "content";
    final String SAVED_DESC = "desc";
    final String SAVED_DATA = "data";

    final String SAVED_EXEC_CONTENT = "content";
    final String SAVED_EXEC_DESC = "desc";
    final String SAVED_EXEC_DATA = "data";

    ListView listView;
    ExecNotesAdapter adapter;
    ArrayList<Notes> execNotes = new ArrayList<Notes>();

    AlertDialog.Builder deleteDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab2_list_note);

        listView = (ListView) findViewById(R.id.listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                Intent intent = new Intent(ExecNotes.this, ShowNote.class);

                intent.putExtra("SHOW_CONTENT", execNotes.get(i).content);
                intent.putExtra("SHOW_DESC", execNotes.get(i).desc);
                intent.putExtra("SHOW_DATA", execNotes.get(i).createData);
                startActivity(intent);
            }
        });

        loadNotes(this);
    }

    private void loadNotes(Context ctx) {
        sPref = getPreferences(MODE_PRIVATE);

        int size = sPref.getInt(SIZE_NOTES, 0);

        for (int i = 0; i < size; i++) {
            String savedContent = sPref.getString(SAVED_CONTENT + i, "");
            String savedDesc = sPref.getString(SAVED_DESC + i, "");
            String savedData = sPref.getString(SAVED_DATA + i, "");

            execNotes.add(new Notes(savedContent, savedDesc, savedData));
        }

        adapter = new ExecNotesAdapter(ctx, execNotes);
        listView.setAdapter(adapter);
    }

    private void loadExecNotes(Context ctx) {
        sPref = getSharedPreferences("ClickExecNote", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();

        if (!(sPref.getAll().isEmpty())) {
            String savedExecContent = sPref.getString(SAVED_EXEC_CONTENT, "");
            String savedExecDesc = sPref.getString(SAVED_EXEC_DESC, "");
            String savedExecData = sPref.getString(SAVED_EXEC_DATA, "");
            execNotes.add(new Notes(savedExecContent,savedExecDesc,savedExecData));
        }
        adapter = new ExecNotesAdapter(ctx, execNotes);
        listView.setAdapter(adapter);

        ed.clear();
        ed.commit();
    }

    protected void saveNotes() {
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();

        ed.putInt(SIZE_NOTES, execNotes.size());

        for (int i = 0; i < execNotes.size(); i++) {
            ed.putString(SAVED_CONTENT + i, execNotes.get(i).content);
            ed.putString(SAVED_DESC + i, execNotes.get(i).desc);
            ed.putString(SAVED_DATA + i, execNotes.get(i).createData);
        }

        ed.commit();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveNotes();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadExecNotes(this);
    }

    public void onDeleteClick(View view) {
        String posit = view.getTag().toString();
        final int pos = Integer.parseInt(posit);

        deleteDialog = new AlertDialog.Builder(this);
        deleteDialog.setTitle("Delete");
        deleteDialog.setMessage("Are you sure?");
        deleteDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                execNotes.remove(pos);
                adapter.notifyDataSetChanged();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        }).create().show();
    }
}
