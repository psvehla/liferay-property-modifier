/**
 * Liferay Property Modifier
 *
 * Copyright (c) 2014, Red Barn Consulting, All rights reserved.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library.
 */

package au.com.redbarn.liferay.property.modifier;

import java.util.List;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.SimpleAction;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.PortalPreferences;
import com.liferay.portal.service.PortalPreferencesLocalServiceUtil;

/**
 * Property modifier action.
 * 
 * @author peter
 *
 */
public class PropertyModifierStartupAction extends SimpleAction {
	
	private static final Log log = LogFactoryUtil.getLog(PropertyModifierStartupAction.class);

	/* (non-Javadoc)
	 * @see com.liferay.portal.kernel.events.SimpleAction#run(java.lang.String[])
	 */
	@Override
	public void run(String[] arg0) throws ActionException {
		try {
			clearPreferences();
		} catch (SystemException e) {
			log.error("Something has gone terribly wrong.", e);
		}
	}
	
	/**
	 * Clears the desired portal preferences (hard coded: ldap.auth.enabled, ldap.auth.required).
	 * 
	 * @throws SystemException
	 */
	private void clearPreferences() throws SystemException {
		
		List<PortalPreferences> portalPreferencesList = PortalPreferencesLocalServiceUtil.getPortalPreferenceses(QueryUtil.ALL_POS, QueryUtil.ALL_POS);
		
		for (PortalPreferences portalPreferences : portalPreferencesList) {
			log.info(portalPreferences.getPreferences());
			
			String prefs = portalPreferences.getPreferences();
			String newPrefs = prefs;
			
			final String LDAP_AUTH_ENABLED = "ldap.auth.enabled";
			
			if (prefs.contains(LDAP_AUTH_ENABLED)) {
				newPrefs = clearPreference(LDAP_AUTH_ENABLED, portalPreferences, prefs);
			}
			
			final String LDAP_AUTH_REQUIRED = "ldap.auth.required";
			
			if (prefs.contains(LDAP_AUTH_REQUIRED)) {
				newPrefs = clearPreference(LDAP_AUTH_REQUIRED, portalPreferences, newPrefs);
			}
			
			PortalPreferencesLocalServiceUtil.updatePreferences(portalPreferences.getOwnerId(), portalPreferences.getOwnerType(), newPrefs);
		}
	}
	
	/**
	 * Manipulates the {@link PortalPreferences} XML fragment to remove the preference in question.
	 * This is just a string hack for the purposes of this example.
	 * 
	 * @param pref The preference to remove.
	 * @param portalPreferences The {@link PortalPreferences} preference group being worked on. 
	 * @param prefs The preference group XML string as it currently stands.
	 * 
	 * @return The preference group XML string with the preference removed.
	 * 
	 * @throws SystemException
	 */
	private String clearPreference(String pref, PortalPreferences portalPreferences, String prefs) throws SystemException {
		String newPrefs = prefs.substring(0, prefs.indexOf(pref) - 18) + prefs.substring(prefs.indexOf(pref) + pref.length() + 39, prefs.length());
		log.info(newPrefs);
		return newPrefs;
	}
}
