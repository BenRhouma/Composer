package org.xos.components.java

import javax.lang.model.element.Modifier

import org.xos.meta.project.{Job, Node}

/**
  * @author z.benrhouma
  * @since  28/06/2015
  */

object Java {

 }

class Java(implicit job :Job) extends Node( id="xos.common.java.for"
   ){

   override def configureComponentJavaWriter(): Unit = {

   }
 }
