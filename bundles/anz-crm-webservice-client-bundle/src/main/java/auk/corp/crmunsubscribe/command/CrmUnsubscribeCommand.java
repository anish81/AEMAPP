/**
 * Author           :Anish Sreedharan
 * 
 * Date Created     : 15/11/2016
 *
 * Source File Name : CrmUnsubscribeCommand.java
 * Package Name     : auk.corp.crmunsubscribe.command
 * 
 * © Allianz Insurance plc 2014. All Rights Reserved.
 *
 */
package auk.corp.crmunsubscribe.command;

import auk.corp.crmunsubscribe.service.CrmUnsubscribeService;
import auk.corp.crmunsubscribe.utilities.util.UnSubscribeTO;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import javax.servlet.ServletException;
import javax.jcr.RepositoryException;
import javax.jcr.PathNotFoundException;
import javax.jcr.ValueFormatException;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import java.io.PrintWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;
import java.util.HashMap;
import javax.jcr.Node;
import org.apache.sling.api.resource.ResourceResolver;
import java.util.Set;
import javax.jcr.PathNotFoundException;
import javax.jcr.ValueFormatException;
import javax.jcr.RepositoryException;
import java.sql.Timestamp;
import java.util.Date;
import java.text.DateFormat;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.net.URL;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import javax.xml.namespace.QName;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import java.util.ArrayList;
import org.apache.commons.lang.StringUtils;
import javax.jcr.Session;
import org.apache.sling.jcr.api.SlingRepository;
import javax.jcr.RepositoryException;
import org.apache.felix.scr.annotations.Reference;
import java.net.URLDecoder;



/**
 * 
 * @author i83421
 * @date 15/11/2016 Command class for CRM Webservice Interaction.
 */
@Component(immediate = true)
@Service
@Properties({
		@Property(name = "sling.servlet.paths", value = "/bin/service/crmunsubscribe"),
		@Property(name = "sling.servlet.methods", value = "GET") })
public class CrmUnsubscribeCommand extends SlingAllMethodsServlet {
	private static final Logger LOG = LoggerFactory
			.getLogger(CrmUnsubscribeCommand.class);
	@Reference
	private SlingRepository repository;
	private static final String SPLIT=":";

	/**
	 * @author i83421
	 * @method doGet The doGet method is overriddent to achive the business
	 *         functionality.
	 */
	public void doGet(SlingHttpServletRequest request,
			SlingHttpServletResponse response) throws IOException {
		LOG.info("Getting in CrmUnsubscribe Command");

		CrmUnsubscribeService crmUnsubscribeService = new CrmUnsubscribeService();
		UnSubscribeTO unSubscribeTO = prepareRequest(request);
		String redirectPage = crmUnsubscribeService
				.unSubscribeEmail(unSubscribeTO);
		// redirect to the confirmation or error page
		response.sendRedirect(redirectPage);

	}

	private UnSubscribeTO prepareRequest(SlingHttpServletRequest request)throws IOException {
		// Pid should remain encoded
		String pid = request.getParameter("pId");
		// Email should be decoded
		String email = request.getParameter("email");
		// Mid
		String mid = request.getParameter("mId");
		// Flag
		String flag = request.getParameter("flag");
		
		LOG.info("Getting in CrmUnsubscribe **********");
		// Site would be anibase/ petplan/tracer
		String site = request.getServerName();
		LOG.info("Request Parameters " + "email" + email + "mid" + mid + "flag"
				+ flag + "site" + site);
		UnSubscribeTO unSubscribeTO = new UnSubscribeTO();
		unSubscribeTO.setPid(pid);
		 email = URLDecoder.decode(email, "UTF-8");
		unSubscribeTO.setEmail(email);
		unSubscribeTO.setMid(mid);
		unSubscribeTO.setFlag(flag);
		unSubscribeTO.setSite(site);
		String config[] = StringUtils.split(getConfigurationProperty(request,"passwordConfiguration"),SPLIT);
		unSubscribeTO.setUsername(config[0]);
		unSubscribeTO.setPassword(config[1]);
		String endPointURL=getConfigurationProperty(request,"EndPointURL");
		unSubscribeTO.setEndPointURL(endPointURL);
		return unSubscribeTO;
	}

	

	public String getConfigurationProperty(SlingHttpServletRequest request,String resourceName)
			throws IOException {
		String referencePropertyValue = "";
		try {
			
			String config_page = "/content/crmsite/en_gb/application/configuration/service_configuration/jcr:content/styles";
			LOG.info("Reference Configuration page :: " + config_page);
			Map<String, String> referencePropertyMap = new HashMap<String, String>();
			ResourceResolver resourceResolver = request.getResourceResolver();
			Resource resource = resourceResolver.getResource(config_page);
			Node referencePropertyConfigurationNode = resource
					.adaptTo(Node.class);
			NodeIterator referencePropertyConfigurationNodeIterator = referencePropertyConfigurationNode
					.getNodes();
			while (referencePropertyConfigurationNodeIterator.hasNext()) {
				Node referencePropertyConfigurationChildNode = (Node) referencePropertyConfigurationNodeIterator
						.next();
				if (referencePropertyConfigurationChildNode
						.hasProperty("serviceId")
						&& referencePropertyConfigurationChildNode
								.hasProperty("serviceUrl")) {
					referencePropertyMap.put(
							referencePropertyConfigurationChildNode
									.getProperty("serviceId").getString(),
							referencePropertyConfigurationChildNode
									.getProperty("serviceUrl").getString());
					LOG.info("Inside getReferenceProperty method of ReferenceClass class"
							+ ":: serviceId ::"
							+ referencePropertyConfigurationChildNode
									.getProperty("serviceId").getString()
							+ " :: serviceUrl ::"
							+ referencePropertyConfigurationChildNode
									.getProperty("serviceUrl").getString());
				}
			}
			if (StringUtils.isNotBlank(referencePropertyMap
					.get(resourceName))) {
				referencePropertyValue = referencePropertyMap
						.get(resourceName);
				LOG.info("referencePropertyValue:" + referencePropertyValue);
			}
			return referencePropertyValue;
		} catch (Exception e) {
			return null;
		}
	}

}
