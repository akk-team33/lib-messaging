package net.team33.messaging;

import java.util.function.Consumer;

@FunctionalInterface
public interface Register<M> {

    void add(Consumer<? super M> consumer);
}
