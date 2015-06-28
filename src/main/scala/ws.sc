import org.xos.components.db.mysql.MysqlConnection

val xz = new MysqlConnection()
val x ={xz.configureComponentJavaWriter}

println(x.toString())