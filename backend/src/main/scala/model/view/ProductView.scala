package model.view

import zio.json.JsonCodec

final case class ProductView(id: String, name: String, description: String, imageUrl: String, url: String, upvoted: Int)
    derives JsonCodec
