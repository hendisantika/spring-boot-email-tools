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

package open.springboot.mail.service.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FAILED_DEPENDENCY, reason = "Something went wrong when processing the email template")
// 424
public class CannotSendEmailException extends Exception {

    private static final long serialVersionUID = -4063622108151903645L;

    public CannotSendEmailException() {
    }

    public CannotSendEmailException(final String message) {
        super(message);
    }

    public CannotSendEmailException(final Throwable cause) {
        super(cause);
    }

    public CannotSendEmailException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public CannotSendEmailException(final String message, final Throwable cause,
                                    final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
