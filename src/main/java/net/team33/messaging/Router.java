package net.team33.messaging;

import net.team33.patterns.Monitor;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public class Router implements Consumer<Object> {

    private final Monitor monitor = new Monitor();
    @SuppressWarnings("rawtypes")
    private final Map<Class, Set> registered = new HashMap<>(0);
    @SuppressWarnings("rawtypes")
    private final Map<Class, Set> addressees = new HashMap<>(0);

    @SuppressWarnings("unchecked")
    private <M> Collection<Consumer<? super M>> registered(final Class<M> messageClass) {
        final Set<Consumer<? super M>> result;
        if (registered.containsKey(messageClass)) {
            result = (Set<Consumer<? super M>>) registered.get(messageClass);
        } else {
            result = new HashSet<>(0);
            registered.put(messageClass, result);
        }
        return result;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private Collection<Consumer<Object>> addressees(final Class<?> messageClass) {
        final Set<Consumer<Object>> result;
        if (addressees.containsKey(messageClass)) {
            result = (Set<Consumer<Object>>) addressees.get(messageClass);
        } else {
            result = new HashSet<>(0);
            registered.entrySet().stream()
                    .filter(entry -> entry.getKey().isAssignableFrom(messageClass))
                    .map(Map.Entry::getValue)
                    .forEach(result::addAll);
            addressees.put(messageClass, result);
        }
        return result;
    }

    @Override
    public final void accept(final Object message) {
        monitor.get(() -> addressees(message.getClass()))
                .forEach(addressee -> addressee.accept(message));
    }

    public final <M> Register<M> getRegister(final Class<M> msgClass) {
        return consumer -> monitor.run(() -> {
            registered(msgClass).add(consumer);
            addressees.clear();
        });
    }
}
