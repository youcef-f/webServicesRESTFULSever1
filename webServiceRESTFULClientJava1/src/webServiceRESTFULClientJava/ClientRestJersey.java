package webServiceRESTFULClientJava;

import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import metier.entities.Categorie;

public class ClientRestJersey {

	static String urlRest = "http://localhost:8080/webServicesRESTFULServer1/";

	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {

		Client client = Client.create(new DefaultClientConfig());
		URI uri = UriBuilder.fromUri(urlRest).build();

		// Consulter toutes les categories
		// GET http://localhost:8080/webServicesRESTFULServer1/catalogue/categories
		ClientResponse consultCategorieResponse = client.resource(uri).path("catalogue").path("categories")
				.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);

		System.out.println("GET http://localhost:8080/webServicesRESTFULServer1/catalogue/categories");
		// recupere le body de la réponse sous forme de texte
		String consultCategorieBodyReponseHttp = consultCategorieResponse.getEntity(String.class);
		System.out.println(consultCategorieBodyReponseHttp);
		/*
		 * [ { "idCategorie": 1, "nomCategorie": "Ordinateur", "photo": "ordinateur.jpg"
		 * }, { "idCategorie": 2, "nomCategorie": "Imprimante", "photo":
		 * "imprimante.jpg" }, { "idCategorie": 3, "nomCategorie": "Télévisieur",
		 * "photo": "téléviseur.jpg" }]
		 */

		// Convertir le String body en un tableau de l'objet Categorie
		// Deserialisation json ( via api jackson )
		ObjectMapper objectMapperRead = new ObjectMapper();
		Categorie[] categories = objectMapperRead.readValue(consultCategorieBodyReponseHttp, Categorie[].class);

		for (Categorie categorie : categories) {
			System.out.println(categorie.getNomCategorie());
		}

		/*
		 * Ordinateur Imprimante Télévisieur
		 * 
		 */

		// Ajouter une categorie sous forme d'une chaine
		// POST http://localhost:8080/webServicesRESTFULServer1/catalogue/categories
		/*
		 * { "idCategorie": 4, "nomCategorie": "Smart phone", "photo": "smartphone.jpg"
		 * }
		 */
		String NewCategorieString = "{ \"idCategorie\": 4,\"nomCategorie\": \"Smart phone\",\"photo\": \"smartphone.jpg\" }";

		ClientResponse ajoutCategorieStringResponse = client.resource(uri).path("catalogue").path("categories")
				.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON)
				.post(ClientResponse.class, NewCategorieString);

		System.out.println("POST http://localhost:8080/webServicesRESTFULServer1/catalogue/categories  as String");
		// recupere le body de la réponse sous forme de texte
		String ajoutCategorieStringBodyReponseHttp = ajoutCategorieStringResponse.getEntity(String.class);
		System.out.println(ajoutCategorieStringBodyReponseHttp);

		// Ajouter une categorie sous forme d'un objet "Categorie"
		// POST http://localhost:8080/webServicesRESTFULServer1/catalogue/categories
		/*
		 * { "idCategorie": 4, "nomCategorie": "Smart phone", "photo": "smartphone.jpg"
		 * }
		 */

		Categorie NewCategorieObject = new Categorie(8L, "Scanner", "scanner.jpg");

		// serialise l'objet Categorie en json
		ObjectMapper objectMapperWrite = new ObjectMapper();

		ClientResponse ajoutCategorieObjectResponse = client.resource(uri).path("catalogue").path("categories")
				.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON)
				.post(ClientResponse.class, objectMapperWrite.writeValueAsString(NewCategorieObject));

		System.out.println("POST http://localhost:8080/webServicesRESTFULServer1/catalogue/categories  As object");
		// recupere le body de la réponse sous forme de texte
		String ajoutCategorieObjectBodyReponseHttp = ajoutCategorieObjectResponse.getEntity(String.class);
		System.out.println(ajoutCategorieObjectBodyReponseHttp);

	}
}
