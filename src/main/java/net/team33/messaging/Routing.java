package net.team33.messaging;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Routing implements Consumer<Object> {

    private static final Map<Identity, Routing> ROUTINGS = new HashMap<>(0);

    public static Routing from(final Object sender) {
        return null;
    }

    public static void release(final Consumer<?> listener) {
    }

    public <M> Route<M> route(final Class<M> messageClass) {
        return null;
    }

    @Override
    public void accept(final Object message) {
    }

    private static class Identity {
    }

    public class Route<M> {

        public void to(final Consumer<? super M> listener) {
        }
    }
}
