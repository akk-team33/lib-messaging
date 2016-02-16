package net.team33.messaging;

import org.junit.Test;

import java.util.function.Consumer;

import static org.junit.Assert.assertEquals;

public class CentralTest {

    @Test
    public final void test() {
        Object sender = new Object();
        Listener listener = new Listener();

        Routing.from(sender).route(Message.class).to(listener);
        Routing.from(sender).accept(new MessageDerivate());
        // wait some time?
        assertEquals(1, listener.count);
        Routing.from(sender).accept(new MessageDerivate());
        // wait some time?
        assertEquals(2, listener.count);
        Routing.release(listener);
        Routing.from(sender).accept(new MessageDerivate());
        // wait some time?
        assertEquals(2, listener.count);
    }

    private class Message {
    }

    private class MessageDerivate {
    }

    private class Listener implements Consumer<Message> {
        private int count = 0;

        @Override
        public void accept(Message message) {

        }
    }
}