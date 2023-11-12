package repository

import domain.Product
import zio.ZLayer

case class ProductRepositoryInMemory() extends ProductRepository {
  override def getProducts: List[Product] = ProductRepositoryInMemory.products
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
      "src/assets/tickerbell.png",
      "https://tickerbell.ai/marketlive"
    ),
    Product(
      "3",
      "Dead Rabbit",
      "Finance Newsletter Auto‑Generated",
      1,
      "src/assets/deadrabbit.png",
      "https://tickerbell.ai/newsletter"
    ),
    Product(
      "4",
      "financeGPT",
      "Natural Language Search, Personal Chatbot on Markets",
      1,
      "src/assets/financegpt.png",
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
  )

  def layer: ZLayer[Any, Nothing, ProductRepository] = ZLayer.succeed(ProductRepositoryInMemory())
}
