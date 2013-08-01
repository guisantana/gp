package gp.service.supplier;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import lumis.doui.processaction.ProcessActionHandler;
import lumis.doui.source.Source;
import lumis.portal.PortalContext;
import lumis.portal.PortalException;
import lumis.portal.PortalRequestParameters;
import lumis.portal.manager.ManagerFactory;
import lumis.portal.sendmail.IMailBody;
import lumis.portal.sendmail.ISingleMail;
import lumis.util.TextUtil;
import lumis.util.XmlUtil;

public class SupplierProcessActionHandler extends ProcessActionHandler<Source> {

	private static final String SEND_PRACTICAL_BUDGET = "sendPracticalBudget";
	SupplierDaoJdbc supplierDao = new SupplierDaoJdbc();
	

	public void processAction() throws PortalException {
		String actionType = XmlUtil.readAttributeString("actionType", processActionNode, "");
		
		if (actionType.equals(SEND_PRACTICAL_BUDGET))
			processSendPracticalBudget();
	}
	
	protected void processSendPracticalBudget() throws PortalException {
		
		String pageId = douiContext.getRequest().getPageConfig().getId();
		String siteName = "sss";
		
		String[] categoryIds = getParameter("idCategory", String[].class);
		String userEmail = getParameter("email", String.class);
		String userName = getParameter("name", String.class);
		List<String> categoryIdList = Arrays.asList(categoryIds);
		
		String serviceInstanceId = douiContext.getRequest().getServiceInstanceConfig().getId();
		Collection<String> serviceInterfaceInstanceIds = ManagerFactory.getServiceInterfaceInstanceManager().getIdsByServiceInterfaceIdAndServiceInstanceId(sessionConfig, "guiaseportais.noivas.service.supplier.administrationPracticalBudget", serviceInstanceId, true, true, transaction);
		String serviceInterfaceInstanceId = serviceInterfaceInstanceIds.iterator().next();
		String adminUrl = null;

		if(serviceInterfaceInstanceId != null) {
			String adminPageId = ManagerFactory.getServiceInterfaceInstanceManager().get(sessionConfig, serviceInterfaceInstanceId, transaction).getPageId();
			String website = PortalContext.getFrameworkUrl();
			if(!website.endsWith("/"))
				website += "/";
			
			adminUrl = website + "main.jsp?" + PortalRequestParameters.PAGE_PARAMETER_PAGEID + "=" + adminPageId;
		}
		
		String subject = localize("STR_EMAIL_SUBJECT;"+TextUtil.escapeLocalizationParameter(siteName));
		
		List<PracticalBudgetConfig> practicalBudgets = supplierDao.getSuppliersByCategoryList(categoryIdList, transaction); 
		
		for(PracticalBudgetConfig practicalBudget : practicalBudgets) {
			
			String htmlBody = localize("STR_EMAIL_BODY;"+
					TextUtil.escapeLocalizationParameter(practicalBudget.getName()) + ";" +
					TextUtil.escapeLocalizationParameter(siteName) + ";" +
					TextUtil.escapeLocalizationParameter(userName) + ";" +
					TextUtil.escapeLocalizationParameter(adminUrl));
			
			supplierDao.addPracticalBudget(practicalBudget, userName, userEmail, subject, htmlBody, true, transaction);
			
			sendEmail(practicalBudget.getBudgetEmail(), subject, htmlBody);
		}
		
		addDefaultResponse();
	}
	
	protected void sendEmail(String toEmail, String subject, String htmlBody) throws PortalException {
		
		ISingleMail singleMail = ManagerFactory.getSendMailManager().createSingleMail();
		singleMail.setSourceComponent(subject);
		singleMail.setFrom("guisantana@lumis.com.br");
		singleMail.setTo(toEmail);
		singleMail.setSubject(subject);
		IMailBody mailBody = singleMail.getBody();			
		mailBody.setHtmlMsg(htmlBody);
		
		ManagerFactory.getSendMailManager().addMailToSendQueue(sessionConfig, singleMail, null, transaction);
	}



}
