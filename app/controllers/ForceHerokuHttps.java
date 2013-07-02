package controllers;

import play.*;
import play.mvc.*;

// Redirects non-https requests to the https endpoint.
// Works as a simple action. It can be applied by using 
// @With(ForceHerokuHttps.class) on a controller, or sub-action
public class ForceHerokuHttps extends Action.Simple{
    private static String SSL_HEADER_HEROKU = "x-forwarded-proto";

    @Override
    public Result call(Http.Context ctx) throws Throwable {
        if(!isHttpsRequest(ctx.request())){
            return redirect("https://" + ctx.request().host() + ctx.request().uri());
        }
        return delegate.call(ctx);
    }

    private boolean isHttpsRequest(Http.Request request){
        if(Play.isDev()){
            return true;
        }

        String header = request.getHeader(SSL_HEADER_HEROKU);
        if(header != null && header.contains("https")){
            return true;
        }
    
        return false;
    }    
}

