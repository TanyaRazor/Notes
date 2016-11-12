package tanya.org.ingvin.notes.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by tanya on 11.11.16.
 */
public class ShowNote extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_note);

        Intent intent = getIntent();
        String content = intent.getStringExtra("SHOW_CONTENT");
        String desc = intent.getStringExtra("SHOW_DESC");
        String show_data = intent.getStringExtra("SHOW_DATA");

        ((TextView)findViewById(R.id.show_content_view)).setText(content);
        ((TextView)findViewById(R.id.show_desc_view)).setText(desc);
        ((TextView)findViewById(R.id.show_data_view)).setText(show_data);
   }
}
