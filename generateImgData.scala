import sttp.client4.*
import sttp.client4.httpclient.HttpClientFutureBackend

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.Await
import scala.concurrent.duration.*


@main def generateImgData() =
  val imgPattern = "https://imx.to/u/t/\\d{4}/\\d{2}/\\d{2}/\\w+\\.jpg".r
  val sourceUri =
    uri"https://vipergirls.to/threads/6058603-Erotic-amp-Fine-Nudes-Photographers/"
  val backend = HttpClientFutureBackend()

  val responses =
    for i <- 1 to 16
    yield
      val request = basicRequest
        .get(sourceUri addPath s"page$i")
        .response(asString.getRight)
      val response = backend.send(request)
      response.map { response =>
        imgPattern.findAllIn(response.body).toSeq
      }

  val imgs = Await
    .result(Future.reduceLeft(responses)(_ ++ _), 10.seconds)
    .distinct
    .map(_.replace("/u/t/", "/u/i/"))

  os.write.over(
    os.pwd / "docs/data.js",
    "let imgs = [\n\"" + imgs.mkString("\",\n\"") + "\"\n]"
  )
