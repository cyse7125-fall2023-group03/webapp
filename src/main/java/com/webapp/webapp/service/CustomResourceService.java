package com.webapp.webapp.service;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import io.fabric8.kubernetes.api.model.GenericKubernetesResource;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientBuilder;
import io.fabric8.kubernetes.client.dsl.base.CustomResourceDefinitionContext;

@SuppressWarnings("deprecation")
@Service
public class CustomResourceService {

	public void applyCRDAndCreateCustomResource() {
		try (final KubernetesClient client = new KubernetesClientBuilder().build()) {
			// Load CRD as object from YAML
			CustomResourceDefinitionContext context = new CustomResourceDefinitionContext.Builder()
					.withName("healthchecks.webapp.udaykk.me") // CRD name
					.withGroup("webapp.udaykk.me") // CRD group
					.withScope("Namespaced").withVersion("v1").withPlural("healthchecks") // CRD plural name
					.build();

			InputStream inputStream = ResourceLoader.class.getClassLoader().getResourceAsStream("crd.yml");

			String yamlString = new String(inputStream.readAllBytes());

			// Parse YAML into GenericKubernetesResource
			ObjectMapper yamlReader = new ObjectMapper(new YAMLFactory());
			GenericKubernetesResource resource = yamlReader.readValue(yamlString, GenericKubernetesResource.class);
			GenericKubernetesResource cr = client.genericKubernetesResources(context)
					.load(CustomResourceService.class.getResourceAsStream("/crd.yml")).get();

			client.genericKubernetesResources(context).inNamespace("default").resource(resource).createOrReplace();

//			GenericKubernetesResourceList cronTabs = client.genericKubernetesResources(context).inNamespace("default").list();
//			cronTabs.getItems().stream().map(GenericKubernetesResource::getMetadata).map(ObjectMeta::getName).forEach(logger::info);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String loadTemplateFromFile(String filePath) throws Exception {
		ClassPathResource resource = new ClassPathResource(filePath);
		return StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
	}
}
