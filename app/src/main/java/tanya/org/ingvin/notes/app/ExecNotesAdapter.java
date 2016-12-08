package tanya.org.ingvin.notes.app;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by tanya on 10.11.16.
 */
public class ExecNotesAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<Notes> notesArrayList;

    ExecNotesAdapter(Context context, ArrayList<Notes> notes){
        ctx = context;
        notesArrayList = notes;

        Collections.sort(notes, new Comparator<Notes>() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
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

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        if (view == null){
            view = lInflater.inflate(R.layout.exec_item, viewGroup, false);
        }
        Notes n = getNotes(i);

        ((TextView) view.findViewById(R.id.content_item_view)).setText(n.content);
        ((TextView) view.findViewById(R.id.data_view)).setText(n.createData);

        Button deleteButton = (Button)view.findViewById(R.id.deleteButton);
        deleteButton.setTag(i);
        deleteButton.setFocusable(false);

        return view;
    }

    Notes getNotes(int position){
        return ((Notes)getItem(position));
    }

}

