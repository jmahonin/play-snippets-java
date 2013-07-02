package controllers;

import play.*;
import play.mvc.*;
import play.data.*;
import play.libs.*;
import play.libs.F.*;
import play.libs.WS;
import play.libs.WS.*;

import java.util.*;

import models.*;
import utils.*;
import binders.*;

public class WebCallback extends Controller {

    public static Result getResource(UUIDWrapper id) {
        // Search the DB for some resource based of the UUID
        
        response().setContentType("text/xml");

        Form<ResourceParams> paramsForm = form(ResourceParams.class).bindFromRequest();
        Map<String, String> mapParams = new HashMap<String, String>();
        mapParams.put("Foo", paramsForm.get().foo);
        mapParams.put("Bar", paramsForm.get().bar);
        return ok(
                "<?xml version=\"1.0\" encoding=\"utf-8\"?>" // + some resource XML
                );
    }

    public static class ResourceParams {
        public String foo;
        public String bar;
        public String validate() { return null; }
    }

}
