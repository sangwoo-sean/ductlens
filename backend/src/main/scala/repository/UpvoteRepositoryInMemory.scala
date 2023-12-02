package repository

import model.domain.Upvote
import zio.*

class UpvoteRepositoryInMemory(upvotes: Ref[List[Upvote]]) extends UpvoteRepository {
  def getAll: UIO[List[Upvote]] = upvotes.get
  def add(upvote: Upvote): UIO[Unit] = {
    upvotes.update(_ :+ upvote)
  }
}

object UpvoteRepositoryInMemory {
  private final val upvotes = List.empty[Upvote]

  val layer: ZLayer[Any, Nothing, UpvoteRepositoryInMemory] = ZLayer {
    for {
      upvotes: Ref[List[Upvote]] <- Ref.make(upvotes)
    } yield UpvoteRepositoryInMemory(upvotes)
  }
}
