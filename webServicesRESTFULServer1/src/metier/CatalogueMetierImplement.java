package metier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import metier.entities.Categorie;
import metier.entities.Produit;

public class CatalogueMetierImplement implements ICatalogMetier {

	
	private Map<Long, Categorie> categories = new HashMap<Long, Categorie>();
	private Map<Long, Produit> produits = new HashMap<Long, Produit>();

	
	@Override
	public Categorie addCategorie(Categorie categorie) {
		categories.put(categorie.getIdCategorie(), categorie);
		return categorie;
	}

	@Override
	public Produit addProduit(Produit produit) {
		produit.setCategorie(getCategorie(produit.getCategorie().getIdCategorie()));
		produits.put(produit.getIdProduit(), produit);
		return produit;
	}

	@Override
	public List<Categorie> listCategories() {
		return new ArrayList<Categorie>(categories.values());
	}

	@Override
	public List<Produit> produitsParCategorie(Long idCategorie) {
		List<Produit> prods = new ArrayList<Produit>();

		for (Produit p : produits.values()) {
			if (p.getCategorie().getIdCategorie().equals(idCategorie)) {

				prods.add(p);

			}
		}
		return prods;

	}

	@Override
	public List<Produit> listProduits() {
		return new ArrayList<Produit>(produits.values());

	}

	@Override
	public List<Produit> produitsParMotCle(String motCle) {
		List<Produit> prods = new ArrayList<Produit>();

		for (Produit p : produits.values()) {
			if (p.getDesignation().contains(motCle)) {
				prods.add(p);

			}
		}
		return prods;

	}

	@Override
	public Categorie updateCategorie(Categorie categorie) {

		categories.put(categorie.getIdCategorie(), categorie);
		return categorie;
	}

	@Override
	public Produit updateProduit(Produit produit) {
		produits.put(produit.getIdProduit(), produit);
		return produit;
	}

	@Override
	public boolean deleteProduit(Long idProduit) {

		if (produits.get(idProduit) != null) {
			produits.remove(idProduit);
			return true;
		} else
			throw new RuntimeException("Produit introuvable");
	}

	@Override
	public Categorie getCategorie(Long idCat) {
		return categories.get(idCat);
	}

	@Override
	public Produit getProduit(Long idProd) {
		return produits.get(idProd);
	}

	// Alimenter les list categories et produits pour des besoins de test.
	public void initialiserCatalogue() {
		addCategorie(new Categorie(1L, "Ordinateur", "ordinateur.jpg"));
		addCategorie(new Categorie(2L, "Imprimante", "imprimante.jpg"));
		addCategorie(new Categorie(3L, "Télévisieur", "téléviseur.jpg"));

		addProduit(new Produit(1L, "HP parvions 17-f227n pc ", 6500, "ord1.jpg", getCategorie(1L)));
		addProduit(new Produit(2L, "Asus pc portable reconditionné", 4500, "ord2.jpg", getCategorie(1L)));
		addProduit(new Produit(3L, "Lenovo notebook ", 3500, "ord2.jpg", getCategorie(1L)));
		addProduit(new Produit(4L, "HP Imprimante laser laserjet ", 1500, "imp1.jpg", getCategorie(2L)));
		addProduit(new Produit(5L, "Canon Imprimante prixma ", 1000, "imp2.jpg", getCategorie(2L)));
		addProduit(new Produit(6L, "Tomson TV", 6000, "tv1.jpg", getCategorie(3L)));

		System.out.println(
				"-------------------------------------------------------------------------------------------------");
		for (Produit p : this.listProduits()) {
			System.out.println("produit: " + p.getIdProduit() + " designation :" + p.getDesignation() + " categorieID: "
					+ p.getCategorie().getIdCategorie());
		}
		System.out.println(
				"-------------------------------------------------------------------------------------------------");

	}

}
