package controllers

import java.util.Date

import anorm._
import play.api.Logger
import play.api.Play.current
import play.api.db._
import play.api.libs.json.{JsNumber, JsString, Json}
import play.api.mvc._

object AnimeMovieV1 extends Controller {

  def masterList = Action {

    // TODO @AKB428 Ehcacheを使う
    DB.withConnection { implicit c =>
      val records = SQL("SELECT year FROM movie_year_infos ORDER BY year")().map {
        row => row[Int]("year")
      }.toList

      Ok(Json.toJson(records))
    }
  }

  def year(year_num: String) = Action {
    DB.withConnection { implicit c =>
      val bases_records = SQL("SELECT * FROM base_movies WHERE year = {YEAR} ORDER BY start_date").on("YEAR" -> year_num)().map {
        row =>
          Map(
            "id" -> JsNumber(row[Int]("id")),
            "title" -> JsString(row[String]("title")),
            "title_short1" -> JsString(row[String]("title_short1")),
            "title_short2" -> JsString(row[Option[String]]("title_short2").getOrElse("")),
            "title_short3" -> JsString(row[Option[String]]("title_short3").getOrElse("")),
            "public_url" -> JsString(row[String]("public_url")),
            "twitter_account" -> JsString(row[String]("twitter_account")),
            "twitter_hash_tag" -> JsString(row[String]("twitter_hash_tag")),
            "sex" -> JsNumber(BigDecimal(row[Option[Int]]("sex").getOrElse(0))),
            "sequel" -> JsNumber(BigDecimal(row[Option[Int]]("sequel").getOrElse(0))),
            "created_at" -> JsString(row[Date]("created_at").toString),
            "updated_at" -> JsString(row[Date]("updated_at").toString),
            "city_name" -> JsString(row[String]("city_name")),
            "city_code" -> JsNumber(row[Int]("city_code"))
          )
      }.toList

      Ok(Json.toJson(bases_records))
    }
  }

  def yearMonth(year_num: String, month: String) = Action {
    DB.withConnection { implicit c =>
      val bases_records = SQL("SELECT * FROM base_movies WHERE year = {year_num} and start_month = {month} ORDER BY start_date").
        on("year_num" -> year_num, "month" -> month)().map {
        row =>
          Map(
            "id" -> JsNumber(row[Int]("id")),
            "title" -> JsString(row[String]("title")),
            "title_short1" -> JsString(row[String]("title_short1")),
            "title_short2" -> JsString(row[Option[String]]("title_short2").getOrElse("")),
            "title_short3" -> JsString(row[Option[String]]("title_short3").getOrElse("")),
            "public_url" -> JsString(row[String]("public_url")),
            "twitter_account" -> JsString(row[String]("twitter_account")),
            "twitter_hash_tag" -> JsString(row[String]("twitter_hash_tag")),
            "sex" -> JsNumber(BigDecimal(row[Option[Int]]("sex").getOrElse(0))),
            "sequel" -> JsNumber(BigDecimal(row[Option[Int]]("sequel").getOrElse(0))),
            "created_at" -> JsString(row[Date]("created_at").toString),
            "updated_at" -> JsString(row[Date]("updated_at").toString),
            "city_name" -> JsString(row[String]("city_name")),
            "city_code" -> JsNumber(row[Int]("city_code"))
          )
      }.toList

      Ok(Json.toJson(bases_records))
    }
  }
}