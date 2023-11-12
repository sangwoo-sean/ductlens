package domain

import zio.json.JsonCodec

final case class Product(id: String, name: String, description: String, upvoted: Int, imageUrl: String, url: String)
    derives JsonCodec
