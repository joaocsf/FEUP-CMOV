package feup.cmpv.feup.casadamusica.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONObject;

import feup.cmpv.feup.casadamusica.R;
import feup.cmpv.feup.casadamusica.adapters.bar.ProductListAdapter;
import feup.cmpv.feup.casadamusica.adapters.personal.TicketListAdapter;
import feup.cmpv.feup.casadamusica.adapters.personal.VoucherListAdapter;
import feup.cmpv.feup.casadamusica.services.TerminalServices;
import feup.cmpv.feup.casadamusica.structures.Order;
import feup.cmpv.feup.casadamusica.utils.Archive;

import static feup.cmpv.feup.casadamusica.utils.Utils.df2;
import static feup.cmpv.feup.casadamusica.utils.Utils.formatDate;

public class TerminalLoginActivity extends AppCompatActivity {

    Button btn;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.terminal_login_activity);
        setSupportActionBar(findViewById(R.id.toolbar));

        TextInputEditText username = findViewById(R.id.username_login);
        TextInputEditText password = findViewById(R.id.password_login);

        btn = findViewById(R.id.button_login);

        btn.setOnClickListener((v) -> {
            String id = username.getText().toString();
            String pw = password.getText().toString();
            login(id, pw);
            btn.setActivated(false);
        });

    }

    private void login(String identifier, String password){
        TerminalServices.Login(identifier.trim(), password.trim(),
                this::parseResponse,
                this::onLoginError);
    }
    private void onLoginError(VolleyError error){
        btn.setActivated(true);
        Toast.makeText(getApplicationContext(),"Invalid credentials", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        MenuItem itemLogout = menu.add(0, 0, Menu.NONE, "Configure");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == 0){
            Archive.setToken(null);
            Intent intent;
            intent = new Intent(this, ConfigureHostActivity.class);
            startActivity(intent);
        }
        return true;
    }

    public void parseResponse(JSONObject obj) {
        try{
            String token = obj.getString("token");
            Archive.setToken(token);
            btn.setActivated(true);
            setResult(1);
            finish();
        } catch (Exception e){

        }
    }
}
