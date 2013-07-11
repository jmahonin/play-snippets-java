package models;

import java.util.*;
import java.security.SecureRandom;

import play.*;
import play.db.ebean.*;
import play.data.validation.Constraints.*;

import javax.persistence.*;

import org.mindrot.jbcrypt.BCrypt;

@Entity
public class AppUser extends Model {
    @Id
    public Long id;

    @Required
    public String email;

    @Required
    public String salted_password;

    // Create a user with a BCrypted password
    public AppUser(String email, String password) {
        this.email = email.toLowerCase();
        this.salted_password = BCrypt.hashpw(password, BCrypt.gensalt());
    }
    
    public static Finder<Long,AppUser> find = 
        new Finder(Long.class, AppUser.class);

    public static List<AppUser> all() {
        return find.all();
    }

    public static AppUser findByEmail(String email) {
        return find.where().eq("email", email.toLowerCase()).findUnique();
    }

    public static void create(AppUser account) {
        account.save();
    }

    public static void delete(Long id) {
        find.ref(id).delete();
    }

    // Authenticate a user. First search for the email, then check the PW
    public static AppUser authenticate(String email, String password) {
        AppUser user = find.where()
            .eq("email", email.toLowerCase())
            .findUnique();
        if(user != null) {
            if(BCrypt.checkpw(password, user.salted_password)) {
                return user;
            }
        }
        return null;
    }

    // Generate a new salted password
    public void changePassword(String newPassword) {
        this.salted_password = BCrypt.hashpw(newPassword, BCrypt.gensalt());
    }

    // Generate a new random password with salt
    public String resetPassword() {
        String newPass = randomString(12);
        changePassword(newPass);
        return newPass;
    }

    public String toString() {
        return "User: " + email;
    }

    // Helper method to generate a random String of a given length
    protected static String randomString( int len ) 
    {
        String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder( len );
        for( int i = 0; i < len; i++ ) 
            sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
       return sb.toString();
    }
    
}
