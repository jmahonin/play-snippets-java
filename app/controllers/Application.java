package controllers;

import play.*;
import play.mvc.*;
import play.data.*;
import play.mvc.BodyParser; 
import play.libs.Json;
import play.libs.Json.*;

import binders.*;
import models.*;
import utils.*;

import views.html.*;

import scala.Option;

import java.lang.*;
import java.util.*;

@With(ForceHttps.class)
public class Application extends Controller {

    public static Result main() {
        return ok(front.render(
            form(Login.class), 
            getUserOption(session("email"))
            )
        );
    }

    public static Result logout() {
        session().clear();
        flash("success", "You've been logged out");
        return redirect(
            routes.Application.main()
        );
    }

    public static Result authenticate() {
        Form<Login> loginForm = form(Login.class).bindFromRequest();
        if(loginForm.hasErrors()) {
            return badRequest(front.render(loginForm, getUserOption(null)));
        }
        else {
            session("email", loginForm.get().email);
            return redirect(
                routes.Dashboard.index()
            );
        }
    }

    public static class Login {
        public String email;
        public String password;

        public String validate() {
            if(AppUser.authenticate(email, password) == null) {
                return "Invalid username or password.";
            }
            return null;
        }
    }

    // Some Java trickery to get a wrapped Option(AppUser) object
    private static Option<AppUser> getUserOption(String email) {
        if(email == null) {
            return scala.Option$.MODULE$.apply(null);
        }
        else {
	    AppUser user = AppUser.findByEmail(email);
            if(user == null)
                return scala.Option$.MODULE$.apply(null);
            return Option.apply(user);
        }
    }
}
