package tanya.org.ingvin.notes.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by tanya on 10.11.16.
 */
public class NotesAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<Notes> notesArrayList;

    NotesAdapter(Context context, ArrayList<Notes> notes){
        ctx = context;
        notesArrayList = notes;
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
            view = lInflater.inflate(R.layout.item, viewGroup, false);
        }
        Notes n = getNotes(i);

        ((TextView) view.findViewById(R.id.content_item_view)).setText(n.content);
        ((TextView) view.findViewById(R.id.desc_item_view)).setText(n.desc);

        return view;
    }

    Notes getNotes(int position){
        return ((Notes)getItem(position));
    }

}
