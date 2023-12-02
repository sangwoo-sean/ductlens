package repository

import model.domain.Product
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

          newId = UUID.randomUUID().toString
          _ <- service.add(
            Product(
              id = newId,
              name = "test name",
              description = "test description",
              imageUrl = "test image url",
              url = "test url"
            )
          )
          found <- service.findById(newId)
        } yield assertTrue(found.exists(_.name == "test name"))
      },
      suite("deleteById")(
        test("success") {
          for {
            service <- ZIO.service[ProductRepository]

            newId = UUID.randomUUID().toString
            _ <- service.add(
              Product(
                id = newId,
                name = "test name",
                description = "test description",
                imageUrl = "test image url",
                url = "test url"
              )
            )
            found <- service.findById(newId)

            result           <- service.deleteById(newId)
            foundAfterDelete <- service.findById(newId)
          } yield assertTrue(found.exists(_.name == "test name") && result.isRight && foundAfterDelete.isEmpty)
        },
        test("fail") {
          for {
            service <- ZIO.service[ProductRepository]

            newId = UUID.randomUUID().toString
            result <- service.deleteById(newId)
          } yield assertTrue(result.isLeft)
        },
      ),
      suite("update")(
        test("success") {
          for {
            service <- ZIO.service[ProductRepository]

            newId = UUID.randomUUID().toString
            _ <- service.add(
              Product(
                id = newId,
                name = "test name",
                description = "test description",
                imageUrl = "test image url",
                url = "test url"
              )
            )

            result <- service.update(Product(
              id = newId,
              name = "updated name",
              description = "updated description",
              imageUrl = "updated image url",
              url = "updated url"
            ))

            found <- service.findById(newId)
          } yield assertTrue(result.isRight && found.map(_.name).contains("updated name"))
        },
        test("fail") {
          for {
            service <- ZIO.service[ProductRepository]

            newId = UUID.randomUUID().toString
            result <- service.update(Product(
              id = newId,
              name = "updated name",
              description = "updated description",
              imageUrl = "updated image url",
              url = "updated url"
            ))

            found <- service.findById(newId)
          } yield assertTrue(result.isLeft && found.isEmpty)
        },
      )
    ).provideShared(ProductRepositoryInMemory.layer)
}
