package binders;

import java.util.UUID;
import play.mvc.PathBindable;

// Create a Wrapper for the UUID type so that we can use it in 'routes'
public class UUIDWrapper implements PathBindable<UUIDWrapper> {

    private UUID uuid;

    public static UUIDWrapper create(final UUID uuid) {
        final UUIDWrapper w = new UUIDWrapper();
        w.uuid = uuid;
        return w;
    }
    
    @Override
    public UUIDWrapper bind(String key, String value) {
        this.uuid = UUID.fromString(value);
        return this;
    }

    @Override
    public String unbind(String key) {
        return key.toString();
    }

    @Override
    public String javascriptUnbind() {
        return null;
    }

    public UUID getUUID() {
        return uuid;
    }
}
