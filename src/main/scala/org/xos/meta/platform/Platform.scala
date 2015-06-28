package org.xos.meta.platform

import org.xos.meta.project.Node

/**
 * @author z.benrhouma 
 * @since  11/06/2015
 */

case class Tree[+T](category:String , children : Seq[Tree[T]]=Seq()){
    def isPartOf(node :Node): Boolean= node.path.take(node.path.lastIndexOf(".")).startsWith(category)
    def inject(node :Node )={

    }
}
// platform is the abstraction of the ide itself
object Platform {
    val registry:Tree[Node] = Tree(category = "org.xos")
    val javaSourceVersion = 1.7
    val javaCompilerVersion = 1.7
    val encoding = "UTF-8"
    val groupId = "org.xos"

    def subscribe(node :Node): Unit ={
       print(node.id)
       node.path.split("\\.").map{
         path=> println(path)
       }
    }


}
