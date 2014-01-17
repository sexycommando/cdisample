package cdisample.entity;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name="DOCUMENTS")
public class Document implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	private String createdby;
	private String text;
	private String title;
    public Document() {}

	public String getId() {
		return this.id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCreatedby() {
		return this.createdby;
	}
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}
	public String getText() {
		return this.text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getTitle() {
		return this.title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
}