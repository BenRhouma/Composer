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

  init()
  def init() : Unit = {
    job.dependencies= job.dependencies :+ JarDependency(mysql.jdbcDriverGroupName,mysql.jdbcDriverArtifactName,mysql.artifactVersion)
  }

  private def buildUrl(user : String , password :String ,db :String ,  port : String = "3306"): String={
      s"jdbc:mysql://localhost:${port}/${db}?user=${user}&password=${password}"
  }

  override def configureComponentJavaWriter = {

    val me = MethodSpec.methodBuilder("getConnection")
      .addException(classOf[Exception])
      .returns(classOf[java.sql.Connection])//.addException(new Type)
      .addStatement("Class.forName(\"com.mysql.jdbc.Driver\")")
      .addCode("try{\n")
      .addStatement(s"  return DriverManager.getConnection(${buildUrl("zied","zied","test")}})")

    .addCode("}catch(Exception e){\n  throw e;\n} finally {\n}\n")
    .build()

    job.builder= job.builder.addMethod(me)

  }


}
