package controllers

import anorm._
import play.api.Play.current
import play.api.db._
import play.api.libs.json.Json
import play.api.mvc._

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
    DB.withConnection { implicit c =>
      val records = SQL("SELECT * FROM COURS_INFOS ORDER BY ID")().map {
        row => (
          Map("id" -> row[Int]("id"), "year" -> row[Int]("year"), "cours" -> row[Int]("cours")))
      }.toList

      Ok(Json.toJson(records))
    }
  }
}