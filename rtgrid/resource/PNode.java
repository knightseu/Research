/**
 * 
 */
package rtgrid.resource;

import rtgrid.COMMON;

/**
 * PNode.java
 * Package:  rtgrid.resource
 * @author Xuan, 2006-10-17 
 * 
 * Processing Node
 */
public class PNode implements IResource {

	/* (non-Javadoc)
	 * @see rtgrid.resource.IResource#accept(rtgrid.resource.IResourceVisitor)
	 */
	public void accept(IResourceVisitor visitor) {
		

	}

	/* (non-Javadoc)
	 * @see rtgrid.resource.IResource#getType()
	 */
	public String getType() {
		return COMMON.RESOURCE_PNODE;
	}

}
