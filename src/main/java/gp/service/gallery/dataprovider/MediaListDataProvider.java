package gp.service.gallery.dataprovider;


import java.util.List;

import org.w3c.dom.Node;

import gp.service.gallery.entity.Media;
import gp.service.gallery.manager.GalleryManager;

import lumis.doui.source.IDataProvider;
import lumis.doui.source.ISourceData;
import lumis.doui.source.TabularData;
import lumis.doui.source.TabularSource;
import lumis.portal.PortalException;
import lumis.portal.authentication.SessionConfig;
import lumis.portal.manager.ManagerFactory;
import lumis.util.ITransaction;
import lumis.util.XmlUtil;


public class MediaListDataProvider implements IDataProvider<TabularSource<?>>{

	@Override
	public void loadData(SessionConfig sessionConfig, TabularSource<?> source,
			ITransaction transaction) throws PortalException {
		
		String parentFolder = (String) source.getParameterValue("parentFolder");
		String title = (String) source.getParameterValue("title");
		
		String serviceInstanceId = (String) source.getDouiContext().getRequest().getServiceInstanceConfig().getId();
		String orderBy = getOrderByParameters(source);
		String repository = ManagerFactory.getServiceInstanceManager().getServiceInstanceByDependency(sessionConfig, serviceInstanceId, "media_repository", transaction).getId();
		GalleryManager manager =  new GalleryManager();
		List<Media> listMedia = manager.listMediaByRepository(transaction,repository,parentFolder,title,orderBy);
		System.out.print(repository);

		
		TabularData tabularData = source.getData();
		
		
		if(parentFolder != null){
			//pega os dados da pasta filtrada para retornar um nível acima
			Media currentFolder = manager.getFolderParentData(transaction, repository, parentFolder);
			ISourceData linha = tabularData.addRow();
			linha.put("id", currentFolder.getParentFolder());
			linha.put("title", "...");
			linha.put("legend", currentFolder.getLegend());
			linha.put("credits", currentFolder.getCredits());
			linha.put("type", currentFolder.getType());
			linha.put("mediaFile", currentFolder.getMediaFile());
			linha.put("parentFolder", null );
		}
		
		for (Media media : listMedia) {
			
			ISourceData linha = tabularData.addRow();
			
			linha.put("id", media.getId());
			linha.put("title", media.getTitle());
			linha.put("legend", media.getLegend());
			linha.put("credits", media.getCredits());
			linha.put("type", media.getType());
			linha.put("mediaFile", media.getMediaFile());
			linha.put("parentFolder", media.getParentFolder());
		}
		
		source.applyPostLoadPagination();
		tabularData.setTotalRows(listMedia.size());
				
	}
	
	public String getOrderByParameters(TabularSource<?> source) throws PortalException{
			String orderBy = "";		  
			Node orderNode = XmlUtil.selectSingleNode("orderBy", source.getDefinitionNode());  
			if (orderNode != null) {  
				Node[] fieldNodes = XmlUtil.selectNodes("field", orderNode);  
				for (int i=0;i<fieldNodes.length;i++) {
					if(i>0)
						orderBy+=", ";
					String direction = XmlUtil.readAttributeString("direction", fieldNodes[i]);
					orderBy+= XmlUtil.readAttributeString("id", fieldNodes[i])+" "+(direction.equalsIgnoreCase("descending")?"desc":"");
				}
			}
			return orderBy;
	}


}
