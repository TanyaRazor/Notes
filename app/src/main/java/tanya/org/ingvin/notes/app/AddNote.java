package tanya.org.ingvin.notes.app;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by tanya on 09.11.16.
 */
public class AddNote extends Activity {
    public final static String CONTENT = "CONTENT";
    public static final String DESC = "DESC";
    public final static String DATA = "DATA";

    Calendar dateOfCreateNote = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temp_note);
    }

    public void saveClick(View view) {
        Intent intent = new Intent();

        String content = ((EditText)findViewById(R.id.contentNote)).getText().toString();
        String desc = ((EditText)findViewById(R.id.descNote)).getText().toString();
        String createDataView = ((TextView)findViewById(R.id.createDataView)).getText().toString();

        intent.putExtra(CONTENT,content);
        intent.putExtra(DESC, desc);
        intent.putExtra(DATA, createDataView);

        setResult(RESULT_OK, intent);
        finish();
    }

    public void onClickDateButton(View view) {
        new DatePickerDialog(AddNote.this,d,
                dateOfCreateNote.get(Calendar.YEAR),
                dateOfCreateNote.get(Calendar.MONTH),
                dateOfCreateNote.get(Calendar.DAY_OF_MONTH)).show();
    }

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener(){
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){
            dateOfCreateNote.set(Calendar.YEAR, year);
            dateOfCreateNote.set(Calendar.MONTH, monthOfYear);
            dateOfCreateNote.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            TextView createData = ((TextView) findViewById(R.id.createDataView));
            createData.setText((dayOfMonth + "." + (monthOfYear+1) + "." + year).toString());
        }
    };
}
