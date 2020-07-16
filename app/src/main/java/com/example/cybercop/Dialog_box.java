package com.example.cybercop;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import androidx.fragment.app.DialogFragment;

public class Dialog_box extends DialogFragment {
    TextView textview,textview2,textview3;
    @Override
    public android.app.Dialog onCreateDialog( Bundle savedInstanceState) {
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog,null);
        Bundle bundle=getArguments();
        String h=bundle.getString("TEXT","");
        String g=bundle.getString("HEADING","");
        String k=bundle.getString("TEXT2","");
        textview=(TextView)view.findViewById(R.id.textView2);
        textview2=(TextView)view.findViewById(R.id.textView1);
        textview3=(TextView)view.findViewById(R.id.textView3);
        textview.setText(h);
        textview2.setText(g);
        textview3.setText(k);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);

        return builder.create();
    }
}
