package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import model.Equipment;
import model.Site;
import model.Tower;

@Path("/site")
public class SiteDAO {
	
	EntityManagerFactory myFactory = Persistence.createEntityManagerFactory("DBJPAJWS");
	EntityManager entityManager = null;
	
	@GET
	@Path("/{ID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Site findSite(@PathParam("ID") int siteId) {
		Site site = null;
		entityManager = myFactory.createEntityManager();
		entityManager.getTransaction().begin();
		site = entityManager.find(Site.class, siteId);
		entityManager.getTransaction().commit();
		entityManager.close();
		return site;
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Site> findAllSites() {
		entityManager = myFactory.createEntityManager();
		entityManager.getTransaction().begin();
		Query selectAll = entityManager.createQuery("SELECT s FROM Site s");
		// = new ArrayList<Site>();
		List<Site> sites = selectAll.getResultList();
		entityManager.getTransaction().commit();
		entityManager.close();
		return sites;
	}
	
	@PUT
	@Path("/{ID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Site> updateSite(@PathParam("ID") int siteId, Site site) {
		//List<Site> sites = new ArrayList<Site>();
		entityManager = myFactory.createEntityManager();
		entityManager.getTransaction().begin();
		site.setId(siteId);
		entityManager.merge(site);
		Query selectAll = entityManager.createQuery("SELECT s FROM Site s");
		List<Site> sites = selectAll.getResultList();
		entityManager.getTransaction().commit();
		entityManager.close();
		return sites;
	}
	
	@DELETE
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Site> removeSite(@PathParam("ID") int siteId) {
		//List<Site> sites = new ArrayList<Site>();
		entityManager = myFactory.createEntityManager();
		entityManager.getTransaction().begin();
		Site site = entityManager.find(Site.class, siteId);
		entityManager.remove(site);
		Query selectAll = entityManager.createQuery("SELECT s FROM Site s");
		List<Site> sites = selectAll.getResultList();
		entityManager.getTransaction().commit();
		entityManager.close();
		return sites;
	}
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Site> createSite(Site site) {
		//List<Site> sites = new ArrayList<Site>();
		entityManager = myFactory.createEntityManager();
		entityManager.getTransaction().begin();;
		entityManager.persist(site);
		Query selectAll = entityManager.createQuery("SELECT s FROM Site s");
		List<Site> sites = selectAll.getResultList();
		entityManager.getTransaction().commit();
		entityManager.close();
		return sites;
	}
	
	
	public static void main(String[] args){
		SiteDAO dao = new SiteDAO();
		
		System.out.println("test selectall now:");
		List<Site> sites = dao.findAllSites();
		for(Site site:sites){
			System.out.println("id:"+site.getId());
		}
		
		System.out.println("test create site now:");
		Site site1 = new Site();
		site1.setId(4);
		site1.setLatitude(4.0);
		site1.setLongitude(4.0);
		site1.setName("Site 4 now:");
		dao.createSite(site1);
		List<Site> sites2 = dao.findAllSites();
		for(Site site2:sites2){
			System.out.println("id:"+site2.getId());
		}
		
		System.out.println("test find site now:");
		Site site = dao.findSite(1);
		System.out.println("id:"+site.getId());
		

		
		System.out.println("test update site now:");
		Site site4 = new Site();
		site4.setId(3);
		site4.setLatitude(8.0);
		site4.setLongitude(8.0);
		site4.setName("Site 3 now:");
		dao.updateSite(3, site4);
		List<Site> sites5 = dao.findAllSites();
		for(Site site5:sites5){
			System.out.println("id:"+site5.getId());
		}
		
		System.out.println("test delete site now:");
		dao.removeSite(4);
		List<Site> sites3 = dao.findAllSites();
		for(Site site3:sites3){
			System.out.println("id:"+site3.getId());
		}
		
		
		System.out.println(site.getId());
		List<Tower> towers = site.getTowers();
		for(Tower tower:towers){
			System.out.print(tower.getId()+": ");
			List<Equipment> equis = tower.getEquipments();
			for(Equipment equi:equis){
				System.out.print(equi.getId()+"; ");
			}
			System.out.println();
		}
		
	}
}
