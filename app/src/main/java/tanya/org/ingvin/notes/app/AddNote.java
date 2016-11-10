package tanya.org.ingvin.notes.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * Created by tanya on 09.11.16.
 */
public class AddNote extends Activity {
    public final static String CONTENT = "CONTENT";
    public static final String DESC = "DESC";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_note);
    }

    public void saveClick(View view) {
        Intent intent = new Intent();

        String content = ((EditText)findViewById(R.id.contentNote)).getText().toString();
        String desc = ((EditText)findViewById(R.id.descNote)).getText().toString();

        intent.putExtra(CONTENT,content);
        intent.putExtra(DESC, desc);

        setResult(RESULT_OK, intent);
        finish();
    }
}
