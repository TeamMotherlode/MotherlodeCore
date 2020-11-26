package motherlode.base;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public class MotherlodeInitEvents {
    /**
     * An event that is guaranteed to be invoked once in the main entry point after all modules have been initialized.
     */
    public static Event<Init> MAIN = EventFactory.createArrayBacked(Init.class, callbacks -> () -> {
        for (Init callback : callbacks)
            callback.initialize();
    });

    /**
     * An event that is guaranteed to be invoked once on the client side after all modules have been initialized.
     */
    public static Event<Init> CLIENT = EventFactory.createArrayBacked(Init.class, callbacks -> () -> {
        for (Init callback : callbacks)
            callback.initialize();
    });

    /**
     * An event that is guaranteed to be invoked once on a dedicated server after all modules have been initialized.
     *
     * <p>Note: Everything done in this event will not affect singleplayer.
     */
    public static Event<Init> DEDICATED_SERVER = EventFactory.createArrayBacked(Init.class, callbacks -> () -> {
        for (Init callback : callbacks)
            callback.initialize();
    });

    public interface Init {
        void initialize();
    }
}
