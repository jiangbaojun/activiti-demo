package demo;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

public class ActivitiTest {
	
	/**
	 * 部署流程定义
	 * @Author		姜宝俊
	 * @Group 		技术组
	 * @Worker	 	1861 
	 * @date 		2017年12月22日 下午2:40:20     
	 * @Company		Vstsoft
	 * @version 	V1.0
	 */
	@Test
	public void deploymentProcessDefinition() {
		/******************************部署流程******************************/
	    //创建核心引擎对象
	    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	    Deployment deployment = processEngine.getRepositoryService()// 与流程定义和部署对象相关的service
	            .createDeployment()// 创建一个部署对象
	            .name("helloworld入门程序")// 添加部署的名称
	            .addClasspathResource("diagrams/HelloWorld.bpmn")// classpath的资源中加载，一次只能加载
	                                                                // 一个文件
	            .addClasspathResource("diagrams/HelloWorld.png")// classpath的资源中加载，一次只能加载
	                                                            // 一个文件
	            .deploy();// 完成部署
	    System.out.println("部署ID:" + deployment.getId());
	    System.out.println("部署名称：" + deployment.getName());
	    
	    
	    /******************************启动流程******************************/
	    // 流程定义的key
	    String processDefinitionKey = "HelloWorld";
	    ProcessInstance pi = processEngine.getRuntimeService()// 于正在执行的流程实例和执行对象相关的Service
	            .startProcessInstanceByKey(processDefinitionKey);// 使用流程定义的key启动流程实例，key对应hellworld.bpmn文件中id的属性值，使用key值启动，默认是按照最新版本的流程定义启动
	    System.out.println("流程实例ID:" + pi.getId());// 流程实例ID 101
	    System.out.println("流程定义ID:" + pi.getProcessDefinitionId()); // 流程定义ID HelloWorld:1:4
	    
	    
	    /******************************查询个人业务******************************/
	    String assignee = "张三";
        List<Task> list = processEngine.getTaskService()// 与正在执行的认为管理相关的Service
                .createTaskQuery()// 创建任务查询对象
                .taskAssignee(assignee)// 指定个人认为查询，指定办理人
                .list();
        if (list != null && list.size() > 0) {
            for (Task task:list) {
                System.out.println("任务ID:"+task.getId());
                System.out.println("任务名称:"+task.getName());
                System.out.println("任务的创建时间"+task);
                System.out.println("任务的办理人:"+task.getAssignee());
                System.out.println("流程实例ID:"+task.getProcessInstanceId());
                System.out.println("执行对象ID:"+task.getExecutionId());
                System.out.println("流程定义ID:"+task.getProcessDefinitionId());
                System.out.println("#################################");
            }
        }
        
        // 执行任务，根据ID  
        String taskId = "47508";  
        processEngine.getTaskService().complete(taskId);  
        System.out.println("完成任务：任务ID:" + taskId);  
	    
	}
}
