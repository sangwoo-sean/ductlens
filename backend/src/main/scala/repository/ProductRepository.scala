package repository

import model.domain.Product
import zio.*

trait ProductRepository {
  def getProducts: UIO[List[Product]]
  def add(product: Product): UIO[Unit]
  def findById(id: String): UIO[Option[Product]]
  def deleteById(id: String): UIO[Either[Exception, Unit]]
  def update(product: Product): UIO[Either[Exception, Unit]]
}

object ProductRepository {
  def getProducts: ZIO[ProductRepository, Nothing, List[Product]] = ZIO.serviceWithZIO[ProductRepository](_.getProducts)
}
