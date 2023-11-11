import zio.*
import zio.Console.printLine
import zio.http.*

object Main extends ZIOAppDefault:

  val app: HttpApp[Any] =
    Routes(
      Method.GET / "text" -> handler(Response.text("Hello World!"))
    ).toHttpApp

  override def run: ZIO[Environment & ZIOAppArgs & Scope, Any, Any] =
    Server.install(app)
      .flatMap(port => ZIO.logInfo(s"Listening on port $port") *> ZIO.never)
      .provide(Server.defaultWithPort(8080))
