package com.webapp.webapp.service;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import io.fabric8.kubernetes.api.model.GenericKubernetesResource;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientBuilder;
import io.fabric8.kubernetes.client.dsl.base.CustomResourceDefinitionContext;
import io.fabric8.kubernetes.client.utils.Serialization;

@Service
public class CustomResourceService {

	public void applyCRDAndCreateCustomResource() {
		try (final KubernetesClient client = new KubernetesClientBuilder().build()) {

			String namespace = "default";

			CustomResourceDefinitionContext healthcheckCrdContext = new CustomResourceDefinitionContext.Builder()
			        .withName("healthchecks.webapp.udaykk.me")  // CRD name
			        .withGroup("webapp.udaykk.me")  // CRD group
			        .withScope("Namespaced")
			        .withVersion("v1")
			        .withPlural("healthchecks")  // CRD plural name
			        .build();

			// Load YAML file into String
			ClassLoader classLoader = getClass().getClassLoader();

			InputStream inputStream = classLoader.getResourceAsStream("crd.yaml");

			String yamlString = new String(inputStream.readAllBytes());
			
			// Parse YAML into GenericKubernetesResource
			ObjectMapper yamlReader = new ObjectMapper(new YAMLFactory());
			GenericKubernetesResource resource = yamlReader.readValue(yamlString, GenericKubernetesResource.class);

//			String crBasicString = loadTemplateFromFile("/crd.yaml");
//			String crdWithDynamicValue = crBasicString.replace("${IMAGE_NAME}", "dsvhbdsv");
//			
//			ObjectMapper yamlReader = new ObjectMapper(new YAMLFactory());
//			Map<String, Object> crdObj = yamlReader.readValue(crdYamlString, Map.class);

//			GenericKubernetesResource cr1 = client.genericKubernetesResources(animalCrdContext)
//					.load(CustomResourceService.class.getResourceAsStream("/crd.yml")).get();
//			client.genericKubernetesResources(animalCrdContext).inNamespace(namespace).resource(cr1).create();

			// Creating from Raw JSON String
//			String crBasicString = "{" + "  \"apiVersion\": \"jungle.example.com/v1\"," + "  \"kind\": \"Animal\","
//					+ "  \"metadata\": {" + "    \"name\": \"mongoose\"," + "    \"namespace\": \"default\"" + "  },"
//					+ "  \"spec\": {" + "    \"image\": \"my-silly-mongoose-image\"" + "  }" + "}";
//			GenericKubernetesResource cr3 = Serialization.jsonMapper().readValue(crdWithDynamicValue,
//					GenericKubernetesResource.class);
			client.genericKubernetesResources(healthcheckCrdContext).inNamespace(namespace).resource(resource).create();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

//	public void applyCRDWithDynamicValue(String imageName) {
//        try (KubernetesClient client = new KubernetesClientBuilder().build()) {
//            String crdTemplatePath = "custom-resource-definition-template.yaml";
//
//            // Load CRD template from file
//            String crdTemplate = loadTemplateFromFile(crdTemplatePath);
//
//            // Replace placeholder with dynamic value
//            String crdWithDynamicValue = crdTemplate.replace("${IMAGE_NAME}", imageName);
//
//            // Deserialize YAML to CustomResourceDefinition object
//            CustomResourceDefinition crd = Serialization.unmarshal(crdWithDynamicValue, CustomResourceDefinition.class);
//
//            // Create the CRD using the Kubernetes client
//            client.apiExtensions().v1().customResourceDefinitions().create(crd);
//
//            System.out.println("Custom Resource Definition created successfully!");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
	private String loadTemplateFromFile(String filePath) throws Exception {
		ClassPathResource resource = new ClassPathResource(filePath);
		return StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
	}
}
