package com.webapp.webapp.service;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import io.fabric8.kubernetes.api.model.apiextensions.CustomResourceDefinition;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.dsl.Resource;
import io.fabric8.kubernetes.client.dsl.base.CustomResourceDefinitionContext;

@SuppressWarnings("deprecation")
@Service
public class CustomResourceService {

	@SuppressWarnings("deprecation")
	public void applyCRDAndCreateCustomResource() {
		// Initialize Kubernetes client
		try (KubernetesClient client = new DefaultKubernetesClient()) {
			// Get the Custom Resource Definition (CRD)
            List<CustomResourceDefinition> crdList = client.customResourceDefinitions().list().getItems();
         // Display information about each CRD
            for (CustomResourceDefinition crd : crdList) {
                System.out.println("CRD Name: " + crd.getMetadata().getName());
                System.out.println("CRD Group: " + crd.getSpec().getGroup());
                System.out.println("CRD Version: " + crd.getSpec().getVersions().get(0).getName());
                // You can access other CRD details as needed
                System.out.println("-------------------------------------------");
            }
			CustomResourceDefinition crd = client.customResourceDefinitions().withName("healthchecks.webapp.udaykk.me")
					.get();

			CustomResourceDefinitionContext crdContext = new CustomResourceDefinitionContext.Builder()
					.withGroup("webapp.udaykk.me") // Update with your CRD group
					.withPlural("healthchecks") // Update with your CRD plural name
					.withVersion("v1") // Update with your CRD version
					.withScope("Namespaced").build();

			System.out.println(crdContext.getVersion() + " fs " + crdContext.toString());
			if (crd != null) {
				Map<String, Object> customResourceData = new HashMap<>();
				customResourceData.put("apiVersion",
						crd.getSpec().getGroup() + "/" + crd.getSpec().getVersions().get(0).getName());
				customResourceData.put("kind", crd.getSpec().getNames().getKind());
				// Add other fields as needed in the custom resource specification

				// Create the custom resource in the default namespace
				client.customResources(crdContext, null, null, null).inNamespace("default").createOrReplace();
			} else {
				System.out.println("CRD not found.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

//		try (final KubernetesClient client = new KubernetesClientBuilder().build()) {
//
//			String namespace = "default";
//
//			List<CustomResourceDefinition> crdlist = client.apiextensions().v1().customResourceDefinitions().list()
//					.getItems();
//			// Filter the CRD list using Java streams to find the CRD by name
//			CustomResourceDefinition desiredCRD = crdlist.stream()
//					.filter(crd -> "healthchecks.webapp.udaykk.me".equals(crd.getMetadata().getName())).findFirst()
//					.orElse(null);
//			client.apiextensions().v1beta1().customResourceDefinitions().resource(desiredCRD).create();
////            log("Created Custom Resource Definition");
//
////			client.apiextensions().v1().customResourceDefinitions().resource(desiredCRD).create();
//
//            CustomResourceDefinitionContext healthcheckCrdContext = new CustomResourceDefinitionContext.Builder()
//			        .withName("healthchecks.webapp.udaykk.me")  // CRD name
//			        .withGroup("webapp.udaykk.me")  // CRD group
//			        .withScope("Namespaced")
//			        .withVersion("v1")
//			        .withPlural("healthchecks")  // CRD plural name
//			        .build();
//            client.genericKubernetesResources(healthcheckCrdContext).inNamespace(namespace).resource(desiredCRD).create();
//
//            // Check if the desired CRD is found
//            if (desiredCRD != null) {
//            	String apiVersion = desiredCRD.getSpec().getGroup() + "/" + desiredCRD.getSpec().getVersions().get(0).getName();
//                String plural = desiredCRD.getSpec().getNames().getPlural();
//                Resource<Object> customResource = client.resources(apiVersion, plural, Object.class);
//
//                // Create a custom resource object with required fields
//                Map<String, Object> customResourceData = new HashMap<>();
//                // Add fields according to the custom resource's schema
//                customResourceData.put("apiVersion", apiVersion);
//                customResourceData.put("kind", desiredCRD.getSpec().getNames().getKind());
//                // Add other fields as needed in the custom resource specification
//
//                // Create the custom resource in the Kubernetes cluster
//                customResource.inNamespace("default").createOrReplace("example-custom-resource", customResourceData);
//            } else {
//                System.out.println("Desired CRD not found.");
//            }
//			client.apiextensions().v1().customResourceDefinitions().resource(animalCrd).create();
////			
////
////			
//////
////			// Load YAML file into String
////			ClassLoader classLoader = getClass().getClassLoader();
////
////			InputStream inputStream = classLoader.getResourceAsStream("crd.yaml");
////
////			String yamlString = new String(inputStream.readAllBytes());
////			
////			// Parse YAML into GenericKubernetesResource
////			ObjectMapper yamlReader = new ObjectMapper(new YAMLFactory());
////			GenericKubernetesResource resource = yamlReader.readValue(yamlString, GenericKubernetesResource.class);
//
////			String crBasicString = loadTemplateFromFile("/crd.yaml");
////			String crdWithDynamicValue = crBasicString.replace("${IMAGE_NAME}", "dsvhbdsv");
////			
////			ObjectMapper yamlReader = new ObjectMapper(new YAMLFactory());
////			Map<String, Object> crdObj = yamlReader.readValue(crdYamlString, Map.class);
//
////			GenericKubernetesResource cr1 = client.genericKubernetesResources(animalCrdContext)
////					.load(CustomResourceService.class.getResourceAsStream("/crd.yml")).get();
////			client.genericKubernetesResources(animalCrdContext).inNamespace(namespace).resource(cr1).create();
//
//			// Creating from Raw JSON String
////			String crBasicString = "{" + "  \"apiVersion\": \"jungle.example.com/v1\"," + "  \"kind\": \"Animal\","
////					+ "  \"metadata\": {" + "    \"name\": \"mongoose\"," + "    \"namespace\": \"default\"" + "  },"
////					+ "  \"spec\": {" + "    \"image\": \"my-silly-mongoose-image\"" + "  }" + "}";
////			GenericKubernetesResource cr3 = Serialization.jsonMapper().readValue(crdWithDynamicValue,
////					GenericKubernetesResource.class);
//
////			client.genericKubernetesResources(healthcheckCrdContext).inNamespace(namespace).resource(resource).createOrReplace();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
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
