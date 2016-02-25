package com.atp.wash;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.Locale;

/**
 * Created by khiem on 2/25/16.
 */
public class MainFragment extends Fragment implements View.OnClickListener{
    View rootView;
    ImageView ws1, ws2, ws3, ws4, ws5, ws6, ws7, ws8;
    public MainFragment() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main, container, false);
        initView();
        return rootView;
    }

    private void initView() {
        ws1 = (ImageView) rootView.findViewById(R.id.ws_1);
        ws2 = (ImageView) rootView.findViewById(R.id.ws_2);
        ws3 = (ImageView) rootView.findViewById(R.id.ws_3);
        ws4 = (ImageView) rootView.findViewById(R.id.ws_4);
        ws5 = (ImageView) rootView.findViewById(R.id.ws_5);
        ws6 = (ImageView) rootView.findViewById(R.id.ws_6);
        ws7 = (ImageView) rootView.findViewById(R.id.ws_7);
        ws8 = (ImageView) rootView.findViewById(R.id.ws_8);

        ws1.setOnClickListener(this);
        ws2.setOnClickListener(this);
        ws3.setOnClickListener(this);
        ws4.setOnClickListener(this);
        ws5.setOnClickListener(this);
        ws6.setOnClickListener(this);
        ws7.setOnClickListener(this);
        ws8.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ws_1:
                ws1.setImageResource(R.drawable.washing_machine);
                break;
            case R.id.ws_2:
                ws2.setImageResource(R.drawable.washing_machine);
                break;
            case R.id.ws_3:
                ws3.setImageResource(R.drawable.washing_machine);
                break;
            case R.id.ws_4:
                ws4.setImageResource(R.drawable.washing_machine);
                break;
            case R.id.ws_5:
                ws5.setImageResource(R.drawable.washing_machine);
                break;
            case R.id.ws_6:
                ws6.setImageResource(R.drawable.washing_machine);
                break;
            case R.id.ws_7:
                ws7.setImageResource(R.drawable.washing_machine);
                break;
            case R.id.ws_8:
                ws8.setImageResource(R.drawable.washing_machine);
                break;
        }
    }
}
