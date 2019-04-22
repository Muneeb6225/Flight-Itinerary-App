package cs.b07.cscb07project.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import cs.b07.cscb07project.R;

import cs.b07.cscb07project.driver.Save;
import cs.b07.cscb07project.users.Client;
import cs.b07.cscb07project.users.User;

import java.util.Map;


public class PersonalInfo extends AppCompatActivity {

  private Client currentUser;
  private Map<String, User> allClients;
  private String fileLocation;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_personal_info);
    setTitle(R.string.user_personal_info_page);

    Intent intent = getIntent();

    fileLocation = intent.getStringExtra("fileLocation");
    currentUser = (Client) intent.getSerializableExtra("currentUser");
    allClients = (Map<String, User>) intent.getSerializableExtra("allTheUser");
    // Adding this User's data into the field so they don't have to retype things they
    // don't want to change.
    ((EditText) findViewById(R.id.first_name_field)).setText(currentUser.getFirstName());
    ((EditText) findViewById(R.id.last_name_field)).setText(currentUser.getLastName());
    ((EditText) findViewById(R.id.password_field)).setText(currentUser.getPassword());
    ((EditText) findViewById(R.id.email_field)).setText(currentUser.getEmail());
    ((EditText) findViewById(R.id.credit_card_field)).setText(currentUser.getccNumber());
    ((EditText) findViewById(R.id.credit_card_expiry_field)).setText(currentUser.getccDate());
    ((EditText) findViewById(R.id.address_field)).setText(currentUser.getAddress());
  }

  /**
   * Hides the password once its typed
   * @param view defines the layout.
   */
  public void showHidePassword(View view) {
    // Switches the password to text once the checkbox is checked, and to stars if it isn't
    EditText passwordText = (EditText) findViewById(R.id.password_field);
    CheckBox passwordBox = (CheckBox) findViewById(R.id.password_field_box);
    if (passwordBox.isChecked()) {
      passwordText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
    } else {
      passwordText.setTransformationMethod(PasswordTransformationMethod.getInstance());
    }
  }

  /**
   * Submits the changes made to the personal info.
   * @param view represents the view
   */
  public void submitChanges(View view) {
    // Get all the information, save it, and exit.
    currentUser.setFirstName(((EditText) findViewById(R.id.first_name_field)).getText().toString());
    currentUser.setLastName(((EditText) findViewById(R.id.last_name_field)).getText().toString());
    currentUser.setPassword(((EditText) findViewById(R.id.password_field)).getText().toString());
    currentUser.setCcNumber(((EditText) findViewById(R.id.credit_card_field)).getText().toString());
    currentUser.setCcDate(((EditText) findViewById(R.id.credit_card_expiry_field))
            .getText().toString());
    currentUser.setAddress(((EditText) findViewById(R.id.address_field)).getText().toString());
    Save.saveClient(allClients, currentUser, fileLocation);
    finish();
  }
}
