package repository

import domain.Product
import zio.{Exit, Ref, UIO, ZIO, ZLayer}

case class ProductRepositoryInMemory(products: Ref[List[Product]]) extends ProductRepository {
  override def getProducts: UIO[List[Product]]            = products.get
  override def add(product: Product): UIO[Unit]           = products.update { prev => prev :+ product }
  override def findById(id: String): UIO[Option[Product]] = products.get.map(_.find(_.id == id))
  override def deleteById(id: String): UIO[Either[Exception, Unit]] =
    for {
      maybeTarget <- products.get.map(_.find(_.id == id))
      result <- maybeTarget match {
        case Some(_) => {
          products.update { ps => ps.filterNot(_.id == id) }.asRight
        }
        case None => ZIO.succeed(Left(new Exception("Product not found")))
      }
    } yield result
  override def update(product: Product): UIO[Either[Exception, Unit]] =
    for {
      maybeTarget <- products.get.map(_.find(_.id == product.id))

      result <- maybeTarget match
        case Some(target) =>
          products.update { ps =>
            ps.map {
              case p if p.id == target.id =>
                target.copy(
                  name = product.name,
                  description = product.description,
                  imageUrl = product.imageUrl,
                  url = product.url
                )
              case p => p
            }
          }.asRight
        case None => ZIO.succeed(Left(new Exception("Product not found")))
    } yield result

}

object ProductRepositoryInMemory {
  private final val products = List(
    Product(
      "1",
      "Lilys AI",
      "영상을 보지 않아도 몇 분만에 모든 내용을 이해할 수 있습니다. 너무 길거나, 외국어로 되어있어 엄두도 안 났던 영상의 요약 노트를 만들어 보세요 ",
      1,
      "https://lilys.ai/static/media/lilys_main_logo.f14d74e2d3b0527ba63743c1e7b87b2d.svg",
      "https://lilys.ai/"
    ),
    Product(
      "2",
      "TickerBell",
      "Financial News & Research Automation",
      1,
      "/src/assets/tickerbell.png",
      "https://tickerbell.ai/marketlive"
    ),
    Product(
      "3",
      "Dead Rabbit",
      "Finance Newsletter Auto‑Generated",
      1,
      "/src/assets/deadrabbit.png",
      "https://tickerbell.ai/newsletter"
    ),
    Product(
      "4",
      "financeGPT",
      "Natural Language Search, Personal Chatbot on Markets",
      1,
      "/src/assets/financegpt.png",
      "https://tickerbell.ai/financegpt"
    ),
    Product(
      "5",
      "ChatGPT",
      "ChatGPT is a free-to-use AI system. Use it for engaging conversations, gain insights, automate tasks, and witness the future of AI, all in one place.",
      1,
      "https://upload.wikimedia.org/wikipedia/commons/thumb/0/04/ChatGPT_logo.svg/1200px-ChatGPT_logo.svg.png",
      "https://chat.openai.com/"
    ),
    Product(
      "6",
      "Bard",
      "Bard is a conversational generative artificial intelligence chatbot developed by Google, based initially on the LaMDA family of large language models and later PaLM.",
      1,
      "https://preview.redd.it/google-ai-bard-logo-design-v0-1gvnsrl6b6ia1.png?width=3001&format=png&auto=webp&s=95ff97d8b16192d04f3442f910216f5b1cd4982c",
      "https://bard.google.com/chat"
    ),
    Product(
      "7",
      "Claude AI",
      "Claude is a next-generation AI assistant for your tasks, no matter the scale. Our API is currently being offered to a limited set of customers and researchers.",
      1,
      "https://avatars.slack-edge.com/2023-01-25/4682316783575_bbab0cdcdb3685eb5c87_512.png",
      "https://claude.ai/"
    ),
    Product(
      "8",
      "Chatgot",
      "Chat Freely, Got Every AI Assistants Here for You",
      1,
      "https://cdn-1.webcatalog.io/catalog/chatgot/chatgot-icon-filled-256.webp?v=1699344518859",
      "https://www.chatgot.io/"
    ),
    Product(
      "9",
      "Clipdrop",
      "CREATE STUNNING VISUALS IN SECONDS",
      1,
      "https://static.clipdrop.co/web/homepage/preview.jpg",
      "https://clipdrop.co/"
    ),
  )

  val layer: ZLayer[Any, Nothing, ProductRepository] = ZLayer {
    for {
      _  <- ZIO.logInfo("loading products...")
      ps <- Ref.make(products)
      _  <- ZIO.logInfo(s"loaded ${products.size} products")
    } yield ProductRepositoryInMemory(ps)
  }
}
