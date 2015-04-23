import akka.actor._
import akka.routing._
import scala.concurrent._
import scala.io.Source

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
    case MessageActor.Message(message: String) => {
      println(s"Sent a message to website with worker ${self.path}: ${message}")
      val html = Source.fromURL("http://www.arturgajewski.com/wait.php?message=" + message)
      val s = html.mkString
      println(s"Response from website for worker ${self.path} with message ${message}: ${s}")
    }
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

  for (i <- 1 to 10) {
    mailbox ! MessageActor.Message("Hello-" + i)
  }


}
