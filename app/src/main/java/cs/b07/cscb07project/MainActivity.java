package cs.b07.cscb07project;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import cs.b07.cscb07project.activities.UserControl;


public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    setTitle(R.string.login_page);
  }

  /**
   * Retrieves the email and password and pass it to UserControl to initial the login.
   *
   * @param view given view on screen.
   */
  public void login(View view) {

    //Loading message
    ProgressDialog loading = new ProgressDialog(this);
    loading.setTitle(getResources().getString(R.string.loading_login_title));
    loading.setMessage(getResources().getString(R.string.loading_login_message));
    loading.show();

    //Actual login procedure
    Intent intent = new Intent(this, UserControl.class);

    EditText emailText = (EditText) findViewById(R.id.email_field);
    EditText passwordText = (EditText) findViewById(R.id.password_field);

    String email = emailText.getText().toString();
    String password = passwordText.getText().toString();

    intent.putExtra("email", email);
    intent.putExtra("password", password);
    startActivity(intent);

    //Remove loading message
    loading.dismiss();
  }
}
