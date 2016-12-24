package tanya.org.ingvin.notes.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import static android.R.drawable.*;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by tanya on 10.11.16.
 */
public class NotesAdapter extends BaseAdapter {
    final static String NIGHT_THEME="night_theme";
    SharedPreferences sp;
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<Notes> notesArrayList;

    NotesAdapter(Context context, ArrayList<Notes> notes){
        ctx = context;
        notesArrayList = notes;

        Collections.sort(notes, new Comparator<Notes>() {
            @Override
            public int compare(Notes t1, Notes t2) {
                SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

                Date data1 = new Date();
                Date data2 = new Date();
                try {
                    data1 = format.parse(t1.createData);
                    data2 = format.parse(t2.createData);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return data1.compareTo(data2);
            }
        });

        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return notesArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return notesArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        sp = ctx.getSharedPreferences("NightTheme", MODE_PRIVATE);
        String check_night_theme = sp.getString(NIGHT_THEME, "");

        View view = convertView;
        if (view == null){
            view = lInflater.inflate(R.layout.item, viewGroup, false);
        }
        Notes n = getNotes(i);

        GridLayout gl = (GridLayout) view.findViewById(R.id.gl);

        TextView content_view = (TextView) view.findViewById(R.id.content_item_view);
        TextView data_view = (TextView) view.findViewById(R.id.data_view);
        content_view.setText(n.content);
        data_view.setText(n.createData);

        ImageButton editButton = (ImageButton) view.findViewById(R.id.editButton);
        ImageButton deleteButton = (ImageButton)view.findViewById(R.id.deleteButton);
        ImageButton execButton = (ImageButton)view.findViewById(R.id.execButton);

        editButton.setTag(i);
        deleteButton.setTag(i);
        execButton.setTag(i);

        editButton.setFocusable(false);
        deleteButton.setFocusable(false);
        execButton.setFocusable(false);

        if (check_night_theme.contentEquals("night")){
            content_view.setTextColor(ContextCompat.getColor(ctx, R.color.white));
            data_view.setTextColor(ContextCompat.getColor(ctx, R.color.white));
            gl.setBackground(ContextCompat.getDrawable(ctx, alert_dark_frame));
        }else if (check_night_theme.contentEquals("default")){
            content_view.setTextColor(ContextCompat.getColor(ctx, R.color.black));
            data_view.setTextColor(ContextCompat.getColor(ctx, R.color.black));
            gl.setBackground(ContextCompat.getDrawable(ctx, alert_light_frame));
        }


        return view;
    }

    Notes getNotes(int position){
        return ((Notes)getItem(position));
    }

}
