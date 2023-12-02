import domain.*
import repository.{ProductRepository, ProductRepositoryInMemory}
import zio.*
import zio.http.*
import zio.json.*

import java.util.UUID

final case class ProductAddRequest(name: String, description: String, imageUrl: String, url: String) derives JsonCodec

object Main extends ZIOAppDefault {

  private val app: HttpApp[ProductRepository] =
    (Routes(
      Method.GET / "api" / "products" -> handler(
        ProductRepository.getProducts.map(products => Response.json(products.toJson))
      ),
      Method.POST / "api" / "products" -> handler { (request: Request) =>
        for {
          body <- request.body.asString
          response <- body.fromJson[ProductAddRequest] match
            case Left(reason) =>
              ZIO.succeed(Response.badRequest(reason))
            case Right(req) =>
              ZIO
                .serviceWithZIO[ProductRepository](
                  _.add(
                    Product(
                      UUID.randomUUID().toString,
                      req.name,
                      req.description,
                      0,
                      req.imageUrl,
                      req.url
                    )
                  )
                )
                .as(Response(Status.NoContent))
        } yield response
      },
      Method.DELETE / "api" / "products" / string("id") -> handler { (id: String, req: Request) =>
        ZIO.serviceWithZIO[ProductRepository](_.deleteById(id)).map {
          case Left(error) => Response.badRequest(error.getMessage)
          case Right(_)    => Response(Status.NoContent)
        }
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
