package feup.cmpv.feup.casadamusica.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import feup.cmpv.feup.casadamusica.R;
import feup.cmpv.feup.casadamusica.adapters.bar.ProductListAdapter;
import feup.cmpv.feup.casadamusica.adapters.personal.TicketListAdapter;
import feup.cmpv.feup.casadamusica.adapters.personal.VoucherListAdapter;
import feup.cmpv.feup.casadamusica.structures.Order;

import static feup.cmpv.feup.casadamusica.utils.Utils.df2;
import static feup.cmpv.feup.casadamusica.utils.Utils.formatDate;

public class TicketValidationActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.nfc_receive_activity);
        TextView tv = findViewById(R.id.nfc_validating_order);

        Bundle extras = getIntent().getExtras();
        int validTickets = extras.getInt("tickets");
        tv.setText(validTickets + " Valid Tickets");
    }
}
