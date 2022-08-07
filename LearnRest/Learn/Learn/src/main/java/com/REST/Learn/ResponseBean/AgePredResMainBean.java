/**
 * 
 */
package com.REST.Learn.ResponseBean;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * @author 91735
 *
 */

public class AgePredResMainBean  implements Serializable {
	
	
	
	public AgePredResBean objAgePredResBean;

	public AgePredResBean getObjAgePredResBean() {
		return objAgePredResBean;
	}

	public void setObjAgePredResBean(AgePredResBean objAgePredResBean) {
		this.objAgePredResBean = objAgePredResBean;
	}
	
	
	

}
