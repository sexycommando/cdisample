package cdisample.model;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Model;
import javax.inject.Inject;

import cdisample.dao.DataAccess;
import cdisample.entity.Document;
import cdisample.entity.User;
import cdisample.qualifier.LoggedIn;

@RequestScoped
@Model
public class DocumentEditor implements java.io.Serializable {

	@Inject
	private DataAccess<Document, String> docEditor;

	private String id;
	private String title;
	private String text;
	
	@Inject
	@LoggedIn
	private User currentUser;

	public void load() {
		Document document = docEditor.load(id);
		if (document == null) {
			document = new Document();
		}
		title = document.getTitle();
		text = document.getText();
		
	}

	public void save() {
		Document document = new Document();
		document.setId(id);
		document.setTitle(title);
		document.setText(text);
		document.setCreatedby(currentUser.getUsername());
		docEditor.save(document);
	}
	
	public void delete() {
		docEditor.delete(id);
		id = null;
		title = null;
		text = null;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
