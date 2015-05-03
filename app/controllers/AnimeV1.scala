package controllers

import anorm._
import play.api.Play.current
import play.api.db._
import play.api.libs.json.Json
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
        row => Map("title" -> row[String]("title"), "public_url" -> row[String]("public_url"))}.toList



      Ok(Json.toJson(bases_records))
    }
  }
}