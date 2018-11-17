package feup.cmpv.feup.casadamusica.fragments.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import feup.cmpv.feup.casadamusica.R;
import feup.cmpv.feup.casadamusica.services.Api;
import feup.cmpv.feup.casadamusica.services.CostumerServices;
import feup.cmpv.feup.casadamusica.utils.Archive;

public class UpdatePasswordDialogFragment extends DialogFragment {

    private EditText oldPassword;
    private EditText newPassword;
    private EditText confirmNewPassword;

    public static DialogFragment getInstance(){
        return new UpdatePasswordDialogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.update_password_dialog_fragment, container,false);

        oldPassword = view.findViewById(R.id.old_password);
        newPassword = view.findViewById(R.id.new_password);
        confirmNewPassword = view.findViewById(R.id.confirm_new_password);

        Button btn_update = view.findViewById(R.id.btn_update);
        btn_update.setOnClickListener(this::updatePassword);

        Button btn_cancel = view.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener((v) -> this.dismiss());

        return view;
    }

    private void updatePassword(View view) {

        if(!verifyFields())
            return;

        JSONObject object = new JSONObject();
        try {
            object.put("uuid", Archive.getUuid());
            object.put("oldPassword", oldPassword.getText().toString());
            object.put("newPassword", newPassword.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        CostumerServices.UpdatePassword(object,
                (success) -> {
                    Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                    this.dismiss();
                },
                (error) -> {
                    try {
                        Toast.makeText(getContext(), Api.getBodyFromError(error).get("msg").toString(), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    private boolean verifyFields(){

        boolean valid = true;

        if(oldPassword.getText().toString().isEmpty()){
            oldPassword.setError("Can not be empty");
            valid = false;
        }

        if(newPassword.getText().toString().isEmpty()){
            newPassword.setError("Can not be empty");
            valid = false;
        }

        if(confirmNewPassword.getText().toString().isEmpty()){
            confirmNewPassword.setError("Can not be empty");
            valid = false;
        }

        if(!confirmNewPassword.getText().toString().equals(newPassword.getText().toString())){
            confirmNewPassword.setError("Should be equal to new password");
            valid = false;
        }

        return valid;
    }

    @Override
    public void onStart() {
        super.onStart();
        Objects.requireNonNull(getDialog().getWindow())
                .setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }
}
