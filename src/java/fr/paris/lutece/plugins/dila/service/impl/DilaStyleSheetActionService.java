/*
 * Copyright (c) 2002-2014, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.dila.service.impl;

import fr.paris.lutece.plugins.dila.business.stylesheet.DilaStyleSheetAction;
import fr.paris.lutece.plugins.dila.business.stylesheet.dao.IDilaStyleSheetActionDAO;
import fr.paris.lutece.plugins.dila.service.IDilaStyleSheetActionService;
import fr.paris.lutece.portal.service.i18n.I18nService;

import java.io.Serializable;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Named;


/**
 * This class provides instances management method find for DilaStyleSheetAction
 * objects
 */
public class DilaStyleSheetActionService implements IDilaStyleSheetActionService, Serializable
{
    /** Serial ID */
    private static final long serialVersionUID = 4573909087219976434L;
    @Inject
    @Named( "dilaStyleSheetActionDAO" )
    private IDilaStyleSheetActionDAO _dilaStyleSheetActionDAO;

    /**
     * Load the list of actions
     *
     * @param locale the locale
     * @return The Collection of actions
     */
    public List<DilaStyleSheetAction> selectActions( Locale locale )
    {
        List<DilaStyleSheetAction> listDilaStyleSheetActions = _dilaStyleSheetActionDAO.selectActions(  );

        return (List<DilaStyleSheetAction>) I18nService.localizeCollection( listDilaStyleSheetActions, locale );
    }
}
