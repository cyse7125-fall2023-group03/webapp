package com.webapp.webapp.service;

import java.io.InputStream;
import java.util.*;
import java.nio.charset.StandardCharsets;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.webapp.webapp.model.HttpCheck;

import io.fabric8.kubernetes.api.model.GenericKubernetesResource;
import io.fabric8.kubernetes.api.model.GenericKubernetesResourceList;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientBuilder;
import io.fabric8.kubernetes.client.dsl.base.CustomResourceDefinitionContext;

@SuppressWarnings("deprecation")
@Service
public class CustomResourceService {
	private final String namespace = "default";

	public void applyCRDAndCreateCustomResource(HttpCheck httpCheck) {
		try (final KubernetesClient client = new KubernetesClientBuilder().build()) {
			// Load CRD as object from YAML
			CustomResourceDefinitionContext context = new CustomResourceDefinitionContext.Builder()
					.withName("healthchecks.webapp.udaykk.me") // CRD name
					.withGroup("webapp.udaykk.me") // CRD group
					.withScope("Namespaced").withVersion("v1").withPlural("healthchecks") // CRD plural name
					.build();

			InputStream inputStream = ResourceLoader.class.getClassLoader().getResourceAsStream("cr.yml");

			String yamlString = new String(inputStream.readAllBytes());

			final String uuid = httpCheck.getId().toString().replace("-", "");
			System.out.println("uuid = " + uuid);

			Map<String, String> variableMap = new HashMap<>();
			String string = httpCheck.getId();
			variableMap.put("metaname", "hcs" + uuid);
			variableMap.put("ID", httpCheck.getId());
			variableMap.put("NAME", httpCheck.getName());
			variableMap.put("URI", httpCheck.getUri());
			variableMap.put("FOO", "bar");
			variableMap.put("IS_PAUSED", String.valueOf(httpCheck.isIs_paused()));
			variableMap.put("NUM_RETRIES", String.valueOf(httpCheck.getNum_retries()));
			variableMap.put("UPTIME_SLA", String.valueOf(httpCheck.getUptime_sla()));
			variableMap.put("RESPONSE_TIME_SLA", String.valueOf(httpCheck.getResponse_time_sla()));
			variableMap.put("USE_SSL", String.valueOf(httpCheck.isUse_ssl()));
			variableMap.put("RESPONSE_STATUS_CODE", String.valueOf(httpCheck.getResponse_status_code()));
			variableMap.put("CHECK_INTERVAL", String.valueOf(httpCheck.getCheck_interval_in_seconds()));
			variableMap.put("CHECK_CREATED", httpCheck.getCheck_created().toString());
			variableMap.put("CHECK_UPDATED", "" + httpCheck.getCheck_updated().toString());

			String substitutedYaml = substituteVariables(yamlString, variableMap);
			System.out.println(substitutedYaml);

			// Parse YAML into GenericKubernetesResource
			ObjectMapper yamlReader = new ObjectMapper(new YAMLFactory());
			GenericKubernetesResource resource = yamlReader.readValue(substitutedYaml, GenericKubernetesResource.class);
//			GenericKubernetesResource cr = client.genericKubernetesResources(context)
//					.load(CustomResourceService.class.getResourceAsStream("/cr.yml")).get();

			client.genericKubernetesResources(context).inNamespace("default").resource(resource).create();

//			GenericKubernetesResourceList cronTabs = client.genericKubernetesResources(context).inNamespace("default").list();
//			cronTabs.getItems().stream().map(GenericKubernetesResource::getMetadata).map(ObjectMeta::getName).forEach(logger::info);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String substituteVariables(String yamlString, Map<String, String> variableMap) {
		for (Map.Entry<String, String> variable : variableMap.entrySet()) {
			yamlString = yamlString.replaceAll("\\$\\{" + variable.getKey() + "}", variable.getValue());
		}
		return yamlString;
	}

//	private String loadTemplateFromFile(String filePath) throws Exception {
//		ClassPathResource resource = new ClassPathResource(filePath);
//		return StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
//	}

	public void getAllResources() {
		try (final KubernetesClient client = new KubernetesClientBuilder().build()) {
			CustomResourceDefinitionContext context = new CustomResourceDefinitionContext.Builder()
					.withName("healthchecks.webapp.udaykk.me") // CRD name
					.withGroup("webapp.udaykk.me") // CRD group
					.withScope("Namespaced").withVersion("v1").withPlural("healthchecks") // CRD plural name
					.build();
			GenericKubernetesResourceList crlist = client.genericKubernetesResources(context).inNamespace(namespace)
					.list();
			for (GenericKubernetesResource cr : crlist.getItems()) {
				System.out.println(cr.getMetadata().getName());

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateCustomResource(HttpCheck httpCheck, String id) {
		try (final KubernetesClient client = new KubernetesClientBuilder().build()) {
			// Load CRD as object from YAML
			CustomResourceDefinitionContext context = new CustomResourceDefinitionContext.Builder()
					.withName("healthchecks.webapp.udaykk.me") // CRD name
					.withGroup("webapp.udaykk.me") // CRD group
					.withScope("Namespaced").withVersion("v1").withPlural("healthchecks") // CRD plural name
					.build();

			String metadatnameString = "hcs" + id.replace("-", "");
			GenericKubernetesResource gkcr = client.genericKubernetesResources(context).inNamespace(namespace)
					.withName(metadatnameString).get();

			Map<String, Object> variableMap = new HashMap<>();
			String string = httpCheck.getId();
			variableMap.put("id", httpCheck.getId());
			variableMap.put("name", httpCheck.getName());
			variableMap.put("uri", httpCheck.getUri());
			variableMap.put("foo", "bar");
			variableMap.put("is_paused", httpCheck.isIs_paused());
			variableMap.put("num_retries", httpCheck.getNum_retries());
			variableMap.put("uptime_sla", httpCheck.getUptime_sla());
			variableMap.put("response_time_sla", httpCheck.getResponse_time_sla());
			variableMap.put("use_ssl", httpCheck.isUse_ssl());
			variableMap.put("response_status_code", httpCheck.getResponse_status_code());
			variableMap.put("check_interval_in_seconds", httpCheck.getCheck_interval_in_seconds());
			variableMap.put("check_created", httpCheck.getCheck_created().toString());
			variableMap.put("check_updated", "" + httpCheck.getCheck_updated().toString());

			Map<String, Object> additionalProperties = gkcr.getAdditionalProperties();
			if (additionalProperties == null) {
				additionalProperties = new HashMap<>();
			}
			additionalProperties.put("spec", variableMap);
			gkcr.setAdditionalProperties(additionalProperties);

			client.genericKubernetesResources(context).inNamespace(namespace).resource(gkcr).replace();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void deleteCr(String id) {
		try (final KubernetesClient client = new KubernetesClientBuilder().build()) {
			// Load CRD as object from YAML
			CustomResourceDefinitionContext context = new CustomResourceDefinitionContext.Builder()
					.withName("healthchecks.webapp.udaykk.me") // CRD name
					.withGroup("webapp.udaykk.me") // CRD group
					.withScope("Namespaced").withVersion("v1").withPlural("healthchecks") // CRD plural name
					.build();

			String metadatnameString = "hcs" + id.replace("-", "");
			client.genericKubernetesResources(context).inNamespace(namespace).withName(metadatnameString).delete();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
