package controllers

import play.api._
import play.api.db._
import play.api.mvc._
import play.api.Play.current
import play.api.libs.json.Json
import anorm._

object Application extends Controller {

  def index = Action {
    Ok(views.html.index("ShangriLa Anime API Server"))
  }
}