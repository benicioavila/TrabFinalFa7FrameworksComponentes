package br.com.caelum.livraria.bean;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class TemaBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String tema = "vader";

	public String getTema() {
		return tema;
	}

	public void setTema(String tema) {
		this.tema = tema;
	}

	public String[] getTemas() {
		return new String[] { "afterdark", "afternoon", "afterwork", "aristo",
				"black-tie", "blitzer", "bluesky", "bootstrap", "casablanca",
				"cupertino", "cruze", "dark-hive", "delta", "dot-luv",
				"eggplant", "excite-bike", "flick", "glass-x", "home",
				"hot-sneaks", "humanity", "le-frog", "midnight", "mint-choc",
				"overcast", "pepper-grinder", "redmond", "rocket", "sam",
				"smoothness", "south-street", "start", "sunny", "swanky-purse",
				"trontastic", "ui-darkness", "ui-lightness", "vader" };
	}

}
