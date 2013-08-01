package gp.service.supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lumis.portal.PortalException;
import lumis.portal.UnexpectedException;
import lumis.portal.dao.jdbc.ITransactionJdbc;
import lumis.util.ITransaction;
import lumis.util.PortalUtil;

public class SupplierDaoJdbc {

	public List<PracticalBudgetConfig> getSuppliersByCategoryList(List<String> categoryList, ITransaction transaction) throws PortalException {
		try {
			List<PracticalBudgetConfig> practicalBudgets = new ArrayList<PracticalBudgetConfig>();
			ITransactionJdbc daoTransactionJdbc = (ITransactionJdbc) transaction;
			Connection connection = daoTransactionJdbc.getConnection();
			
			StringBuilder querySql = new StringBuilder();
			querySql.append("select s.id, s.name, s.email, s.budgetEmail, c.id as categoryId, c.name as category from gp_nv_supplier s, gp_nv_suppliercategory c, gp_nv_supplierrelation r ");
			querySql.append("where s.id=r.idSupplier and c.id=r.idCategory and s.published=1 and c.id in (%s) order by s.id");

			String sql = String.format(querySql.toString(), preparePlaceHolders(categoryList.size()));
			
			PreparedStatement statement = connection.prepareStatement(sql);	
			
			try {
				setValues(statement, categoryList.toArray());
				ResultSet resultSet = statement.executeQuery();

				try {
					while(resultSet.next()){

						PracticalBudgetConfig practicalBudget = new PracticalBudgetConfig();
						practicalBudget.setId(resultSet.getString(1));
						practicalBudget.setName(resultSet.getString(2));
						practicalBudget.setEmail(resultSet.getString(3));
						practicalBudget.setBudgetEmail(resultSet.getString(3));
						
						String budgetEmail = resultSet.getString(4);
						if(!"".equals(budgetEmail) && budgetEmail != null)
							practicalBudget.setBudgetEmail(budgetEmail);
						
						addCategory(resultSet.getString(1), practicalBudget, resultSet);
						
						practicalBudgets.add(practicalBudget);
	
					}
				}
				finally {
					resultSet.close();
				}
			}
			finally {
				statement.close();
			}
			
			return practicalBudgets;
		}
		catch (PortalException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new UnexpectedException(e);
		}
	}
	
	public void addPracticalBudget(PracticalBudgetConfig practicalBudget, String name, String fromAddress, String subject, String htmlBody, boolean bAddCategorys, ITransaction transaction) throws PortalException {
		try {
			ITransactionJdbc daoTransactionJdbc = (ITransactionJdbc) transaction;
			Connection connection = daoTransactionJdbc.getConnection();
			String id = PortalUtil.generateNewGuid();
			
			PreparedStatement statement = connection.prepareStatement("insert into gp_nv_supplierPracticalBudget(id, idSupplier, name, toAddress, fromAddress, subject, body, createdDate, modifiedDate) values (?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP())");
			try {
				statement.setString(1, id);
				statement.setString(2, practicalBudget.getId());
				statement.setString(3, name);
				statement.setString(4, practicalBudget.getBudgetEmail());
				statement.setString(5, fromAddress);
				statement.setString(6, subject);
				statement.setString(7, htmlBody);
				statement.executeUpdate();
			}
			finally {
				statement.close();
			}
			
			if (bAddCategorys) {
				Map<String, String> categorysMap = practicalBudget.getCategorysMap();

		        for (Map.Entry<String, String> entry : categorysMap.entrySet()) {
		        	addPracticalBudgetCategory(id, entry.getKey(), transaction);
		        	//entry.getValue();
		        }
			}
		}
		catch (PortalException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new UnexpectedException(e);
		}
	}
	
	public void addPracticalBudgetCategory(String practicalBudgetId, String categoryId, ITransaction transaction) throws PortalException {
		try {
			ITransactionJdbc daoTransactionJdbc = (ITransactionJdbc) transaction;
			Connection connection = daoTransactionJdbc.getConnection();
			
			PreparedStatement statement = connection.prepareStatement("insert into gp_nv_supplierPracticalBudgetCategory(id, idPracticalBudget, idCategory) values (?, ?, ?)");
			try {
				statement.setString(1, PortalUtil.generateNewGuid());
				statement.setString(2, practicalBudgetId);
				statement.setString(3, categoryId);
				statement.executeUpdate();

			}
			finally {
				statement.close();
			}
		}
		catch (PortalException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new UnexpectedException(e);
		}
		
	}
	
	private static String preparePlaceHolders(int length) {
	    StringBuilder builder = new StringBuilder(length * 2 - 1);
	    for (int i = 0; i < length; i++) {
	        if (i > 0) builder.append(',');
	        builder.append('?');
	    }
	    return builder.toString();
	}

	private static void setValues(PreparedStatement preparedStatement, Object[] values) throws SQLException {
	    for (int i = 0; i < values.length; i++) {
	        preparedStatement.setObject(i + 1, values[i]);
	    }
	}
	
	private static void addCategory(String id, PracticalBudgetConfig practicalBudget, ResultSet resultSet) throws SQLException {
		
		practicalBudget.addCategory(resultSet.getString(5), resultSet.getString(6));
		
		if(resultSet.next()){
			String currentId = resultSet.getString(1);
			
			if(currentId.equals(id)) {
				practicalBudget.addCategory(resultSet.getString(5), resultSet.getString(6));
				
				addCategory(currentId, practicalBudget, resultSet);
			}
			else {
				resultSet.previous();
			}
		}
		
		
	}
}
	
