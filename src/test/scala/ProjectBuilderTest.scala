import org.xos.meta.project.Project

/**
 * @author z.benrhouma 
 * @since  11/06/2015
 */
class ProjectBuilderTest {


    @org.testng.annotations.Test
    def createProject() {
        val projectName = "project test"
        val p = Project(projectName)
        assert(p!=null)
        assert(p.name !=null && p.name.eq(projectName))
    }


    @org.testng.annotations.Test
    def createNode() {
        val projectName = "project test"
        val p = Project(projectName)
        assert(p!=null)
        assert(p.name !=null && p.name.eq(projectName))
    }

}
