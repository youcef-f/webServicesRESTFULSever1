package service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import metier.ICatalogMetier;
import metier.entities.Categorie;
import metier.entities.Produit;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.sun.jersey.spi.container.ParamQualifier;
import com.sun.jersey.spi.resource.Singleton;

@Singleton // demande à JERSEY de ne creer qu'une seul instance du web service pour toutes
			// les requetes des clients. post,get,put ...
@Produces(MediaType.APPLICATION_JSON + "; charset=utf8") // support "é,ê,è .."
@Path("/catalogue")
public class CatalogueService {

	// private ICatalogMetier catalogMetier = new

	ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
			new String[] { "applicationContext.xml" });

	private ICatalogMetier metier;

	public CatalogueService() {
		metier = (ICatalogMetier) context.getBean("catalogueMetier");
		metier.initialiserCatalogue();
	}

	@GET
	@Path("/categories")
	public List<Categorie> consulterCategories() {
		return metier.listCategories();
	}

	// @pathParam =
	// http://localhost:8080/webServicesRESTFULServer1/catalogue/categories/1/produits
	// chercher les produits d'un produit donné
	@GET
	@Path("/categories/{idCateg}/produits")
	public List<Produit> produitsParCategorie(@PathParam(value = "idCateg") Long idCategorie) {
		return metier.produitsParCategorie(idCategorie);
	}

	// @QueryParam =
	// http://localhost:8080/webServicesRESTFULServer1/catalogue/produits?keyWord=HP
	// chercher les produits par mot clé
	@GET
	@Path("/produits")
	public List<Produit> produitsParMotCle(@QueryParam(value = "keyWord") String motCle) {
		return metier.produitsParMotCle(motCle);
	}

	// @QueryParam =
	// http://localhost:8080/webServicesRESTFULServer1/catalogue/produits?keyWord=HP
	// chercher les produits par mot clé
	@GET
	@Path("/allproduits")
	public List<Produit> getProduits() {
		return metier.listProduits();
	}

	// @@PathParam
	// =http://localhost:8080/webServicesRESTFULServer1/catalogue/produits/1
	// chercher une categorie suivant id
	@GET
	@Path("/categories/{idCateg}")
	public Categorie getCategorie(@PathParam(value = "idCateg") Long idCategorie) {
		return metier.getCategorie(idCategorie);
	}

	// @PathParam
	// =http://localhost:8080/webServicesRESTFULServer1/catalogue/produits/1
	@GET
	@Path("/produits/{idProd}")
	public Produit getProduit(@PathParam(value = "idProd") Long idProduit) {
		return metier.getProduit(idProduit);
	}

	@POST
	@Path("/categories")
	@Consumes(MediaType.APPLICATION_JSON) // le client doit envoyer en json
	public Categorie saveCategorie(Categorie categorie) {
		return metier.addCategorie(categorie);
	}

	@POST
	@Path("/produits")
	@Consumes(MediaType.APPLICATION_JSON) // le client doit envoyer en json
	public Produit saveProduit(Produit produit) {
		return metier.addProduit(produit);
	}

	// @FormParam = envoyer dans le "body" de la requet sous content-type :
	// application/x-www-from-unlencoded
	@DELETE
	@Path("/produits")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED) // le client doit envoyer en json
	public boolean deleteProduit(@FormParam(value = "idProd") Long idProduit) {
		return metier.deleteProduit(idProduit);
	}

	@PUT
	@Path("/produits")
	public Produit updateProduit(Produit produit) {
		return metier.updateProduit(produit);
	}

}
