package org.xos.components.java

import org.xos.meta.project.{SlotType, Slot, Job, Node}

/**
  * @author z.benrhouma
  * @since  28/06/2015
  */

object Root{

}


class RootSlot()(implicit node : Node) extends Slot("org.xos.db.mysql.connection.slot","getConnection",required = true,typeOfSlot=SlotType.Producer){

  def produce(): Unit ={
    this.node.parentJob.builder.addMethod()
      .setName("init")
      .setReturnTypeVoid() //.addException(new Type)
      .setBody("")

  }

  override def isValid: Boolean = true
}

class Root(implicit job :Job) extends Node( id="xos.common.root"){

  this.slots+= "init" -> new RootSlot()

  override def configureComponentJavaWriter(): Unit = {
     val me = job.builder.addMethod()
     me.setPublic()
     me.setReturnType("void")
   }
 }
