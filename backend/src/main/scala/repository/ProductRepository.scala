package repository

import domain.Product
import zio.*

trait ProductRepository {
  def getProducts: UIO[List[Product]]
}

object ProductRepository {
  def getProducts: ZIO[ProductRepository, Nothing, List[Product]] = ZIO.serviceWithZIO[ProductRepository](_.getProducts)
}
