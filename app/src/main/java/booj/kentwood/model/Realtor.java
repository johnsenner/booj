package booj.kentwood.model;

import java.io.Serializable;

/**
 * Holds data for a Realtor.
 */

public class Realtor implements Serializable {
    public String first_name;
    public String last_name;
    public String id;
    public String rebrand;
    public String office;
    public String is_team;
    public String phone_number;
    public String photo;

    public String getFullNameString() {
        if (last_name == null) {
            // This is a group without a last name assigned...
            return first_name;
        } else {
            return String.format("%s %s", first_name, last_name);
        }
    }
}
