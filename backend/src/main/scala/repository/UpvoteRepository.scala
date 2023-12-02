package repository

import model.domain.Upvote
import zio.*

trait UpvoteRepository {
  def getAll: UIO[List[Upvote]]
  def add(upvote: Upvote): UIO[Unit]
}
