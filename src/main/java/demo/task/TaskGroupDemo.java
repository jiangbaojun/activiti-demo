package demo.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.junit.Test;

//组用户任务、认领、完成
public class TaskGroupDemo {
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
	@Test
	public void crud() {
		try {
//			deployAndStart();
			findGroupTask();
//			cliam();
//			completeTask();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deployAndStart() {
		 //创建核心引擎对象
	    Deployment deployment = processEngine.getRepositoryService()// 与流程定义和部署对象相关的service
	            .createDeployment()// 创建一个部署对象
	            .name("TaskGroup示例")// 添加部署的名称
	            .addClasspathResource("diagrams/TaskGroup.bpmn")// classpath的资源中加载，一次只能加载
	                                                                // 一个文件
	            .addClasspathResource("diagrams/TaskGroup.png")// classpath的资源中加载，一次只能加载
	                                                            // 一个文件
	            .deploy();// 完成部署
	    System.out.println("部署ID:" + deployment.getId());
	    System.out.println("部署名称：" + deployment.getName());
	    
	    /******************************添加用户角色组******************************/
        IdentityService identityService = processEngine.getIdentityService();//与组织机构管理相关的servive  
        //创建角色(两个角色)  
        identityService.saveGroup(new GroupEntity("部门经理"));  
        identityService.saveGroup(new GroupEntity("总经理"));  
        //创建用户（三个用户）  
        identityService.saveUser(new UserEntity("张三"));  
        identityService.saveUser(new UserEntity("李四"));  
        identityService.saveUser(new UserEntity("王五"));  
        //创建用户角色关联关系  
        identityService.createMembership("张三", "部门经理");  
        identityService.createMembership("李四", "部门经理");  
        identityService.createMembership("王五", "总经理");  
        System.out.println("添加组织机构成功");  
	    
	    
	    /******************************启动流程******************************/
	    // 流程定义的key
	    String processDefinitionKey = "TaskGroup";
	    Map<String, Object> variables = new HashMap<String, Object>();  
	    variables.put("startUser", "王五");
	    ProcessInstance pi = processEngine.getRuntimeService()// 于正在执行的流程实例和执行对象相关的Service
	            .startProcessInstanceByKey(processDefinitionKey, variables);// 使用流程定义的key启动流程实例，key对应hellworld.bpmn文件中id的属性值，使用key值启动，默认是按照最新版本的流程定义启动
	    System.out.println("流程实例ID:" + pi.getId());// 流程实例ID 101
	    System.out.println("流程定义ID:" + pi.getProcessDefinitionId()); // 流程定义ID HelloWorld:1:4
	    
	}
	
	/**查询张三或者李四的任务*/  
    public void findGroupTask(){  
        String candidateUser = "王五";  
        List<Task> list =processEngine.getTaskService()  
                     .createTaskQuery()  
                     .taskCandidateOrAssigned(candidateUser)  
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
    /**候选者不一定真正的参与任务的办理，所以我们需要拾取任务，将组任务分配给个人任务,即指定任务办理人字段*/  
    public void cliam(){  
        //将组任务分配给个人任务  
        //任务ID  
        String taskId ="127511";  
        //分配的个人任务（可以是组任务中的成员，也可以是非组任务的成员）  
        String userId ="张三";  
        processEngine.getTaskService()  
                     .claim(taskId, userId);  
        //当执行完之后查询正在执行的任务表（act_ru_task）可以发现ASSIGNEE_字段（指定任务办理人字段）值为'大F'  
        //此时任务就指定给了大F，在用小A或者下B等在去查个人组任务的话，就查询不出来任何任务【组任务最终也是需要指定一个人办理的，所以需要拾取任务】  
    }  
    public void completeTask(){  
        String taskId ="127511";  
        processEngine.getTaskService()  
                     .complete(taskId);  
    } 
}
