package tanya.org.ingvin.notes.app;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

public class MainActivity extends TabActivity {

    final String TABS_TAG_1 = "Tag 1";
    final String TABS_TAG_2 = "Tag 2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabHost tabHost = getTabHost();

        TabHost.TabSpec tabSpec;

        tabSpec = tabHost.newTabSpec(TABS_TAG_1);
        tabSpec.setIndicator("Заметки");
        tabSpec.setContent(new Intent(this,AllNotes.class));

        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec(TABS_TAG_2);
        tabSpec.setContent(new Intent(this,ExecNotes.class));
        tabSpec.setIndicator("Выполнено");
        tabHost.addTab(tabSpec);

    }
}
