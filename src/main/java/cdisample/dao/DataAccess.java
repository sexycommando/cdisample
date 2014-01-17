package cdisample.dao;

public interface DataAccess<T, V> {
	public T load(V id);
	public void save(T object);
	public void delete(V object);
	public Class<T> getDataType();
}
