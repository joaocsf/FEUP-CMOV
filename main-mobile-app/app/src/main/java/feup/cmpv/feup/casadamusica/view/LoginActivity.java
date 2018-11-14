package feup.cmpv.feup.casadamusica.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import feup.cmpv.feup.casadamusica.R;
import feup.cmpv.feup.casadamusica.services.CostumerServices;
import feup.cmpv.feup.casadamusica.structures.Costumer;
import feup.cmpv.feup.casadamusica.utils.Archive;

public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText pwd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.login_activity);
        setSupportActionBar(findViewById(R.id.toolbar));

        username = findViewById(R.id.username);
        pwd = findViewById(R.id.password);


        Button btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this::login);

        TextView registered_text = findViewById(R.id.navigate_to_registration);
        registered_text.setOnClickListener(this::navigateToRegistration);
    }

    private void navigateToRegistration(View view) {
        Intent intent = new Intent(this, UserRegister.class);

        startActivity(intent);
    }

    private void login(View view) {
        String password = pwd.getText().toString();
        String user = username.getText().toString();

        String publicKeyString = Archive.createKeyPair();

        Costumer costumer = new Costumer();
        costumer.setUsername(user);
        costumer.setPassword(password);
        costumer.setPublicKey(publicKeyString);

        CostumerServices.Login(costumer, (obj) -> {
            try {
                Archive.setUuid(obj.getString("uuid"));
                navigateToMainBody();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, (error) -> Toast.makeText(getApplicationContext(),"Invalid credentials", Toast.LENGTH_LONG).show());
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

    private void navigateToMainBody(){
        Intent intent = new Intent(this, MainBody.class);
        finish();
        startActivity(intent);
    }

}
