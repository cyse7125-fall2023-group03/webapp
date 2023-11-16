package com.webapp.webapp.service;

import java.nio.charset.StandardCharsets;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

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

			CustomResourceDefinitionContext animalCrdContext = new CustomResourceDefinitionContext.Builder()
					.withName("animals.jungle.example.com").withGroup("jungle.example.com").withScope("Namespaced")
					.withVersion("v1").withPlural("animals").build();
			String crBasicString = loadTemplateFromFile("/crd.yaml");
			String crdWithDynamicValue = crBasicString.replace("${IMAGE_NAME}", "dsvhbdsv");

//			GenericKubernetesResource cr1 = client.genericKubernetesResources(animalCrdContext)
//					.load(CustomResourceService.class.getResourceAsStream("/crd.yml")).get();
//			client.genericKubernetesResources(animalCrdContext).inNamespace(namespace).resource(cr1).create();

			// Creating from Raw JSON String
//			String crBasicString = "{" + "  \"apiVersion\": \"jungle.example.com/v1\"," + "  \"kind\": \"Animal\","
//					+ "  \"metadata\": {" + "    \"name\": \"mongoose\"," + "    \"namespace\": \"default\"" + "  },"
//					+ "  \"spec\": {" + "    \"image\": \"my-silly-mongoose-image\"" + "  }" + "}";
			GenericKubernetesResource cr3 = Serialization.jsonMapper().readValue(crdWithDynamicValue,
					GenericKubernetesResource.class);
			client.genericKubernetesResources(animalCrdContext).inNamespace(namespace).resource(cr3).create();
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
