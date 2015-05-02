package controllers

import play.api._
import play.api.db._
import play.api.mvc._
import play.api.Play.current
import anorm._

object Application extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def test = Action {
    DB.withConnection { implicit c =>
      val bases = SQL("SELECT * FROM BASES ORDER BY ID")().map {
        row => (row[Int]("ID"), row[String]("title"))
      }
      Ok(<ul>
        {bases.map { case (id, title) => <li>
          {id}
          -
          {title}
        </li>
        }}</ul>).as(HTML)
    }
  }

}