package com.REST.Learn.User;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.REST.Learn.ResponseBean.AgePredResBean;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class UserResource {

	@Autowired
	private UserDaoService service;
	@Autowired
	Environment env;

	@GetMapping("/users")
	public List<User> retrieveAllUsers() {

		return service.findAll();
	}

	// Calling External API

	@GetMapping("/externalapi/{name}")
	public String callExternalAPI(@PathVariable String name) {

		String url = env.getProperty("prdict.api.url");

		FileReader read = null;
		//FileWriter write = null;
		Properties prop = new Properties();
		
		File file = new File("G:\\project\\LearnRest\\Learn\\Learn\\src\\main\\resources\\WebServiceClient.properties");

		try {
			read = new FileReader(file);

			//write = new FileWriter(file);
			
			prop.load(read);
			String urlprop=prop.getProperty("prdict.api.url");
			//System.out.print(urlprop+"properties");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		WebClient client = WebClient.create();
		

		String jsonString = client.get().uri(url, name)
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).retrieve().bodyToMono(String.class)
				.block();

		// set json string into prop
		//prop.setProperty("jsonstring", jsonString);
		/*
		 * try { //prop.store(write, "helo"); } catch (IOException e1) { // TODO
		 * Auto-generated catch block e1.printStackTrace(); }
		 */
		ObjectMapper mapper = new ObjectMapper();
		try {
			/*
			 * Map<String, Object> map = mapper.readValue(jsonString, Map.class); JsonNode
			 * rootNode = mapper.readTree(jsonString);
			 */
			AgePredResBean lib = mapper.readValue(jsonString, AgePredResBean.class);
			String h = lib.getAge();
			System.out.print(h);

		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return jsonString;

	}

	/*
	 * @GetMapping("/externalapi/{name}") public void callExternalAPI(@PathVariable
	 * String name){
	 * 
	 * WebClient client = WebClient.create();
	 * 
	 * AgePredResMainBean obj =client.get()
	 * .uri("https://api.agify.io?name={name}",name)
	 * .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
	 * .retrieve().bodyToMono(AgePredResMainBean.class).block();
	 * 
	 * //String g = obj.getObjAgePredResBean().getAge();
	 * 
	 * 
	 * }
	 */

	@GetMapping("/users/{id}")
	public EntityModel<User> retrieveUser(@PathVariable int id) {

		User user = service.findOne(id);

		if (user == null) {
			throw new UserNotFoundException("user not found for this id" + id);
		}

		EntityModel<User> model = EntityModel.of(user);

		WebMvcLinkBuilder linkToUsers = linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).retrieveAllUsers());
		model.add(linkToUsers.withRel("all-users"));
		// return service.findOne(id);
		return model;

	}

	@PostMapping("/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {

		User savedUser = service.save(user);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}").buildAndExpand(savedUser.getId())
				.toUri();

		return ResponseEntity.created(location).build();

	}

	@DeleteMapping("/users/{id}")
	public void deleteById(@PathVariable int id) {

		User user = service.deleteById(id);

		if (user == null) {
			throw new UserNotFoundException("user not found for this id" + id);
		}

	}

}
