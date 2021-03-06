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
package fr.paris.lutece.plugins.dila.business.fichelocale.dao.impl;

import fr.paris.lutece.plugins.dila.business.fichelocale.dao.ILocalDAO;
import fr.paris.lutece.plugins.dila.business.fichelocale.dto.LocalDTO;
import fr.paris.lutece.plugins.dila.business.fichelocale.dto.LocalTypeDTO;
import fr.paris.lutece.plugins.dila.service.DilaLocalTypeEnum;
import fr.paris.lutece.plugins.dila.service.DilaPlugin;
import fr.paris.lutece.plugins.dila.utils.DilaUtils;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.util.sql.DAOUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Implementation of {@link ILocalDAO}
 */
public class LocalDAO implements ILocalDAO, Serializable
{
    /** Serial ID */
    private static final long serialVersionUID = -6005693978102619298L;
    private static final String SQL_QUERY_NEW_PK = "SELECT max(id) FROM dila_local";
    private static final String SQL_QUERY_FIND_LOCAL_BY_ID_AND_AUDIENCE_AND_TYPE = "SELECT title , author, path, type_id FROM dila_local "
            + "WHERE id = ? AND audience_id = ? AND type_id = ?";
    private static final String SQL_QUERY_FIND_BY_ID_AND_TYPE_AND_AUDIENCE = "SELECT title FROM dila_local "
            + "WHERE id = ? AND type_id = ? AND audience_id = ?";
    private static final String SQL_QUERY_SELECT_ALL = " SELECT id, title, author, path, xml, audience_id, type_id, date_creation  "
            + "FROM  dila_local ORDER by id";
    private static final String SQL_QUERY_SELECT_ALL_BY_AUDIENCE_ID = " SELECT id, title, author, path, xml, audience_id, type_id, date_creation  "
            + "FROM  dila_local WHERE audience_id = ? ORDER by id";
    private static final String SQL_QUERY_INSERT = " INSERT INTO dila_local ( id , title , author, path, xml, audience_id, type_id ) "
            + " VALUES ( ?, ? ,?, ?, ?, ?, ? )";
    private static final String SQL_QUERY_DELETE = " DELETE FROM dila_local WHERE id = ?";
    private static final String SQL_QUERY_UPDATE = " UPDATE dila_local "
            + "SET title = ?, author = ?, path = ?, xml = ?, audience_id = ? WHERE id = ?";
    private static final String SQL_QUERY_FIND_XML_BY_ID = "SELECT xml FROM dila_local WHERE id = ?";
    private static final String SQL_QUERY_FIND_LAST_CARDS_BY_AUDIENCE = "SELECT id, title FROM dila_local "
            + "WHERE audience_id = ? AND type_id = '1' ORDER BY date_creation DESC";

