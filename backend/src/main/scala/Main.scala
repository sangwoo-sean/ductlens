import repository.{ProductRepository, ProductRepositoryInMemory, UpvoteRepository, UpvoteRepositoryInMemory}
import router.{ProductRouter, UpvoteRouter}
import zio.*
import zio.http.*

object Main extends ZIOAppDefault {

  private val app: Routes[UpvoteRepository with ProductRepository, Nothing] =
    (ProductRouter.routes ++ UpvoteRouter.routes)
      .handleError(e => Response.internalServerError) @@ Middleware.cors

  override def run: ZIO[Environment & ZIOAppArgs & Scope, Any, Any] =
    Server
      .install(app.toHttpApp)
      .flatMap(port => ZIO.logInfo(s"Listening on port $port") *> ZIO.never)
      .provide(Server.defaultWithPort(8080), ProductRepositoryInMemory.layer, UpvoteRepositoryInMemory.layer)
}
