package scala.repository

import domain.Product
import repository.{ProductRepository, ProductRepositoryInMemory}
import zio.*
import zio.test.*

import java.util.UUID

object ProductRepositoryInMemoryTest extends ZIOSpecDefault {
  def spec =
    suite("ProductRepositoryInMemory")(
      test("getProducts") {
        for {
          service <- ZIO.service[ProductRepository]
          ps      <- service.getProducts
        } yield assertTrue(ps.nonEmpty)
      },
      test("add & findById") {
        for {
          service <- ZIO.service[ProductRepository]
          before  <- service.getProducts

          newId = UUID.randomUUID().toString
          _ = println(s"newId = ${newId}")
          _ <- service.add(
            Product(
              id = newId,
              name = "test name",
              description = "test description",
              upvoted = 0,
              imageUrl = "test image url",
              url = "test url"
            )
          )
          found <- service.findById(newId)
          after <- service.getProducts
        } yield assertTrue(before.size == after.size - 1 && found.exists(_.name == "test name"))
      },
    ).provide(ProductRepositoryInMemory.layer)
}
