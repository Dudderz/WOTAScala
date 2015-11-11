package GUI

import DatabaseConnector.{CustomerOrderLineSQL, ProductSQL}
import Entities.{CustomerOrderLine, Product}
import scalafx.Includes._
import scalafx.scene.image.Image
import scalafx.scene.control.Button
import scalafx.scene.layout.GridPane
import scalafx.scene.control.{Label, ComboBox}
import scalafx.event.{ActionEvent, EventHandler}
import scalafx.collections.ObservableBuffer
import scalafx.stage.Popup
import scalafx.scene.layout.{StackPane, BorderPane}
import javafx.scene.paint.ImagePattern
import scalafx.geometry.{Pos, Orientation, Insets}
import scalafx.scene.shape.{Rectangle}
import scalafx.stage.Popup
import scalafx.collections.ObservableBuffer
import scalafx.scene.paint.Color
import scalafx.application.JFXApp.PrimaryStage

/**
 * @author tdudley
 */
class PurchaseOrderForm(stage : PrimaryStage) 
{
   def createAlertPopup(popupText : String) = new Popup
   {
     inner =>
   content.add(new StackPane 
     {
     println("called")
     
      children = List(
         new Rectangle 
         {
           width = 400
           height = 300
           //arcWidth = 20
           //arcHeight = 20
           fill = Color.White
           stroke = Color.WhiteSmoke
           strokeWidth = 4
         
           
         },
         new BorderPane 
         {
           center = new GridPane
           {
             padding = Insets(20, 20, 20, 20)
             add(
                 new Label
                 {
                   text = "Product ID: "
                   style = "-fx-font-size: 12pt"
                 }, 1, 1)
                 

             add(
               new Label
               {
                 text = "Product Quantity: "
                 style = "-fx-font-size: 10pt"
               }, 1, 3)
           }
           bottom = new Button("Close") 
           {
             onAction = 
             {
               e: ActionEvent => inner.hide
             }
             alignmentInParent = Pos.CENTER
             margin = Insets(10, 0, 10, 0)
           }
         }
     )
   }.delegate
   )
   }
  
   def showPopUp() : Unit =
   {
     val popup = createAlertPopup("")
     popup show(stage, 800, 400)
   }
   
}