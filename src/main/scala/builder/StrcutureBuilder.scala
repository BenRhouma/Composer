package builder

import java.io.{File, FileWriter}

import org.xos.meta.project.{JarDependency, Job, Project}

import scala.xml._

/**
 * @author z.benrhouma 
 * @since  27/05/2015
 */
class StrcutureBuilder {

}

object ProjectBuilder{
    private def buildPom(project: Project)={
        <project xmlns="http://maven.apache.org/POM/4.0.0"
                 xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                 xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
            <modelVersion>4.0.0</modelVersion>
            <groupId>org.xos</groupId>
            <artifactId>{project.name}</artifactId>
            <packaging>jar</packaging>
            <version>{project.version}</version>

            <properties>
                <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
                <maven.compiler.source>1.7</maven.compiler.source>
                <maven.compiler.target>1.7</maven.compiler.target>
            </properties>

            <dependencies>
                {(for{
                p <- project.jobs ; d <- p.dependencies
            } yield {
                    d match {
                        case dependency: JarDependency => dependency
                        case _ =>
                    }
                }
              ).map { e =>

              val c =
                <dependency>
                    <groupId>{e.asInstanceOf[JarDependency].groupId}</groupId>
                    <artifactId>{e.asInstanceOf[JarDependency].artifactId}</artifactId>
                    <version>{e.asInstanceOf[JarDependency].version}</version>
                </dependency>

              val xm : Elem =Elem(null, null, Null, TopScope, Text(""))

              c

            }}
            </dependencies>
</project>

    }

    def writePomToFolder(project: Project,folder:File): Unit ={
        val file =new File(folder.getAbsolutePath+"/pom.xml")
        val writer =new FileWriter(file)
      val printer = new scala.xml.PrettyPrinter(80, 2)
      val pom = buildPom(project)
        XML.write(writer,pom,"utf-8",xmlDecl = true,null)
        writer.flush()
    }

    def writeJavaFiles(project: Project, folder: File): Unit ={
        for(p : Job <- project.jobs){
           val fw = new FileWriter(s"${folder.getAbsolutePath}/src/main/java/${p.name}.java")
            fw.write(p.print())
            fw.flush()
        }
    }

    def buildProject(project:Project, folder :File): Unit ={
        writePomToFolder(project,folder)
        writeJavaFiles(project,folder)
    }
}

