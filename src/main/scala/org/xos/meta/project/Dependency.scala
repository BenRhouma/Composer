package org.xos.meta.project

import java.io.File


trait Dependency

case class JarDependency(groupId : String , artifactId :String , version: String) extends Dependency{
  var dependencies: List[Dependency] = null
  var excludes: List[String] = null
}

case class JobDependency(job : Job) extends Dependency
case class ResourceDependency(file : File) extends Dependency

