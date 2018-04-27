package demo.start;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

public class StartDemo {

	@Test
	public void deploy() {
		try {
			start1();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void start1() {
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		String processDefinitionKey = "MyProcess";
		// 使用流程定义的key启动流程实例，key对应hellworld.bpmn文件中id的属性值，使用key值启动，默认是按照最新版本的流程定义启动
		ProcessInstance pi = processEngine.getRuntimeService()// 于正在执行的流程实例和执行对象相关的Service
				.startProcessInstanceByKey(processDefinitionKey);
		System.out.println("流程实例ID:" + pi.getId());// 流程实例ID 101
		System.out.println("流程定义ID:" + pi.getProcessDefinitionId()); // 流程定义ID HelloWorld:1:4
	}
}
