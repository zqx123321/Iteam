import cn.ouctechnology.dao.IAdminDao;
import cn.ouctechnology.dao.IApplicationDao;
import cn.ouctechnology.dao.IDeptDao;
import cn.ouctechnology.dao.IUploadDao;
import cn.ouctechnology.domain.Application;
import cn.ouctechnology.domain.Dept;
import cn.ouctechnology.domain.Upload;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext*.xml")
public class BaseTest {

    @Autowired
    private IDeptDao deptDao;

    @Autowired
    private IUploadDao uploadDao;

    @Autowired
    private IAdminDao adminDao;


    @Autowired
    private IApplicationDao applicationDao;

    @Test
    public void test(){
        Dept dept = new Dept();
        dept.setName("牛逼学院");
        deptDao.save(dept);
        List<Upload> uploads = new ArrayList<>();
        Upload upload = new Upload();
        Upload upload2 = new Upload();
        upload.setUrl("1111");
        upload.setToken(Long.valueOf(11111));
        uploads.add(upload);
        upload2.setUrl("55555");
        upload2.setToken(Long.valueOf(11111));
        uploads.add(upload2);

        for (Upload upload1 : uploads) {
            uploadDao.save(upload1);
        }
        Application application = new Application();
        application.setClubName("111");
        application.setClubCreate("111");
        application.setClubEmail("111");
        application.setIntro("111");
        application.setClubOwner("111");
        application.setClubPhone("111");
//        application.setDeptId(dept);

        applicationDao.save(application);

    }
}
