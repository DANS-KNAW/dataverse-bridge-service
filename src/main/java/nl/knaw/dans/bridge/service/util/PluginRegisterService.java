/**
 * Copyright (C) 2018 DANS - Data Archiving and Networked Services (info@dans.knaw.nl)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package nl.knaw.dans.bridge.service.util;

import nl.knaw.dans.bridge.plugin.lib.common.DarPluginConf;
import nl.knaw.dans.bridge.plugin.lib.common.XslSource;
import nl.knaw.dans.bridge.plugin.lib.common.XslTransformer;
import nl.knaw.dans.bridge.plugin.lib.exception.BridgeException;
import nl.knaw.dans.bridge.service.api.config.PluginStorageProperties;
import org.apache.abdera.i18n.iri.IRI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
public class PluginRegisterService {
    private static final Logger LOG = LoggerFactory.getLogger(PluginRegisterService.class);
    private final Path pluginStorageLocation;

    @Autowired
    private PluginRegisterService(PluginStorageProperties pluginStorageProperties) throws BridgeException {
        this.pluginStorageLocation = Paths.get(pluginStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.pluginStorageLocation);
        } catch (Exception ex) {
            throw new BridgeException("Could not create the directory where the uploaded files will be stored.", ex, this.getClass());
        }
    }

    public DarPluginConf storePlugin(InputStream zipPluginInputStream, String uploadedFilename, String pluginName) throws BridgeException {

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = zipPluginInputStream.read(buffer)) > -1) {
                baos.write(buffer, 0, len);
            }
            baos.flush();

            //We need to copy inputStream since every process the inputstream will be closed
            InputStream inputStreamForCheckingZipfile = new ByteArrayInputStream(baos.toByteArray());
            InputStream inputStreamForCheckingPluginStructure = new ByteArrayInputStream(baos.toByteArray());
            InputStream inputStreamForStoringPluginZip = new ByteArrayInputStream(baos.toByteArray());

            if (!isZipFile(inputStreamForCheckingZipfile))
                throw new BridgeException(uploadedFilename + " is not zip file.", this.getClass());

            if (!validZipPluginStructure(inputStreamForCheckingPluginStructure, pluginName))
                throw new BridgeException(uploadedFilename + " is not valid.", this.getClass());

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.pluginStorageLocation.resolve(pluginName.toLowerCase() + ".zip");
            Files.copy(inputStreamForStoringPluginZip, targetLocation, StandardCopyOption.REPLACE_EXISTING);

            unZipIt(targetLocation.toString(), pluginName.toLowerCase());
            return attachDarPlugin(targetLocation.toFile().getParentFile());
        } catch (IOException ex) {
            throw new BridgeException("Could not store file " + pluginName + ".", ex, this.getClass());
        }
    }

    private boolean isZipFile(InputStream zipPluginInputStream) throws BridgeException {
        int test;
        try {
            DataInputStream in = new DataInputStream(new BufferedInputStream(zipPluginInputStream));
            test = in.readInt();
            in.close();
        } catch (IOException e) {
            throw new BridgeException("isZipFile, IOException", e, this.getClass());
        }

        return (test == 0x504b0304);//check whether the uploaded file zip file or not.
    }

    private void unZipIt(String zipFile, String outputFolder) throws BridgeException {

        byte[] buffer = new byte[1024];

        try {
            //create output directory if not exists
            File folder = new File("plugins");
            if (!folder.exists()) {
                folder.mkdir();
            }

            //get the zip file content
            ZipInputStream zis =
                    new ZipInputStream(new FileInputStream(zipFile));
            //get the zipped file list entry
            ZipEntry ze = zis.getNextEntry();

            while (ze != null) {

                String fileName = ze.getName();
                File newFile = new File(outputFolder + File.separator + fileName);

                LOG.info("file unzip : " + newFile.getAbsoluteFile());

                //create all non exists folders
                //else you will hit FileNotFoundException for compressed folder
                new File(newFile.getParent()).mkdirs();

                FileOutputStream fos = new FileOutputStream(newFile);

                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }

                fos.close();
                ze = zis.getNextEntry();
            }

            zis.closeEntry();
            zis.close();

            LOG.info("Unzipping is done");

        } catch (IOException ex) {
            LOG.error("Error during unzipping - unZipIt, msg: {}", ex.getMessage());
            throw new BridgeException("Error during unzipping - unZipIt.", ex, this.getClass());
        }
    }

    public static DarPluginConf attachDarPlugin(File darPluginDir) throws FileNotFoundException, MalformedURLException {

        DarPluginConf darPluginConf = new DarPluginConf();
        File darPluginFiles[] = darPluginDir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                LOG.info("#      {} ", name);
                return name.equals(dir.getName() + ".json");
            }
        });

        if (darPluginFiles == null) {
            LOG.error("The plugin has no proper plugin structure");
            return null;
        }
        if (darPluginFiles.length != 1) {
            LOG.error("NO " + darPluginDir.getName() + ".json file");
            return null;
        }


        File darJsonFile = darPluginFiles[0];
        FileInputStream fis = new FileInputStream(darJsonFile);
        JsonReader reader = Json.createReader(fis);
        JsonObject darPluginConfJson = reader.readObject();
        reader.close();
        LOG.info("#      DAR name   : " + darPluginConfJson.getString("dar-name"));
        LOG.info("#      DAR IRI   : " + darPluginConfJson.getString("dar-iri"));
        LOG.info("#      action-class-name    : " + darPluginConfJson.getString("action-class-name"));
        LOG.info("#      action-class-url: " + darPluginConfJson.getString("action-class-url"));
        darPluginConf.setDarName(darPluginConfJson.getString("dar-name"));
        darPluginConf.setDarIri(new IRI(darPluginConfJson.getString("dar-iri")));

        darPluginConf.setActionClassName(darPluginConfJson.getString("action-class-name"));
        //Eko says: We can also check the lib dir, but for now just use the dar conf.
        PluginClassLoader pc = new PluginClassLoader(new URL("file:///" + darPluginDir.getAbsolutePath() + "/" + darPluginConfJson.getString("action-class-url")));
        URLClassLoader actionClassLoader = new URLClassLoader(new URL[]{new URL("file:///" + darPluginDir.getAbsolutePath() + "/" + darPluginConfJson.getString("action-class-url"))});
        darPluginConf.setActionClassLoader(pc);

        JsonArray xslJsonArray = darPluginConfJson.getJsonArray("xsl");
        if (xslJsonArray != null) {
            List<XslSource> xslSourceList = xslJsonArray.stream().map(jv -> new XslSource(((JsonObject)jv).getString("source")
                    , ((JsonObject)jv).getJsonArray("transformer").stream()
                    .map(k -> new XslTransformer(((JsonObject)k).getString("xsl-name"), createURL(((JsonObject)k).getString("xsl-url"))))
                    .collect(Collectors.toList()))).collect(Collectors.toList());
            darPluginConf.setXslSourceList(xslSourceList);
        }

        return darPluginConf;
    }
    private static URL createURL(String url) {
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            LOG.error("PluginRegisterService - createURL with url: {}", e.getMessage());
            return null;
        }
    }

    //Eko says: this is very simple check. Just for now.
    private boolean validZipPluginStructure(InputStream zipPluginInputStream, String darName) throws BridgeException {
        ZipInputStream zip = new ZipInputStream(zipPluginInputStream);
        ZipEntry entry;
        String pluginName = darName.toLowerCase();

        boolean pluginNameDir = false;
        boolean libDirExist = false;
        boolean xslDirExist = false;
        boolean jsonFileExist = false;

        try {
            while ((entry = zip.getNextEntry()) != null) {
                if (entry.isDirectory()) {
                    if (entry.getName().equals(pluginName + "/"))
                        pluginNameDir = true;

                    if (entry.getName().equals(pluginName + "/lib/"))
                        libDirExist = true;

                    if (entry.getName().equals(pluginName + "/xsl/"))
                        xslDirExist = true;
                } else {
                    if (entry.getName().equals(pluginName + "/" + pluginName + ".json"))
                        jsonFileExist = true;
                }
            }
        } catch (IOException e) {
            throw new BridgeException("hasValidZipPluginStructure, IOException", e, this.getClass());
        }

        return (pluginNameDir && libDirExist && xslDirExist && jsonFileExist);
    }
}