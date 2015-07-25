import java.io.File

import builder.ProjectBuilder
import com.typesafe.config.{ConfigFactory, ConfigValueFactory}
import org.xos.components.db.mysql.{MysqlQuery, MysqlConnection}
import org.xos.meta.project.{Job, Project}

/**
 * @author z.benrhouma 
 * @since  14/06/2015
 */
object main {

  implicit val project: Project = new Project(name = "model-generator")
  implicit val job = new Job(name = "Poc")



  def main(args: Array[String]): Unit = {

    val mc = new MysqlConnection( ConfigFactory.empty()
      .withValue("login",ConfigValueFactory.fromAnyRef("zied"))
      .withValue("password",ConfigValueFactory.fromAnyRef("zied"))
      .withValue("db",ConfigValueFactory.fromAnyRef("remot")))

    val mq = new MysqlQuery()

    job.root.connect(job.root.slots("init"),mc.signals("onOk"))

    mc.connect(mc.slots("getConnection") , mq.signals("getConnection"))

    val currentFolder : File = new File("./src/main/gen-java")
    ProjectBuilder.buildProject(project,currentFolder)

  }
}

// MysCnx --> MysqlQuery [query="select * from table"] -r-> log(r)
