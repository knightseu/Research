/**
 * 
 */
package rtgrid.resource;

/**
 * IResource.java
 * Package:  rtgrid.resource
 * @author Xuan, 2006-10-17 
 */
public interface IResource {
	/**
	 * 
	 * @return the type of the resource, such as "cluster, link..."
	 */
	public String getType();
	
	/**
	 * Visitor Pattern
	 * @param visitor 
	 */
	public void accept(IResourceVisitor visitor);

}
