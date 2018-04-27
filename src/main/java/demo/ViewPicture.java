package demo;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

public class ViewPicture {

	@Test
    public void viewPic() throws IOException {
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        // 将生产的图片放到文件夹下
        String deploymentId = "42501";// TODO
        // 获取图片资源名称
        List<String> list = processEngine.getRepositoryService()
                .getDeploymentResourceNames(deploymentId);

        // 定义图片资源名称
        String resourceName = "";
        if (list != null && list.size() > 0) {
            for (String name : list) {
                if (name.indexOf(".png") >= 0) {
                    resourceName = name;
                }
            }
        }

        // 获取图片的输入流
        InputStream in = processEngine.getRepositoryService()
                .getResourceAsStream(deploymentId, resourceName);

        File file = new File("D:/" + resourceName);
        // 将输入流的图片写到D盘下
        FileUtils.copyInputStreamToFile(in, file);
    }
}
