/*
 * Copyright (C) 2013 Atol Conseils et Développements.
 * http://www.atolcd.com/
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.atolcd.alfresco.repo.jscript;

import org.alfresco.repo.jscript.BaseScopableProcessorExtension;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.atolcd.alfresco.AtolVolumetryEntry;
import com.atolcd.alfresco.web.scripts.shareStats.InsertAuditPost;

public class ShareStats extends BaseScopableProcessorExtension implements InitializingBean {
	// Logger
	private static final Log logger = LogFactory.getLog(ShareStats.class);

	private InsertAuditPost wsInsertAudits;

	public void setWsInsertAudits(InsertAuditPost wsInsertAudits) {
		this.wsInsertAudits = wsInsertAudits;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(wsInsertAudits);
	}

	public boolean insertVolumetry(String siteId, long siteSize, int folderCount, int fileCount, long atTime) {
		boolean success = true;
		try {
			AtolVolumetryEntry atolVolumetryEntry = new AtolVolumetryEntry(siteId, siteSize, folderCount, fileCount, atTime);
			this.wsInsertAudits.insertVolumetry(atolVolumetryEntry);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage(), e);
			}
			success = false;
		}
		return success;
	}
}