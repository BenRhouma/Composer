package org.xos.components.db.mysql


import com.squareup.javapoet.MethodSpec
import org.xos.meta.project.{JarDependency, Job, Node}

/**
 * @author z.benrhouma 
 * @since  01/06/2015
 */
class MysqlConnection(implicit job :Job)
        extends Node( id="db.mysql.connection"
                      ,classType="org.xos.processor.MysqlConnectionProcessor"
                      ,path="org.xos.db"){

//  val db = ???
//  val port= ???
//  val user= ???
//  val password= ???

  def initDependencies()={
    this.dependencies :+ JarDependency(mysql.jdbcDriverGroupName,mysql.jdbcDriverArtifactName,mysql.artifactVersion)
  }
  private def buildUrl(user : String , password :String ,db :String ,  port : String = "3306"): String={
      s"jdbc:mysql://localhost:${port}/${db}?user=${user}&password=${password}"
  }
  override def output: String = {

    val me = MethodSpec.methodBuilder("getConnection").returns(classOf[java.sql.Connection])//.addException(new Type)
      .
      .addStatement("Class.forName(\"com.mysql.jdbc.Driver\")")
      .addCode("try{\n")
      .addStatement(s"  return DriverManager.getConnection(${buildUrl("zied","zied","test")}})")

    .addCode("}catch(Exception e){\n  throw e;\n} finally {\n}\n")
    .build()


    this.job.builder.toBuilder.addMethod(me).build().toString

  }
}