    /**
     * Generates a new primary key
     * @return The new primary key
     */
    private Long newPrimaryKey( )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_NEW_PK, PluginService.getPlugin( DilaPlugin.PLUGIN_NAME ) );
        daoUtil.executeQuery( );

        Long nKey = 1L;

        if ( daoUtil.next( ) )
        {
            nKey = daoUtil.getLong( 1 ) + 1L;
        }

        daoUtil.free( );

        return nKey;
    }

    @Override
    public LocalDTO findLocalByIdAndTypeAndAudience( Long id, Long type, Long idAudience )
    {
        StringBuilder sbQuery = new StringBuilder( SQL_QUERY_FIND_LOCAL_BY_ID_AND_AUDIENCE_AND_TYPE );

        DAOUtil daoUtil = new DAOUtil( sbQuery.toString( ), PluginService.getPlugin( DilaPlugin.PLUGIN_NAME ) );
        daoUtil.setLong( 1, id );
        daoUtil.setLong( 2, idAudience );
        daoUtil.setLong( 3, type );

        daoUtil.executeQuery( );

        LocalDTO local = null;

        if ( daoUtil.next( ) )
        {
            local = new LocalDTO( );
            local.setId( id );
            local.setTitle( daoUtil.getString( 1 ) );
            local.setAuthor( daoUtil.getString( 2 ) );
            local.setBreadCrumb( daoUtil.getString( 3 ) );

            DilaLocalTypeEnum typeEnum = DilaLocalTypeEnum.fromId( daoUtil.getLong( 4 ) );

            LocalTypeDTO localType = new LocalTypeDTO( );
            localType.setId( typeEnum.getId( ) );
            localType.setLabel( typeEnum.getLabel( ) );
            local.setType( localType );
        }

        daoUtil.free( );

        return local;
    }

    @Override
    public String findTitleByIdAndTypeAndAudience( Long id, Long type, Long idAudience )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_FIND_BY_ID_AND_TYPE_AND_AUDIENCE,
                PluginService.getPlugin( DilaPlugin.PLUGIN_NAME ) );
        daoUtil.setLong( 1, id );
        daoUtil.setLong( 2, type );
        daoUtil.setLong( 3, idAudience );

        daoUtil.executeQuery( );

        String title = null;

        if ( daoUtil.next( ) )
        {
            title = daoUtil.getString( 1 );
        }

        daoUtil.free( );

        return title;
    }

    @Override
    public List<LocalDTO> findAll( )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_ALL, PluginService.getPlugin( DilaPlugin.PLUGIN_NAME ) );
        daoUtil.executeQuery( );

        List<LocalDTO> results = new ArrayList<LocalDTO>( );

        while ( daoUtil.next( ) )
        {
            LocalDTO local = new LocalDTO( );

            local.setId( daoUtil.getLong( 1 ) );
            local.setTitle( daoUtil.getString( 2 ) );
            local.setAuthor( daoUtil.getString( 3 ) );
            local.setBreadCrumb( daoUtil.getString( 4 ) );
            local.setXml( daoUtil.getString( 5 ) );
            local.setIdAudience( daoUtil.getLong( 6 ) );
            local.setDisplayPath( DilaUtils.convertBreadcrumbIntoDisplay( local.getBreadCrumb( ), local.getIdAudience( ) ) );

            DilaLocalTypeEnum typeEnum = DilaLocalTypeEnum.fromId( daoUtil.getLong( 7 ) );

            LocalTypeDTO type = new LocalTypeDTO( );
            type.setId( typeEnum.getId( ) );
            type.setLabel( typeEnum.getLabel( ) );
            local.setType( type );

            local.setCreationDate( daoUtil.getDate( 8 ) );

            results.add( local );
        }

        daoUtil.free( );

        return results;
    }

    @Override
    public Long insert( LocalDTO local, boolean addIdToBreadcrumb )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_INSERT, PluginService.getPlugin( DilaPlugin.PLUGIN_NAME ) );

        local.setId( newPrimaryKey( ) );

        daoUtil.setLong( 1, local.getId( ) );
        daoUtil.setString( 2, local.getTitle( ) );
        daoUtil.setString( 3, local.getAuthor( ) );

        if ( addIdToBreadcrumb )
        {
            daoUtil.setString( 4, local.getBreadCrumb( ) + ";" + local.getId( ) );
        }
        else
        {
            daoUtil.setString( 4, local.getBreadCrumb( ) );
        }

        daoUtil.setString(
                5,
                local.getXml( ).replaceAll( "<%ID%>", "" + local.getId( ) )
                        .replaceAll( "<%NIVEAU_ID%>", "" + local.getId( ) ) );
        daoUtil.setLong( 6, local.getIdAudience( ) );
        daoUtil.setLong( 7, local.getType( ).getId( ) );

        daoUtil.executeUpdate( );
        daoUtil.free( );

        return local.getId( );
    }

    @Override
    public void delete( String idLocal )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_DELETE, PluginService.getPlugin( DilaPlugin.PLUGIN_NAME ) );

        daoUtil.setString( 1, idLocal );

        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    @Override
    public void store( LocalDTO localDTO, boolean addIdToBreadcrumb )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_UPDATE, PluginService.getPlugin( DilaPlugin.PLUGIN_NAME ) );

        daoUtil.setString( 1, localDTO.getTitle( ) );
        daoUtil.setString( 2, localDTO.getAuthor( ) );

        if ( addIdToBreadcrumb )
        {
            daoUtil.setString( 3, localDTO.getBreadCrumb( ) + ";" + localDTO.getId( ) );
        }
        else
        {
            daoUtil.setString( 3, localDTO.getBreadCrumb( ) );
        }

        daoUtil.setString(
                4,
                localDTO.getXml( ).replaceAll( "<%ID%>", "" + localDTO.getId( ) )
                        .replaceAll( "<%NIVEAU_ID%>", "" + localDTO.getId( ) ) );
        daoUtil.setLong( 5, localDTO.getIdAudience( ) );
        daoUtil.setLong( 6, localDTO.getId( ) );

        daoUtil.executeUpdate( );
        daoUtil.free( );
    }

    @Override
    public String findXmlById( Long id )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_FIND_XML_BY_ID, PluginService.getPlugin( DilaPlugin.PLUGIN_NAME ) );
        daoUtil.setLong( 1, id );

        daoUtil.executeQuery( );

        String xml = null;

        if ( daoUtil.next( ) )
        {
            xml = daoUtil.getString( 1 );
        }

        daoUtil.free( );

        return xml;
    }

    @Override
    public List<LocalDTO> findLastCardsByAudience( Long audienceId )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_FIND_LAST_CARDS_BY_AUDIENCE,
                PluginService.getPlugin( DilaPlugin.PLUGIN_NAME ) );
        daoUtil.setLong( 1, audienceId );

        List<LocalDTO> result = new ArrayList<LocalDTO>( );

        daoUtil.executeQuery( );

        while ( daoUtil.next( ) )
        {
            LocalDTO dto = new LocalDTO( );

            dto.setId( daoUtil.getLong( 1 ) );
            dto.setTitle( daoUtil.getString( 2 ) );

            result.add( dto );
        }

        daoUtil.free( );

        return result;
    }

    @Override
    public List<LocalDTO> findAllByAudienceId( Long audienceId )
    {
        DAOUtil daoUtil = new DAOUtil( SQL_QUERY_SELECT_ALL_BY_AUDIENCE_ID,
                PluginService.getPlugin( DilaPlugin.PLUGIN_NAME ) );
        daoUtil.setLong( 1, audienceId );
        daoUtil.executeQuery( );

        List<LocalDTO> results = new ArrayList<LocalDTO>( );

        while ( daoUtil.next( ) )
        {
            LocalDTO local = new LocalDTO( );

            local.setId( daoUtil.getLong( 1 ) );
            local.setTitle( daoUtil.getString( 2 ) );
            local.setAuthor( daoUtil.getString( 3 ) );
            local.setBreadCrumb( daoUtil.getString( 4 ) );
            local.setXml( daoUtil.getString( 5 ) );
            local.setIdAudience( daoUtil.getLong( 6 ) );
            local.setDisplayPath( DilaUtils.convertBreadcrumbIntoDisplay( local.getBreadCrumb( ), local.getIdAudience( ) ) );

            DilaLocalTypeEnum typeEnum = DilaLocalTypeEnum.fromId( daoUtil.getLong( 7 ) );

            LocalTypeDTO type = new LocalTypeDTO( );
            type.setId( typeEnum.getId( ) );
            type.setLabel( typeEnum.getLabel( ) );
            local.setType( type );

            local.setCreationDate( daoUtil.getDate( 8 ) );

            results.add( local );
        }

        daoUtil.free( );

        return results;
    }
}
