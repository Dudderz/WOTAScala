import DatabaseConnector.DBConnector

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.stage.Stage
import scalafx.stage.StageStyle

import scalafx.scene.Scene
import scalafx.scene.layout.HBox
import scalafx.scene.effect.DropShadow
import scalafx.scene.paint.Color._
import scalafx.scene.paint.LinearGradient
import scalafx.scene.paint.Stops
import scalafx.scene.paint.Color

import scalafx.geometry.Insets

import scalafx.scene.text.Text

import scalafx.collections.ObservableBuffer
import scalafx.scene.control.TableColumn._
import scalafx.scene.control.{TableCell, TableColumn, TableView}
import scalafx.scene.paint.Color
import scalafx.scene.shape.Circle

/**
 * @author tdudley
 */
object Main extends JFXApp {

  val db = new DBConnector()
  
  db connect()
  
  stage = new PrimaryStage 
  {
    initStyle(StageStyle.UNIFIED)
    title = "Warehouse Order Tracking Application"
    width = 800
    height = 600   
    
    scene = new Scene
    {
      content = new HBox
      {
        children = Seq(
          new Text {
            text = "Scala"
            style = "-fx-font: italic bold 100pt sans-serif"
            fill = new LinearGradient(
               endX = 0,
               stops = Stops(White, DarkGray)
            )
            
            effect = new DropShadow{
              color = DarkGray
              radius = 15
              spread - 0.25
            }
          },
          new Text{
            text = "FX"
            style = "-fx-font: italic bold 100pt sans-serif"
            fill = new LinearGradient(
                endX = 0,
                stops = Stops(White, DarkGray)
            )
            
            effect = new DropShadow{
              color = DarkGray
              radius = 15
              spread = 0.25
            }
          }
        )
      }
    }
   
  }
  
}
