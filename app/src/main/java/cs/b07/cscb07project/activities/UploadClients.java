package cs.b07.cscb07project.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import cs.b07.cscb07project.R;

import cs.b07.cscb07project.driver.Load;
import cs.b07.cscb07project.driver.Save;
import cs.b07.cscb07project.users.Client;

import java.io.File;
import java.io.IOException;
import java.util.Map;


public class UploadClients extends AppCompatActivity {

  private String clientsFile;
  private String fileLocation;
  private String passwordPath;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_upload_clients);
    setTitle(R.string.client_upload);
    Intent intent = getIntent();
    clientsFile = intent.getStringExtra("allClientsFile");
    fileLocation = intent.getStringExtra("fileLocation");
    passwordPath = intent.getStringExtra("passwordPath");
  }

  /**
   * Saves the client in the database given a file of clients.
   * @param view defines the layout
   */
  public void uploadClients(View view) {
    String fileName = ((EditText) findViewById(R.id.clients_file_location)).getText().toString();
    if (new File(fileLocation + "/" + fileName).exists()) {
      // See if file exist, then load it
      try {
        String clientPath = fileLocation + "/" + fileName;
        Map<String, Client> newUsers = Load.loadClient(clientPath, fileLocation + "/"
                + passwordPath);
        Map<String, Client> oldUsers = Load.loadInternalClients(fileLocation + "/" + clientsFile);
        for (String newUser : newUsers.keySet()) {
          // If the the new file has any emails as the old one, we update that information
          if (oldUsers.containsKey(newUser)) {
            oldUsers.remove(newUser);
          }
          oldUsers.put(newUser, newUsers.get(newUser));
        }
        Save.save(oldUsers, fileLocation + "/" + clientsFile);
        new AlertDialog.Builder(this).setCancelable(false).setTitle(getResources()
                .getString(R.string.upload_client_success_title))
                .setMessage(getResources().getString(R.string.upload_client_success_message))
                .setPositiveButton(getResources().getString(R.string.confirm),
                      new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialog, int which) {
                        ((EditText) findViewById(R.id.clients_file_location)).setText("");
                      }
                    })
                .create().show();
      } catch (IOException e) {
        // An error occurred during Flight creation, make sure the file is correct
        new AlertDialog.Builder(this).setCancelable(false)
            .setTitle(getResources().getString(R.string.upload_client_file_read_error))
            .setMessage(getResources().getString(R.string.upload_client_file_read_error_message))
            .setPositiveButton(getResources().getString(R.string.confirm),
                new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int which) {
                    ((EditText) findViewById(R.id.clients_file_location)).setText("");
                  }
                })
            .create().show();
      }
    } else {
      new AlertDialog.Builder(this).setCancelable(false)
          .setTitle(getResources().getString(R.string.upload_client_file_not_found))
          .setMessage(getResources().getString(R.string.upload_client_file_does_not_exist))
          .setPositiveButton(getResources().getString(R.string.confirm),
              new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                  ((EditText) findViewById(R.id.clients_file_location)).setText("");
                }
              })
          .create().show();
    }
  }
}


