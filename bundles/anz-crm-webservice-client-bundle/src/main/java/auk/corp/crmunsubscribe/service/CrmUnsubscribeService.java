/**
 * Author           :Anish Sreedharan
 * 
 * Date Created     :  15/11/2016
 *
 * Source File Name :	CrmUnsubscribeService.java
 *  * Package Name     : auk.corp.CrmUnsubscribeService.service
 * 
 * © Allianz Insurance plc 2014. All Rights Reserved.
 *
 */
package auk.corp.crmunsubscribe.service;

import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.HandlerResolver;
import javax.xml.ws.handler.PortInfo;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.ws.Holder;
import javax.xml.ws.Service;

import java.net.URL;

import javax.xml.namespace.QName;

import java.net.MalformedURLException;
import java.security.KeyManagementException;
import java.security.KeyStore.Builder;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Date;
import java.text.DateFormat;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import auk.corp.crmunsubscribe.utilities.alz.aemsoapinterface.AEMSOAPInterfaceMethodsSoap;
import auk.corp.crmunsubscribe.utilities.alz.aemsoapinterface.AlzAEMSOAPInterface;
import auk.corp.crmunsubscribe.utilities.util.UnSubscribeTO;
import auk.corp.crmunsubscribe.xtk.session.Element;
import auk.corp.crmunsubscribe.xtk.session.Logon;
import auk.corp.crmunsubscribe.xtk.session.ObjectFactory;
import auk.corp.crmunsubscribe.xtk.session.SessionMethodsSoap;
import auk.corp.crmunsubscribe.xtk.session.XtkSession;

import javax.jcr.PathNotFoundException;
import javax.jcr.ValueFormatException;
import javax.jcr.RepositoryException;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.JAXBContext;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.StringReader;

import javax.xml.ws.BindingProvider;

import org.apache.commons.lang.StringUtils;

import auk.corp.crmunsubscribe.util.SystemConstants;

/**
 * 
 * @author i83421
 * 
 */

public class CrmUnsubscribeService {

	/**
	 * @author i83421
	 * @method unSubscribeCampaign
	 * @input UnSubscribeTO unSubscribeTO
	 * @output pcmsData String
	 */

	private static final Logger LOG = LoggerFactory
			.getLogger(CrmUnsubscribeService.class);

	public String unSubscribeEmail(UnSubscribeTO unSubscribeTO) {
		// Getting the context classloader object
		ClassLoader tccl = Thread.currentThread().getContextClassLoader();
		boolean isError = false;
		try {
			LOG.info("Getting in Unsubscribe Service");
			Thread.currentThread().setContextClassLoader(
					this.getClass().getClassLoader());
			// getting security token from logon webservice
			if (validateRequestAttributes(unSubscribeTO)) {
				String securityToken = createSecurityToken(unSubscribeTO);
				if (null != securityToken || !"".equals(securityToken))
					unSubscribeCampaignEmail(securityToken, unSubscribeTO);
				else
					isError = true;
			} else
				isError = true;
		} catch (Exception e) {
			StringWriter sw = new StringWriter(1024);
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			pw.flush();
			LOG.info(sw.toString());
			return createErrorResponse(unSubscribeTO);
		} finally {
			Thread.currentThread().setContextClassLoader(tccl);
		}
		if (isError)
			return createErrorResponse(unSubscribeTO);
		else
			return createConfirmationResponse(unSubscribeTO);
	}

	private boolean validateRequestAttributes(UnSubscribeTO unSubscribeTO) {

		if ("".equals(unSubscribeTO.getMid())
				|| !StringUtils.isNumeric(unSubscribeTO.getMid())) 
			return false;
		if ("".equals(unSubscribeTO.getPid())
				|| StringUtils.isEmpty(unSubscribeTO.getPid())) 
			return false;
		
		if ("".equals(unSubscribeTO.getFlag())
				|| StringUtils.isEmpty(unSubscribeTO.getFlag()))
			return false;
		 if("".equals(unSubscribeTO.getEmail())||StringUtils.isEmpty(unSubscribeTO.getEmail())||!EmailValidator(unSubscribeTO.getEmail()))
			return false;
		else 
			return true;
		
	}

