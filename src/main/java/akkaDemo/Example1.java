package akkaDemo;


import akka.actor.*;
import akka.japi.pf.ReceiveBuilder;
import scala.PartialFunction;
import scala.concurrent.duration.Duration;
import scala.runtime.BoxedUnit;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

public class Example1 {
    public static class Greeting implements Serializable {
        public static final long serialVersionUID = 1;
        public final String message;
        public Greeting(String message) {
            this.message = message;
        }
    }
    public static class Greeter extends AbstractActor {
        String greeting = "";

        public Greeter() {
            receive(ReceiveBuilder.
                match(WhoToGreet.class, message -> greeting = "hello, " + message.who).
                match(Greet.class, message -> sender().tell(new Greeting(greeting), self())).
                build());
        }
    }

public static void main(String[] args) {
    final ActorSystem system = ActorSystem.create("helloakka");

    // Create the 'greeter' actor
    final ActorRef greeter = system.actorOf(Props.create(Greeter.class), "greeter");
    greeter.tell(new WhoToGreet("akka"), ActorRef.noSender());
}
}
