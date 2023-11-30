import repository.{ProductRepository, ProductRepositoryInMemory}
import zio.*
import zio.Console.printLine
import zio.http.*
import zio.json.*

final case class ProductAddRequest(name: String, description: String, imageUrl: String, url: String) derives JsonCodec

object Main extends ZIOAppDefault {

  private val app: HttpApp[ProductRepository] =
    (Routes(
      Method.GET / "api" / "products" -> handler(
        ProductRepository.getProducts.map(products => Response.json(products.toJson))
      ),
      Method.POST / "api" / "products" -> handler { (request: Request) =>
        for {
          _ <- ZIO.unit

          body <- request.body.asString
          _ = println (s"body = ${body}")

        } yield Response.json("{}")
      }
    ) @@ Middleware.cors)
      .handleError(e => Response.internalServerError)
      .toHttpApp

  override def run: ZIO[Environment & ZIOAppArgs & Scope, Any, Any] =
    Server
      .install(app)
      .flatMap(port => ZIO.logInfo(s"Listening on port $port") *> ZIO.never)
      .provide(Server.defaultWithPort(8080), ProductRepositoryInMemory.layer)
}
