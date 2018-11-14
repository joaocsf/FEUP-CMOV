package feup.cmpv.feup.casadamusica.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import feup.cmpv.feup.casadamusica.R;
import feup.cmpv.feup.casadamusica.adapters.bar.ProductListAdapter;
import feup.cmpv.feup.casadamusica.adapters.personal.TicketListAdapter;
import feup.cmpv.feup.casadamusica.adapters.personal.VoucherListAdapter;
import feup.cmpv.feup.casadamusica.structures.Order;
import feup.cmpv.feup.casadamusica.utils.Archive;

import static feup.cmpv.feup.casadamusica.utils.Utils.df2;
import static feup.cmpv.feup.casadamusica.utils.Utils.formatDate;

public class ConfigureHostActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.configure_host_activity);
        Bundle extras = getIntent().getExtras();

        TextInputEditText hostname = findViewById(R.id.hostname);
        Button confirm = findViewById(R.id.confirm);

        hostname.setText(Archive.getHost());

        confirm.setOnClickListener((v) -> {
            Archive.setHost(hostname.getText().toString());
            finish();
        });
    }
}
