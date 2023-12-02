package model.domain

import java.time.LocalDateTime

final case class Upvote(id: String, productId: String, createdAt: LocalDateTime)
