package feup.cmpv.feup.casadamusica.fragments.settings;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import feup.cmpv.feup.casadamusica.R;
import feup.cmpv.feup.casadamusica.structures.Show;

public class UpdatePersonalInformationDialogFragment extends DialogFragment {

    public static DialogFragment getInstance(Show show) {
        return new UpdatePersonalInformationDialogFragment();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.buy_ticket_fragment, container,false);

        InitializeView();

        return view;
    }

    private void InitializeView() {

    }
}
