package router

import model.domain.Product
import model.request.ProductAddRequest
import model.view.ProductView
import repository.{ProductRepository, UpvoteRepository}
import zio.*
import zio.json.*
import zio.http.*

import java.util.UUID

object ProductRouter {
  final val routes = Routes(
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
  )
}
