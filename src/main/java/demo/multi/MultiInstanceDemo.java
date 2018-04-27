package demo.multi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

public class MultiInstanceDemo {
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
	@Test
	public void multi() {
		deployAndStart();
//		completeTask();
		
	}
	
	public void deployAndStart() {
		 //创建核心引擎对象
	    Deployment deployment = processEngine.getRepositoryService()// 与流程定义和部署对象相关的service
	            .createDeployment()// 创建一个部署对象
	            .name("mutiInstance示例")// 添加部署的名称
	            .addClasspathResource("diagrams/mutiInstance.bpmn")// classpath的资源中加载，一次只能加载
	                                                                // 一个文件
	            .addClasspathResource("diagrams/mutiInstance.png")// classpath的资源中加载，一次只能加载
	                                                            // 一个文件
	            .deploy();// 完成部署
	    System.out.println("部署ID:" + deployment.getId());
	    System.out.println("部署名称：" + deployment.getName());
	    
	    
	    /******************************启动流程******************************/
	    // 流程定义的key
	    String processDefinitionKey = "MultiInstance";
	    ProcessInstance pi = processEngine.getRuntimeService()// 于正在执行的流程实例和执行对象相关的Service
	            .startProcessInstanceByKey(processDefinitionKey);// 使用流程定义的key启动流程实例，key对应hellworld.bpmn文件中id的属性值，使用key值启动，默认是按照最新版本的流程定义启动
	    System.out.println("流程实例ID:" + pi.getId());// 流程实例ID 101
	    System.out.println("流程定义ID:" + pi.getProcessDefinitionId()); // 流程定义ID HelloWorld:1:4
	    setProcessVariables(pi.getId());
	}
	
	public void setProcessVariables(String processInstanceId){  
	    TaskService taskService = processEngine.getTaskService();//获取任务的Service，设置和获取流程变量  
	    //查询当前办理人的任务ID  
	    Task task = taskService.createTaskQuery()  
	            .processInstanceId(processInstanceId)//使用流程实例ID  
	            .singleResult();  
	    List<String> assigneeList = new ArrayList<String>();
	    assigneeList.add("小强");
	    assigneeList.add("小明");
	    assigneeList.add("小红");
	    taskService.setVariable(task.getId(), "assigneeList", assigneeList);  
	    System.out.println("会签集合:" + assigneeList.toString());
	}  
	
	public void completeTask(){  
	    //任务ID  
	    String taskId = "75017";  
	    //完成任务的同时，设置流程变量，让流程变量判断连线该如何执行  
	    Map<String, Object> variables = new HashMap<String, Object>();  
	    //其中message对应sequenceFlow.bpmn中的${message=='不重要'}，不重要对应流程变量的值  
	    //如果没有符合的条件，流程会结束在当前任务。能不能没有符合条件，不允许完成任务？？？
	    variables.put("message", "重要");  
	    processEngine.getTaskService()//  
	                    .complete(taskId,variables);  
	    System.out.println("完成任务："+taskId);  
	}  
}
