package org.xos.meta.project


trait Dependency

case class JarDependency(groupId : String , artifactId :String , version: String) extends Dependency{
  var dependencies: List[Dependency] = null
  var excludes: List[String] = null
}
