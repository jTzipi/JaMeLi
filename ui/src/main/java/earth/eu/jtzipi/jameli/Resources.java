/*
 * Copyright (c) 2020 Tim Langhammer
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package earth.eu.jtzipi.jameli;

import earth.eu.jtzipi.modules.io.IOUtils;
import earth.eu.jtzipi.modules.io.image.ImageDimension;
import earth.eu.jtzipi.modules.utils.Utils;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.CompletableFuture;

/**
 * Load and store Resources.
 * <p>
 * Methods to load font's and image's.
 *
 * @author jTzipi
 */
public final class Resources {


    private static final Logger LOG = LoggerFactory.getLogger( "earth.eu.jtzipi.jameli.Resources" );


    private static final Path PATH_TO_RES = IOUtils.getProgramDir().resolve( "ui/src/main/resources" );


    private static final double FONT_SIZE_MIN = 10D;        // min size
    private static final double FONT_SIZE_MAX = 190D;       // max size
    private static final String FONT_DEFAULT = "Consolas";  //
    //
    //
    //
    private static final Path PATH_TO_FONT = PATH_TO_RES.resolve( "font" );
    private static final Path PATH_TO_IMG = PATH_TO_RES.resolve( "img" );
    private static final Path PATH_TO_PROPERTIES = PATH_TO_RES.resolve( "properties" );
    private static final Path PATH_TO_PROP_UI = PATH_TO_PROPERTIES.resolve( "ui.properties" );

    private static final Properties PROP_UI = new Properties(); // UI Properties
    private static final Map<String, Path> FONT_MAP = new HashMap<>();  // Font path list
    private static final Map<ImageDimension, Map<String, Image>> IMG_MAP = new HashMap<>(); // Image per dim

    private Resources() {
        throw new AssertionError( -1L );
    }

    /**
     * Check path and resources.
     */
    static void init() throws IOException {

        boolean resOk = Files.isDirectory( PATH_TO_RES );
        boolean fontOk = Files.isDirectory( PATH_TO_FONT );
        boolean propsOk = Files.isDirectory( PATH_TO_PROPERTIES );

        LOG.info( "Path to resource '" + PATH_TO_RES + "' -> " + resOk );
        LOG.info( "Path to font '" + PATH_TO_FONT + "' -> " + fontOk );
        LOG.info( "Path to properties '" + PATH_TO_PROPERTIES + "' -> " + propsOk );
        LOG.info( "" );
        IOUtils.loadProperties( PATH_TO_PROP_UI, PROP_UI );
        LOG.info( "UI Properties load -> Done" );
    }

    /**
     * Load all Resources.
     * void
     *
     * @return CompletableFuture which loads all resources
     */
    static CompletableFuture<Void> loadResources() {

        CompletableFuture<Void> cfFL = loadFonts().thenRun( () -> LOG.info( "Font loaded." ) );

        CompletableFuture<Void> cfIM = loadImages().thenRun( () -> LOG.info( "Images loaded!" ) );

        return CompletableFuture.allOf( cfFL, cfIM );
    }

    /**
     * Return image for name and size.
     *
     * @param imgDescriptor  image name
     * @param imageDimension image dimension
     * @return image if found else {@code null}
     * @throws NullPointerException if {@code imgDescriptor}|{@code imageDimension}
     */
    public Image getImage( String imgDescriptor, ImageDimension imageDimension ) {
        Objects.requireNonNull( imgDescriptor );
        Objects.requireNonNull( imageDimension );
        if ( !IMG_MAP.containsKey( imageDimension ) ) {
            LOG.warn( "ImageDimension '" + imageDimension + "' not found" );
            // TODO return special img
            return null;
        }

        if ( !IMG_MAP.get( imageDimension ).containsKey( imgDescriptor ) ) {
            LOG.warn( "Image for '" + imgDescriptor + "' not loaded!" );
            return null;
        }

        return IMG_MAP.get( imageDimension ).get( imgDescriptor );
    }

    /**
     * Return font for name and size.
     *
     * @param fontDescriptor ui name
     * @param size           size
     * @return font if found and readable or default [
     * @throws NullPointerException if {@code fontDescriptor}
     */
    public static Font getFont( String fontDescriptor, double size ) {
        Objects.requireNonNull( fontDescriptor );
        // font size < min use min
        if ( FONT_SIZE_MIN > size || size > FONT_SIZE_MAX ) {
            LOG.warn( "size  " + size + " illegal" );
            size = Utils.clamp( size, FONT_SIZE_MIN, FONT_SIZE_MAX );
        }

        String fontName = PROP_UI.getProperty( fontDescriptor );
        // not found?
        if ( null == fontName ) {
            LOG.warn( "Font '" + fontDescriptor + "' property not stored" );
            return defaultFont( size );
        }
        // no such font?
        if ( !FONT_MAP.containsKey( fontName ) ) {
            LOG.warn( "Font '" + fontName + "' store in properties but not loaded" );
            return defaultFont( size );
        }

        Path fp = FONT_MAP.get( fontName );
        Font font;
        try {
            font = IOUtils.loadFont( fp, size );

        } catch ( IOException e ) {
            LOG.error( "Error loading font!", e );
            font = defaultFont( size );
        }
        LOG.info( "Load '" + font + "' " );

        return font;
    }


    private static Font defaultFont( final double size ) {

        return Font.font( FONT_DEFAULT, Utils.clamp( size, FONT_SIZE_MIN, FONT_SIZE_MAX ) );
    }

    private static CompletableFuture<Void> loadFonts() {

        return CompletableFuture.supplyAsync( () -> {
            //
            for ( Path fp : IOUtils.lookupDir( PATH_TO_FONT, IOUtils.PATH_ACCEPT_FONT ) ) {

                String fn = fp.getFileName().toString();
                LOG.info( "Load font '" + fn + "'" );

                FONT_MAP.put( fn, fp );
            }

            return null;
        } );
    }

    private static CompletableFuture<Void> loadImages() {

        return CompletableFuture.supplyAsync( () -> {
            // dirs contained below img
            List<Path> dirImg = IOUtils.lookupDir( PATH_TO_IMG, IOUtils.PATH_ACCEPT_DIR );

            // loolup all dirs
            for ( Path dir : dirImg ) {
                LOG.info( "Enter dir '" + dir );
                String dimStr = dir.getFileName().toString();
                int dim = Integer.parseInt( dimStr );
                ImageDimension imgDim = ImageDimension.of( dim, dim );

                IMG_MAP.put( imgDim, new HashMap<>() );
                // find and store all images
                for ( Path imgPath : IOUtils.lookupDir( dir, IOUtils.PATH_ACCEPT_IMAGE ) ) {

                    LOG.info( "Try to load image '" + imgPath );
                    try {
                        Image img = IOUtils.loadImage( imgPath );
                        String imgDesc = IOUtils.getPathPrefix( imgPath ).toLowerCase();
                        IMG_MAP.get( imgDim ).put( imgDesc, img );
                    } catch ( IOException ioE ) {
                        LOG.error( "Error reading Image '" + imgPath + "'", ioE );
                    }

                }
            }
            return null;
        } );

    }
}
