import model.domain.{Product, Upvote}
import model.view.*
import model.request.*
import repository.{ProductRepository, ProductRepositoryInMemory, UpvoteRepository, UpvoteRepositoryInMemory}
import zio.*
import zio.http.*
import zio.json.*

import java.time.LocalDateTime
import java.util.UUID

object Main extends ZIOAppDefault {

  private val app: HttpApp[ProductRepository with UpvoteRepository] =
    (Routes(
      Method.GET / "api" / "products" -> handler(
        for {
          products <- ProductRepository.getProducts
          upvotes  <- ZIO.serviceWithZIO[UpvoteRepository](_.getAll)
        } yield Response.json(products.map { p =>
          ProductView(
            id = p.id,
            name = p.name,
            description = p.description,
            imageUrl = p.imageUrl,
            url = p.url,
            upvoted = upvotes.count(_.productId == p.id)
          )
        }.toJson)
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
      },
      Method.POST / "api" / "products" / string("id") / "upvote" -> handler { (id: String, request: Request) =>
        for {
          _ <- ZIO.logInfo(s"id: $id")
          _ <- ZIO.serviceWithZIO[UpvoteRepository](
            _.add(
              Upvote(
                id = UUID.randomUUID().toString,
                productId = id,
                createdAt = LocalDateTime.now()
              )
            )
          )
        } yield Response(Status.NoContent)
      },
    ) @@ Middleware.cors)
      .handleError(e => Response.internalServerError)
      .toHttpApp

  override def run: ZIO[Environment & ZIOAppArgs & Scope, Any, Any] =
    Server
      .install(app)
      .flatMap(port => ZIO.logInfo(s"Listening on port $port") *> ZIO.never)
      .provide(Server.defaultWithPort(8080), ProductRepositoryInMemory.layer, UpvoteRepositoryInMemory.layer)
}
