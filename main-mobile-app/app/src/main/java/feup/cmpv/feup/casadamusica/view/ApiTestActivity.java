package feup.cmpv.feup.casadamusica.view;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import org.json.JSONException;
import org.json.JSONObject;

import feup.cmpv.feup.casadamusica.R;
import feup.cmpv.feup.casadamusica.services.Api;
import feup.cmpv.feup.casadamusica.services.CostumerServices;
import feup.cmpv.feup.casadamusica.structures.Costumer;

public class ApiTestActivity extends AppCompatActivity {

    CoordinatorLayout coordinatorLayout;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.api_test_activity);

        coordinatorLayout = (CoordinatorLayout)findViewById(R.id.coordinator_layout);
        Button btn = (Button)findViewById(R.id.button);

        btn.setOnClickListener(
            (click) -> {

                Costumer tmp = new Costumer("Bolacha", "BolachaTest", "bolachas", 92314515, "RSA....");

                CostumerServices.Register(tmp,
                    response -> {
                        try {
                            Snackbar snackbar = null;
                            snackbar = Snackbar.make(coordinatorLayout, "User Created Successfuly!" + response.getString("msg"), Snackbar.LENGTH_LONG);
                            snackbar.show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },
                    error -> {
                        JSONObject obj = Api.getBodyFromError(error);

                        Snackbar snackbar = Snackbar.make(coordinatorLayout, "Error Adding User! " + obj, Snackbar.LENGTH_LONG);
                        snackbar.show();
                    });
            }
        );

    }
}
