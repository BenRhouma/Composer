package org.xos.meta.project

import java.io.File
import java.util.UUID

import com.squareup.javapoet.TypeSpec.Builder
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
                         , dependencies: Seq[Dependency]=Seq()
                          )(implicit job :Job){

  initNode()
  def initNode() ={
    configureComponentJavaWriter()
    job.dependencies= job.dependencies ++ dependencies

  }



  def parentJob :Job = job

  def configureComponentJavaWriter() : Unit


  def register()={
    Platform.registry.inject(this)
  }
}

case class slot(source :Node , destination :Node, name: String)

 case class Job (id : String = UUID.randomUUID().toString,name: String)(implicit project : Project){
   var version: Double = metaModel.engineVersion
   var mainNodes: Seq[Node] = Seq()
   var dependencies: Seq[Dependency] = Seq()
   var builder : Builder  =TypeSpec.classBuilder(name).addModifiers(javax.lang.model.element.Modifier.PUBLIC)

   lazy val parentProject = project
   //called after the constructor to add the current job to the implicit project
   init()
   def init(){
     project.jobs=project.jobs :+ this
   }
    // write java file of the current job
   def print() ={
      val sp =builder.addModifiers().build()
      JavaFile.builder( project.packageName,sp)
       .build().toString
   }
}


case class Project(name:String,version:Double=metaModel.engineVersion){


  var jobs: Seq[Job]= Seq()
  val packageName : String = "org.xos.model"
  val generatedFilesPath = "src/main/gen-java"

  init()
  def init(): Unit ={
    new File(generatedFilesPath).mkdirs()
    new File(s"$generatedFilesPath/src/main/java").mkdirs()
    new File(s"$generatedFilesPath/src/main/resources").mkdirs()
  }
  def Project(){}

}




