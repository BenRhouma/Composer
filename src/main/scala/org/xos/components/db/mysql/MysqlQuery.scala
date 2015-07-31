package org.xos.components.db.mysql

import com.typesafe.config.Config
import org.xos.meta.project._

/**
 * @author z.benrhouma 
 * @since  25/07/2015
 */

class MysqlQuery(implicit job :Job)
  extends Node( id="db.mysql.query"){

  var query : String =""

  this.slots   +=  "getRow"->new MysqlQuerySlot()
  this.signals +=  "getConnection"->new MysqlQuerySignal()

  def this(conf : Config)(implicit job :Job){
    this()
    this.configs= conf
  }

  override def configureComponentJavaWriter = {
    this.slots.foreach { s =>
      println(s)
    }
  }



  def produceJdbc()={
    var query = """
  // assume that conn is an already created JDBC connection (see previous examples)

 Statement stmt = null;
 ResultSet rs = null;

 try {
     stmt = getConnection().createStatement();
     rs = stmt.executeQuery("SELECT foo FROM bar");


     // Now do something with the ResultSet ....
 }
 catch (SQLException ex){
     // handle any errors
     System.out.println("SQLException: " + ex.getMessage());
     System.out.println("SQLState: " + ex.getSQLState());
     System.out.println("VendorError: " + ex.getErrorCode());
 }
 finally {
     if (rs != null) {
         try {
             rs.close();
         } catch (SQLException sqlEx) { } // ignore

         rs = null;
     }
     if (stmt != null) {
         try {
             stmt.close();
         } catch (SQLException sqlEx) { } // ignore

         stmt = null;
     }
 } """
  }

}


class MysqlQuerySignal()(implicit node : Node) extends Signal("org.xos.db.mysql.query.signal","getConnection",parent = node,required = true){

  def produce(): Unit ={
    if(isValid())
      xos.Logger.info(s"Configuration component ${node.id} is Valid")

    else
      xos.Logger.info(s"Configuration component ${node.id} is not Valid")
  }

  override def isValid(): Boolean = {
      parent.signals("getConnection").slot != null
  }
}


class MysqlQuerySlot()(implicit node : Node)
  extends Slot("org.xos.db.mysql.query.slot","getRow",required = true,typeOfSlot=SlotType.Producer){
  override def isValid: Boolean = {
    xos.Logger.debug(s"validating slot : ${this.name}")
    true
  }

  def produce(): Unit ={

  }
}

