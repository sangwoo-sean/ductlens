package router

import model.domain.Upvote
import repository.UpvoteRepository
import zio.*
import zio.http.*

import java.time.LocalDateTime
import java.util.UUID

object UpvoteRouter {
  final val routes = Routes(
    Method.POST / "api" / "products" / string("id") / "upvote" -> handler { (id: String, request: Request) =>
      for {
        _ <- Console.printLine(s"ra = ${request.cookie("user_id")}")

        c = request.cookie("user_id") match {
          case Some(cookie) =>
            println(s"cookie: $cookie")
            cookie
          case None =>
            Cookie.Response("user_id", UUID.randomUUID().toString)
        }

        _ <- ZIO.serviceWithZIO[UpvoteRepository](
          _.add(
            Upvote(
              id = UUID.randomUUID().toString,
              productId = id,
              createdAt = LocalDateTime.now()
            )
          )
        )

      } yield Response(Status.NoContent).addCookie(c.toResponse)
    },
  )
}
