/*
 * fraud-detection-app - fraud detection app
 * Copyright © 2024 Yiting Qiang (qiangyt@wxcount.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package qiangyt.fraud_detection.framework.misc;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import java.util.jar.Manifest;
import java.util.regex.Pattern;

public class ManifestFile {

    public static final Pattern ClassPathJarNamePattern =
            Pattern.compile("([a-zA-Z0-9\\-.]+?)-([0-9]+(?:\\.[0-9]+)*).jar");

    public static Map<String, String> parseClassPath() {
        try (var ins =
                ManifestFile.class
                        .getClassLoader()
                        .getParent()
                        .getResourceAsStream("META-INF/MANIFEST.MF")) {
            if (ins != null) {
                var mf = new Manifest(ins);
                var attrs = mf.getMainAttributes();
                var cp = attrs.getValue("Class-Path");
                if (cp != null) {
                    cp = cp.replace("\n", "");
                    var matcher = ClassPathJarNamePattern.matcher(cp);

                    var r = new TreeMap<String, String>();
                    while (matcher.find()) {
                        r.put(matcher.group(1), matcher.group(2));
                    }
                    return r;
                }
            }
            return Collections.emptyMap();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
