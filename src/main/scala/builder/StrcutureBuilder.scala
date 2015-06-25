package builder

import org.xos.meta.project.{Project, metaModel}

/**
 * @author z.benrhouma 
 * @since  27/05/2015
 */
class StrcutureBuilder {

}

class ProjectBuilder{

    def buildProject(name:String): Unit ={
        // build new project
        Project(name,metaModel.engineVersion)

    }
}

