package repository

import domain.Product
import zio.ZLayer

case class ProductRepositoryInMemory() extends ProductRepository {
  override def getProducts: List[Product] = ProductRepositoryInMemory.products
}

object ProductRepositoryInMemory {
  private final val products = List(
    Product("1", "Product 1", "Description 1", 1, "https://via.placeholder.com/150", "https://www.google.com"),
    Product("2", "Product 2", "Description 2", 1, "https://via.placeholder.com/150", "https://www.google.com"),
    Product("3", "Product 3", "Description 3", 1, "https://via.placeholder.com/150", "https://www.google.com")
  )

  def layer: ZLayer[Any, Nothing, ProductRepository] = ZLayer.succeed(ProductRepositoryInMemory())
}
