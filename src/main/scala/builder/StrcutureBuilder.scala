package builder

import java.io.{File, FileWriter}

import org.xos.meta.platform.Platform
import org.xos.meta.project._

import scala.xml._

/**
 * @author z.benrhouma 
 * @since  27/05/2015
 */

object ProjectBuilder {
  private def buildPom(project: Project) = {
    <project xmlns="http://maven.apache.org/POM/4.0.0"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
      <modelVersion>4.0.0</modelVersion>
      <groupId>{Platform.groupId}</groupId>
      <artifactId>{project.name}</artifactId>
      <packaging>jar</packaging>
      <version>{project.version}</version>

      <properties>
        <project.build.sourceEncoding>{Platform.encoding}</project.build.sourceEncoding>
        <maven.compiler.source>{Platform.javaSourceVersion}</maven.compiler.source>
        <maven.compiler.target>{Platform.javaCompilerVersion}</maven.compiler.target>
      </properties>

      <dependencies>
        {(for {
            j <- project.jobs; d <- j.dependencies
        } yield {
                d match {
                  case dependency: JarDependency => dependency
                  case _ =>
                }
              }
        ).map { e =>
        // empty line
        <dependency>
          <groupId>{e.asInstanceOf[JarDependency].groupId}</groupId>
          <artifactId>{e.asInstanceOf[JarDependency].artifactId}</artifactId>
          <version>{e.asInstanceOf[JarDependency].version}</version>
        </dependency>
      }}
      </dependencies>
    </project>

  }

  def writePomToFolder(project: Project, folder: File): Unit = {
    val file = new File(folder.getAbsolutePath + "/pom.xml")
    val writer = new FileWriter(file)
    val printer = new scala.xml.PrettyPrinter(80, 2)
    val pom = buildPom(project)
    XML.write(writer, pom, "utf-8", xmlDecl = true, null)
    writer.flush()
  }

  def writeJavaFiles(project: Project, folder: File): Unit = {
    for (job: Job <- project.jobs) {
      val fw = new FileWriter(s"${folder.getAbsolutePath}/src/main/java/${job.name}.java")
      job.build()
      fw.write(job.getSourceCode())
      fw.flush()
    }
  }



  def buildProject(project: Project, folder: File): Unit = {
    writePomToFolder(project, folder)
    writeJavaFiles(project, folder)
  }
}

