package org.xos.meta.project

import java.util.UUID

import com.squareup.javapoet.{JavaFile, TypeSpec}
import org.xos.meta.platform.Platform

/**
 * @author z.benrhouma 
 * @since  27/05/2015
 */
object metaModel {
  val engineVersion = 1.0
}

abstract case class Node(id:String
                         , classType:String
                         , path : String
                         , slots : Seq[slot]= Seq()
                         , dependencies: Seq[JarDependency]=Seq()
                          )(implicit job :Job){

  def register()={
      Platform.registry.inject(this)
  }

  def parentJob :Job = job

  def output : String

}

case class slot(source :Node , destination :Node, name: String)

 class Job (id : String = UUID.randomUUID().toString,name: String)(implicit project : Project){
   var version: Double = metaModel.engineVersion
   var mainNodes: Seq[Node] = Seq()
   var dependencies: Seq[Dependency] = Seq()
   var builder : TypeSpec  =TypeSpec.classBuilder(name).build()

   lazy val parentProject = project
   //called after the constructor to add the current job to the implicit project
   init()
   def init(){
     project.jobs=project.jobs :+ this
   }
    // write java file of the current job
   def print() ={
     JavaFile.builder( project.packageName,builder)
       .build().toString
   }
}


case class Project(name:String,version:Double=metaModel.engineVersion){

  var jobs: Seq[Job]= Seq()
  val packageName : String = "org.xos.model"
  def Project(){}

}




