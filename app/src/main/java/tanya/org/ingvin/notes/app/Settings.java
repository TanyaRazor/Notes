package tanya.org.ingvin.notes.app;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.Toast;

/**
 * Created by tanya on 15.12.16.
 */
public class Settings extends AppCompatActivity {
    SharedPreferences sp;
    CheckBox check_night_theme;
    final static String NIGHT_THEME="night_theme";
    final static String CHECK = "checked";
    String status;
    boolean check_status;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        check_night_theme = (CheckBox)findViewById(R.id.night_theme);
        sp = getSharedPreferences("NightTheme", MODE_PRIVATE);
        check_status = sp.getBoolean(CHECK, false);

        check_night_theme.setChecked(check_status);
    }

    protected void saveSettings() {
        SharedPreferences.Editor ed = sp.edit();

        check_night_theme = (CheckBox)findViewById(R.id.night_theme);
        boolean check = check_night_theme.isChecked();

        if (check_night_theme.isChecked()){
            status = "night";
        }else{
            status = "default";
        }

        ed.putString(NIGHT_THEME,status);
        ed.putBoolean(CHECK, check);

        ed.commit();

    }

    @Override
    protected void onPause() {
        saveSettings();
        super.onPause();
    }
}
