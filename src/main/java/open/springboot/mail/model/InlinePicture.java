/*
 * Copyright 2012-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package open.springboot.mail.model;

import java.io.File;

public interface InlinePicture {

    ImageType getImageType();

    File getFile();

    /**
     * Return the name used in the template with the path to the resource.
     * <p/>
     * The name has to be replaced by a proper cid.
     *
     * @return the name used in the template, included any path to folders.
     */
    String getTemplateName();

}
