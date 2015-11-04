package GUI

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.control.TableColumn._
import scalafx.scene.paint.Color._
import scalafx.scene.Scene
import scalafx.scene.layout.HBox
import scalafx.scene.paint.LinearGradient
import scalafx.scene.effect.DropShadow
import scalafx.scene.paint.Stops
import scalafx.scene.text.Text

/**
 * @author tdudley
 */
class GUIMain (stage : PrimaryStage)
{

  stage title = "Warehouse Order Tracking Application"
  stage width = 800
  stage height = 600   
    
  val scene = new Scene
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
  
  stage setScene(scene)
}
