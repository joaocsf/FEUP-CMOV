package feup.cmov.casadamusica.ticketterminal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import feup.cmpv.feup.casadamusica.utils.Archive;
import feup.cmpv.feup.casadamusica.view.TerminalLoginActivity;

public class TicketFirstTerminalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent;
        if(Archive.getToken() == null){
            intent = new Intent(this, TerminalLoginActivity.class);
            startActivityForResult(intent, 5);
        }else{
            openTerminal();
        }
    }

    private void openTerminal(){
        Intent intent;
        intent = new Intent(this, TicketTerminalShowSelectionActivity.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 5 && resultCode == 1){
            openTerminal();
            return;
        }
        finish();
    }
}
