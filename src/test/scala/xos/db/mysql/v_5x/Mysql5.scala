package xos.db.mysql.v_5x

import org.xos.components.db.mysql.MysqlConnection
import org.xos.meta.project.{Project, Job}

/**
 * @author z.benrhouma 
 * @since  11/06/2015
 */
class Mysql5 {

    @org.testng.annotations.Test
    def createDbConnectionNode() {
        implicit val project:Project = new Project(name="test")
        implicit val jbo:Job = new Job(name="test")
        val mysqlConnectNode = new MysqlConnection()
    }

}
