package controllers

import java.util.Date

import anorm._
import play.api.Play.current
import play.api.db._
import play.api.libs.json._


import play.api.mvc._
import play.api.Logger

object AnimeV1 extends Controller {

  def masterList = Action {
    // TODO @AKB428 Ehcacheを使う
    DB.withConnection { implicit c =>
      val records = SQL("SELECT * FROM cours_infos ORDER BY id")().map {
        row => (row[Int]("id").toString,
          Map("id" -> row[Int]("id"), "year" -> row[Int]("year"), "cours" -> row[Int]("cours")))
      }.toMap

      Ok(Json.toJson(records))
    }
  }

  def year(year_num: String) = Action {
    // TODO @AKB428 Ehcacheを使う
    DB.withConnection { implicit c =>
      val cours_infos_records = SQL("SELECT * FROM cours_infos WHERE YEAR = {year_num} ORDER BY id").on("year_num" -> year_num)().map {
        row => row[Int]("id")
      }.toList
      Logger.debug(cours_infos_records.toString())
      if (cours_infos_records.size == 0) {
        Logger.warn(s"Execute no data request year=$year_num")
        Ok(Json.toJson(cours_infos_records))
      }
      else {

        val seq_cours_infos_records: Seq[Int] = cours_infos_records

        val bases_records = SQL("SELECT * FROM bases WHERE cours_id IN ({IDS}) ORDER BY id").on("IDS" -> seq_cours_infos_records)().map {
          row =>
            Map(
              "id" -> JsNumber(row[Int]("id")),
              "title" -> JsString(row[String]("title")))
        }.toList

        Ok(Json.toJson(bases_records))
      }
    }
  }

  def yearCours(year_num: String, cours: String) = Action { request =>

    val params = request.queryString.map { case (k,v) => k -> v.mkString }

    // TODO @AKB428 Ehcacheを使う
    DB.withConnection { implicit c =>
      val cours_infos_records = SQL("SELECT * FROM cours_infos WHERE YEAR = {year_num} AND cours = {cours} ").
        on("year_num" -> year_num, "cours" -> cours)().map {
        row => row[Int]("id")
      }.toList
      Logger.debug(cours_infos_records.toString())

      if (cours_infos_records.size == 0) {
        Logger.warn(s"Execute no data request year=$year_num cours=$cours")
        Ok(Json.toJson(cours_infos_records))
      }
      else if (params.contains("ogp")){

        Logger.debug("OGP")

        val seq_cours_infos_records: Seq[Int] = cours_infos_records

        val bases_records = SQL("SELECT bases.*, site_meta_data.og_title, site_meta_data.og_type, site_meta_data.og_description, site_meta_data.og_url, site_meta_data.og_image, site_meta_data.og_site_name FROM bases left join site_meta_data on bases.id = site_meta_data.bases_id WHERE COURS_ID IN ({IDS}) ORDER BY ID").on("IDS" -> seq_cours_infos_records)().map {
          row =>
            Map(
              "id" -> JsNumber(row[Int]("id")),
              "title" -> JsString(row[String]("title")),
              "title_short1" -> JsString(row[String]("title_short1")),
              "title_short2" -> JsString(row[String]("title_short2")),
              "title_short3" -> JsString(row[String]("title_short3")),
              "public_url" -> JsString(row[String]("public_url")),
              "twitter_account" -> JsString(row[String]("twitter_account")),
              "twitter_hash_tag" -> JsString(row[String]("twitter_hash_tag")),
              "cours_id" -> JsNumber(row[Int]("cours_id")),
              "sex" -> JsNumber(BigDecimal(row[Option[Int]]("sex").getOrElse(0))),
              "sequel" -> JsNumber(BigDecimal(row[Option[Int]]("sequel").getOrElse(0))),
              "created_at" -> JsString(row[Date]("created_at").toString),
              "updated_at" -> JsString(row[Date]("updated_at").toString),
              "city_name" -> JsString(row[String]("city_name")),
              "city_code" -> JsNumber(row[Int]("city_code")),
              "ogp" -> Json.obj(
                "og_title" -> JsString(row[String]("og_title")),
                "og_type" -> JsString(row[String]("og_type")),
                "og_description" -> JsString(row[String]("og_description")),
                "og_url" -> JsString(row[String]("og_url")),
                "og_image" -> JsString(row[String]("og_image")),
                "og_site_name" -> JsString(row[String]("og_site_name"))
              )
            )
        }.toList

        Ok(Json.toJson(bases_records))
      }
      else {

        val seq_cours_infos_records: Seq[Int] = cours_infos_records

        val bases_records = SQL("SELECT * FROM bases WHERE COURS_ID IN ({IDS}) ORDER BY ID").on("IDS" -> seq_cours_infos_records)().map {
          row =>
            Map(
              "id" -> JsNumber(row[Int]("id")),
              "title" -> JsString(row[String]("title")),
              "title_short1" -> JsString(row[String]("title_short1")),
              "title_short2" -> JsString(row[String]("title_short2")),
              "title_short3" -> JsString(row[String]("title_short3")),
              "public_url" -> JsString(row[String]("public_url")),
              "twitter_account" -> JsString(row[String]("twitter_account")),
              "twitter_hash_tag" -> JsString(row[String]("twitter_hash_tag")),
              "cours_id" -> JsNumber(row[Int]("cours_id")),
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
}