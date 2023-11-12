package repository

import domain.Product
import zio.*

trait ProductRepository {
  def getProducts: List[Product]
}

object ProductRepository {
  def getProducts: ZIO[ProductRepository, Nothing, List[Product]] = ZIO.serviceWith[ProductRepository](_.getProducts)
}
