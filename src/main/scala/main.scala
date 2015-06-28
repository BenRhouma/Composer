import java.io.File

import builder.ProjectBuilder
import org.xos.components.context.Hocon
import org.xos.components.db.mysql.MysqlConnection
import org.xos.meta.project.{Job, Project}

/**
 * @author z.benrhouma 
 * @since  14/06/2015
 */
object main {

  implicit val project: Project = new Project(name = "model-generator")
  implicit val job = new Job(name = "logSql")


  def addComponents(): Unit ={
    new MysqlConnection()
    new Hocon()
  }

  def main(args: Array[String]): Unit = {


    val currentFolder : File = new File("./src/main/gen-java")

    addComponents()
    ProjectBuilder.buildProject(project,currentFolder )

  }
}
