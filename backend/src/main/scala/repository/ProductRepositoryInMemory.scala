package repository

import domain.Product
import zio.ZLayer

case class ProductRepositoryInMemory() extends ProductRepository {
  override def getProducts: List[Product] = ProductRepositoryInMemory.products
}

object ProductRepositoryInMemory {
  private final val products = List(
    Product("1", "Product 1", "Description 1", 1),
    Product("2", "Product 2", "Description 2", 2),
    Product("3", "Product 3", "Description 3", 3),
    Product("4", "Product 4", "Description 4", 4),
    Product("5", "Product 5", "Description 5", 5),
    Product("6", "Product 6", "Description 6", 6),
    Product("7", "Product 7", "Description 7", 7),
    Product("8", "Product 8", "Description 8", 8),
    Product("9", "Product 9", "Description 9", 9),
    Product("10", "Product 10", "Description 10", 10),
    Product("11", "Product 11", "Description 11", 11)
  )

  def layer: ZLayer[Any, Nothing, ProductRepository] = ZLayer.succeed(ProductRepositoryInMemory())
}