	private String createSecurityToken(UnSubscribeTO unSubscribeTO) {

		
		URL 	serviceURL			= CrmUnsubscribeService.class.getClassLoader().getResource("WSDL/logon.schemawsdl.wsdl");
		QName 	serviceQName		= new QName("urn:xtk:session", "XtkSession");
		
		
		Service service	= javax.xml.ws.Service.create(serviceURL, serviceQName);
		SessionMethodsSoap sessionMethodsSoap			 	= service.getPort(SessionMethodsSoap.class);
		BindingProvider bp = (BindingProvider)sessionMethodsSoap;
		bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, unSubscribeTO.getEndPointURL());
		LOG.info("bp " + "BindingProvider="
				+ bp);
		Holder<java.lang.String> ptrSessionToken = new Holder<java.lang.String>();
		Holder<Element> ptrSessionInfo = new Holder<Element>();
		Holder<java.lang.String> ptrSecurityToken = new Holder<java.lang.String>();
		Element element = new Element();
		// get the login details from content
		sessionMethodsSoap.logon("", unSubscribeTO.getUsername(),
				unSubscribeTO.getPassword(), element, ptrSessionToken,
				ptrSessionInfo, ptrSecurityToken);
		LOG.info("Security Token Value from Login " + "ptrSessionToken="
				+ ptrSessionToken.value);
		return ptrSessionToken.value;
	}

	private void unSubscribeCampaignEmail(String strSessionToken,
			UnSubscribeTO unSubscribeTO) {
		LOG.info("Getting in  unSubscribeCampaignEmail");
		URL serviceURL = CrmUnsubscribeService.class.getClassLoader()
				.getResource("WSDL/unsub.schemawsdl.wsdl");
		QName serviceQName = new QName("urn:alz:AEMSOAPInterface",
				"AlzAEMSOAPInterface");
		Service service = javax.xml.ws.Service.create(serviceURL, serviceQName);

		AEMSOAPInterfaceMethodsSoap aEMSOAPInterfaceMethodsSoap = service
				.getPort(AEMSOAPInterfaceMethodsSoap.class);
		aEMSOAPInterfaceMethodsSoap.alzUnsub(strSessionToken,
				unSubscribeTO.getPid(),
				Integer.parseInt(unSubscribeTO.getMid()),
				unSubscribeTO.getFlag());
		LOG.info("Getting out  unSubscribeCampaignEmail");
	}

	private String createErrorResponse(UnSubscribeTO unSubscribeTO) {
		LOG.info("Getting in  createErrorResponse");

		if (unSubscribeTO.getSite().contains(SystemConstants.PETPLAN)) {
			return SystemConstants.CONFORMATIONURL + "="
					+ SystemConstants.PETPLAN;
		} else if (unSubscribeTO.getSite().contains(SystemConstants.TRACER)) {
			return SystemConstants.CONFORMATIONURL + "="
					+ SystemConstants.TRACER;
		} else if (unSubscribeTO.getSite().contains(SystemConstants.ANIBASE)) {
			return SystemConstants.CONFORMATIONURL + "="
					+ SystemConstants.ANIBASE;
		} else {
			return SystemConstants.EMPTY;
		}

	}

	private String createConfirmationResponse(UnSubscribeTO unSubscribeTO) {
		LOG.info("Getting in  createConfirmationResponse");
		if (unSubscribeTO.getSite().contains(SystemConstants.PETPLAN)) {
			return SystemConstants.CONFORMATIONURL + "="
					+ SystemConstants.PETPLAN + "&email="
					+ unSubscribeTO.getEmail();
		} else if (unSubscribeTO.getSite().contains(SystemConstants.TRACER)) {
			return SystemConstants.CONFORMATIONURL + "="
					+ SystemConstants.TRACER + "&email="
					+ unSubscribeTO.getEmail();
		} else if (unSubscribeTO.getSite().contains(SystemConstants.ANIBASE)) {
			return SystemConstants.CONFORMATIONURL + "="
					+ SystemConstants.ANIBASE + "&email="
					+ unSubscribeTO.getEmail();
		} else {
			return SystemConstants.EMPTY;
		}

	}

	private boolean EmailValidator(String email) {
		Pattern pattern = Pattern.compile(SystemConstants.EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(email);
		LOG.info("Email id " + matcher.matches());
		return matcher.matches();
	}
}