package controllers;

import play.*;
import play.mvc.*;
import play.data.*;
import play.libs.*;
import play.libs.F.*;
import play.libs.WS;
import play.libs.WS.*;

import java.util.*;

// Acts as a proxy class for client-side geolocation requests. CORS isn't
// widely supported yet, and JSONP is terrible. Also, we can invoke this server
// side if ever we need it now.

@With(ForceHttps.class)
public class NominatimProxy extends Controller {
    private static final String LOOKUP_URL="http://nominatim.openstreetmap.org/search";
    private static final String REVERSE_URL="http://nominatim.openstreetmap.org/reverse";

    // Set up the reverse router for javascript
    public static Result javascriptRoutes() {
        response().setContentType("text/javascript");
        return ok(
            Routes.javascriptRouter("jsRoutesProxy",
                controllers.routes.javascript.NominatimProxy.search(),
                controllers.routes.javascript.NominatimProxy.reverseLookup()
            )
        );
    }

    // Notice the countrycodes are restricted to 'US' and 'CA' below
    public static Result search(String location) {
        return async(
	    WS.url(LOOKUP_URL).
		setQueryParameter("format", "json").
		setQueryParameter("addressdetails", "1").
		setQueryParameter("countrycodes", "us,ca").
                setQueryParameter("q", location).
                get().map(
                    new Function<WS.Response, Result>() {
                        public Result apply(WS.Response response) {
                            return ok(response.asJson());
                        }
                    }
                )	
        );
    }

    public static Result reverseLookup(String lat_lon) {
	String[] array = lat_lon.split(",");
        return async(
	    WS.url(REVERSE_URL).
		setQueryParameter("format", "json").
		setQueryParameter("lat", array[0]).
                setQueryParameter("lon", array[1]).
                get().map(
                    new Function<WS.Response, Result>() {
                        public Result apply(WS.Response response) {
                            return ok(response.asJson());
                        }
                    }
                )	
        );
    }
}
