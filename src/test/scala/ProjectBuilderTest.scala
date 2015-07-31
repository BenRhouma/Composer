import java.io.File

import builder.ProjectBuilder
import com.typesafe.config.{ConfigValueFactory, ConfigFactory}
import org.xos.components.db.mysql.{MysqlQuery, MysqlConnection}
import org.xos.meta.project.{Job, Project}

/**
 * @author z.benrhouma 
 * @since  11/06/2015
 */
class ProjectBuilderTest {


  @org.testng.annotations.Test
  def createProject() {
    val projectName = "project test"
    val p = Project(projectName)
    assert(p != null)
    assert(p.name != null && p.name.eq(projectName))
  }


  @org.testng.annotations.Test
  def createNode() {
    val projectName = "project test"
    val p = Project(projectName)
    assert(p != null)
    assert(p.name != null && p.name.eq(projectName))
  }

  @org.testng.annotations.Test
  def connect() {
    implicit val project: Project = new Project(name = "model-generator")
    implicit val job = new Job(name = "Poc")

    val mc = new MysqlConnection(ConfigFactory.empty()
      .withValue("login", ConfigValueFactory.fromAnyRef("zied"))
      .withValue("password", ConfigValueFactory.fromAnyRef("zied"))
      .withValue("db", ConfigValueFactory.fromAnyRef("remot")))

    val mq = new MysqlQuery()

    job.root.connect(job.root.slots("init"), mc.signals("onOk"))

    mc.connect(mc.slots("getConnection"), mq.signals("getConnection"))

    val currentFolder: File = new File("./src/main/gen-java")
    ProjectBuilder.buildProject(project, currentFolder)

    assert(currentFolder.exists())

  }

}
