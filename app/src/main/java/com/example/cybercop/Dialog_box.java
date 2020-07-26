package com.example.cybercop;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import androidx.fragment.app.DialogFragment;

public class Dialog_box extends DialogFragment {
    TextView textview,textview2,textview3,textview4,textview5,textview6,textview7;
    @Override
    public android.app.Dialog onCreateDialog( Bundle savedInstanceState) {
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog,null);
        Bundle bundle=getArguments();
        String status=bundle.getString("TEXT","");
        String g=bundle.getString("HEADING","");
        String premise=bundle.getString("TEXT2","");
        String weapon=bundle.getString("weapon","");
        String crime=bundle.getString("crime","");
        String area=bundle.getString("area","");
        String gender=bundle.getString("gender","");
        textview=(TextView)view.findViewById(R.id.textView2);
        textview2=(TextView)view.findViewById(R.id.textView1);
        textview3=(TextView)view.findViewById(R.id.textView3);
        textview4=(TextView)view.findViewById(R.id.textView4);
        textview5=(TextView)view.findViewById(R.id.textView5);
        textview6=(TextView)view.findViewById(R.id.textView6);
        textview7=(TextView)view.findViewById(R.id.textView7);
        textview.setText("Premise Description: "+premise);
        textview2.setText(g);
        textview5.setText("Status Description: "+status);
        textview4.setText("Weapon Description: "+weapon);
        textview3.setText("Area Name: "+area);
        textview6.setText("Crime Description: "+crime);
        textview7.setText("Victim Gender: "+gender);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);

        return builder.create();
    }
}
