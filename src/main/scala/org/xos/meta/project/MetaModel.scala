package org.xos.meta.project

import java.io.File
import java.util.UUID

import org.jboss.forge.roaster.Roaster
import org.jboss.forge.roaster.model.source.JavaClassSource
import org.xos.components.java.Root
import org.xos.meta.platform.Platform

/**
 * @author z.benrhouma 
 * @since  27/05/2015
 */
object metaModel {
  val engineVersion = 1.0
}

// ui properties of the base node { to be used depending the graphical environment}

 case class Job (id : String = UUID.randomUUID().toString,name: String)(implicit project : Project){
   var version: Double = metaModel.engineVersion
   var mainNodes: Seq[Node] = Seq()
   var dependencies: Seq[Dependency] = Seq()
   val builder : JavaClassSource  = Roaster.create(classOf[JavaClassSource])
   val root = new Root()(this)
   builder
     .setPublic()
     .setName(name)
     .setPackage(project.packageName)

    def build()={
      xos.Logger.info(s"Building job $name")
      root.slots.foreach{
        s : (String, Slot) => {
          if (s._2 != null) {
              s._2.signal.parent.build()
          }
        }
      }
    }
//     TypeSpec.classBuilder(name).addModifiers(javax.lang.model.element.Modifier.PUBLIC)

   lazy val parentProject = project
   //called after the constructor to add the current job to the implicit project
   init()
   def init(){
     project.jobs=project.jobs :+ this
   }
    // write java file of the current job
   def getSourceCode() ={
      builder.toString
   }
}


case class Project(name:String,version:Double=metaModel.engineVersion){


  var jobs: Seq[Job]= Seq()
  val packageName : String = Platform.groupId
  val generatedFilesPath = Platform.generatedFilePath

  init()
  def init(): Unit ={
    new File(generatedFilesPath).mkdirs()
    new File(s"$generatedFilesPath/src/main/java").mkdirs()
    new File(s"$generatedFilesPath/src/main/resources").mkdirs()
  }

}




