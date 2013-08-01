package gp.service.gallery.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import lumis.portal.PortalException;
import lumis.portal.UnexpectedException;
import lumis.portal.dao.jdbc.ITransactionJdbc;
import gp.service.gallery.entity.Media;
import lumis.util.ITransaction;
import lumis.util.log.ILogger;
import lumis.util.log.LoggerFactory;

import gp.service.gallery.entity.Gallery;

public class GalleryManager {

	private final ILogger logger = LoggerFactory.getServiceLogger(this.getClass().getName());
	
	public List<Gallery> listDestaqueImageByTopic(ITransaction transaction, String parentContent) throws PortalException{
		
		List<Gallery> listDestaquesImages = new ArrayList<Gallery>();
		
		try {
			
			ITransactionJdbc daoTransactionJdbc = (ITransactionJdbc) transaction;
			ResultSet rs;
			Connection connection = daoTransactionJdbc.getConnection();
						
			String sql = "select distinct id, media, parentContent, position from globo_rm_imagehighlight where id in ( " +
							"select itemId from lum_ContentVersion where id in " +
							"(select contentversionid from lum_ContentPublication where published = 1 and contentversionid in " +
							"(select id from lum_ContentVersion where id in " +
							"(select activeversionid from lum_ContentLocale where contentid in " +
							"(select id from lum_Content where serviceinstanceid in " +
							"(select serviceinstanceid from lum_ServiceInstance where serviceId = 'com.globo.robertomarinho.destaqueImagem') and sourceid ='imagehighlight'))))) and parentContent = ? " + 
							"order by position ";
							
			PreparedStatement st = connection.prepareStatement(sql);
			st.setString(1, parentContent);
			
			try {
				rs = st.executeQuery();
				while(rs.next()){
					
					Gallery destaqueImagem = new Gallery();
					
					destaqueImagem.setId(rs.getString("id"));
					destaqueImagem.setMedia(rs.getString("media"));
					destaqueImagem.setParentContent(rs.getString("parentContent"));
					destaqueImagem.setPosition(rs.getInt("position"));
					
					listDestaquesImages.add(destaqueImagem);
				}
			} finally{
				st.close();
				connection.close();
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
			throw new UnexpectedException(e.getMessage(), e);
		}
		
		return listDestaquesImages;
	}
	public List<Media> listMediaByRepository(ITransaction transaction, String repositoryId, String parentFolder, String title,String orderBy) throws PortalException{
		
		List<Media> listDestaquesImages = new ArrayList<Media>();
		
		try {			
			
			ITransactionJdbc daoTransactionJdbc = (ITransactionJdbc) transaction;
			ResultSet rs;
			Connection connection = daoTransactionJdbc.getConnection();
			
			
						
	/*		String sql = "select id,title,legend,credits,m.type,mediaFile,m.parentFolder,m.description " +
						 "from lum_Media m left join lum_File f on m.mediaFile=f.fileId " +
						 "where 	(serviceInstanceId=? or serviceInstanceId is null and m.type=1) ";
			*/
			String sql = 
					"select 	m.* " +
					"from 		lum_Media m, "+
					"lum_Content c, "+
					"lum_ContentLocale l," +
					"lum_ContentVersion v "+
					"where c.id=l.contentId and "+
					"l.id=v.contentLocaleId and "+
					"v.itemId=m.id and "+
					"c.serviceInstanceId=? ";
						
			if(parentFolder!=null){
				sql +=  " and m.parentFolder=? ";
			}
			else{
				sql += " and m.parentFolder is ? ";
			}
			
			if(title != null && !"".equals(title))
				sql+=" and title like ? ";
			else
				sql+=" and title like '%' ";
			
			if(orderBy.length()>0){
				sql+=" order by "+orderBy;
			}
			
			
			
			PreparedStatement st = connection.prepareStatement(sql);
			st.setString(1, repositoryId);
			st.setString(2, parentFolder);
			
			if(title != null && !"".equals(title))
				st.setString(3, title);
			
			try {
				rs = st.executeQuery();
				while(rs.next()){
					
					Media media = new Media();
					media.setId(rs.getString("id"));
					media.setTitle(rs.getString("title"));
					media.setCredits(rs.getString("credits"));
					media.setLegend(rs.getString("legend"));
					media.setDescription(rs.getString("description"));
					media.setType(rs.getInt("type"));
					media.setMediaFile(rs.getString("mediaFile"));
					media.setParentFolder(rs.getString("parentFolder"));					
					
					listDestaquesImages.add(media);
				}
			} finally{
				st.close();
				connection.close();
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
			throw new UnexpectedException(e.getMessage(), e);
		}
		
		return listDestaquesImages;
	}
	public Media getFolderParentData(ITransaction transaction, String repositoryId, String parentFolder) throws PortalException{
		
		
		Media media = new Media();
		try {
			
			ITransactionJdbc daoTransactionJdbc = (ITransactionJdbc) transaction;
			ResultSet rs;
			Connection connection = daoTransactionJdbc.getConnection();
			
			
						
	/*		String sql = "select id,title,legend,credits,m.type,mediaFile,m.parentFolder,m.description " +
						 "from lum_Media m left join lum_File f on m.mediaFile=f.fileId " +
						 "where 	(serviceInstanceId=? or serviceInstanceId is null and m.type=1) ";
			*/
			String sql = 
					"select 	m.* " +
					"from 		lum_Media m, "+
					"lum_Content c, "+
					"lum_ContentLocale l," +
					"lum_ContentVersion v "+
					"where c.id=l.contentId and "+
					"l.id=v.contentLocaleId and "+
					"v.itemId=m.id and "+
					"c.serviceInstanceId=? and m.id=?";
			
			PreparedStatement st = connection.prepareStatement(sql);
			st.setString(1, repositoryId);
			st.setString(2, parentFolder);
			
			try {
				rs = st.executeQuery();
				if(rs.next()){					
					
					media.setId(rs.getString("id"));
					media.setTitle(rs.getString("title"));
					media.setCredits(rs.getString("credits"));
					media.setLegend(rs.getString("legend"));
					media.setDescription(rs.getString("description"));
					media.setType(rs.getInt("type"));
					media.setMediaFile(rs.getString("mediaFile"));
					media.setParentFolder(rs.getString("parentFolder"));					
					
				}
			} finally{
				st.close();
				connection.close();
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
			throw new UnexpectedException(e.getMessage(), e);
		}
		
		return media;
	}
}
