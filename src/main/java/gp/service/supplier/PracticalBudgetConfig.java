package gp.service.supplier;

import java.util.HashMap;
import java.util.Map;

public class PracticalBudgetConfig {
	
	private String id;
	private String name;
	private String email;
	private String budgetEmail;
	private Map<String, String> categorysMap;	
	
	public PracticalBudgetConfig() {
		categorysMap = new HashMap<String, String>();
	}
	
	public String getId(){
		return id;
	}
	public void setId(String id){
		this.id = id;
	}
	
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	
	public String getEmail(){
		return email;
	}
	public void setEmail(String email){
		this.email = email;
	}
	
	public String getBudgetEmail(){
		return budgetEmail;
	}
	public void setBudgetEmail(String budgetEmail){
		this.budgetEmail = budgetEmail;
	}
	
	public Map<String, String> getCategorysMap() {
		return categorysMap;
	}
	public void setCategorysMap(Map<String, String> categorysMap) {
		this.categorysMap = categorysMap;
	}
	public void addCategory(String categoryId, String category) {
		this.categorysMap.put(categoryId, category);
	}
	/*
	public String getCategory(){
		return category;
	}
	public void setCategory(String category){
		this.category = category;
	}
		
	public String getCategoryId(){
		return categoryId;
	}
	public void setCategoryId(String categoryId){
		this.categoryId = categoryId;
	}
*/
}
