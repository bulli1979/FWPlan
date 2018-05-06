package dbtests;
import java.util.List;
import java.util.UUID;

import org.junit.*;

import data.Plan;
import data.db.DBPlan;


public class DBPlanCRUDTest {
	
	@Test
	public void testInsert(){
		//TODO Simon ein bissl mehr testen und schön machen. 
		String id = UUID.randomUUID().toString();
		Plan testPlan = new Plan.Builder().setId(id).withTitle("test").withDescription("test").withPlanNumber("number").build();
		DBPlan.getInstance().insertPlan(testPlan);
		
		List<Plan> planList = DBPlan.getInstance().getAllPlans();
		System.out.println(planList.size());
		System.out.println(planList.get(0).getTitle());
		for(Plan plan : planList){
			Plan updatePlan = new Plan.Builder().withDescription("update").withPlanNumber("2").withTitle("updateTitle").setId(plan.getId()).build();
			DBPlan.getInstance().updatePlan(updatePlan);
			DBPlan.getInstance().deletePlan(updatePlan);
		}
	}
	
	
	
	
}
