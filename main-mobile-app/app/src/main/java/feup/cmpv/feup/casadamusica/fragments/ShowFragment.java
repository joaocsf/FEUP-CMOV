package feup.cmpv.feup.casadamusica.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import feup.cmpv.feup.casadamusica.R;

public class ShowFragment extends Fragment {

    private static Fragment instance;

    public static Fragment getInstance(String value) {
        Fragment fragment = new ShowFragment();
        Bundle b = new Bundle();
        b.putString("value", value);
        fragment.setArguments(b);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.show_fragment, container,false);
        InitializeView(view);
        return view;
    }

    private void InitializeView(View view){
        TextView tv = view.findViewById(R.id.textView2);
        tv.setText(getArguments().getString("value"));
    }
}
