package org.xos.meta.project

import com.typesafe.config.ConfigFactory
import org.xos.meta.platform.Platform
import xos.{Logger=>logger}
import org.xos.meta.project.SlotType.SlotType

case class UIHints(var visible : Boolean = true , x : Integer =0, y : Integer =0, h : Integer =50, w : Integer=40)

object SlotType extends Enumeration {
  type SlotType = Value
  val Producer,Consumer,Static = Value
}

abstract case class Slot(id : String
                         ,  name: String
                         , required : Boolean = false
                         , var signal: Signal= null
                         , typeOfSlot : SlotType )(implicit  node:Node ) {
  def isValid : Boolean // virtual
  def produce : Unit
}
abstract case class Signal( id : String ,  name: String,parent :Node, required : Boolean = false , var slot : Slot = null){
  def isValid : Boolean
  def produce : Unit


}

class onOk()(implicit node : Node)
  extends Signal("org.xos.signal","onOk",required = false,parent = node){
  override def isValid: Boolean =true
  def produce(): Unit ={}
}

abstract case class Node(id:String
                         , var slots : scala.collection.mutable.Map[String , Slot]= scala.collection.mutable.Map()
                         , var signals : scala.collection.mutable.Map[String , Signal]= scala.collection.mutable.Map()
                         , dependencies: List[Dependency]=Nil
                         , state : Boolean = false
                         , uIHints: UIHints = UIHints()
                         , valid : Boolean = true
                          )(implicit job :Job){

  var configs :com.typesafe.config.Config = ConfigFactory.empty()
  job.dependencies= job.dependencies ++ dependencies
  implicit  val node : Node = this

  signals.put("onOk" ,new onOk())
  def connect(slot: Slot , s : Signal) ={
      slot.signal=s
      s.slot=slot
  }

  def build(): Unit={
    xos.Logger.debug(s"Building node $id")
    slots.foreach{
      s : (String, Slot) => {
        if (s._2 != null && s._2.signal!= null) {
          logger.debug(s"trigger ($id.slot(${s._1}) --> ${s._2.signal.parent.id}.signal(${s._2.signal.name})")
          s._2.signal.produce
        }
      }
    }
  }

  /**
   * check whereas the component is valid by check every input and output
   * @return
   */
  def validate(): Boolean={
    slots.values.exists(s => !s.isValid) && signals.values.exists(s => !s.isValid)
  }



  def parentJob :Job = job

  def configureComponentJavaWriter() : Unit


  def register()={
    Platform.registry.inject(this)
  }
}
