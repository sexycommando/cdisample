package cdisample.dao;

import cdisample.entity.Document;
import cdisample.qualifier.Documents;
import cdisample.qualifier.Secure;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@Stateless
@Secure
public class DocumentDataAccess implements DataAccess<Document, String> {

    @Inject
    @Documents
    EntityManager em;

    @Override
    public Document load(String id) {
        return em.find(Document.class, id);
    }

    @Override
    public void save(Document document) {
        Document currentDocument = em.find(Document.class, document.getId());
        if (currentDocument == null) {
            em.persist(document);
        } else {
            em.merge(document);
        }
    }

    @Override
    public void delete(String id) {
        Document currentDocument = em.find(Document.class, id);
        if (currentDocument != null) {
            em.remove(currentDocument);
        }
    }

    @Override
    public Class<Document> getDataType() {
        return Document.class;
    }
}
