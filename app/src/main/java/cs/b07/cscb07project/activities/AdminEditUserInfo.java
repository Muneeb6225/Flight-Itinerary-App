package cs.b07.cscb07project.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import cs.b07.cscb07project.R;
import cs.b07.cscb07project.users.Client;

import java.util.Map;


public class AdminEditUserInfo extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_admin_edit_user_info);
    setTitle(R.string.edit_user);
  }

  /**
   * Admin edits user information.
   * @param view defines the layout
   */
  public void editUserInformation(View view) {
    Intent obtainIntent = getIntent();
    String email = ((EditText) findViewById(R.id.user_email_text)).getText().toString();
    Map<String, Client> allClients = (Map<String, Client>) obtainIntent
            .getSerializableExtra("allClient");
    if (allClients.containsKey(email)) {
      Intent intent = new Intent(this, UserControl.class);
      intent.putExtra("email", email);
      intent.putExtra("password", allClients.get(email).getPassword());
      startActivity(intent);
    }
  }
}
