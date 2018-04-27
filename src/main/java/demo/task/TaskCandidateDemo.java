package demo.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.junit.Test;

//组用户任务、认领、完成
public class TaskCandidateDemo {
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
	@Test
	public void crud() {
//		deployAndStart();
//		findGroupTaskList();
//		findGroupCandidate();
//		claim();
//		addGroupUser();
//		deleteGroupUser();
//		completeTask();
		
	}
	
	public void deployAndStart() {
		 //创建核心引擎对象
	    Deployment deployment = processEngine.getRepositoryService()// 与流程定义和部署对象相关的service
	            .createDeployment()// 创建一个部署对象
	            .name("TaskCandidate示例")// 添加部署的名称
	            .addClasspathResource("diagrams/TaskCandidate.bpmn")// classpath的资源中加载，一次只能加载
	                                                                // 一个文件
	            .addClasspathResource("diagrams/TaskCandidate.png")// classpath的资源中加载，一次只能加载
	                                                            // 一个文件
	            .deploy();// 完成部署
	    System.out.println("部署ID:" + deployment.getId());
	    System.out.println("部署名称：" + deployment.getName());
	    
	    
	    /******************************启动流程******************************/
	    // 流程定义的key
	    String processDefinitionKey = "TaskCandidate";
	    Map<String, Object> variables = new HashMap<String, Object>();  
	    ProcessInstance pi = processEngine.getRuntimeService()// 于正在执行的流程实例和执行对象相关的Service
	            .startProcessInstanceByKey(processDefinitionKey);// 使用流程定义的key启动流程实例，key对应hellworld.bpmn文件中id的属性值，使用key值启动，默认是按照最新版本的流程定义启动
	    System.out.println("流程实例ID:" + pi.getId());// 流程实例ID 101
	    System.out.println("流程定义ID:" + pi.getProcessDefinitionId()); // 流程定义ID HelloWorld:1:4
	    
	}
	
	public void findGroupTaskList() {  
        // 任务办理人  
        String candidateUser = "小A";  
        List<Task> list = processEngine.getTaskService()//  
                .createTaskQuery()//  
                .taskCandidateUser(candidateUser)// 参与者，组任务查询  
                .list();  
        if (list != null && list.size() > 0) {  
            for (Task task : list) {  
                System.out.println("任务ID：" + task.getId());  
                System.out.println("任务的办理人：" + task.getAssignee());  
                System.out.println("任务名称：" + task.getName());  
                System.out.println("任务的创建时间：" + task.getCreateTime());  
                System.out.println("流程实例ID：" + task.getProcessInstanceId());  
                System.out.println("#######################################");  
            }  
        }  
    } 
	
	public void findGroupCandidate(){  
        //任务ID  
        String taskId = "120008";  
        List<IdentityLink> list = processEngine.getTaskService()//  
                        .getIdentityLinksForTask(taskId);  
        if(list!=null && list.size()>0){  
            for(IdentityLink identityLink:list){  
                System.out.println("任务ID："+identityLink.getTaskId());  
                System.out.println("流程实例ID："+identityLink.getProcessInstanceId());  
                System.out.println("用户ID："+identityLink.getUserId());  
                System.out.println("工作流角色ID："+identityLink.getGroupId());  
                System.out.println("#########################################");  
            }  
        }  
    } 
	//认领任务
	public void claim(){  
        //任务ID  
        String taskId = "120008";  
        //个人任务的办理人  
        String userId = "小A";  
        processEngine.getTaskService()//  
                    .claim(taskId, userId);  
    }  
	/**向组任务中添加成员*/  
    public void addGroupUser(){  
        //任务ID  
        String taskId = "120008";  
        //新增组任务的成员  
        String userId = "如来";  
        processEngine.getTaskService()//  
                    .addCandidateUser(taskId, userId);  
    }  
      
    /**向组任务中删除成员*/  
    public void deleteGroupUser(){  
        //任务ID  
        String taskId = "120008";  
        //新增组任务的成员  
        String userId = "小D";  
        processEngine.getTaskService()//  
                    .deleteCandidateUser(taskId, userId);  
    }  
    /**完成任务*/  
    public void completeTask(){  
        //任务ID  
        String taskId = "120008";  
        processEngine.getTaskService()//  
                        .complete(taskId);  
        System.out.println("完成任务："+taskId);  
    }  
}
