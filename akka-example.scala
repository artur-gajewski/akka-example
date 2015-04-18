import akka.actor._
import akka.routing._

/**
 * Companion object that holds a message
 */
object MessageActor {
  case class Message(message: String)
}

/**
 * Receiving actor that listens to incoming messages
 */
class MessageActor extends Actor {
  def receive = {
    case MessageActor.Message(message: String) => println(s"Message from ${self.path}: ${message}")
  }
}

/**
 * Send 500,000 messages with 5 workers layed out as Round-robin
 */
object Main extends App {
  val system = ActorSystem("ActorSystem")

  val mailbox = system.actorOf(
    Props[MessageActor].withRouter(RoundRobinRouter(10))
  )

  for (i <- 1 to 10) {
    mailbox ! MessageActor.Message("Hello world #" + i)
  }
}
