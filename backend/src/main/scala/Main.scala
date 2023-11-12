import repository.{ProductRepository, ProductRepositoryInMemory}
import zio.*
import zio.Console.printLine
import zio.http.*
import zio.json.*

object Main extends ZIOAppDefault {

  private val app: HttpApp[ProductRepository] =
    Routes(
      Method.GET / "text" -> handler(Response.text("Hello World!")),
      Method.GET / "api" / "products" -> handler(
        ProductRepository.getProducts.map(products => Response.json(products.toJson))
      )
    ).toHttpApp @@ Middleware.cors

  override def run: ZIO[Environment & ZIOAppArgs & Scope, Any, Any] =
    Server
      .install(app)
      .flatMap(port => ZIO.logInfo(s"Listening on port $port") *> ZIO.never)
      .provide(Server.defaultWithPort(8080), ProductRepositoryInMemory.layer)
}
