package net.team33.messaging;

import org.junit.Test;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import static org.junit.Assert.assertEquals;

public class RouterTest {

    private static void repeat(final int times, final Runnable job) {
        for (int i = 0; i < times; ++i) {
            job.run();
        }
    }

    @Test
    public final void accept() {
        final List<Object> target = new LinkedList<>();
        final Router router = new Router();
        repeat(4, () ->
                router.getRegister(Number.class).add(target::add));

        router.accept(new Date());

        assertEquals(0, target.size());

        router.accept(Math.PI);

        assertEquals(4, target.size());
        target.forEach(entry ->
                assertEquals(Math.PI, entry));

        router.accept(278);

        assertEquals(8, target.size());
        target.stream()
                .filter(e -> !Objects.equals(e, Math.PI))
                .forEach(entry ->
                        assertEquals(278, entry));
    }

    @Test
    public final void remove() {
        final List<Object> target = new LinkedList<>();
        final Router router = new Router();
        final Consumer<Number> consumer = target::add;
        router.getRegister(Number.class).add(consumer);

        router.accept(278);

        assertEquals(1, target.size());
        target.forEach(entry ->
                assertEquals(278, entry));

        router.remove(consumer);
        router.accept(279);

        assertEquals(1, target.size());
        target.forEach(entry ->
                assertEquals(278, entry));
    }
}