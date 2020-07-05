package com.example.cybercop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class TwoColumListadapter extends ArrayAdapter<message> {

    private LayoutInflater inflater;
    private ArrayList<message> messages;
    private int resourceid;
    public TwoColumListadapter(Context context, int textViewResourceId, ArrayList<message> msg) {
        super(context, textViewResourceId, msg);
        this.messages = msg;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        resourceid = textViewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(resourceid, null);

        message m = messages.get(position);

        if (m != null) {
            TextView from = (TextView) convertView.findViewById(R.id.from);
            TextView msg = (TextView) convertView.findViewById(R.id.message);
            if (from != null) {
                from.setText(m.getMsgFrom());
            }
            if (msg != null) {
                msg.setText(m.getMessage());
            }

        }

        return convertView;
    }
}
