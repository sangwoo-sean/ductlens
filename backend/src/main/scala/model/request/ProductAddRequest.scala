package model.request

import zio.json.JsonCodec

final case class ProductAddRequest(name: String, description: String, imageUrl: String, url: String) derives JsonCodec

