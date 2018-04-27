package demo.gateway;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

public class ParallelGateWayDemo {
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
	@Test
	public void crud() {
		deployAndStart();
//		findPersonalTaskList();
//		completeTask();
		
	}
	
	public void deployAndStart() {
		 //创建核心引擎对象
	    Deployment deployment = processEngine.getRepositoryService()// 与流程定义和部署对象相关的service
	            .createDeployment()// 创建一个部署对象
	            .name("ParallelGateWay示例")// 添加部署的名称
	            .addClasspathResource("diagrams/ParallelGateWay.bpmn")// classpath的资源中加载，一次只能加载
	                                                                // 一个文件
	            .addClasspathResource("diagrams/ParallelGateWay.png")// classpath的资源中加载，一次只能加载
	                                                            // 一个文件
	            .deploy();// 完成部署
	    System.out.println("部署ID:" + deployment.getId());
	    System.out.println("部署名称：" + deployment.getName());
	    
	    
	    /******************************启动流程******************************/
	    // 流程定义的key
	    String processDefinitionKey = "ParallelGateWay";
	    ProcessInstance pi = processEngine.getRuntimeService()// 于正在执行的流程实例和执行对象相关的Service
	            .startProcessInstanceByKey(processDefinitionKey);// 使用流程定义的key启动流程实例，key对应hellworld.bpmn文件中id的属性值，使用key值启动，默认是按照最新版本的流程定义启动
	    System.out.println("流程实例ID:" + pi.getId());// 流程实例ID 101
	    System.out.println("流程定义ID:" + pi.getProcessDefinitionId()); // 流程定义ID HelloWorld:1:4
	    
	}
	
	public void findPersonalTaskList(){  
	    //任务办理人  
	    String assignee = "买家";  
	    List<Task> list = processEngine.getTaskService()//  
	                    .createTaskQuery()//  
	                    .taskAssignee(assignee)//个人任务的查询  
	                    .list();  
	    if(list!=null && list.size()>0){  
	        for(Task task:list){  
	            System.out.println("任务ID："+task.getId());  
	            System.out.println("任务的办理人："+task.getAssignee());  
	            System.out.println("任务名称："+task.getName());  
	            System.out.println("任务的创建时间："+task.getCreateTime());  
	            System.out.println("流程实例ID："+task.getProcessInstanceId());  
	            System.out.println("#######################################");  
	        }  
	    }  
	} 
	
	public void completeTask(){  
	    //任务ID  
	    String taskId = "27502";  
	    Map<String, Object> variables = new HashMap<String, Object>();  
	    processEngine.getTaskService()//  
	                    .complete(taskId);  
	    System.out.println("完成任务："+taskId);  
	}  
}
