/*
 * @(#)Cacheable.java	1.2 01/02/22
 *
 * Project Name Project
 *
 * Author: Govind Thirumalai 
 */



package core.util;


/**
 * << CLASS DESCRIPTION GOES HERE >>
 *
 * @version	1.0
 * @author	Govind Thirumalai
 * @since	1.0
 */

public interface Cacheable {

	public String getCacheName();
	public String getListCacheName();
	public Object getCacheKey();

}
