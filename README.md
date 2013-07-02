play-snippets-java
==================

A useful skeleton of snippets for use in Play 2.0.x in java

app/
  Global.java - Demonstrates loading initial data to a DB from a YAML file
  binders/
    UUIDWrapper.java - Wrapper class for UUID, so we can use it in 'routes'
  controllers/
    Application.java - Sample controller to demonstrate login/logout
    ForceHerokuHttps.java - Simple action to force an https redirect on unsecured requests on Heroku
    ForceNoCache.java - Simple action apply the 'Cache-Control: no-cache' header to a request
    NominatimProxy.java - An asynchronous proxy for geosearch and reverse lookup requests to Nominatim
    Secured.java - A security authenticator that ensures a user is logged in for the applied request
    WebCallback.java - An example controller for returning an XML resource from a passed in UUID
  models/
    AppUser.java - User model, implements BCrypt password hashing
  views/
    front.scala.html - Contains sample login form
    main.scala.html - Main page wrapper, shows user option matching
