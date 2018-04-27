package demo.deploy;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.zip.ZipInputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.junit.Test;

public class deployDemo {

	@Test
	public void deploy() {
		try {
			deploy1();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deploy1() {
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	    Deployment deployment = processEngine.getRepositoryService()// 与流程定义和部署对象相关的service
	            .createDeployment()// 创建一个部署对象
	            .name("helloworld入门程序1")// 添加部署的名称
	            .addClasspathResource("diagrams/MyProcess.bpmn")// classpath的资源中加载，一次只能加载 一个文件
	            .addClasspathResource("diagrams/MyProcess.png")// classpath的资源中加载，一次只能加载 一个文件
	            .deploy();// 完成部署
	    System.out.println("部署ID:" + deployment.getId());
	    System.out.println("部署名称：" + deployment.getName());
	}
	
	public void deploy2() throws FileNotFoundException {
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		//获取资源相对路径  
		String bpmnPath = "D:\\myProject\\github\\activiti\\activiti-demo\\src\\main\\resources\\diagrams\\MyProcess.bpmn";  
		String pngPath = "D:\\myProject\\github\\activiti\\activiti-demo\\src\\main\\resources\\diagrams\\MyProcess.png";  
		//读取资源作为一个输入流  
		FileInputStream bpmnfileInputStream = new FileInputStream(bpmnPath);  
		FileInputStream pngfileInputStream = new FileInputStream(pngPath);  
		
		Deployment deployment = processEngine.getRepositoryService()//获取流程定义和部署对象相关的Service  
				.createDeployment()//创建部署对象  
				.name("helloworld stream部署")
				.addInputStream("MyProcess.bpmn",bpmnfileInputStream)  
				.addInputStream("MyProcess.png", pngfileInputStream)  
				.deploy();//完成部署  
		System.out.println("部署ID："+deployment.getId());//1  
		System.out.println("部署时间："+deployment.getDeploymentTime());  
	}
	
	/**
	 * zip方式，一次加载多个流程图文件
	 * @throws FileNotFoundException		
	 * @Author		姜宝俊
	 * @Group 		技术组
	 * @Worker	 	1861 
	 * @date 		2017年12月28日 下午5:27:10     
	 * @Company		Vstsoft
	 * @version 	V1.0
	 */
	public void deploy3() throws FileNotFoundException {
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		//从classpath路径下读取资源文件  
	    InputStream in = this.getClass().getClassLoader().getResourceAsStream("diagrams/resources.zip");  
	    ZipInputStream zipInputStream = new ZipInputStream(in);  
	    Deployment deployment = processEngine.getRepositoryService()//获取流程定义和部署对象相关的Service  
	                    .createDeployment()//创建部署对象              
	                    .name("zip部署")
	                    .addZipInputStream(zipInputStream)//使用zip方式部署，将helloworld.bpmn和helloworld.png压缩成zip格式的文件  
	                    .deploy();//完成部署  
	    System.out.println("部署ID："+deployment.getId());//1  
	    System.out.println("部署时间："+deployment.getDeploymentTime());  
	    
//	    String processDefinitionKey = "myProcess";
//	    ProcessInstance pi = processEngine.getRuntimeService()// 于正在执行的流程实例和执行对象相关的Service
//	            .startProcessInstanceByKey(processDefinitionKey);// 使用流程定义的key启动流程实例，key对应hellworld.bpmn文件中id的属性值，使用key值启动，默认是按照最新版本的流程定义启动
//	    System.out.println("流程实例ID:" + pi.getId());// 流程实例ID 101
//	    System.out.println("流程定义ID:" + pi.getProcessDefinitionId()); // 流程定义ID HelloWorld:1:4
//	    
//	    String processDefinitionKey1 = "HelloWorld";
//	    ProcessInstance pi1 = processEngine.getRuntimeService()// 于正在执行的流程实例和执行对象相关的Service
//	            .startProcessInstanceByKey(processDefinitionKey1);// 使用流程定义的key启动流程实例，key对应hellworld.bpmn文件中id的属性值，使用key值启动，默认是按照最新版本的流程定义启动
//	    System.out.println("流程实例ID:" + pi1.getId());// 流程实例ID 101
//	    System.out.println("流程定义ID:" + pi1.getProcessDefinitionId()); // 流程定义ID HelloWorld:1:4
	}
}
