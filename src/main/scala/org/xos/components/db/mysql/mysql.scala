package org.xos.components.db.mysql

import org.xos.meta.project.Node

/**
 * @author z.benrhouma 
 * @since  15/06/2015
 */
object mysql {
  val jdbcDriver = "com.mysql.jdbc.Driver"

  var jdbcDriverGroupName ="mysql"
  var jdbcDriverArtifactName ="mysql-connector-java"
  var artifactVersion ="5.1.35"


  def produceConnection(node :Node,user :String, pwd: String, db :String ): Unit ={
    node.parentJob.builder.addImport("java.sql.Connection")
    node.parentJob.builder.addMethod()
      .setName("getConnection")
      .setPrivate()
      .addThrows(classOf[Exception])
      .setReturnType(classOf[java.sql.Connection]) //.addException(new Type)
      .setBody( s"""Class.forName("${mysql.jdbcDriver}");
                    try{
                          return java.sql.DriverManager.getConnection("${MysqlConnection.buildUrl(user, pwd, db)}");
                     }catch(Exception e){
                        xos.Logger.error("error during ${node.id}");
                     }
                """)
  }
}
