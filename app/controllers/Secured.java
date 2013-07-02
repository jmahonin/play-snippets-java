package controllers;

import play.*;
import play.mvc.*;
import play.mvc.Http.*;

import models.*;

// Ensures the user invoking the requested action is logged in (has an 'email'
// set in the context). It can be applied by using 
// '@Security.Authenticated(Secured.class)' to a controller or action
public class Secured extends Security.Authenticator {
    
    @Override
    public String getUsername(Context ctx) {
        return ctx.session().get("email");
    }
    
    @Override
    public Result onUnauthorized(Context ctx) {
        return redirect(routes.Application.main());
    }
}
