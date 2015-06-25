package org.xos.components.java

import org.jsource.meta.ClassBloc
import org.xos.meta.project.{Job, Node}

/**
 * @author z.benrhouma
 * @since  01/06/2015
 */
class JavaComponent extends Node( id="java"
  ,classType="org.xos.processor.JavaProcessor"
  ,path="org.xos.java")
    {

  var body :String = ""

  def setBody(value: String, blocType : String = "method", name :String ="")(implicit job : Job): Unit ={
    if(blocType == "static"){
      //body = job.builder.
    }
  }

  def setBody(proccessor : ClassBloc)={
      body = proccessor.render();
  }

  override def output: String = {
    ""
  }
}
