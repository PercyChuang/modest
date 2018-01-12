package com.modest.core.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

/**
 * 资源获取
 * @author 庄濮向 Edmond Chuang
 */
public class ResourceUtils extends org.springframework.util.ResourceUtils {

    private static final String CLASSPATH_FILE_PREFIX = "classpathfile:";

    public static Resource[] getResources(String locationPattern) throws IOException {
        Assert.notNull(locationPattern, "Resource location must not be null");
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(
                ClassUtils.getDefaultClassLoader());
        if (locationPattern.startsWith(CLASSPATH_FILE_PREFIX)) {
            String path = locationPattern.substring(CLASSPATH_FILE_PREFIX.length());
            URL url = ClassUtils.getDefaultClassLoader().getResource("");
            locationPattern = FILE_URL_PREFIX + url.getPath() + path;
        }
        return resolver.getResources(locationPattern);
    }

    public static FileSystemResource getFileSystemResource(final String locationPattern) throws IOException {
        return new FileSystemResource(getFile(locationPattern));
    }

    @Deprecated
    public static File getFileInJBoss6(final String resourceLocation) throws FileNotFoundException {
        Assert.notNull(resourceLocation, "Resource location must not be null");
        if (resourceLocation.startsWith(CLASSPATH_URL_PREFIX)) {
            String path = resourceLocation.substring("classpath:".length());
            String description = "class path resource [" + path + "]";
            URL url = ClassUtils.getDefaultClassLoader().getResource(path);
            if (url == null) {
                throw new FileNotFoundException(description + " cannot be resolved to absolute file path "
                        + "because it does not reside in the file system");
            }
            return getFile(url, description);
        }
        try {
            return getFile(new URL(resourceLocation), "URL");
        } catch (MalformedURLException ex) {
            return new File(resourceLocation);
        }
    }

    public static File getFile(final URL resourceUrl, final String description) throws FileNotFoundException {
        Assert.notNull(resourceUrl, "Resource URL must not be null");
        String protocol = resourceUrl.getProtocol();
        if (!URL_PROTOCOL_FILE.equals(protocol) && !URL_PROTOCOL_VFSFILE.equals(protocol)
                && !URL_PROTOCOL_VFSZIP.equals(protocol)) {
            throw new FileNotFoundException(description + " cannot be resolved to absolute file path "
                    + "because it does not reside in the file system: " + resourceUrl);
        }
        try {
            File file = null;
            if (URL_PROTOCOL_VFSFILE.equals(protocol) || URL_PROTOCOL_VFSZIP.equals(protocol)) {
                file = new File(resourceUrl.getFile());
            } else {
                file = new File(org.springframework.util.ResourceUtils.toURI(resourceUrl).getSchemeSpecificPart());
            }
            return file;
        } catch (URISyntaxException ex) {
            return new File(resourceUrl.getFile());
        }
    }

    public static InputStream getResourceAsStream(final String filename) throws IOException {
        Assert.hasText(filename, "Filename must not be null");
        if (filename.startsWith(CLASSPATH_FILE_PREFIX) || filename.startsWith(CLASSPATH_URL_PREFIX)) {
            Resource[] resources = getResources(filename);
            return resources[0].getInputStream();
        }
        String name = filename;
        if (!filename.startsWith("/")) {
            name = "/" + filename;
        }
        String serverHome = System.getProperty("server.home");
        if (StringUtils.isNotBlank(serverHome)) {
            try {
                Resource[] resources = getResources("file:///" + serverHome + "/conf" + name);
                return resources[0].getInputStream();
            } catch (IOException e) {
            }
        }
        try {
            Resource[] resources = getResources("classpath:conf" + name);
            return resources[0].getInputStream();
        } catch (IOException e) {
        }
        return ClassUtils.getDefaultClassLoader().getResourceAsStream(filename);
    }
}
