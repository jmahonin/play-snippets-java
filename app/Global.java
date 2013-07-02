import play.*;
import play.libs.*;

import java.util.*;

import com.avaje.ebean.*;

import models.*;

public class Global extends GlobalSettings {
    
    // On app start, seed initial data
    public void onStart(Application app) {
        InitialData.insert(app);
    }
    
    // Seed initial data if there's not already anything in there
    static class InitialData {
        public static void insert(Application app) {
            if(Ebean.find(AppUser.class).findRowCount() == 0) {
		// Load the YAML as a map
                Map<String,List<Object>> all = (Map<String,List<Object>>)Yaml.load("initial-data.yml");
                // Save the users to the DB
                Ebean.save(all.get("users"));
            }
        }
    }   
}
