package org.xos.components.context

import org.xos.meta.project.{JarDependency, Job, Node}

/**
 * @author z.benrhouma 
 * @since  28/06/2015
 */

object Hocon {
  var groupName ="com.typesafe"
  var artifactName ="config"
  var version ="1.0.2"
}

class Hocon (implicit job :Job)    extends Node( id="xos.common.context"
  ,dependencies = List(JarDependency(Hocon.groupName,Hocon.artifactName,Hocon.version))){

  this.uIHints.visible = false

  override def configureComponentJavaWriter(): Unit = {
//    val me = MethodSpec.methodBuilder("getContext").addModifiers(Modifier.PUBLIC)
//      .returns(TypeName.VOID)//.addException(new Type)
//      .build()
//    job.builder= job.builder.addMethod(me)
  }
}
