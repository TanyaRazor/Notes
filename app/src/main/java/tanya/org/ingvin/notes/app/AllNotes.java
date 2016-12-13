package tanya.org.ingvin.notes.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by tanya on 11.11.16.
 */
public class AllNotes extends Activity {
    static final private int ADD_NOTE = 1;
    static final private int EDIT_NOTE = 2;

    SharedPreferences sPref;
    final String SIZE_NOTES = "size";
    final String SAVED_CONTENT = "content";
    final String SAVED_DESC = "desc";
    final String SAVED_DATA = "data";

    final String SAVED_EXEC_CONTENT = "content";
    final String SAVED_EXEC_DESC = "desc";
    final String SAVED_EXEC_DATA = "data";

    final int MENU_EDIT_NOTE = 1;
    final int MENU_DELETE_NOTE = 2;
    final int MENU_EXEC_NOTE = 3;

    ListView listView;
    NotesAdapter adapter;
    ArrayList<Notes> notes = new ArrayList<Notes>();

    AlertDialog.Builder deleteDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab1_list_note);


        listView = (ListView) findViewById(R.id.listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                Intent intent = new Intent(AllNotes.this, ShowNote.class);

                intent.putExtra("SHOW_CONTENT", notes.get(i).content.toString());
                intent.putExtra("SHOW_DESC", notes.get(i).desc.toString());
                intent.putExtra("SHOW_DATA", notes.get(i).createData.toString());
                startActivity(intent);
            }
        });

        registerForContextMenu(listView);

        loadNotes(this);
    }

    public void onAddClick(View view) {
        Intent intent = new Intent(AllNotes.this, AddNote.class);
        startActivityForResult(intent, ADD_NOTE);
    }

    public void onEditClick(View view) {
        Intent intent = new Intent(AllNotes.this, EditNote.class);

        String posit = view.getTag().toString();
        int pos = Integer.parseInt(posit);

        intent.putExtra("CONTENT", notes.get(pos).content.toString());
        intent.putExtra("DESC", notes.get(pos).desc.toString());
        intent.putExtra("CREATE_DATA", notes.get(pos).createData.toString());
        startActivityForResult(intent, EDIT_NOTE);

        notes.remove(pos);
    }

    public void onExecClick(View view) {
        String posit = view.getTag().toString();
        int pos = Integer.parseInt(posit);

        sPref = getSharedPreferences("ClickExecNote", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();

        ed.putString(SAVED_EXEC_CONTENT, notes.get(pos).content);
        ed.putString(SAVED_EXEC_DESC, notes.get(pos).desc);
        ed.putString(SAVED_EXEC_DATA, notes.get(pos).createData);

        ed.commit();

        notes.remove(pos);
        adapter.notifyDataSetChanged();

        Toast.makeText(this, R.string.exec, Toast.LENGTH_SHORT).show();
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
        if (requestCode == EDIT_NOTE) {
            if (resultCode == RESULT_OK) {
                String content = data.getStringExtra(EditNote.CONTENT);
                String desc = data.getStringExtra(EditNote.DESC);
                String dataOfNote = data.getStringExtra(EditNote.DATA);

                notes.add(new Notes(content, desc, dataOfNote));

                adapter = new NotesAdapter(this, notes);

                listView.setAdapter(adapter);
            }
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;

        menu.setHeaderTitle(notes.get(info.position).content);
        menu.add(Menu.NONE, MENU_EDIT_NOTE, Menu.NONE, R.string.edit_note);
        menu.add(Menu.NONE, MENU_DELETE_NOTE, Menu.NONE, R.string.delete_note);
        menu.add(Menu.NONE, MENU_EXEC_NOTE, Menu.NONE, R.string.exec_note);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info;
        final int pos;
        switch (item.getItemId()) {
            case MENU_EDIT_NOTE:
                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                pos = info.position;

                Intent intent = new Intent(AllNotes.this, EditNote.class);
                intent.putExtra("CONTENT", notes.get(pos).content.toString());
                intent.putExtra("DESC", notes.get(pos).desc.toString());
                intent.putExtra("CREATE_DATA", notes.get(pos).createData.toString());
                startActivityForResult(intent, EDIT_NOTE);

                notes.remove(pos);
                break;
            case MENU_DELETE_NOTE:
                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                pos = info.position;

                deleteDialog = new AlertDialog.Builder(this);
                deleteDialog.setTitle(R.string.delete_title);
                deleteDialog.setMessage(R.string.delete_mess);
                deleteDialog.setPositiveButton(R.string.pos_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        notes.remove(pos);
                        adapter.notifyDataSetChanged();
                    }
                }).setNegativeButton(R.string.neg_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                }).create().show();
                break;
            case MENU_EXEC_NOTE:
                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                pos = info.position;

                sPref = getSharedPreferences("ClickExecNote", MODE_PRIVATE);
                SharedPreferences.Editor ed = sPref.edit();

                ed.putString(SAVED_EXEC_CONTENT, notes.get(pos).content);
                ed.putString(SAVED_EXEC_DESC, notes.get(pos).desc);
                ed.putString(SAVED_EXEC_DATA, notes.get(pos).createData);

                ed.commit();

                notes.remove(pos);
                adapter.notifyDataSetChanged();

                Toast.makeText(this, R.string.exec, Toast.LENGTH_SHORT).show();
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

            notes.add(new Notes(savedContent, savedDesc, savedData));
        }

        adapter = new NotesAdapter(ctx, notes);
        listView.setAdapter(adapter);
    }

    protected void saveNotes() {
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();

        ed.putInt(SIZE_NOTES, notes.size());

        for (int i = 0; i < notes.size(); i++) {
            ed.putString(SAVED_CONTENT + i, notes.get(i).content);
            ed.putString(SAVED_DESC + i, notes.get(i).desc);
            ed.putString(SAVED_DATA + i, notes.get(i).createData);
        }

        ed.commit();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveNotes();
    }

    public void onDeleteClick(View view) {
        String posit = view.getTag().toString();
        final int pos = Integer.parseInt(posit);

        deleteDialog = new AlertDialog.Builder(this);
        deleteDialog.setTitle(R.string.delete_title);
        deleteDialog.setMessage(R.string.delete_mess);
        deleteDialog.setPositiveButton(R.string.pos_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                notes.remove(pos);
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
