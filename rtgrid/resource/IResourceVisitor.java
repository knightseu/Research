/**
 * 
 */
package rtgrid.resource;

/**
 * IResourceVisitor.java
 * Package:  rtgrid.resource
 * @author Xuan, 2006-10-17 
 */
public interface IResourceVisitor {
	
	
	public void visitCluster(Cluster cluster);

    public void visitMachine(PNode pnode);
    
}
