package metier;

import java.util.List;

import metier.entities.Categorie;
import metier.entities.Produit;

public interface ICatalogMetier {

	public Categorie addCategorie(Categorie categorie);

	public Produit addProduit(Produit produit);

	public List<Categorie> listCategories();

	public List<Produit> listProduits();

	public List<Produit> produitsParCategorie(Long idCategorie);

	public List<Produit> produitsParMotCle(String motCle);

	public Categorie updateCategorie(Categorie categorie);

	public Produit updateProduit(Produit produit);

	public boolean deleteProduit(Long idProduit);

	public Categorie getCategorie(Long idCat);

	public Produit getProduit(Long idProd);

	public void initialiserCatalogue();

}
