package cs.b07.cscb07project.users;

import java.io.Serializable;


/**
 * An Admin User.
 */
public class Admin extends User implements Serializable {

  private static final long serialVersionUID = 476027939117931999L;

  /**
   * Create a new Admin user with the given name and email.
   * @param lastName last name of this Admin
   * @param firstName first name of this Admin
   * @param email email name of this Admin
   */
  public Admin(String lastName, String firstName, String email) {
    super(lastName, firstName, email);
  }

}
