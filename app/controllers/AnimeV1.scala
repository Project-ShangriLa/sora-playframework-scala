package controllers

import anorm._
import play.api.Play.current
import play.api.db._
import play.api.libs.json.{JsString, JsNumber, Json}
import play.api.mvc._
import play.api.Logger

object AnimeV1 extends Controller {

  def masterList = Action {
    DB.withConnection { implicit c =>
      val records = SQL("SELECT * FROM COURS_INFOS ORDER BY ID")().map {
        row => (row[Int]("id").toString,
          Map("id" -> row[Int]("id"), "year" -> row[Int]("year"), "cours" -> row[Int]("cours")))
      }.toMap

      Ok(Json.toJson(records))
    }
  }

  def masterList2 = Action {
    // TODO @AKB428 EHCASHEを使う
    DB.withConnection { implicit c =>
      val records = SQL("SELECT * FROM COURS_INFOS ORDER BY ID")().map {
        row => (
          Map("id" -> row[Int]("id"), "year" -> row[Int]("year"), "cours" -> row[Int]("cours")))
      }.toList

      Ok(Json.toJson(records))
    }
  }

  def year(year_num: String) = Action {
    // TODO @AKB428 EHCASHEを使う
    DB.withConnection { implicit c =>
      val cours_infos_records = SQL("SELECT * FROM COURS_INFOS WHERE YEAR = {year_num} ORDER BY ID").on("year_num" -> year_num)().map {
        row => (row[Int]("id"))
      }.toList
      Logger.debug(cours_infos_records.toString())
      val seq_cours_infos_records: Seq[Int] = cours_infos_records

      val bases_records = SQL("SELECT * FROM BASES WHERE COURS_ID IN ({IDS}) ORDER BY ID").on("IDS" -> seq_cours_infos_records)().map {
        row => (
          Map(
            "id" -> JsNumber(row[Int]("id")),
            "title" -> JsString(row[String]("title")))
          )
      }.toList



      Ok(Json.toJson(bases_records))
    }
  }

  def yearCours(year_num: String, cours: String) = Action {
    // TODO @AKB428 EHCASHEを使う
    DB.withConnection { implicit c =>
      val cours_infos_records = SQL("SELECT * FROM COURS_INFOS WHERE YEAR = {year_num} AND cours = {cours} ").
        on("year_num" -> year_num, "cours" -> cours)().map {
        row => (row[Int]("id"))
      }.toList
      Logger.debug(cours_infos_records.toString())
      val seq_cours_infos_records: Seq[Int] = cours_infos_records

      val bases_records = SQL("SELECT * FROM BASES WHERE COURS_ID IN ({IDS}) ORDER BY ID").on("IDS" -> seq_cours_infos_records)().map {
        row => (
          Map(
            "id" -> JsNumber(row[Int]("id")),
            "title" -> JsString(row[String]("title")))
          )
      }.toList



      Ok(Json.toJson(bases_records))
    }
  }
}