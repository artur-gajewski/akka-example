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
 * Send 50 messages with five workers layed out as Round-robin
 */
object Main extends App {
  val system = ActorSystem("ActorSystem")

  val mailbox = system.actorOf(
    Props[MessageActor].withRouter(RoundRobinRouter(5))
  )

  for (i <- 1 to 50) {
    mailbox ! MessageActor.Message("Hello world #" + i)
  }
}
