package builder

import java.io.{File, FileWriter}

import org.xos.meta.project.{JarDependency, Project}

import scala.xml.XML

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
                <dependency>
                    <groupId>{e.asInstanceOf[JarDependency].groupId}</groupId>
                    <artifactId>{e.asInstanceOf[JarDependency].artifactId}</artifactId>
                    <version>{e.asInstanceOf[JarDependency].version}</version>
                </dependency>
            }}
            </dependencies>
</project>

    }

    def writePomToFolder(project: Project,folder:File): Unit ={
        val file =new File(folder.getAbsolutePath+"/pom.xml")
        val writer =new FileWriter(file)
        XML.write(writer,buildPom(project),"utf-8",xmlDecl = true,null)
        writer.flush()
    }
    def buildProject(project:Project, folder :File): Unit ={
        writePomToFolder(project,folder)
    }
}

