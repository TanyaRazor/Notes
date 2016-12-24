package tanya.org.ingvin.notes.app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by tanya on 11.11.16.
 */
public class ExecNotes extends AppCompatActivity {
    final static String NIGHT_THEME="night_theme";
    SharedPreferences sp;

    SharedPreferences sPref;
    final String SIZE_NOTES = "size";
    final String SAVED_CONTENT = "content";
    final String SAVED_DESC = "desc";
    final String SAVED_DATA = "data";

    final String SAVED_EXEC_CONTENT = "content";
    final String SAVED_EXEC_DESC = "desc";
    final String SAVED_EXEC_DATA = "data";

    final int MENU_DELETE_NOTE = 1;

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

        registerForContextMenu(listView);

        loadNotes(this);
    }

    private void getPrefs(){
        sp = getSharedPreferences("NightTheme", MODE_PRIVATE);

        String check_night_theme = sp.getString(NIGHT_THEME, "");

        RelativeLayout rl = (RelativeLayout) findViewById(R.id.relativeLayout);

        if (check_night_theme.contentEquals("night")){
            rl.setBackgroundColor(ContextCompat.getColor(ExecNotes.this, R.color.black));
        }else if (check_night_theme.contentEquals("default")){
            rl.setBackgroundColor(ContextCompat.getColor(ExecNotes.this, R.color.white));
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;

        menu.setHeaderTitle(execNotes.get(info.position).content);
        menu.add(Menu.NONE, MENU_DELETE_NOTE, Menu.NONE, R.string.delete_note);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info;
        final int pos;
        switch (item.getItemId()) {
            case MENU_DELETE_NOTE:
                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                pos = info.position;

                deleteDialog = new AlertDialog.Builder(this);
                deleteDialog.setTitle(R.string.delete_title);
                deleteDialog.setMessage(R.string.delete_mess);
                deleteDialog.setPositiveButton(R.string.pos_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        execNotes.remove(pos);
                        adapter.notifyDataSetChanged();
                    }
                }).setNegativeButton(R.string.neg_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                }).create().show();
                break;
        }
        return super.onContextItemSelected(item);
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
        getPrefs();
        adapter.notifyDataSetChanged();
    }

    public void onDeleteClick(View view) {
        String posit = view.getTag().toString();
        final int pos = Integer.parseInt(posit);

        deleteDialog = new AlertDialog.Builder(this);
        deleteDialog = new AlertDialog.Builder(this);
        deleteDialog.setTitle(R.string.delete_title);
        deleteDialog.setMessage(R.string.delete_mess);
        deleteDialog.setPositiveButton(R.string.pos_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                execNotes.remove(pos);
                adapter.notifyDataSetChanged();
            }
        }).setNegativeButton(R.string.neg_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        }).create().show();
    }
}
