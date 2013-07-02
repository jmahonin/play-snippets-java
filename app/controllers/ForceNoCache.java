package controllers;

import play.*;
import play.mvc.*;

// Adds the 'Cache-Control: no-cache' header to any actions with this applied
// as an annotation using @With(ForceNoCache.class)
public class ForceNoCache extends Action.Simple{

    @Override
    public Result call(Http.Context ctx) throws Throwable {
        ctx.response().setHeader("Cache-Control", "no-cache");
        return delegate.call(ctx);
    }

}

