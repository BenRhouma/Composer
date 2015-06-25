import org.xos.components.db.mysql.MysqlConnection
import org.xos.meta.project.{Job, Project}
/**
 * @author z.benrhouma 
 * @since  14/06/2015
 */
object main {

  def main(args :Array[String]): Unit ={

    implicit val project:Project = new Project(name="")
    implicit val job = new Job(name="test")

    project.jobs :+ job


    val xz = new MysqlConnection()
    println(xz.output)
  }
}
