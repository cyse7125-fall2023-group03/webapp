package com.webapp.webapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "http_check")
public class HttpCheck {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	@Column(name = "name")
	private String name;

	@Column(name = "uri")
	private String uri;

	@Column(name = "is_paused")
	private boolean is_paused;

	@Column(name = "num_retries")
	private int num_retries;
	
	@Column(name = "uptime_sla")
	private int uptime_sla;
	
	@Column(name = "response_time_sla")
	private int response_time_sla;
	
	@Column(name = "use_ssl")
	private boolean use_ssl;
	
	@Column(name = "response_status_code")
	private int response_status_code;
	
	@Column(name = "check_interval_in_seconds")
	private int check_interval_in_seconds;
	
	@Column(name = "check_created")
	private String check_created;
	
	@Column(name = "check_updated")
	private String check_updated;
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public boolean isIs_paused() {
		return is_paused;
	}

	public void setIs_paused(boolean is_paused) {
		this.is_paused = is_paused;
	}

	public int getNum_retries() {
		return num_retries;
	}

	public void setNum_retries(int num_retries) {
		this.num_retries = num_retries;
	}

	public int getUptime_sla() {
		return uptime_sla;
	}

	public void setUptime_sla(int uptime_sla) {
		this.uptime_sla = uptime_sla;
	}

	public int getResponse_time_sla() {
		return response_time_sla;
	}

	public void setResponse_time_sla(int response_time_sla) {
		this.response_time_sla = response_time_sla;
	}

	public boolean isUse_ssl() {
		return use_ssl;
	}

	public void setUse_ssl(boolean use_ssl) {
		this.use_ssl = use_ssl;
	}

	public int getResponse_status_code() {
		return response_status_code;
	}

	public void setResponse_status_code(int response_status_code) {
		this.response_status_code = response_status_code;
	}

	public int getCheck_interval_in_seconds() {
		return check_interval_in_seconds;
	}

	public void setCheck_interval_in_seconds(int check_interval_in_seconds) {
		this.check_interval_in_seconds = check_interval_in_seconds;
	}

	public String getCheck_created() {
		return check_created;
	}

	public void setCheck_created(String check_created) {
		this.check_created = check_created;
	}

	public String getCheck_updated() {
		return check_updated;
	}

	public void setCheck_updated(String check_updated) {
		this.check_updated = check_updated;
	}

}
