package org.xos.components.db.mysql


import com.typesafe.config.Config
import org.xos.meta.project._
import xos.{Logger => logger}

class MysqlConnectionSlot()(implicit node : Node) extends Slot("org.xos.db.mysql.connection.slot","getConnection",required = true,typeOfSlot=SlotType.Producer){
  override def isValid: Boolean = {
    logger.debug(s"validating slot : ${this.name}")
    true
  }

  def produce(): Unit ={
          logger.debug(s"produce $name")
          this.node.parentJob.builder.addImport("java.sql.Connection")
          this.node.parentJob.builder.addMethod()
            .setName("getConnection")
            .addThrows(classOf[Exception])
            .setReturnType(classOf[java.sql.Connection]) //.addException(new Type)
            .setBody( s"""Class.forName("${mysql.jdbcDriver}")
                      try{\n"
                          return java.sql.DriverManager.getConnection("${MysqlConnection.buildUrl("zied", "zied", "test")}");
                        }catch(Exception e){\n  throw e;\n}
                        finally {\n}\n"""")
  }
}

class MysqlConnectionSignal()(implicit node : Node) extends Signal("org.xos.db.mysql.connection.signal","getConnection",parent = node,required = true){

  def produce(): Unit ={
    if(isValid()){
      mysql.produceConnection(node , node.configs.getString("login"),node.configs.getString("password"),node.configs.getString("db"))
      xos.Logger.debug(s"Produce component ${node.id}")
    }
    else
      xos.Logger.debug(s"Configuration component ${node.id} is not Valid")
  }

  override def isValid(): Boolean = {
    node.configs.hasPath("login")&& node.configs.hasPath("password") && node.configs.hasPath("db")
  }
}

// produce method call
//         nreturn type;

object MysqlConnection{
  def buildUrl(user : String , password :String ,db :String ,  port : String = "3306"): String={
    s"jdbc:mysql://localhost:${port}/${db}?user=${user}&password=${password}"
  }
}
/**
 * @author z.benrhouma 
 * @since  01/06/2015
 */
class MysqlConnection(implicit job :Job)
        extends Node( id="db.mysql.connection"
                      ,dependencies = List(JarDependency(mysql.jdbcDriverGroupName,mysql.jdbcDriverArtifactName,mysql.artifactVersion))){


    this.slots   +=  "getConnection"->new MysqlConnectionSlot()
    this.signals +=  "getConnection"->new MysqlConnectionSignal()

  def this(conf : Config)(implicit job :Job){
    this()
    this.configs= conf
  }

  override def configureComponentJavaWriter = {
    this.slots.foreach { s =>
        println(s)
    }
  }

}


// sql --> sqConnection (slot)
// sql --> cnx

